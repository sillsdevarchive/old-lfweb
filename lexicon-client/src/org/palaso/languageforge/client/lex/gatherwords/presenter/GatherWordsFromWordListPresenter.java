package org.palaso.languageforge.client.lex.gatherwords.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.palaso.languageforge.client.lex.model.FieldSettings;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.LexiconEntryListDto;
import org.palaso.languageforge.client.lex.model.MultiText;
import org.palaso.languageforge.client.lex.model.ResultDto;
import org.palaso.languageforge.client.lex.presenter.EntryPresenter;
import org.palaso.languageforge.client.lex.presenter.EntryPresenter.IEntryView;
import org.palaso.languageforge.client.lex.presenter.MultiTextPresenter;
import org.palaso.languageforge.client.lex.presenter.MultiTextPresenter.IMultiTextView;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.gatherwords.GatherWordsEventBus;
import org.palaso.languageforge.client.lex.gatherwords.view.GatherWordsFromWordListView;
import org.palaso.languageforge.client.lex.main.service.ILexService;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = GatherWordsFromWordListView.class)
public class GatherWordsFromWordListPresenter
		extends
		BasePresenter<GatherWordsFromWordListPresenter.IGatherWordsFromWordListView, GatherWordsEventBus> {

	@Inject
	public ILexService lexService;
	protected List<LexiconEntryDto> lexiconListEntry;
	protected LinkedHashMap<String, LexiconEntryDto> recentlyAddedLexiconEntry = new LinkedHashMap<String, LexiconEntryDto>();
	protected int wordIndex = 0;
	protected EntryPresenter newWordEntryPresenter = null;
	protected LexiconEntryDto currentLexiconEntryDto = null;

	// protected List<LexiconEntryDto> recentAddedLexiconEntry = new
	// ArrayList<LexiconEntryDto>();

	public void onStartWordList(SimplePanel panel) {
		panel.setWidget(view.getWidget());
		// before server call back , need to disable all;
		view.setPreBtnEnabled(false);
		view.setNextBtnEnabled(false);
		view.setAddWordBtnEnabled(false);

		lexiconListEntry = new ArrayList<LexiconEntryDto>();
		recentlyAddedLexiconEntry = new LinkedHashMap<String, LexiconEntryDto>();
		wordIndex = 0;
		newWordEntryPresenter = null;
		currentLexiconEntryDto = null;
		view.getRecentlyAddedTable().setRowCount(
				recentlyAddedLexiconEntry.size());
		view.getRecentlyAddedTable().setRowData(getRecentAddedAsList());
		view.getRecentlyAddedTable().redraw();
		// call to server to get list
		lexService
				.getNewWordListForGatherWord(new AsyncCallback<LexiconEntryListDto>() {

					@Override
					public void onSuccess(LexiconEntryListDto result) {
						lexiconListEntry = convertListDtoToArrayList(result);
						wordIndex = 0;
						fillData(0);
					}

					@Override
					public void onFailure(Throwable caught) {
						eventBus.handleError(caught);
					}
				});
	}

	public void bind() {
		// view.getSimpleMultiText().addNewTextPanel("test2", "1", false);
		// view.getSimpleMultiText().addNewTextPanel("test4", "1", false);

		view.preBtnClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fillData(-1);
			}
		});

		view.nextBtnClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fillData(+1);
			}
		});

		view.addWordBtnClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (recentlyAddedLexiconEntry
						.containsKey(currentLexiconEntryDto.getId())) {
					eventBus.displayMessage(I18nConstants.STRINGS
							.GatherWordsFromWordListPresenter_This_word_was_already_added());
					return;
				}

				if (newWordEntryPresenter != null) {
					newWordEntryPresenter.updateModel();

					final LexiconEntryDto entryDTO = newWordEntryPresenter
							.getModel();

					entryDTO.addSense(currentLexiconEntryDto.getSense(0));
					entryDTO.setGuid(currentLexiconEntryDto.getId());
					if (entryDTO.getWordFirstForm().isEmpty()) {
						eventBus.displayMessage(I18nConstants.STRINGS
								.GatherWordsFromWordListPresenter_New_word_cannot_be_empty());
						return;
					}
					view.setAddWordBtnEnabled(false);
					lexService.saveEntry(entryDTO,
							new AsyncCallback<ResultDto>() {
								@Override
								public void onSuccess(ResultDto result) {

									// don't use result! it return empty from
									// server!
									recentlyAddedLexiconEntry.put(
											currentLexiconEntryDto.getId(),
											entryDTO);
									view.getRecentlyAddedTable().setRowCount(
											recentlyAddedLexiconEntry.size());
									view.getRecentlyAddedTable().setRowData(
											getRecentAddedAsList());
									view.getRecentlyAddedTable().redraw();
									newWordEntryPresenter.getWordPresenter().setEnabled(false);
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
		for (int i = 0; i < fieldSettings.value("Word").getLanguages()
				.length(); i++) {
			final String key = fieldSettings.value("Word").getLanguages()
					.get(i);
			// Add a text column to show the name.
			TextColumn<LexiconEntryDto> IPAColumn = new TextColumn<LexiconEntryDto>() {
				@Override
				public String getValue(LexiconEntryDto object) {
					return object.getEntry().value(key);
				}
			};
			cellTable.addColumn(IPAColumn, key);
		}
		// set a empty list first!
		view.getRecentlyAddedTable().setRowData(getRecentAddedAsList());
	}

	private void fillData(int step) {

		wordIndex += step;
		setNavigationButtonStatus();
		view.setAddWordBtnEnabled(false);
		currentLexiconEntryDto = lexiconListEntry.get(wordIndex);

		view.getSimpleMultiText().clear();
		view.getDictEditView().clear();
		new MultiTextPresenter(view.getSimpleMultiText(),
				currentLexiconEntryDto.getEntry(),
				MultiText.createLabelFromSettings(FieldSettings
						.fromWindowForrGatherWordFromList().value("Word")),
				false);
		if (recentlyAddedLexiconEntry.containsKey(currentLexiconEntryDto
				.getId())) {
			view.setAddWordBtnEnabled(false);
			newWordEntryPresenter = new EntryPresenter(view.getDictEditView(),
					recentlyAddedLexiconEntry.get(currentLexiconEntryDto
							.getId()),
					FieldSettings.fromWindowForrGatherWordFromList(),true,true,false);
			newWordEntryPresenter.getWordPresenter().setEnabled(false);
		} else {
			view.setAddWordBtnEnabled(true);
			newWordEntryPresenter = new EntryPresenter(view.getDictEditView(),
					LexiconEntryDto.createFromSettings(FieldSettings
							.fromWindowForrGatherWordFromList()),
					FieldSettings.fromWindowForrGatherWordFromList(),true,true,false);
			newWordEntryPresenter.getWordPresenter().setEnabled(true);
		}
	}

	private void setNavigationButtonStatus() {
		if (lexiconListEntry.size() <= 1) {
			// have only one or none
			view.setPreBtnEnabled(false);
			view.setNextBtnEnabled(false);
		} else {
			// have more then one.
			if (wordIndex < 1) {
				view.setPreBtnEnabled(false);
				view.setNextBtnEnabled(true);
			} else if (wordIndex >= lexiconListEntry.size()) {
				view.setPreBtnEnabled(true);
				view.setNextBtnEnabled(false);
			} else {
				view.setPreBtnEnabled(true);
				view.setNextBtnEnabled(true);
			}
		}
	}

	private List<LexiconEntryDto> convertListDtoToArrayList(
			LexiconEntryListDto lexiconListDto) {
		JsArray<LexiconEntryDto> listEntry = lexiconListDto.getEntries();
		List<LexiconEntryDto> lexiconListEntry = new ArrayList<LexiconEntryDto>();

		for (int i = 0; i < listEntry.length(); i++) {
			lexiconListEntry.add(listEntry.get(i));
		}
		return lexiconListEntry;
	}

	protected List<LexiconEntryDto> getRecentAddedAsList() {
		List<LexiconEntryDto> arrayList = new ArrayList<LexiconEntryDto>(
				Arrays.asList(recentlyAddedLexiconEntry.values().toArray(
						new LexiconEntryDto[0])));

		return arrayList;
	}

	public interface IGatherWordsFromWordListView {
		public Widget getWidget();

		public IMultiTextView getSimpleMultiText();

		public IEntryView getDictEditView();

		public void setPreBtnEnabled(boolean enabled);

		public void setNextBtnEnabled(boolean enabled);

		public void setAddWordBtnEnabled(boolean enabled);

		public HasClickHandlers preBtnClickHandlers();

		public HasClickHandlers nextBtnClickHandlers();

		public HasClickHandlers addWordBtnClickHandlers();

		public CellTable<LexiconEntryDto> getRecentlyAddedTable();
	}
}
