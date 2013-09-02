package org.palaso.languageforge.client.lex.addinfo.presenter;

import org.palaso.languageforge.client.lex.addinfo.AddInfoEventBus;
import org.palaso.languageforge.client.lex.addinfo.view.IncompleteWordEditView;
import org.palaso.languageforge.client.lex.common.ConsoleLog;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.common.PermissionManager;
import org.palaso.languageforge.client.lex.common.enums.DomainPermissionType;
import org.palaso.languageforge.client.lex.common.enums.EntryFieldType;
import org.palaso.languageforge.client.lex.common.enums.OperationPermissionType;
import org.palaso.languageforge.client.lex.main.service.ILexService;
import org.palaso.languageforge.client.lex.model.FieldSettings;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.ResultDto;
import org.palaso.languageforge.client.lex.controls.presenter.EntryPresenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = IncompleteWordEditView.class)
public class IncompleteWordEditPresenter extends
		BasePresenter<IncompleteWordEditPresenter.IView, AddInfoEventBus> {
	/**
	 * Interface created for MissInfoEditView controls , these functions must be
	 * implemented in MissInfoEditView class
	 */
	public interface IView {
		EntryPresenter.IEntryView createDictionaryView(boolean userAccess);

		void destroyDictionaryView();

		void showNotification(String text, boolean success);
	}

	@Inject
	public ILexService LexService;
	private EntryPresenter entryPresenter;
	private boolean isNewEntry = false;
	/**
	 * Empty constructor for MissInfoEditPresenter
	 * 
	 */
	public IncompleteWordEditPresenter() {
		entryPresenter = null;
	}

	/**
	 * Bind the button controls from loading by the EventBus
	 * 
	 */
	@Override
	public void bind() {
		eventBus.setButtonControl();
	}

	/**
	 * To reset view and remove existing data
	 * 
	 */
	public void onResetEditorView() {
		view.destroyDictionaryView();
	}

	/**
	 * To move to previous entry in the list.
	 * 
	 */
	public void onPreviousWord() {

	}

	public void onNoWordsToDisplay() {
		String message = "";

		switch (IncompleteWordListPresenter.getEntryFieldType()) {
		case DEFINITION:
			message = I18nConstants.STRINGS
					.IncompleteWordEditPresenter_All_words_have_meanings();
			break;
		case EXAMPLESENTENCE:
			message = I18nConstants.STRINGS
					.IncompleteWordEditPresenter_All_words_have_meanings();
			break;
		case POS:
			message = I18nConstants.STRINGS
					.IncompleteWordEditPresenter_All_words_have_part_of_speech_information();
			break;
		default:
			throw new RuntimeException("Unsupports entry field.");
		}

		if (!message.isEmpty()) {
			view.showNotification(message, false);
		}
	}

	/**
	 * To move next entry in the list.
	 * 
	 */
	public void onNextWord() {

	}

	/**
	 * To create a call to json-rpc service to load an entry details.
	 * 
	 */
	public void onWordSelected(String id) {

		LexService.getEntry(id, new AsyncCallback<LexiconEntryDto>() {

			@Override
			public void onFailure(Throwable caught) {

				eventBus.handleError(caught);
			}

			@Override
			public void onSuccess(LexiconEntryDto result) {
				renderWord(result);
			}

		});

	}

	/**
	 * To create a call to json-rpc service to update an entry.
	 * 
	 */
	public void onWordUpdated() {
		entryPresenter.updateModel();
		final LexiconEntryDto entryDTO = entryPresenter.getModel();
		ConsoleLog.log("Before add-info save.");
		LexService.saveEntry(entryDTO, new AsyncCallback<ResultDto>() {

			@Override
			public void onFailure(Throwable caught) {

				eventBus.handleError(caught);
				view.showNotification(I18nConstants.STRINGS
						.IncompleteWordEditPresenter_Entry_update_failed(),
						false);
			}

			@Override
			public void onSuccess(ResultDto result) {
				ConsoleLog.log("Add-info save success.");
				if (isNewEntry) {
					view.showNotification(
							I18nConstants.STRINGS
									.IncompleteWordEditPresenter_Entry_created_successfully(),
							true);
					isNewEntry = false;
				} else {
					view.showNotification(
							I18nConstants.STRINGS
									.IncompleteWordEditPresenter_Entry_updated_successfully(),
							true);
				}
				ConsoleLog.log("Remove old key: " + entryDTO.getId());
				LexService.removeEntryFromCache(entryDTO.getId());
				eventBus.clientDataRefresh(IncompleteWordListPresenter
						.getEntryFieldType());
			}
		});
	}

	/**
	 * To load the selected entry details values to controls
	 * 
	 */
	private void renderWord(LexiconEntryDto result) {
		eventBus.setNavigationButtonStatus();
		FieldSettings fieldSettings = FieldSettings.fromWindow();
		if (IncompleteWordListPresenter.getEntryFieldType() == EntryFieldType.DEFINITION)
			fieldSettings = FieldSettings.fromWindowForAddMeaning();
		else if (IncompleteWordListPresenter.getEntryFieldType() == EntryFieldType.POS)
			fieldSettings = FieldSettings.fromWindowForAddPartOfSpeech();
		else if (IncompleteWordListPresenter.getEntryFieldType() == EntryFieldType.EXAMPLESENTENCE)
			fieldSettings = FieldSettings.fromWindowForAddExample();

		boolean allowEdit=false;
		if (PermissionManager.getPermission(DomainPermissionType.DOMAIN_LEX_ENTRY, OperationPermissionType.CAN_EDIT_OTHER)) {
			allowEdit=true;
		}
		
		entryPresenter = new EntryPresenter(
				view.createDictionaryView(allowEdit),
				result, fieldSettings, true, true, true);
	}

}
