package org.palaso.languageforge.client.lex.gatherwords.presenter;

import gwtupload.client.BaseUploadStatus;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnChangeUploaderHandler;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import gwtupload.client.Uploader;

import org.palaso.languageforge.client.lex.common.MessageFormat;
import org.palaso.languageforge.client.lex.common.FileUploadProgress;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.gatherwords.GatherWordsEventBus;
import org.palaso.languageforge.client.lex.gatherwords.view.GatherWordsFromTextView;
import org.palaso.languageforge.client.lex.main.service.ILexService;
import org.palaso.languageforge.client.lex.model.ResultDto;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = GatherWordsFromTextView.class)
public class GatherWordsFromTextPresenter
		extends
		BasePresenter<GatherWordsFromTextPresenter.IGatherWordsFromTextView, GatherWordsEventBus> {
	@Inject
	public ILexService lexService;

	private boolean isFileUploadCancelled = false;

	public void onStartFromText(SimplePanel panel) {
		panel.setWidget(view.getWidget());
		view.setBtnAddFileEnabled(false);
		view.setAvoidEmptyFiles(true);
		view.setUploadFileUplodPanelVisible(false);

		// use customized progress bar
		view.setUploadProgressBar(new FileUploadProgress(view
				.getProgressBarPanel()));
	}

	public void bind() {
		view.addFileUploaderChangeHandler(new OnChangeUploaderHandler() {
			@Override
			public void onChange(IUploader uploader) {
				view.setFilename(view.getFilenameFromUpLoader());

				if (view.getFilename().trim().length() > 0) {
					view.setBtnAddFileEnabled(true);
				} else {
					view.setBtnAddFileEnabled(false);
				}
			}
		});
		
		view.addOnFinishUploaderHandler(new OnFinishUploaderHandler() {			
			@Override
			public void onFinish(final IUploader uploader) {
				view.setFileNameBoxVisible(true);
				view.setUploadFileUplodPanelVisible(false);
				view.setBtnAddFileEnabled(true);
				switch (uploader.getStatus()) {
				case SUCCESS:
					if (!isFileUploadCancelled) {
						lexService.gatherWordsFromText("",
								uploader.getInputName(),
								new AsyncCallback<ResultDto>() {
									@Override
									public void onFailure(Throwable caught) {
										eventBus.handleError(caught);
									}

									@Override
									public void onSuccess(ResultDto result) {
										view.clearFileUploader();
										uploader.reset();
										view.setBtnAddFileEnabled(false);
										eventBus.displayMessage(
												MessageFormat.format(
														I18nConstants.STRINGS.GatherWordsFromTextPresenter_X_words_submitted()
														, new Object[]{result.getCode()}));
									}
								});
					}
					break;
				case CANCELED:
					isFileUploadCancelled = true;
					uploader.cancel();
					view.clearFileUploader();
					uploader.reset();
					eventBus.displayMessage(I18nConstants.STRINGS.GatherWordsFromTextPresenter_upload_canceled());
					break;
				case ERROR:
					isFileUploadCancelled = true;
					view.cancelUpload();
					view.clearFileUploader();
					uploader.reset();
					//eventBus.displayMessage("upload failed!");
					break;
				default:
					break;
				}
			}
		});

		view.getAddFileClickedHandlers().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				view.setUploadFileUplodPanelVisible(true);
				view.setFileNameBoxVisible(false);
				view.setBtnAddFileEnabled(false);
				isFileUploadCancelled = false;
				view.submitFile();
				if (view.getUploader().isFinished())
				{
					view.setFileNameBoxVisible(true);
					view.setUploadFileUplodPanelVisible(false);
					view.setBtnAddFileEnabled(false);
					view.cancelUpload();
					view.clearFileUploader();
					view.getUploader().reset();
				}
			}
		});

		view.getAddTextClickedHandlers().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (view.getText().trim().isEmpty()) {
					eventBus.displayMessage(I18nConstants.STRINGS.GatherWordsFromTextPresenter_Textbox_can_not_be_empty());
					return;
				}
				lexService.gatherWordsFromText(view.getText().trim(), "",
						new AsyncCallback<ResultDto>() {
							@Override
							public void onFailure(Throwable caught) {
								eventBus.handleError(caught);
							}

							@Override
							public void onSuccess(ResultDto result) {
								view.clearTextBox();
								eventBus.displayMessage(MessageFormat.format(
										I18nConstants.STRINGS.GatherWordsFromTextPresenter_X_words_submitted()
										, new Object[]{result.getCode()}));
							}
						});
			}
		});
		view.getUploadCancelClickedHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						view.cancelUpload();
					}
				});
	}


	public interface IGatherWordsFromTextView {

		public void setFilename(String text);

		public String getFilenameFromUpLoader();

		public String getFilename();

		public String getText();

		public HasClickHandlers getBrowserFileClickedHandlers();

		public HasClickHandlers getAddFileClickedHandlers();

		public HasClickHandlers getAddTextClickedHandlers();

		public HasClickHandlers getFileNameTextboxClickedHandlers();

		public HasClickHandlers getUploadCancelClickedHandlers();

		public void addFileUploaderChangeHandler(OnChangeUploaderHandler handler);

		public HasValueChangeHandlers<String> getTextValueChangeHandler();

		public void addOnStartUploaderHandler(OnStartUploaderHandler handler);

		public void addOnFinishUploaderHandler(OnFinishUploaderHandler handler);

		public void setBtnBrowserEnabled(boolean enabled);

		public void setBtnAddFileEnabled(boolean enabled);

		public void setBtnAddTextEnabled(boolean enabled);

		public void submitFile();

		public void clearTextBox();

		public void clearFileUploader();

		public void cancelUpload();

		public void setAvoidEmptyFiles(boolean avoid);

		public void setFileNameBoxVisible(boolean visible);

		public SimplePanel getProgressBarPanel();

		public void setUploadProgressBar(BaseUploadStatus baseUploadStatus);

		public void setUploadFileUplodPanelVisible(boolean visible);
		
		public Widget getWidget();
		
		public Uploader getUploader();
	}
}
