package org.palaso.languageforge.client.lex.dashboard.view;

import org.palaso.languageforge.client.lex.controls.ExtendedComboBox;
import org.palaso.languageforge.client.lex.controls.ProgressLabel;
import org.palaso.languageforge.client.lex.controls.LineChart;
import org.palaso.languageforge.client.lex.dashboard.presenter.DashboardMainPresenter.IDashboardView;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DashboardMainView extends Composite implements IDashboardView {

	interface Binder extends UiBinder<Widget, DashboardMainView> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	
	
	@UiField
	LineChart lineChart;
	@UiField Label lblWordCount;
	@UiField Label lblPOS;
	@UiField Label lblMeanings;
	@UiField Label lblExamples;
	@UiField Button btnAddMoreWords;
	@UiField Button btnAddMorePos;
	@UiField Button btnAddMoreMeanings;
	@UiField Button btnAddMoreExamples;
	@UiField ProgressLabel progressWordCount;
	@UiField ProgressLabel progressPOS;
	@UiField ProgressLabel progressMeanings;
	@UiField ProgressLabel progressExamples;
	@UiField ExtendedComboBox activityTimeRangeListBox;
	@UiField Image updatingImage;
	@UiField HTMLPanel updatingPanel;
	@UiField Label updatingLabel;
	



	public DashboardMainView() {
		initWidget(binder.createAndBindUi(this));
		updatingImage.setUrl(GWT.getModuleBaseURL() +"/images/dashboardupdating.gif");
		updatingPanel.setVisible(false); //default hiden
	}


	@Override
	public LineChart getLineChart() {
		return lineChart;
	}


	@Override
	public HasClickHandlers getAddMoreWordsButtonClickHandlers() {
		return btnAddMoreWords;
	}


	@Override
	public HasClickHandlers getAddMorePosButtonClickHandlers() {
		return btnAddMorePos;
	}




	@Override
	public HasClickHandlers getAddMoreMeaningsButtonClickHandlers() {
		return btnAddMoreMeanings;
	}


	@Override
	public HasClickHandlers getAddMoreExamplesButtonClickHandlers() {
		return btnAddMoreExamples;
	}


	@Override
	public ProgressLabel getProgressWordCount() {
		return progressWordCount;
	}


	@Override
	public ProgressLabel getProgressPOS() {
		return progressPOS;
	}

	@Override
	public ProgressLabel getProgressMeanings() {
		return progressMeanings;
	}


	@Override
	public ProgressLabel getProgressExamples() {
		return progressExamples;
	}
	



	@Override
	public void setAddMoreWordsButtonVisible(boolean visible) {
		btnAddMoreWords.setVisible(visible);
		
	}


	@Override
	public void setAddMorePosButtonVisible(boolean visible) {
		btnAddMorePos.setVisible(visible);
	}


	@Override
	public void setAddMoreMeaningsButtonVisible(boolean visible) {
		btnAddMoreMeanings.setVisible(visible);
		
	}


	@Override
	public void setAddMoreExamplesButtonVisible(boolean visible) {
		btnAddMoreExamples.setVisible(visible);
		
	}


	@Override
	public ExtendedComboBox getActivityTimeRangeListBox() {
		return activityTimeRangeListBox;
	}


	@Override
	public HTMLPanel getUpdatingPanel() {
		return updatingPanel;
	}


	@Override
	public Image getUpdatingPanelImage() {
		return updatingImage;
	}


	@Override
	public Label getUpdatingPanelLabel() {
		return updatingLabel;
	}

}
