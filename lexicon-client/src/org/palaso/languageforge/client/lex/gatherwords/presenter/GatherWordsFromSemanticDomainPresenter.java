package org.palaso.languageforge.client.lex.gatherwords.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.palaso.languageforge.client.lex.common.MessageFormat;
import org.palaso.languageforge.client.lex.common.UUID;
import org.palaso.languageforge.client.lex.controls.ExtendedComboBox;
import org.palaso.languageforge.client.lex.model.DomainQuestionDto;
import org.palaso.languageforge.client.lex.model.DomainTreeDto;
import org.palaso.languageforge.client.lex.model.Example;
import org.palaso.languageforge.client.lex.model.FieldSettings;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.ResultDto;
import org.palaso.languageforge.client.lex.model.Sense;
import org.palaso.languageforge.client.lex.controls.presenter.EntryPresenter;
import org.palaso.languageforge.client.lex.controls.presenter.EntryPresenter.IEntryView;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.gatherwords.GatherWordsEventBus;
import org.palaso.languageforge.client.lex.gatherwords.view.GatherWordsFromSemanticDomainView;
import org.palaso.languageforge.client.lex.main.service.ILexService;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = GatherWordsFromSemanticDomainView.class)
public class GatherWordsFromSemanticDomainPresenter
		extends
		BasePresenter<GatherWordsFromSemanticDomainPresenter.IGatherWordsFromSemanticDomainView, GatherWordsEventBus> {
	@Inject
	public ILexService lexService;

	protected EntryPresenter newWordEntryPresenter = null;
	protected DomainQuestionDto currentDomainQuestionDto = null;
	protected int questionIndex = 0;
	protected HashMap<String, LexiconEntryDto> recentlyAddedLexiconEntry = new HashMap<String, LexiconEntryDto>();

	public void onStartFromSemanticDomain(SimplePanel panel) {
		panel.setWidget(view.getWidget());
		view.getQuestionLabel().setText("");
		newWordEntryPresenter = null;
		lexService.getDomainTreeList(new AsyncCallback<DomainTreeDto>() {

			@Override
			public void onSuccess(DomainTreeDto result) {
				view.getDomainTreeListBox().clear();
				view.getQuestionLabel().setText("");
				questionIndex = 0;
				currentDomainQuestionDto = null;
				view.getDomainTreeListBox().beginUpdateItem();
				// igonre root node
				fillDomainTreeListListIntoListBox(result.getChildren(), "");
				view.getDomainTreeListBox().endUpdateItem();
				domainTreeSelectionChanged();
			}

			@Override
			public void onFailure(Throwable caught) {
				eventBus.handleError(caught);
			}
		});

	}

	public void bind() {
		view.getDomainTreeListBox().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				domainTreeSelectionChanged();
			}
		});

		view.preBtnClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				updateSelectedQuestion(--questionIndex);
			}
		});

		view.nextBtnClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				updateSelectedQuestion(++questionIndex);
			}
		});

		view.addWordBtnClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (newWordEntryPresenter != null) {
					newWordEntryPresenter.updateModel();

					final LexiconEntryDto entryDTO = newWordEntryPresenter
							.getModel();

					if (entryDTO.getWordFirstForm().isEmpty()) {
						eventBus.displayMessage(I18nConstants.STRINGS
								.GatherWordsFromSemanticDomainPresenter_New_word_cannot_be_empty());
						return;
					}
					lexService.saveEntry(entryDTO,
							new AsyncCallback<ResultDto>() {
								@Override
								public void onSuccess(ResultDto result) {

									// don't use result! it return empty from
									// server!
									recentlyAddedLexiconEntry.put(UUID.uuid(),
											entryDTO);
									view.getRecentlyAddedTable().setRowCount(
											recentlyAddedLexiconEntry.size());
									view.getRecentlyAddedTable().setRowData(
											getRecentlyAddedAsList());
									view.getRecentlyAddedTable().redraw();
									updateSelectedQuestion(questionIndex);
								}

								@Override
								public void onFailure(Throwable caught) {
									eventBus.handleError(caught);
									view.setAddWordBtnEnabled(true);

								}
							});

				}
			}
		});

		buildRecentlyAddedTable();
	}

	private void buildRecentlyAddedTable() {
		CellTable<LexiconEntryDto> cellTable = view.getRecentlyAddedTable();

		cellTable.setPageSize(20);

		FieldSettings fieldSettings = FieldSettings
				.fromWindowForrGatherWordFromList();

		final String wordkey = fieldSettings.value("Word").getLanguages()
				.get(0);
		// Add a text column to show the name.
		TextColumn<LexiconEntryDto> wordColumn = new TextColumn<LexiconEntryDto>() {
			@Override
			public String getValue(LexiconEntryDto object) {
				return object.getEntry().value(wordkey);
			}
		};
		cellTable.addColumn(wordColumn, wordkey);

		final String newMeaningKey = fieldSettings.value("NewMeaning")
				.getLanguages().get(0);
		TextColumn<LexiconEntryDto> meaningColumn = new TextColumn<LexiconEntryDto>() {
			@Override
			public String getValue(LexiconEntryDto object) {
				//check first that do we have Sense for this word first.
				if (object.getSenseCount()>0)
				{
					Sense sense=object.getSense(0);
					return sense.getDefinition().value(newMeaningKey);	
				}else
				{
					return "";
				}
			}
		};
		cellTable.addColumn(meaningColumn, newMeaningKey);

		// set a empty list first!
		view.getRecentlyAddedTable().setRowData(getRecentlyAddedAsList());
	}

	protected List<LexiconEntryDto> getRecentlyAddedAsList() {
		List<LexiconEntryDto> arrayList = new ArrayList<LexiconEntryDto>(
				Arrays.asList(recentlyAddedLexiconEntry.values().toArray(
						new LexiconEntryDto[0])));

		return arrayList;
	}

	private void domainTreeSelectionChanged() {
		String selectedItemValue = view.getDomainTreeListBox().getValue(
				view.getDomainTreeListBox().getSelectedIndex());
		view.getQuestionLabel().setText("");
		questionIndex = 0;
		lexService.getDomainQuestion(selectedItemValue,
				new AsyncCallback<DomainQuestionDto>() {

					@Override
					public void onSuccess(DomainQuestionDto result) {
						currentDomainQuestionDto = result;
						updateSelectedQuestion(0);
					}

					@Override
					public void onFailure(Throwable caught) {
						eventBus.handleError(caught);
					}
				});
	}

	private void setNavigationButtonStatus() {
		if (currentDomainQuestionDto.getQuestions().length() <= 1) {
			// have only one or none
			view.setPreBtnEnabled(false);
			view.setNextBtnEnabled(false);
		} else {
			// have more then one.
			if (questionIndex < 1) {
				view.setPreBtnEnabled(false);
				view.setNextBtnEnabled(true);
			} else if (questionIndex >= currentDomainQuestionDto.getQuestions()
					.length() - 1) {
				view.setPreBtnEnabled(true);
				view.setNextBtnEnabled(false);
			} else {
				view.setPreBtnEnabled(true);
				view.setNextBtnEnabled(true);
			}
		}
	}
	
	private void updateSelectedQuestion(int index) {
		questionIndex = index;
		if (currentDomainQuestionDto != null
				&& currentDomainQuestionDto.getQuestions().length() > 0) {
			String labelText = MessageFormat.format(I18nConstants.STRINGS.X_Examples_Y(),
				     currentDomainQuestionDto.getQuestions().get(index),
				     currentDomainQuestionDto.getExampleWords().get(index));
				   view.getQuestionLabel().setText(labelText);
			view.getDictEditView().clear();

			FieldSettings settings = FieldSettings
					.fromWindowForrGatherWordFromList();
			LexiconEntryDto entry = LexiconEntryDto
					.createFromSettings(settings);

			Sense sense = Sense.createFromSettings(settings);
			entry.addSense(sense);

			Example example = Example.createFromSettings(settings);
			sense.addExample(example);

			newWordEntryPresenter = new EntryPresenter(view.getDictEditView(),
					entry,
					FieldSettings.fromWindowForrGatherWordFromSemanticDomain(),
					true, true, false);
			view.setAddWordBtnEnabled(true);
		}
		setNavigationButtonStatus();
	}

	private void fillDomainTreeListListIntoListBox(
			JsArray<DomainTreeDto> domainTreeDtos, String prefix) {
		int count = domainTreeDtos.length();
		for (int i = 0; i < count; ++i) {
			DomainTreeDto entry = domainTreeDtos.get(i);
			view.getDomainTreeListBox().addItem(prefix + entry.getKey(),
					entry.getGuid());
			if (entry.getChildren().length() > 0) {
				fillDomainTreeListListIntoListBox(entry.getChildren(), prefix
						+ "--");
			}
		}
	}

	public interface IGatherWordsFromSemanticDomainView {

		public ExtendedComboBox getDomainTreeListBox();

		public void setPreBtnEnabled(boolean enabled);

		public void setNextBtnEnabled(boolean enabled);

		public void setAddWordBtnEnabled(boolean enabled);

		public HasClickHandlers preBtnClickHandlers();

		public HasClickHandlers nextBtnClickHandlers();

		public HasClickHandlers addWordBtnClickHandlers();

		public Widget getWidget();

		public Label getQuestionLabel();

		public IEntryView getDictEditView();

		public CellTable<LexiconEntryDto> getRecentlyAddedTable();
	}
}
