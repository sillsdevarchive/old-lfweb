package org.palaso.languageforge.client.lex.browse.edit.presenter;

import org.palaso.languageforge.client.lex.common.PermissionManager;
import org.palaso.languageforge.client.lex.common.ProjectPermissionType;
import org.palaso.languageforge.client.lex.common.Tools;
import org.palaso.languageforge.client.lex.model.FieldSettings;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.ResultDto;
import org.palaso.languageforge.client.lex.presenter.EntryPresenter;
import org.palaso.languageforge.client.lex.browse.edit.BrowseAndEditEventBus;
import org.palaso.languageforge.client.lex.browse.edit.view.LexBrowseEditView;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.main.service.ILexService;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = LexBrowseEditView.class)
public class LexBrowseEditPresenter extends
		BasePresenter<LexBrowseEditPresenter.IView, BrowseAndEditEventBus> {
	/**
	 * Interface created for LexDBEEditView controls , these functions must be
	 * implemented in LexDBEEditView class
	 */
	public interface IView {
		EntryPresenter.IEntryView createDictionaryView(boolean userAccess);

		void showMessage(String text, boolean success);
	}

	@Inject
	public ILexService LexService;
	private String entryKey;
	private EntryPresenter entryPresenter = null;
	private FieldSettings fieldSettings = FieldSettings.fromWindow();
	private boolean isNewEntry = false;
	
	public void onStart() {
	}

	/**
	 * Bind the button controls from loading by the eventbus
	 * 
	 */
	@Override
	public void bind() {
	}

	/**
	 * To create basic controls for new Word contains word and single meaning
	 * control
	 * 
	 */
	public void onWordCreated() {
		eventBus.setUpdateButtonEnable(true);
		entryPresenter = new EntryPresenter(view.createDictionaryView(false),
				LexiconEntryDto.createFromSettings(fieldSettings),
				fieldSettings);
		isNewEntry = true;
	}

	/**
	 * To create a call to json-rpc service to delete an entry.
	 * 
	 */
	public void onWordDeleted() {
		// eventBus.showLoadingIndicator();
		if (entryPresenter==null)
		{
			// fail safe
			return;
		}
		LexService.deleteEntry(
				entryKey, 
				entryPresenter.getModel().getMercurialSHA(), 
				new AsyncCallback<ResultDto>() {

			@Override
			public void onFailure(Throwable caught) {
				// eventBus.hideLoadingIndicator();
				eventBus.handleError(caught);
				view.showMessage(I18nConstants.STRINGS
						.LexBrowseEditPresenter_Entry_delete_failed(), false);
			}

			@Override
			public void onSuccess(ResultDto result) {
				if (result.isSucceed())
				{
				view.showMessage(I18nConstants.STRINGS.LexBrowseEditPresenter_Entry_deleted_successfully(), true);
				// eventBus.hideLoadingIndicator();
				eventBus.reloadList();
				}else
				{
					view.showMessage(I18nConstants.STRINGS
							.LexBrowseEditPresenter_Entry_delete_failed(), false);
				}
			}
		});

	}

	/**
	 * To create a call to json-rpc service to load an entry details.
	 * 
	 */
	public void onWordSelected(String id) {
		this.entryKey = id;
		// eventBus.showLoadingIndicator();
		LexService.getEntry(id, new AsyncCallback<LexiconEntryDto>() {

			@Override
			public void onFailure(Throwable caught) {
				// eventBus.hideLoadingIndicator();
				eventBus.handleError(caught);
			}

			@Override
			public void onSuccess(LexiconEntryDto result) {
				// keep a copy for update check and setting timestamp
				renderWord(result);
				eventBus.setUpdateButtonEnable(true);
				boolean allowDelete=false;
				if (PermissionManager.getPermission(ProjectPermissionType.CAN_DELETE_ENTRY)) {
					allowDelete=true;
				}
				eventBus.setDeleteButtonVisible(allowDelete);
				// eventBus.hideLoadingIndicator();
			}

		});
	}

	/**
	 * To create a call to json-rpc service to update an entry.
	 * 
	 */
	public void onWordUpdated() {
		if (entryPresenter==null)
		{
			// fail safe
			return;
		}
		
		if(!entryPresenter.validate())
		{
			view.showMessage("You must have last on word in the entry.", false);
			return;
		}
		
		entryPresenter.updateModel();
		final LexiconEntryDto entryDTO = entryPresenter.getModel();
		// eventBus.showLoadingIndicator();
		LexService.saveEntry(entryDTO, new AsyncCallback<ResultDto>() {

			@Override
			public void onFailure(Throwable caught) {
				// eventBus.hideLoadingIndicator();
				eventBus.handleError(caught);
				view.showMessage(I18nConstants.STRINGS
						.LexBrowseEditPresenter_Entry_update_failed(), false);
			}

			@Override
			public void onSuccess(ResultDto result) {
				eventBus.clientDataRefresh(!isNewEntry, false);
				if (isNewEntry) {
					view.showMessage(
							I18nConstants.STRINGS
									.LexBrowseEditPresenter_Entry_created_successfully(),
							true);
					isNewEntry = false;
				} else {
					view.showMessage(
							I18nConstants.STRINGS
									.LexBrowseEditPresenter_Entry_updated_successfully(),
							true);
				}
			}
		});
	}

	/**
	 * To load the selected entry details values to controls
	 * 
	 */
	private void renderWord(LexiconEntryDto result) {
		boolean allowEdit=false;
		if (PermissionManager.getPermission(ProjectPermissionType.CAN_EDIT_ENTRY)) {
			allowEdit=true;
		}
		entryPresenter = new EntryPresenter(
				view.createDictionaryView(allowEdit),
				result, fieldSettings);
	}
	


}
