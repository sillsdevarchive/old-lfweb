package org.palaso.languageforge.client.lex.browse.edit.presenter;

import org.palaso.languageforge.client.lex.browse.edit.BrowseAndEditEventBus;
import org.palaso.languageforge.client.lex.browse.edit.view.LexBrowseEditView;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.common.PermissionManager;
import org.palaso.languageforge.client.lex.common.enums.DomainPermissionType;
import org.palaso.languageforge.client.lex.common.enums.OperationPermissionType;
import org.palaso.languageforge.client.lex.controls.presenter.EntryPresenter;
import org.palaso.languageforge.client.lex.main.service.ILexService;
import org.palaso.languageforge.client.lex.model.CurrentEnvironmentDto;
import org.palaso.languageforge.client.lex.model.FieldSettings;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.ResultDto;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabBar.Tab;
import com.google.gwt.user.client.ui.TabLayoutPanel;
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

		Frame getEntryDisplayPanel();

		Frame getEntryCommentPanel();
		
		public FlowPanel getBowserPanel();

		public FlowPanel getCommentPanel();

		public TabLayoutPanel getTabPanel();
		
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
				fieldSettings, false, false, true, false);
		isNewEntry = true;
		view.getEntryDisplayPanel().setVisible(false);
	}

	/**
	 * To create a call to json-rpc service to delete an entry.
	 * 
	 */
	public void onWordDeleted() {
		// eventBus.showLoadingIndicator();
		if (entryPresenter == null) {
			// fail safe
			return;
		}
		LexService.deleteEntry(entryKey, entryPresenter.getModel()
				.getMercurialSHA(), new AsyncCallback<ResultDto>() {

			@Override
			public void onFailure(Throwable caught) {
				// eventBus.hideLoadingIndicator();
				eventBus.handleError(caught);
				eventBus.handleError(new Throwable(I18nConstants.STRINGS
						.LexBrowseEditPresenter_Entry_delete_failed()));
			}

			@Override
			public void onSuccess(ResultDto result) {
				if (result.isSucceed()) {
					eventBus.displayMessage(I18nConstants.STRINGS
							.LexBrowseEditPresenter_Entry_deleted_successfully());
					// eventBus.hideLoadingIndicator();
					eventBus.reloadList();
				} else {
					eventBus.handleError(new Throwable(I18nConstants.STRINGS
							.LexBrowseEditPresenter_Entry_delete_failed()));
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
				showWordOnDispalyPanel(result.getId());
				eventBus.setUpdateButtonEnable(true);
				boolean allowDelete = false;
				if (PermissionManager.getPermission(
						DomainPermissionType.DOMAIN_LEX_ENTRY,
						OperationPermissionType.CAN_DELETE_OTHER)) {
					allowDelete = true;
				}
				eventBus.setDeleteButtonVisible(allowDelete);
				// eventBus.hideLoadingIndicator();
			}

		});
	}

	private void showWordOnDispalyPanel(String guid) {
		view.getEntryDisplayPanel().setVisible(true);
		String url = "/../../gwtangular/entryblock/"
				+ CurrentEnvironmentDto.getCurrentProject().getProjectId()
				+ "/" + guid;

		view.getEntryDisplayPanel().setUrl(url);
	}

	/**
	 * To create a call to json-rpc service to update an entry.
	 * 
	 */
	public void onWordUpdated() {
		if (entryPresenter == null) {
			// fail safe
			return;
		}

		if (!entryPresenter.validate()) {
			eventBus.handleError(new Throwable(
					"You must have last on word in the entry."));
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
				eventBus.handleError(new Throwable(I18nConstants.STRINGS
						.LexBrowseEditPresenter_Entry_update_failed()));
			}

			@Override
			public void onSuccess(ResultDto result) {
				eventBus.clientDataRefresh(!isNewEntry, false);
				if (isNewEntry) {
					eventBus.displayMessage(I18nConstants.STRINGS
							.LexBrowseEditPresenter_Entry_created_successfully());
					isNewEntry = false;
				} else {
					eventBus.displayMessage(I18nConstants.STRINGS
							.LexBrowseEditPresenter_Entry_updated_successfully());
				}
			}
		});
	}

	/**
	 * To load the selected entry details values to controls
	 * 
	 */
	private void renderWord(final LexiconEntryDto result) {
		
		final String projectId = CurrentEnvironmentDto.getCurrentProject()
				.getProjectId();
		final String entryGuid = result.getId();
		String entryUrl = "/../../gwtangular/sfchecks#/project/" + projectId + "/" + entryGuid;
		
		view.getEntryCommentPanel().setUrl(entryUrl);
		
		boolean allowEdit = false;
		if (PermissionManager.getPermission(
				DomainPermissionType.DOMAIN_LEX_ENTRY,
				OperationPermissionType.CAN_EDIT_OTHER)) {
			allowEdit = true;
		}
		entryPresenter = new EntryPresenter(
				view.createDictionaryView(allowEdit), result, fieldSettings);
		entryPresenter.addCommentClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// set url to ifream first!
				
				String url = "/../../gwtangular/sfchecks#/project/" + projectId + "/" + entryGuid + "/"
						+ ((Button) event.getSource()).getTarget();
				
				view.getEntryCommentPanel().setUrl(url);
				
				view.getTabPanel().selectTab(1);
			}
		});
	}

}
