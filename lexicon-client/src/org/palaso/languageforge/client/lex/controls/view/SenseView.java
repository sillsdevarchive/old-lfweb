package org.palaso.languageforge.client.lex.controls.view;

import org.palaso.languageforge.client.lex.controls.ExtendedComboBox;
import org.palaso.languageforge.client.lex.controls.presenter.ExamplePresenter.IExampleView;
import org.palaso.languageforge.client.lex.controls.presenter.MultiTextPresenter.IMultiTextView;
import org.palaso.languageforge.client.lex.controls.presenter.SensePresenter.ISenseView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SenseView extends Composite implements ISenseView {

	interface Binder extends UiBinder<Widget, SenseView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	// @UiField
	// Label lblTopMeaning;
	@UiField(provided = true)
	MultiTextView mutTxtMeaning;

	@UiField
	Label lblMeaning;

	@UiField
	Label lblPos;
	@UiField
	ExtendedComboBox cmbPos;
	@UiField
	HTMLPanel exampleTranslationHtmlPanel;

	@UiField
	HTMLPanel partOfSpeechHtmlPanel;

	@UiField
	Anchor addMoreExampleLink;
	@UiField
	Button btnRemove;

	public SenseView() {
		this(new MultiTextView());
	}

	public SenseView(IMultiTextView multiTextView) {
		mutTxtMeaning = (MultiTextView) multiTextView;
		initWidget(binder.createAndBindUi(this));
	}

	public Widget getWidget() {
		return this;
	}

	@Override
	public IMultiTextView createMultiTextView() {
		return new MultiTextView();
	}

	@Override
	public IMultiTextView getMeaningMultiText() {
		return mutTxtMeaning;
	}

	@Override
	public IExampleView createExampleView() {
		return new ExampleView();
	}

	@Override
	public String getSelectedPOS() {
		if (cmbPos.getSelectedIndex()>=0)
		{
			return cmbPos.getValue(cmbPos.getSelectedIndex());
		}else
		{
			return "";
		}
	}

	@Override
	public ExtendedComboBox getPartOfSpeechListBox() {
		return cmbPos;
	}

	@Override
	public Label getPosLabel() {
		return lblPos;
	}

	@Override
	public HasClickHandlers getAddNewExampleClickHandlers() {
		return addMoreExampleLink;
	}

	@Override
	public Label getMeaningLabel() {
		return lblMeaning;
	}

	@Override
	public void setMeaningLabelVisible(boolean visible) {
		lblMeaning.setVisible(visible);
	}

	@Override
	public void setAddNewButtonVisible(boolean visible) {
		addMoreExampleLink.setVisible(visible);
	}

	@Override
	public void addExampleAndTranslation(String exampleLabel,
			String translatioLabel, IExampleView view) {
		view.getExampleLabel().setText(exampleLabel);
		view.getTranslationLabel().setText(translatioLabel);
		exampleTranslationHtmlPanel.add(view.getWidget());
	}

	@Override
	public HasClickHandlers getRemoveSenseClickHandlers() {
		return btnRemove;
	}

	@Override
	public void setRemoveSenseVisible(boolean visible) {
		btnRemove.setVisible(visible);
	}

	@Override
	public void removeExampleAndTranslation(IExampleView view) {
		exampleTranslationHtmlPanel.remove(view.getWidget());
	}

	@Override
	public void setPartOfSpeechPanelVisible(boolean visible) {
		partOfSpeechHtmlPanel.setVisible(visible);
	}
}
