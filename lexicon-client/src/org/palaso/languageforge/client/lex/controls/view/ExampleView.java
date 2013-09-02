package org.palaso.languageforge.client.lex.controls.view;

import org.palaso.languageforge.client.lex.controls.presenter.ExamplePresenter.IExampleView;
import org.palaso.languageforge.client.lex.controls.presenter.MultiTextPresenter.IMultiTextView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ExampleView extends Composite implements IExampleView {

	interface Binder extends UiBinder<Widget, ExampleView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	MultiTextView mutTxtExample;
	@UiField
	MultiTextView mutTxtTranslation;
	@UiField
	Label exampleLabel;
	@UiField
	Label translationLabel;
	@UiField
	HTMLPanel translationPanel;
	@UiField
	HTMLPanel examplePanel;
	@UiField
	Button btnRemoveExample;

	public ExampleView() {
		initWidget(binder.createAndBindUi(this));
	}

	public Widget getWidget() {
		return this.asWidget();
	}

	public ExampleView(IMultiTextView multiTextView,
			IMultiTextView translationView) {
		mutTxtExample = (MultiTextView) multiTextView;
		mutTxtTranslation = (MultiTextView) translationView;
	}

	@Override
	public IMultiTextView getExampleMultiText() {
		return mutTxtExample;
	}

	@Override
	public IMultiTextView getTranslationMultiText() {
		return mutTxtTranslation;
	}

	@Override
	public Label getExampleLabel() {
		return exampleLabel;
	}

	@Override
	public Label getTranslationLabel() {
		return translationLabel;
	}

	@Override
	public void setTranslationPanelVisible(boolean visible) {
		translationPanel.setVisible(visible);
	}
	
	@Override
	public HasClickHandlers getRemoveButtonClickHandlers()
	{
		return btnRemoveExample;
	}

	@Override
	public void setExamplePanelVisible(boolean visible) {
		examplePanel.setVisible(visible);
	}

	@Override
	public void setRemoveButtonVisible(boolean visible) {
		btnRemoveExample.setVisible(visible);
	}

}
