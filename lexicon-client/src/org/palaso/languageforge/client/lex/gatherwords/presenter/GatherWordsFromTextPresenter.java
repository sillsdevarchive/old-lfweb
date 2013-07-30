package org.palaso.languageforge.client.lex.gatherwords.presenter;

import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.common.MessageFormat;
import org.palaso.languageforge.client.lex.common.callback.CallbackComm;
import org.palaso.languageforge.client.lex.common.callback.CallbackResultString;
import org.palaso.languageforge.client.lex.common.callback.ICallback;
import org.palaso.languageforge.client.lex.controls.JSNIJQueryWrapper;
import org.palaso.languageforge.client.lex.gatherwords.GatherWordsEventBus;
import org.palaso.languageforge.client.lex.gatherwords.view.GatherWordsFromTextView;
import org.palaso.languageforge.client.lex.main.service.ILexService;
import org.palaso.languageforge.client.lex.model.ResultDto;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.Window;
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

	private JavaScriptObject preAddedFile = null;
	
	public void onStartFromText(SimplePanel panel) {
		panel.setWidget(view.getWidget());
		view.setBtnAddFileEnabled(false);
		view.setUploadFileUplodPanelVisible(false);
		setupFileUploader();
	}

	private void setupFileUploader() {
		ICallback<CallbackResultString> addedCallbackString = new ICallback<CallbackResultString>() {

			@Override
			public void onReturn(CallbackResultString result) {
				view.setFilename(result.getReturnValue());
				if (result.getReturnValue().trim().length() > 0 && result.getAttachedData()!=null) {
					view.setBtnAddFileEnabled(true);
					preAddedFile = result.getAttachedData();
				} else {
					view.setBtnAddFileEnabled(false);
					preAddedFile = null;
				}
			}
		};

		ICallback<CallbackResultString> doneCallbackString = new ICallback<CallbackResultString>() {

			@Override
			public void onReturn(CallbackResultString result) {
				lexService.gatherWordsFromText("", result.getReturnValue().trim(),
						new AsyncCallback<ResultDto>() {
							@Override
							public void onFailure(Throwable caught) {
								eventBus.handleError(caught);
							}

							@Override
							public void onSuccess(ResultDto result) {
								view.clearFileUploader();
								view.setUploadFileUplodPanelVisible(false);
								view.setFileNameBoxVisible(true);
								preAddedFile = null;
								view.setBtnAddFileEnabled(false);
								eventBus.displayMessage(MessageFormat.format(
										I18nConstants.STRINGS
												.GatherWordsFromTextPresenter_X_words_submitted(),
										new Object[] { result.getCode() }));
							}
						});

			}
		};

		ICallback<CallbackResultString> failCallbackString = new ICallback<CallbackResultString>() {

			@Override
			public void onReturn(CallbackResultString result) {
				eventBus.handleError(new Throwable("Failed: "
						+ result.getError().getError()));
				view.setBtnAddFileEnabled(false);
				preAddedFile = null;
			}
		};

		JavaScriptObject jsCallbackAdded = CallbackComm
				.createNativeCallback(addedCallbackString);
		JavaScriptObject jsCallbackDone = CallbackComm
				.createNativeCallback(doneCallbackString);
		JavaScriptObject jsCallbackFail = CallbackComm
				.createNativeCallback(failCallbackString);

		JSNIJQueryWrapper.startjQueryFileUpload("from-text-fileuploader",
				jsCallbackAdded, jsCallbackDone, jsCallbackFail);
	}

	public void bind() {
		view.getAddFileClickedHandlers().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				view.setUploadFileUplodPanelVisible(true);
				view.setFileNameBoxVisible(false);
				view.setBtnAddFileEnabled(false);
				if (preAddedFile!=null)
				{
					JSNIJQueryWrapper.startUploadFileByFileData(preAddedFile);
				}
			}
		});

		view.getAddTextClickedHandlers().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (view.getText().trim().isEmpty()) {
					eventBus.displayMessage(I18nConstants.STRINGS
							.GatherWordsFromTextPresenter_Textbox_can_not_be_empty());
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
										I18nConstants.STRINGS
												.GatherWordsFromTextPresenter_X_words_submitted(),
										new Object[] { result.getCode() }));
							}
						});
			}
		});

	}

	public interface IGatherWordsFromTextView {

		public void setFilename(String text);

		public String getFilename();

		public String getText();

		public HasClickHandlers getAddFileClickedHandlers();

		public HasClickHandlers getAddTextClickedHandlers();

		public HasClickHandlers getFileNameTextboxClickedHandlers();

		public HasValueChangeHandlers<String> getTextValueChangeHandler();

		public void setBtnBrowserEnabled(boolean enabled);

		public void setBtnAddFileEnabled(boolean enabled);

		public void setBtnAddTextEnabled(boolean enabled);

		public void clearTextBox();

		public void clearFileUploader();

		public void setFileNameBoxVisible(boolean visible);

		public void setUploadFileUplodPanelVisible(boolean visible);

		public Widget getWidget();

	}
}
