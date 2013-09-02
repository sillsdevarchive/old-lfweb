package org.palaso.languageforge.client.lex.configure.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.palaso.languageforge.client.lex.controls.ExtendedComboBox;
import org.palaso.languageforge.client.lex.controls.FastTree;
import org.palaso.languageforge.client.lex.controls.FastTreeItem;
import org.palaso.languageforge.client.lex.controls.TreeItemContextClickEvent;
import org.palaso.languageforge.client.lex.controls.TreeItemContextClickHandler;
import org.palaso.languageforge.client.lex.controls.TreeItemSelectedHandler;
import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.configure.ConfigureEventBus;
import org.palaso.languageforge.client.lex.common.IPersistable;
import org.palaso.languageforge.client.lex.common.SettingInputSystemItem;
import org.palaso.languageforge.client.lex.common.SettingInputSystemItemHelper;
import org.palaso.languageforge.client.lex.common.enums.InputSystemIdSpecialPurposeType;
import org.palaso.languageforge.client.lex.common.enums.InputSystemIdSpecialType;
import org.palaso.languageforge.client.lex.configure.view.ConfigureSettingInputSystemsView;
import org.palaso.languageforge.client.lex.model.IanaBaseDataDto;
import org.palaso.languageforge.client.lex.model.UserDto;
import org.palaso.languageforge.client.lex.model.settings.inputsystems.SettingInputSystemElementDto;
import org.palaso.languageforge.client.lex.model.settings.inputsystems.SettingInputSystemsDto;
import org.palaso.languageforge.client.lex.main.service.ILexService;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.touch.client.Point;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = ConfigureSettingInputSystemsView.class)
public class ConfigureSettingInputSystemsPresenter extends
		BasePresenter<ConfigureSettingInputSystemsPresenter.IConfigureSettingInputSystemsView, ConfigureEventBus>
		implements IPersistable {
	@Inject
	private ILexService lexService;
	private SettingInputSystemItemHelper sisHelper;

	public void bind() {

		view.getReadMoreIPAClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.openNewWindow(Constants.URL_IPA);
			}
		});

		view.getReadMoreLangIdClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.openNewWindow(Constants.URL_LANGUAGE_TAGS);
			}
		});

		view.getFastTree().setContextClickCallbackHandler(new TreeItemContextClickHandler() {
			@Override
			public void onTreeItemContextClick(final TreeItemContextClickEvent event) {

				createMenuBarFromTreeItem(event.getFastTreeItem(), event.getPopupPanel());
			}
		});

		view.getFastTree().setItemSelectedCallbackHandler(new TreeItemSelectedHandler() {

			@Override
			public void onTreeItemSelected(FastTreeItem item) {
				fillIdentifiers((SettingInputSystemItem) item.getData());
			}
		});

		view.getBtnMoreClickHandlers().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (view.getFastTree().getSelectedItem() != null) {
					Point btnMoreLoc = view.getBtnMoreLocation();
					PopupPanel popupPanel = new PopupPanel(true);
					popupPanel.addStyleName("popup_input_select");

					createMenuBarFromTreeItem(view.getFastTree().getSelectedItem(), popupPanel);

					popupPanel.setPopupPosition((int) btnMoreLoc.getX() - 40, (int) btnMoreLoc.getY() + 40);
					popupPanel.show();
				}
			}
		});

		view.getBtnNewClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Point btnNewLoc = view.getBtnNewLocation();
				PopupPanel popupPanel = new PopupPanel(true);
				popupPanel.addStyleName("popup_input_select");

				createLanguageCodeLookupPanel(popupPanel);

				popupPanel.setPopupPosition((int) btnNewLoc.getX(), (int) btnNewLoc.getY() + 40);
				popupPanel.show();
			}
		});

		view.getSpecialListBox().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				String selectedValue = view.getSpecialListBox().getValue(view.getSpecialListBox().getSelectedIndex());
				setInputSystemSpecialType(InputSystemIdSpecialType.valueOf(selectedValue));

			}
		});

		ChangeHandler dataChangehandler = new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				identifierTabChanged();
			}
		};

		view.getAbbreviationTextBox().addChangeHandler(dataChangehandler);
		view.getSpecialListBox().addChangeHandler(dataChangehandler);
		view.getSpecialPurposeListBox().addChangeHandler(dataChangehandler);
		view.getSpecialRegionListBox().addChangeHandler(dataChangehandler);
		view.getSpecialScriptListBox().addChangeHandler(dataChangehandler);
		view.getVariantTextBox().addChangeHandler(dataChangehandler);
	}

	private void setInputSystemSpecialType(InputSystemIdSpecialType ipsType) {

		switch (ipsType) {
		case NONE:
			view.setIPAPanelVisable(false);
			view.setScriptRegionVariantBlockVisable(false);
			view.setVoiceBlockVisable(false);
			break;
		case VOICE:
			view.setIPAPanelVisable(false);
			view.setScriptRegionVariantBlockVisable(false);
			view.setVoiceBlockVisable(true);
			break;
		case IPA_TRANSCRIPTION:
			view.setIPAPanelVisable(true);
			view.setScriptRegionVariantBlockVisable(false);
			view.setVoiceBlockVisable(false);
			break;
		case SCRIPT_REGION_VARIANT:
			view.setIPAPanelVisable(false);
			view.setScriptRegionVariantBlockVisable(true);
			view.setVoiceBlockVisable(false);
			break;

		}
	}

	public void onAttachInputSystemsView(SimplePanel simplePanel) {
		simplePanel.clear();
		simplePanel.add(view.getWidget());
		eventBus.addSubControl(simplePanel, this);

		SortedMap<String, IanaBaseDataDto> ianaRegions = new TreeMap<String, IanaBaseDataDto>();
		eventBus.getIanaRegions(ianaRegions);

		SortedMap<String, IanaBaseDataDto> ianaScripts = new TreeMap<String, IanaBaseDataDto>();
		eventBus.getIanaScripts(ianaScripts);

		SortedMap<String, IanaBaseDataDto> ianaLanguages = new TreeMap<String, IanaBaseDataDto>();
		eventBus.getIanaLanguages(ianaLanguages);

		sisHelper = new SettingInputSystemItemHelper(ianaRegions, ianaScripts, ianaLanguages);
		fillPreSetData();
		loadExistData();
	}

	private void loadExistData() {
		view.getFastTree().clear();

		List<SettingInputSystemsDto> dtoList = new ArrayList<SettingInputSystemsDto>();
		eventBus.getSettingInputSystems(dtoList);
		JsArray<SettingInputSystemElementDto> arr = dtoList.get(0).getEntries();

		HashMap<String, FastTreeItem> addedLanguages = new HashMap<String, FastTreeItem>();

		for (int i = 0; i < arr.length(); i++) {
			SettingInputSystemElementDto sisElement = arr.get(i);

			String languageKey = sisElement.getIdentity().getLanguageType().toLowerCase();

			FastTreeItem treeItem = null;
			if (!addedLanguages.containsKey(languageKey)) {
				// root language node not added
				SettingInputSystemItem newItem = sisHelper.getNewSettingInputSystemItem(languageKey,
						InputSystemIdSpecialType.NONE, sisElement.getCollations());
				treeItem = addNewLanguageCodeToInputSystem(newItem);
				addedLanguages.put(languageKey, treeItem);
			} else {
				treeItem = addedLanguages.get(languageKey);
			}

			if (!sisElement.getIdentity().getVariantType().isEmpty()) {

				SettingInputSystemItem newItem = sisHelper.getSettingInputSystemItemFromDto(sisElement);

				addNewLanguageCodeToSubInputSystem(treeItem, newItem);
			}

		}

	}

	private void fillPreSetData() {
		view.setIPAPanelVisable(false);
		view.setScriptRegionVariantBlockVisable(false);
		view.setVoiceBlockVisable(false);

		// Special
		view.getSpecialListBox().clear();
		view.getSpecialListBox().addItem(I18nConstants.STRINGS.ConfigureSettingInputSystemsPresenter_None(),
				InputSystemIdSpecialType.NONE.getValue());
		view.getSpecialListBox().addItem(
				I18nConstants.STRINGS.ConfigureSettingInputSystemsPresenter_IPA_Transcription(),
				InputSystemIdSpecialType.IPA_TRANSCRIPTION.getValue());
		view.getSpecialListBox().addItem(I18nConstants.STRINGS.ConfigureSettingInputSystemsPresenter_Voice(),
				InputSystemIdSpecialType.VOICE.getValue());
		view.getSpecialListBox().addItem(
				I18nConstants.STRINGS.ConfigureSettingInputSystemsPresenter_Script_Region_Variant(),
				InputSystemIdSpecialType.SCRIPT_REGION_VARIANT.getValue());
		view.getSpecialListBox().setItemSelected(0, true); // default selection

		// Purpose
		view.getSpecialPurposeListBox().clear();
		view.getSpecialPurposeListBox().addItem(
				I18nConstants.STRINGS.ConfigureSettingInputSystemsPresenter_Unspecified(),
				InputSystemIdSpecialPurposeType.UNSPECIFIED.getValue());
		view.getSpecialPurposeListBox().addItem(
				I18nConstants.STRINGS.ConfigureSettingInputSystemsPresenter_Etic_raw_phonetic_transcription(),
				InputSystemIdSpecialPurposeType.ETIC.getValue());
		view.getSpecialPurposeListBox().addItem(
				I18nConstants.STRINGS.ConfigureSettingInputSystemsPresenter_Emic_uses_the_phonology_of_the_language(),
				InputSystemIdSpecialPurposeType.EMIC.getValue());
		view.getSpecialPurposeListBox().setItemSelected(0, true); // default
																	// selection

		// Script
		view.getSpecialScriptListBox().clear();
		view.getSpecialScriptListBox().beginUpdateItem();
		SortedMap<String, IanaBaseDataDto> listScript = new TreeMap<String, IanaBaseDataDto>();
		eventBus.getIanaScripts(listScript);

		view.getSpecialScriptListBox().addItem("", ""); // default value;
		for (IanaBaseDataDto ianaData : listScript.values()) {
			view.getSpecialScriptListBox().addItem(ianaData.getDescription(), String.valueOf(ianaData.getSubtag()));
		}
		view.getSpecialScriptListBox().endUpdateItem();
		view.getSpecialScriptListBox().setItemSelected(0, true); // default

		// selection

		// Region

		view.getSpecialRegionListBox().clear();
		view.getSpecialRegionListBox().beginUpdateItem();
		SortedMap<String, IanaBaseDataDto> listRegion = new TreeMap<String, IanaBaseDataDto>();
		eventBus.getIanaRegions(listRegion);

		view.getSpecialRegionListBox().addItem("", ""); // default value;
		for (IanaBaseDataDto ianaData : listRegion.values()) {
			view.getSpecialRegionListBox().addItem(ianaData.getDescription(), String.valueOf(ianaData.getSubtag()));
		}
		view.getSpecialRegionListBox().endUpdateItem();
		view.getSpecialRegionListBox().setItemSelected(0, true); // default

		// Variant
	}

	private void createLanguageCodeLookupPanel(final PopupPanel panel) {
		eventBus.attachLanguageCodeLookupView(panel);
	}

	private void createMenuBarFromTreeItem(final FastTreeItem item, final PopupPanel panel) {
		MenuBar popupMenuBar = new MenuBar(true);
		final SettingInputSystemItem langItem = (SettingInputSystemItem) item.getData();
		final FastTreeItem topLevelItem = findTopLevelItem(item);
		MenuItem addIPAItem = new MenuItem(I18nConstants.STRINGS.ConfigureSettingInputSystemsPresenter_Add_IPA_for()
				+ " " + langItem.getIanaObj().getDescription(), true, new Command() {

			@Override
			public void execute() {
				SettingInputSystemItem newItem = sisHelper.getNewSettingInputSystemItem(langItem.getIanaObj(),
						InputSystemIdSpecialType.IPA_TRANSCRIPTION, langItem.getCollations());
				addNewLanguageCodeToSubInputSystem(topLevelItem, newItem);
				panel.hide();

			}
		});
		MenuItem addVoiceItem = new MenuItem(
				I18nConstants.STRINGS.ConfigureSettingInputSystemsPresenter_Add_Voice_for() + " "
						+ langItem.getIanaObj().getDescription(), true, new Command() {

					@Override
					public void execute() {
						SettingInputSystemItem newItem = sisHelper.getNewSettingInputSystemItem(langItem.getIanaObj(),
								InputSystemIdSpecialType.VOICE, langItem.getCollations());

						addNewLanguageCodeToSubInputSystem(topLevelItem, newItem);
						panel.hide();

					}
				});
		MenuItem addVariantItem = new MenuItem(
				I18nConstants.STRINGS.ConfigureSettingInputSystemsPresenter_Add_a_variant_of() + " "
						+ langItem.getIanaObj().getDescription(), true, new Command() {

					@Override
					public void execute() {
						SettingInputSystemItem newItem = sisHelper.getNewSettingInputSystemItem(langItem.getIanaObj(),
								InputSystemIdSpecialType.SCRIPT_REGION_VARIANT, langItem.getCollations());
						addNewLanguageCodeToSubInputSystem(topLevelItem, newItem);
						panel.hide();

					}
				});

		MenuItem deleteItem = new MenuItem(I18nConstants.STRINGS.ConfigureSettingInputSystemsPresenter_Delete() + " "
				+ langItem.getDisplayText(), true, new Command() {

			@Override
			public void execute() {
				item.remove();
				panel.hide();
			}
		});

		addIPAItem.addStyleName("popup-item");
		addVoiceItem.addStyleName("popup-item");
		addVariantItem.addStyleName("popup-item");
		deleteItem.addStyleName("popup-item");
		deleteItem.addStyleName("popup-item-top-divider");

		popupMenuBar.addItem(addIPAItem).setWidth("100%");
		popupMenuBar.addItem(addVoiceItem).setWidth("100%");
		popupMenuBar.addItem(addVariantItem).setWidth("100%");
		popupMenuBar.addItem(deleteItem).setWidth("100%");

		popupMenuBar.setVisible(true);
		panel.setWidget(popupMenuBar);
	}

	private FastTreeItem findTopLevelItem(FastTreeItem item) {

		FastTreeItem temp_Item = item;
		while (true) {
			if (temp_Item.getParentItem() == null || temp_Item.isTopLevel()) {
				return temp_Item;
			}
			temp_Item = temp_Item.getParentItem();
		}
	}

	public void onAddNewLanguageCodeToInputSystem(IanaBaseDataDto data) {
		SettingInputSystemItem newItem = sisHelper.getNewSettingInputSystemItem(data.getSubtag(),
				InputSystemIdSpecialType.NONE, null);
		addNewLanguageCodeToInputSystem(newItem);
	}

	public FastTreeItem addNewLanguageCodeToInputSystem(SettingInputSystemItem sisItem) {
		if (sisItem != null) {
			FastTreeItem item = new FastTreeItem();
			item.setText(sisItem.getDisplayText());
			item.setData(sisItem);
			view.getFastTree().addItem(item);
			view.getFastTree().setSelectedItem(item);
			view.getFastTree().ensureSelectedItemVisible();
			return item;
		}
		return null;
	}

	public FastTreeItem addNewLanguageCodeToSubInputSystem(FastTreeItem parent, SettingInputSystemItem sisItem) {

		if (parent != null && sisItem != null) {

			FastTreeItem item = new FastTreeItem();
			item.setText(sisItem.getDisplayText());
			item.setData(sisItem);
			view.getFastTree().setSelectedItem(null); // Important! before add
														// sub-node must remove
														// the selection on top
														// level! Bugs?
			parent.addItem(item);
			view.getFastTree().setSelectedItem(item);
			view.getFastTree().ensureSelectedItemVisible();
			return item;
		}
		return null;
	}

	private void fillIdentifiers(SettingInputSystemItem item) {

		// if (item.getSpecial)
		if (item.getIdentifier().getAbbreviation().isEmpty()) {
			if (item.getIanaObj() != null) {
				view.getAbbreviationTextBox().setText(item.getIanaObj().getSubtag());
			}
		} else {
			view.getAbbreviationTextBox().setText(item.getIdentifier().getAbbreviation());
		}

		view.getVariantTextBox().setText(item.getIdentifier().getVariant());

		// set Special
		view.getSpecialListBox().setSelectedIndex(item.getIdentifier().getSpecial().ordinal());
		setInputSystemSpecialType(item.getIdentifier().getSpecial());

		// set Purpose
		view.getSpecialPurposeListBox().setSelectedIndex(item.getIdentifier().getPurpose().ordinal());

		// set Script
		if (item.getIdentifier().getScript() != null && !item.getIdentifier().getScript().getSubtag().isEmpty()) {

			SortedMap<String, IanaBaseDataDto> ianaScripts = new TreeMap<String, IanaBaseDataDto>();
			eventBus.getIanaScripts(ianaScripts);

			view.getSpecialScriptListBox().setSelectedIndex(
					new ArrayList<IanaBaseDataDto>(ianaScripts.values()).indexOf(ianaScripts.get(item.getIdentifier()
							.getScript().getSubtag())) + 1);
		}
		// set Region
		if (item.getIdentifier().getRegion() != null && !item.getIdentifier().getRegion().getSubtag().isEmpty()) {

			SortedMap<String, IanaBaseDataDto> ianaRegions = new TreeMap<String, IanaBaseDataDto>();
			eventBus.getIanaRegions(ianaRegions);

			view.getSpecialRegionListBox().setSelectedIndex(
					new ArrayList<IanaBaseDataDto>(ianaRegions.values()).indexOf(ianaRegions.get(item.getIdentifier()
							.getRegion().getSubtag())) + 1);
		}

		fillIdentifiersTitle(item);
	}

	private void fillIdentifiersTitle(SettingInputSystemItem item) {
		view.setLongLanguageNameLabel(item.getDisplayText());
		view.setLanguageCodeLabel(item.getIdLangCode());
	}

	private void identifierTabChanged() {
		FastTreeItem selectedItem = view.getFastTree().getSelectedItem();
		SettingInputSystemItem langItem = (SettingInputSystemItem) selectedItem.getData();

		// apply changes from UI to data object
		// Abbreviation
		langItem.getIdentifier().setAbbreviation(view.getAbbreviationTextBox().getText());

		// Special
		langItem.getIdentifier().setSpecial(
				InputSystemIdSpecialType.valueOf(view.getSpecialListBox().getValue(
						view.getSpecialListBox().getSelectedIndex())));
		// Purpose
		langItem.getIdentifier().setPurpose(
				InputSystemIdSpecialPurposeType.valueOf(view.getSpecialPurposeListBox().getValue(
						view.getSpecialPurposeListBox().getSelectedIndex())));
		// Variant
		langItem.getIdentifier().setVariant(view.getVariantTextBox().getText());

		// Script
		String selectedValue = view.getSpecialScriptListBox().getValue(
				view.getSpecialScriptListBox().getSelectedIndex());

		if (!selectedValue.trim().isEmpty()) {

			SortedMap<String, IanaBaseDataDto> ianaScript = new TreeMap<String, IanaBaseDataDto>();
			eventBus.getIanaScripts(ianaScript);

			IanaBaseDataDto scriptData = ianaScript.get(selectedValue);
			langItem.getIdentifier().setScript(scriptData);
		}

		// Region
		selectedValue = view.getSpecialRegionListBox().getValue(view.getSpecialRegionListBox().getSelectedIndex());

		if (!selectedValue.trim().isEmpty()) {

			SortedMap<String, IanaBaseDataDto> ianaRegions = new TreeMap<String, IanaBaseDataDto>();
			eventBus.getIanaRegions(ianaRegions);
			IanaBaseDataDto regionData = ianaRegions.get(selectedValue);
			langItem.getIdentifier().setRegion(regionData);
		}

		selectedItem.setText(langItem.getDisplayText());
		fillIdentifiersTitle(langItem);
	}

	private SettingInputSystemsDto createListDataFromTree() {
		SettingInputSystemsDto newDto = SettingInputSystemsDto.getNew();

		for (FastTreeItem item : view.getFastTree().getItems()) {
			createInputSystemsListData(newDto, item);
		}
		return newDto;
	}

	private void createInputSystemsListData(SettingInputSystemsDto dto, FastTreeItem item) {
		SettingInputSystemItem sisItem = (SettingInputSystemItem) item.getData();
		if (sisItem != null) {
			dto.addEntry(sisHelper.getSettingInputSystemElementDtoFromItem(sisItem));
		}

		if (item.getChildren() != null) {
			for (FastTreeItem treeItem : item.getChildren()) {
				createInputSystemsListData(dto, treeItem);
			}
		}
	}

	@Override
	public boolean persistData(UserDto user) {

		SettingInputSystemsDto list = createListDataFromTree();
		lexService.updateSettingInputSystems(list, new AsyncCallback<SettingInputSystemsDto>() {

			@Override
			public void onSuccess(SettingInputSystemsDto result) {
				eventBus.setSettingInputSystems(result);
				loadExistData();
				eventBus.reloadIana();

			}

			@Override
			public void onFailure(Throwable caught) {
				eventBus.handleError(caught);

			}
		});
		return false;
	}

	public interface IConfigureSettingInputSystemsView {
		public Widget getWidget();

		public FastTree getFastTree();

		public HTMLPanel getIPAPanel();

		public HTMLPanel getScriptRegionVariantBlock();

		public HTMLPanel getVoiceBlock();

		public SimplePanel getCustomSortPanel();

		public SimplePanel getSortAsAnotherPanel();

		public void setIPAPanelVisable(boolean visible);

		public void setScriptRegionVariantBlockVisable(boolean visible);

		public void setVoiceBlockVisable(boolean visible);

		public void setCustomSortPanel(boolean visible);

		public void setSortAsAnotherPanel(boolean visible);

		public HasClickHandlers getBtnMoreClickHandlers();

		public HasClickHandlers getBtnNewClickHandlers();

		public Point getBtnMoreLocation();

		public Point getBtnNewLocation();

		public ExtendedComboBox getSpecialListBox();

		public ExtendedComboBox getSpecialPurposeListBox();

		public ExtendedComboBox getSpecialScriptListBox();

		public ExtendedComboBox getSpecialRegionListBox();

		public void setLongLanguageNameLabel(String text);

		public void setLanguageCodeLabel(String text);

		public TextBox getAbbreviationTextBox();

		public TextBox getVariantTextBox();

		public HasClickHandlers getReadMoreIPAClickHandlers();

		public HasClickHandlers getReadMoreLangIdClickHandlers();

	}

	@Override
	public boolean isMultiUserSupported() {
		return false;
	}

	@Override
	public boolean isPersistDataByParentSupported() {
		return true;
	}

}
