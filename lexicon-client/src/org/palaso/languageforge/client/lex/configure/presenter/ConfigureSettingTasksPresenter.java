package org.palaso.languageforge.client.lex.configure.presenter;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

import org.palaso.languageforge.client.lex.common.CheckableItem;
import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.common.IPersistable;
import org.palaso.languageforge.client.lex.controls.ExtendedCheckBox;
import org.palaso.languageforge.client.lex.controls.ExtendedComboBox;
import org.palaso.languageforge.client.lex.controls.ExtendedTextBox;
import org.palaso.languageforge.client.lex.controls.FastTree;
import org.palaso.languageforge.client.lex.controls.FastTreeItem;
import org.palaso.languageforge.client.lex.controls.TextChangeEvent;
import org.palaso.languageforge.client.lex.controls.TextChangeEventHandler;
import org.palaso.languageforge.client.lex.controls.TreeItemSelectedHandler;
import org.palaso.languageforge.client.lex.model.CurrentEnvironmentDto;
import org.palaso.languageforge.client.lex.model.FieldSettings;
import org.palaso.languageforge.client.lex.model.UserDto;
import org.palaso.languageforge.client.lex.model.settings.tasks.SettingTasksDashboardSettings;
import org.palaso.languageforge.client.lex.model.settings.tasks.SettingTasksDto;
import org.palaso.languageforge.client.lex.model.settings.tasks.SettingTasksTaskElementDto;
import org.palaso.languageforge.client.lex.common.ActivityTimeRangeType;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.common.enums.DomainLanguagesType;
import org.palaso.languageforge.client.lex.common.enums.SettingTaskNameType;
import org.palaso.languageforge.client.lex.configure.ConfigureEventBus;
import org.palaso.languageforge.client.lex.configure.view.ConfigureSettingTasksView;
import org.palaso.languageforge.client.lex.main.service.ILexService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Presenter(view = ConfigureSettingTasksView.class)
public class ConfigureSettingTasksPresenter extends BasePresenter<ConfigureSettingTasksPresenter.IConfigureSettingTasksView, ConfigureEventBus>
    implements IPersistable {
    private static final String EXAMPLE = "Example";
    private static final String SENSE = "Sense";
    private static final String EXAMPLE_TRANSLATION = "Example Translation";
    private static final String EXAMPLE_SENTENCE = "Example Sentence";
    private static final String PART_OF_SPEECH = "Part Of Speech";
    private static final String DEFINITION_MEANING = "Definition (Meaning)";
    @Inject
    public ILexService lexService;
    private SettingTasksDto taskDto = null;
    private List<CheckableItem> taskListDs;

    public void bind() {
        view.getTasksTree().setItemSelectedCallbackHandler(new TreeItemSelectedHandler() {
                @Override
                public void onTreeItemSelected(FastTreeItem item) {
                    if (item.getData() != null) {
                        final SettingTasksTaskElementDto relatedData = (SettingTasksTaskElementDto) item.getData();

                        if (relatedData.getTaskName() == SettingTaskNameType.GATHERWORDSBYSEMANTICDOMAINS) {
                            view.setSubSettingSemanticDomainsPanelVisible(true);
                            view.setSubSettingDashboardPanelVisible(false);
                            refreshSemanticDomainSetting(relatedData);
                        } else if (relatedData.getTaskName() == SettingTaskNameType.DASHBOARD) {
                            view.setSubSettingSemanticDomainsPanelVisible(false);
                            view.setSubSettingDashboardPanelVisible(true);
                            refreshDashboardSetting(relatedData);
                        } else {
                            view.setSubSettingSemanticDomainsPanelVisible(false);
                            view.setSubSettingDashboardPanelVisible(false);
                        }
                    }
                }
            });
        view.getDomainLanguageListBox().addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent event) {
                    if (view.getTasksTree().getSelectedItem().getData() instanceof SettingTasksTaskElementDto) {
                        SettingTasksTaskElementDto relatedData = (SettingTasksTaskElementDto) view.getTasksTree()
                                                                                                  .getSelectedItem()
                                                                                                  .getData();

                        if (relatedData.getTaskName() == SettingTaskNameType.GATHERWORDSBYSEMANTICDOMAINS) {
                            relatedData.setSemanticDomainsQuestionFileName(Constants.SEMANTIC_DOMAIN_QUESTIONS_FILE_PREFIX +
                                view.getDomainLanguageListBox()
                                    .getItemText(view.getDomainLanguageListBox()
                                                     .getSelectedIndex()) +
                                ".xml");
                        }
                    }
                }
            });
        view.getTargerWordCountTextbox().addKeyPressHandler(new KeyPressHandler() {
                @Override
                public void onKeyPress(KeyPressEvent event) {
                    // number and Backspace only
                    if (!Character.isDigit(event.getCharCode()) &&
                            (event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE)) {
                        ((TextBox) event.getSource()).cancelKey();
                    }

                    // limited to 10000000
                    try {
                        if ((Integer.parseInt(
                                    ((TextBox) event.getSource()).getValue()) > 99999998) &&
                                (event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE)) {
                            ((TextBox) event.getSource()).cancelKey();
                            ((TextBox) event.getSource()).setText("99999999");
                        }
                    } catch (NumberFormatException e) {
                        ((TextBox) event.getSource()).setText("");
                    }
                }
            });

        view.getActivityTimeRangeListBox()
            .addItem(ActivityTimeRangeType.DAY_30.getValue(),
            String.valueOf(ActivityTimeRangeType.DAY_30.ordinal()));
        view.getActivityTimeRangeListBox()
            .addItem(ActivityTimeRangeType.DAY_90.getValue(),
            String.valueOf(ActivityTimeRangeType.DAY_90.ordinal()));
        view.getActivityTimeRangeListBox()
            .addItem(ActivityTimeRangeType.DAY_365.getValue(),
            String.valueOf(ActivityTimeRangeType.DAY_365.ordinal()));
        view.getActivityTimeRangeListBox()
            .addItem(ActivityTimeRangeType.DAY_ALL.getValue(),
            String.valueOf(ActivityTimeRangeType.DAY_ALL.ordinal()));

        view.getTargerWordCountTextbox().addTextChangeEventHandler(new TextChangeEventHandler() {
                @Override
                public void onTextChange(TextChangeEvent event) {
                    if (view.getTasksTree().getSelectedItem().getData() instanceof SettingTasksTaskElementDto) {
                        SettingTasksTaskElementDto relatedData = (SettingTasksTaskElementDto) view.getTasksTree()
                                                                                                  .getSelectedItem()
                                                                                                  .getData();

                        if (relatedData.getTaskName() == SettingTaskNameType.DASHBOARD) {
                            SettingTasksDashboardSettings dbSpeData = SettingTasksDashboardSettings.<SettingTasksDashboardSettings>decode(relatedData.getTaskSpecifiedData());
                            String wordCountString = view.getTargerWordCountTextbox()
                                                         .getText().trim();

                            try {
                                if (wordCountString.length() > 0) {
                                    dbSpeData.setTargetWordCount(Integer.parseInt(
                                            wordCountString));
                                } else {
                                    dbSpeData.setTargetWordCount(0);
                                }

                                relatedData.setTaskSpecifiedData(SettingTasksDashboardSettings.encode(
                                        dbSpeData));
                            } catch (NumberFormatException e) {
                                view.getTargerWordCountTextbox().setText("0");
                                eventBus.displayMessage(I18nConstants.STRINGS.ConfigureSettingTasksPresenter_value_is_not_a_validate_number_or_too_large());
                            }
                        }
                    }
                }
            });

        view.getActivityTimeRangeListBox().addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent event) {
                    if (view.getTasksTree().getSelectedItem().getData() instanceof SettingTasksTaskElementDto) {
                        SettingTasksTaskElementDto relatedData = (SettingTasksTaskElementDto) view.getTasksTree()
                                                                                                  .getSelectedItem()
                                                                                                  .getData();

                        if (relatedData.getTaskName() == SettingTaskNameType.DASHBOARD) {
                            SettingTasksDashboardSettings dbSpeData =  SettingTasksDashboardSettings.<SettingTasksDashboardSettings>decode(relatedData.getTaskSpecifiedData());

                            switch (Integer.parseInt(view.getActivityTimeRangeListBox()
                                                         .getValue(view.getActivityTimeRangeListBox()
                                                                       .getSelectedIndex()))) {
                            case 0:
                                dbSpeData.setActivityTimeRange(30);

                                break;

                            case 1:
                                dbSpeData.setActivityTimeRange(90);

                                break;

                            case 2:
                                dbSpeData.setActivityTimeRange(365);

                                break;

                            case 3:
                                dbSpeData.setActivityTimeRange(0);

                                break;

                            default:
                                dbSpeData.setActivityTimeRange(30);

                                break;
                            }

                            relatedData.setTaskSpecifiedData(SettingTasksDashboardSettings.encode(
                                    dbSpeData));
                        }
                    }
                }
            });
    }

    public void onAttachTasksView(SimplePanel simplePanel) {
        simplePanel.clear();
        simplePanel.add(view.getWidget());
        eventBus.addSubControl(simplePanel, this);
    }

    private void populateTreeFromDataSource() {
        FastTree tree = view.getTasksTree();
        tree.setSelectedItem(null);
        tree.clear();

        for (CheckableItem chkItem : taskListDs) {
            FastTreeItem topTreeItem = createTreeItem(chkItem);
            topTreeItem.setData(chkItem.getData());
            topTreeItem.setState(true);

            if (chkItem.isVisible()) {
                tree.addItem(topTreeItem);
            }
        }
    }

    private void refreshDashboardSetting(SettingTasksTaskElementDto relatedData) {
        SettingTasksDashboardSettings dbSpeData = SettingTasksDashboardSettings.<SettingTasksDashboardSettings>decode(relatedData.getTaskSpecifiedData());

        view.getTargerWordCountTextbox()
            .setText(String.valueOf(dbSpeData.getTargetWordCount()));

        switch (dbSpeData.getActivityTimeRange()) {
        case 0:
            view.getActivityTimeRangeListBox()
                .setSelectedIndex(ActivityTimeRangeType.DAY_ALL.ordinal());

            break;

        case 30:
            view.getActivityTimeRangeListBox()
                .setSelectedIndex(ActivityTimeRangeType.DAY_30.ordinal());

            break;

        case 90:
            view.getActivityTimeRangeListBox()
                .setSelectedIndex(ActivityTimeRangeType.DAY_90.ordinal());

            break;

        case 365:
            view.getActivityTimeRangeListBox()
                .setSelectedIndex(ActivityTimeRangeType.DAY_365.ordinal());

            break;

        default:
            view.getActivityTimeRangeListBox()
                .setSelectedIndex(ActivityTimeRangeType.DAY_30.ordinal());

            break;
        }
    }

    private void refreshSemanticDomainSetting(
        SettingTasksTaskElementDto relatedData) {
        FastTree tree = view.getSemanticDomainsSubSettingTree();
        tree.setSelectedItem(null);
        tree.clear();

        fillDomainLanguagesListListIntoListBox();

        CheckableItem senseItem = new CheckableItem(false);
        senseItem.setDisplayText(SENSE);

        CheckableItem meaningItem = new CheckableItem(true);
        CheckableItem posItem = new CheckableItem(true);

        CheckableItem exampleItem = new CheckableItem(false);
        exampleItem.setDisplayText(EXAMPLE);

        CheckableItem exampleSentenceItem = new CheckableItem(true);
        CheckableItem exampleTranslationItem = new CheckableItem(true);

        FastTreeItem topSenseTreeItem = createTreeItem(senseItem);
        FastTreeItem topExampleTreeItem = createTreeItem(exampleItem);

        meaningItem.setDisplayText(DEFINITION_MEANING);
        meaningItem.setData(relatedData);
        meaningItem.setChecked(relatedData.getShowMeaningField());

        FastTreeItem meaningTreeItem = createTreeItem(meaningItem);

        posItem.setDisplayText(PART_OF_SPEECH);
        posItem.setData(relatedData);
        posItem.setChecked(relatedData.getShowPOSField());

        FastTreeItem posTreeItem = createTreeItem(posItem);

        exampleSentenceItem.setDisplayText(EXAMPLE_SENTENCE);
        exampleSentenceItem.setData(relatedData);
        exampleSentenceItem.setChecked(relatedData.getShowExampleSentenceField());

        FastTreeItem exampleSentenceTreeItem = createTreeItem(exampleSentenceItem);

        exampleTranslationItem.setDisplayText(EXAMPLE_TRANSLATION);
        exampleTranslationItem.setData(relatedData);
        exampleTranslationItem.setChecked(relatedData.getShowExampleTranslationField());

        FastTreeItem exampleTranslationTreeItem = createTreeItem(exampleTranslationItem);

        topSenseTreeItem.addItem(meaningTreeItem);
        topSenseTreeItem.addItem(posTreeItem);

        topExampleTreeItem.addItem(exampleSentenceTreeItem);
        topExampleTreeItem.addItem(exampleTranslationTreeItem);

        topSenseTreeItem.setState(true);
        topExampleTreeItem.setState(true);

        tree.addItem(topSenseTreeItem);
        tree.addItem(topExampleTreeItem);

        String domainFileName = relatedData.getSemanticDomainsQuestionFileName()
                                           .replaceFirst(Constants.SEMANTIC_DOMAIN_QUESTIONS_FILE_PREFIX,
                "");
        domainFileName = domainFileName.replaceFirst(".xml", "").trim();
        domainFileName = domainFileName.replaceFirst("Ddp4Questions-", "").trim();
        view.getDomainLanguageListBox()
            .setSelectedIndex(DomainLanguagesType.valueOf(domainFileName)
                                                 .ordinal());
    }

    private FastTreeItem createTreeItem(CheckableItem item) {
        FastTreeItem topTreeItem = new FastTreeItem();
        topTreeItem.setData(item.getData());

        if (item.isVisible()) {
            if (item.isCheckable()) {
                ExtendedCheckBox chkBox = new ExtendedCheckBox(item.getDisplayText());

                if (item.getData() != null) {
                    final SettingTasksTaskElementDto relatedData = (SettingTasksTaskElementDto) item.getData();
                    chkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                            @Override
                            public void onValueChange(
                                ValueChangeEvent<Boolean> event) {
                                // the change is makes on DS directly, so easy for
                                // saving operation.
                                if (((ExtendedCheckBox) event.getSource()).getParent() == view.getTasksTree()) {
                                    relatedData.setVisible(event.getValue());
                                } else if (((ExtendedCheckBox) event.getSource()).getParent() == view.getSemanticDomainsSubSettingTree()) {
                                    ExtendedCheckBox checkBox = (ExtendedCheckBox) event.getSource();
                                    FieldSettings fieldSettingsSemanticDomain = FieldSettings.fromWindowForrGatherWordFromSemanticDomain();

                                    if (checkBox.getText()
                                                    .equalsIgnoreCase(DEFINITION_MEANING)) {
                                        relatedData.setShowMeaningField(event.getValue());
                                        fieldSettingsSemanticDomain.value(
                                            "Definition")
                                                                   .setEnabled(event.getValue());
                                    } else if (checkBox.getText()
                                                           .equalsIgnoreCase(PART_OF_SPEECH)) {
                                        relatedData.setShowPOSField(event.getValue());
                                        fieldSettingsSemanticDomain.value("POS")
                                                                   .setEnabled(event.getValue());
                                    } else if (checkBox.getText()
                                                           .equalsIgnoreCase(EXAMPLE_SENTENCE)) {
                                        relatedData.setShowExampleSentenceField(event.getValue());
                                        fieldSettingsSemanticDomain.value(
                                            "Example")
                                                                   .setEnabled(event.getValue());
                                    } else if (checkBox.getText()
                                                           .equalsIgnoreCase(EXAMPLE_TRANSLATION)) {
                                        relatedData.setShowExampleTranslationField(event.getValue());
                                        fieldSettingsSemanticDomain.value(
                                            "Translation")
                                                                   .setEnabled(event.getValue());
                                    }
                                }
                            }
                        });
                }

                chkBox.setValue(item.isChecked());

                if (!item.getToolTip().isEmpty()) {
                    chkBox.setTitle(item.getToolTip());
                }

                topTreeItem.setWidget(chkBox);
            } else {
                topTreeItem.setText(item.getDisplayText());
            }
        }

        if (item.getChilds().size() > 0) {
            for (CheckableItem chkItem : item.getChilds()) {
                topTreeItem.addItem(createTreeItem(chkItem));
            }
        }

        return topTreeItem;
    }

    private boolean isInIgnoreList(SettingTasksTaskElementDto taskElementDto) {
        switch (taskElementDto.getTaskName()) {
        case NOTESBROWSER:
        case ADVANCEDHISTORY:
            return true;

        case DASHBOARD:
            break;

        case ADDMISSINGINFO:

            if (taskElementDto.getField().equalsIgnoreCase("BaseForm")) {
                return true;
            }

            break;

        case GATHERWORDLIST:

            if (!taskElementDto.getWordListFileName().equals("SILCAWL") &&
                    taskElementDto.getLongLabel().isEmpty()) {
                return true;
            }

            break;

        default:
            break;
        }

        return false;
    }

    private String getDisplayName(SettingTasksTaskElementDto taskElementDto,
        boolean isRoot) {
        // do a remapping here
        if (isRoot) {
            if (taskElementDto.getTaskName() == SettingTaskNameType.GATHERWORDLIST) {
                return I18nConstants.STRINGS.ConfigureSettingTasksPresenter_Gather_Words();
            } else if (taskElementDto.getTaskName() == SettingTaskNameType.ADDMISSINGINFO) {
                return I18nConstants.STRINGS.ConfigureSettingTasksPresenter_Add_Information();
            } else if (taskElementDto.getTaskName() == SettingTaskNameType.GATHERWORDSBYSEMANTICDOMAINS) {
                return I18nConstants.STRINGS.ConfigureSettingTasksPresenter_Gather_Words();
            } else if (taskElementDto.getTaskName() == SettingTaskNameType.DASHBOARD) {
                return I18nConstants.STRINGS.ConfigureSettingTasksPresenter_Dashboard();
            }
        } else {
            if (taskElementDto.getTaskName() == SettingTaskNameType.DICTIONARY) {
                return I18nConstants.STRINGS.ConfigureSettingTasksPresenter_Dictionary_Browse_AND_Edit(); // Dictionary
            } else if (taskElementDto.getTaskName() == SettingTaskNameType.GATHERWORDLIST) {
                if (taskElementDto.getWordListFileName().equals("SILCAWL")) {
                    return I18nConstants.STRINGS.ConfigureSettingTasksPresenter_From_a_Word_List();
                }
            } else if (taskElementDto.getTaskName() == SettingTaskNameType.GATHERWORDSBYSEMANTICDOMAINS) {
                return I18nConstants.STRINGS.ConfigureSettingTasksPresenter_From_Semantic_Domains();
            } else if (taskElementDto.getTaskName() == SettingTaskNameType.DASHBOARD) {
                return I18nConstants.STRINGS.ConfigureSettingTasksPresenter_Dashboard();
            }
        }

        return taskElementDto.getLongLabel().isEmpty()
        ? taskElementDto.getTaskName().getValue() : taskElementDto.getLongLabel();
    }

    private void fillDomainLanguagesListListIntoListBox() {
        ExtendedComboBox languageList = view.getDomainLanguageListBox();
        languageList.clear();
        languageList.beginUpdateItem();

        for (DomainLanguagesType value : DomainLanguagesType.values()) {
            languageList.addItem(value.getValue(),
                String.valueOf(value.ordinal()));
        }

        languageList.endUpdateItem();
    }
