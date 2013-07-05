package org.palaso.languageforge.client.lex.common;

import org.palaso.languageforge.client.lex.controls.ProgressBar;
import org.palaso.languageforge.client.lex.controls.ProgressBar.TextFormatter;

import gwtupload.client.BaseUploadStatus;
import gwtupload.client.IUploadStatus;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class FileUploadProgress extends BaseUploadStatus {

	SimplePanel simplePanel;

	ProgressBar progressBar = new ProgressBar(0.0, 100.0, 0.0);;

	TextFormatter formater = new TextFormatter() {
		protected String getText(ProgressBar bar, double curProgress) {
			return fileNameLabel.getText() + "  (" + (int) curProgress + " %)";
		}
	};

	public FileUploadProgress(SimplePanel simplePanel) {
		this.simplePanel = simplePanel;
		setProgressWidget(progressBar);
		
		// move the progress bar back to our panel
		this.simplePanel.setWidget(progressBar);
		progressBar.setWidth("100%");
		progressBar.setTextFormatter(formater);
	}

	@Override
	public void setPercent(int percent) {
		super.setPercent(percent);
		progressBar.setProgress(percent);
		progressBar.setVisible(true);
	}

	@Override
	public void setVisible(boolean v) {
		progressBar.setVisible(v);
	}

	@Override
	public Widget getWidget() {
		return progressBar;
	}

	@Override
	public IUploadStatus newInstance() {
		return new FileUploadProgress(this.simplePanel);
	}

}
