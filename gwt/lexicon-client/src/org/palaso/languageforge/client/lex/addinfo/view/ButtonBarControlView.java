/**
 * A view for button control for the Dictionary browse and edit
 * @author Kandasamy
 * @version 0.1
 * @since July 2011
 */
package org.palaso.languageforge.client.lex.addinfo.view;

import org.palaso.languageforge.client.lex.addinfo.presenter.ButtonBarControlPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class ButtonBarControlView extends Composite implements
		ButtonBarControlPresenter.IButtonControlView {

	@UiTemplate("ButtonBarControlView.ui.xml")
	interface Binder extends UiBinder<Widget, ButtonBarControlView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	Button prevButton;
	@UiField
	Button saveButton;
	@UiField
	Button nextButton;

	public ButtonBarControlView() {
		initWidget(binder.createAndBindUi(this));
	}

	public HasClickHandlers getPrevButtonClickHandlers() {
		return prevButton;
	}

	public HasClickHandlers getUpdateButtonClickHandlers() {
		return saveButton;
	}

	public HasClickHandlers getNextButtonClickHandlers() {
		return nextButton;
	}

	public Widget getViewWidget() {
		return this;
	}

	@Override
	public void setPrevButtonEnabled(boolean enabled) {
		prevButton.setEnabled(enabled);
		
	}

	@Override
	public void setNextButtonEnabled(boolean enabled) {
		nextButton.setEnabled(enabled);
	}

}