//
//    /*
//     * we have added few new tasks into configration file that not exist in
//     * wesay. so here is post process
//     */
//    private void fillNewAddedTasks(JsArray<SettingTasksTaskElementDto> tasksDto) {
//        // Check all exist
//        boolean isReviewFound = false;
//
//        for (int i = 0; i < tasksDto.length(); i++) {
//            SettingTasksTaskElementDto taskElementDto = tasksDto.get(i);
//
//            if (taskElementDto.getTaskName() == SettingTaskNameType.REVIEW) {
//                isReviewFound = true;
//            }
//        }
//
//        // add new
//        if (!isReviewFound) {
//            SettingTasksTaskElementDto taskReviewElementDto = SettingTasksTaskElementDto.getNew();
//            taskReviewElementDto.setVisible(false);
//            taskReviewElementDto.setTaskName(SettingTaskNameType.REVIEW);
//            tasksDto.push(taskReviewElementDto);
//        }
//    }

    public void onFillTasks(JsArray<SettingTasksTaskElementDto> tasksDto) {
        taskListDs = new ArrayList<CheckableItem>();
        taskDto = SettingTasksDto.getNew();
        view.setSubSettingSemanticDomainsPanelVisible(false);
        view.setSubSettingDashboardPanelVisible(false);

        //fillNewAddedTasks(tasksDto);

        if (tasksDto.length() > 0) {
            Map<SettingTaskNameType, CheckableItem> existRootList = new HashMap<SettingTaskNameType, CheckableItem>();

            for (int i = 0; i < tasksDto.length(); i++) {
                SettingTasksTaskElementDto taskElementDto = tasksDto.get(i);

                if (isInIgnoreList(taskElementDto)) {
                    // check do those tasks needed in WebApp or not.
                    continue;
                }

                taskDto.addEntry(taskElementDto);

                // Build datasource
                CheckableItem chkRoot = null;

                // Specail case:
                // put GATHERWORDSBYSEMANTICDOMAINS into GATHERWORD
                SettingTaskNameType settingTaskNameType = taskElementDto.getTaskName();

                if (taskElementDto.getTaskName() == SettingTaskNameType.GATHERWORDSBYSEMANTICDOMAINS) {
                    settingTaskNameType = SettingTaskNameType.GATHERWORDLIST;
                }

                if (existRootList.containsKey(settingTaskNameType)) {
                    chkRoot = existRootList.get(settingTaskNameType);
                } else {
                    chkRoot = new CheckableItem(false);
                    chkRoot.setDisplayText(getDisplayName(taskElementDto, true));
                    existRootList.put(settingTaskNameType, chkRoot);
                    chkRoot.setChecked(taskElementDto.getVisible());
                    taskListDs.add(chkRoot);
                }

                CheckableItem chkChild = new CheckableItem(true);

                chkChild.setDisplayText(getDisplayName(taskElementDto, false));
                chkChild.setToolTip(taskElementDto.getDescription());
                chkChild.setData(taskElementDto);
                chkChild.setChecked(taskElementDto.getVisible());
                chkRoot.addChild(chkChild);
            }
        } else {
            eventBus.displayMessage(I18nConstants.STRINGS.ConfigureSettingTasksPresenter_User_Setting_is_missing());
        }

        populateTreeFromDataSource();
    }

    @Override
    public boolean persistData(final UserDto user) {
        AsyncCallback<SettingTasksDto> asyncCallback = new AsyncCallback<SettingTasksDto>() {
                @Override
                public void onSuccess(SettingTasksDto result) {
                    if ((user != null) &&
                            (user.getId() == CurrentEnvironmentDto.getCurrentUser()
                                                                      .getId())) {
                        // apply user is current user
                        SettingTasksDto.applyToCurrentUser(result);
                        eventBus.taskSettingChanged();
                    } else if (user == null) {
                        // to all? update self too.
                        SettingTasksDto.applyToCurrentUser(result);
                        eventBus.taskSettingChanged();
                    } else {
                        // nothing to do.
                    }
                }

                @Override
                public void onFailure(Throwable caught) {
                    eventBus.handleError(caught);
                }
            };

        if (user != null) {
            lexService.updateSettingUserTasksSetting(String.valueOf(
                    user.getId()), taskDto, asyncCallback);
        } else {
            // apply to all user
            Map<String, UserDto> list = new HashMap<String, UserDto>();
            eventBus.getUserInProjectList(list);

            Iterator<String> itr = list.keySet().iterator();
            String userIds = "";

            while (itr.hasNext()) {
                if (!userIds.isEmpty()) {
                    userIds += "|";
                }

                userIds += itr.next().toString();
            }

            lexService.updateSettingUserTasksSetting(userIds, taskDto,
                asyncCallback);
        }

        return true;
    }

    @Override
    public boolean isMultiUserSupported() {
        return true;
    }

    @Override
    public boolean isPersistDataByParentSupported() {
        return true;
    }

    public interface IConfigureSettingTasksView {
        public Widget getWidget();

        public ExtendedComboBox getDomainLanguageListBox();

        public ExtendedComboBox getActivityTimeRangeListBox();

        public ExtendedTextBox getTargerWordCountTextbox();

        public void setSubSettingSemanticDomainsPanelVisible(boolean visible);

        public void setSubSettingDashboardPanelVisible(boolean visible);

        public FastTree getSemanticDomainsSubSettingTree();

        public FastTree getTasksTree();
    }
}
