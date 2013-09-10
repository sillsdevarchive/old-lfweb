package org.palaso.languageforge.client.lex.gatherwords.view;


import org.palaso.languageforge.client.lex.gatherwords.presenter.GatherWordsFromTextPresenter.IGatherWordsFromTextView;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
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
	Button btnAddFile;
	@UiField
	Label lblText;
	@UiField
	TextArea txtArea;
	@UiField
	Button btnAddText;
	@UiField
	HorizontalPanel fileUploadPanel;
	
	@UiField
	SimplePanel uploadProgressBar;


	public GatherWordsFromTextView() {
		initWidget(binder.createAndBindUi(this));
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
	public HasValueChangeHandlers<String> getTextValueChangeHandler() {
		return txtArea;
	}

	@Override
	public void setBtnBrowserEnabled(boolean enabled) {
	
		//btnBrowser.setEnabled(enabled);

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
	public HasClickHandlers getFileNameTextboxClickedHandlers() {
		return txtFileName;
	}

	@Override
	public void setFilename(String text) {
		txtFileName.setValue(text);

	}


	@Override
	public void clearTextBox() {
		txtArea.setValue("");

	}

	@Override
	public void clearFileUploader() {
		txtFileName.setValue("");
		uploadProgressBar.setWidth("0px");
	}


	@Override
	public void setFileNameBoxVisible(boolean visible) {
		txtFileName.setVisible(visible);

	}


	@Override
	public void setUploadFileUplodPanelVisible(boolean visible) {
		fileUploadPanel.setVisible(visible);
	}


	@Override
	public Widget getWidget() {
		return this.asWidget();
	}

}
