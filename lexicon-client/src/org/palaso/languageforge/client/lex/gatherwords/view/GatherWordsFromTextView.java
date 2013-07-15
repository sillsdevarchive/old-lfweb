package org.palaso.languageforge.client.lex.gatherwords.view;

import gwtupload.client.BaseUploadStatus;
import gwtupload.client.IUploader.OnChangeUploaderHandler;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import gwtupload.client.Uploader;

import org.palaso.languageforge.client.lex.gatherwords.presenter.GatherWordsFromTextPresenter.IGatherWordsFromTextView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class GatherWordsFromTextView extends Composite implements
		IGatherWordsFromTextView {

	interface Binder extends UiBinder<Widget, GatherWordsFromTextView> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	@UiField
	Label lblTitle;
	@UiField
	Label lblFileName;
	@UiField
	TextBox txtFileName;
	@UiField
	Uploader textFileUploader;
	@UiField
	Button btnBrowser;
	@UiField
	Button btnAddFile;
	@UiField
	Label lblText;
	@UiField
	TextArea txtArea;
	@UiField
	Button btnAddText;
	@UiField
	SimplePanel progressBarPanel;
	@UiField
	HorizontalPanel fileUploadPanel;
	@UiField
	Button btnCancel;

	public GatherWordsFromTextView() {
		initWidget(binder.createAndBindUi(this));

		// This enables the jsupload progress mechanism through php apc
		textFileUploader.add(
				new Hidden("APC_UPLOAD_PROGRESS", textFileUploader
						.getInputName()), 0);
	}

	@Override
	public String getFilename() {
		return txtFileName.getValue();
	}

	@Override
	public String getText() {
		return txtArea.getValue();
	}

	@Override
	public HasClickHandlers getAddFileClickedHandlers() {
		return btnAddFile;
	}

	@Override
	public HasClickHandlers getAddTextClickedHandlers() {
		return btnAddText;
	}

	@Override
	public void addFileUploaderChangeHandler(OnChangeUploaderHandler handler) {
		textFileUploader.addOnChangeUploadHandler(handler);
	}

	@Override
	public HasValueChangeHandlers<String> getTextValueChangeHandler() {
		return txtArea;
	}

	@Override
	public void setBtnBrowserEnabled(boolean enabled) {
		textFileUploader.setEnabled(enabled);
		btnBrowser.setEnabled(enabled);

	}

	@Override
	public void setBtnAddFileEnabled(boolean enabled) {
		btnAddFile.setEnabled(enabled);

	}

	@Override
	public void setBtnAddTextEnabled(boolean enabled) {
		btnAddText.setEnabled(enabled);

	}

	@Override
	public String getFilenameFromUpLoader() {

		return textFileUploader.getFileName();
	}

	@Override
	public HasClickHandlers getBrowserFileClickedHandlers() {
		return btnBrowser;
	}

	@Override
	public HasClickHandlers getFileNameTextboxClickedHandlers() {
		return txtFileName;
	}

	@Override
	public void setFilename(String text) {
		txtFileName.setValue(text);

	}

	@Override
	public void submitFile() {
		textFileUploader.submit();
	}

	@Override
	public void addOnStartUploaderHandler(OnStartUploaderHandler handler) {
		textFileUploader.addOnStartUploadHandler(handler);
	}

	@Override
	public void addOnFinishUploaderHandler(OnFinishUploaderHandler handler) {
		textFileUploader.addOnFinishUploadHandler(handler);
	}

	@Override
	public void clearTextBox() {
		txtArea.setValue("");

	}

	@Override
	public void clearFileUploader() {
		txtFileName.setValue("");
	}

	@Override
	public void setAvoidEmptyFiles(boolean avoid) {
		textFileUploader.avoidEmptyFiles(avoid);
	}

	@Override
	public void setFileNameBoxVisible(boolean visible) {
		txtFileName.setVisible(visible);

	}

	@Override
	public SimplePanel getProgressBarPanel() {
		return progressBarPanel;
	}

	@Override
	public void setUploadProgressBar(BaseUploadStatus baseUploadStatus) {
		textFileUploader.setStatusWidget(baseUploadStatus);

	}

	@Override
	public void setUploadFileUplodPanelVisible(boolean visible) {
		fileUploadPanel.setVisible(visible);
	}

	@Override
	public void cancelUpload() {
		textFileUploader.cancel();
	}

	@Override
	public HasClickHandlers getUploadCancelClickedHandlers() {
		return btnCancel;
	}

	@Override
	public Widget getWidget() {
		return this.asWidget();
	}

	@Override
	public Uploader getUploader() {
		return textFileUploader;
	}

}
