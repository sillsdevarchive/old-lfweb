/**
 * A view for button control for the Dictionary browse and edit
 * @author Kandasamy
 * @version 0.1
 * @since July 2011
 */
package org.palaso.languageforge.client.lex.browse.edit.view;

import org.palaso.languageforge.client.lex.browse.edit.presenter.ButtonBarControlPresenter;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
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
	Button newButton;
	@UiField
	Button saveButton;
	@UiField
	Button deleteButton;

	public ButtonBarControlView() {
		initWidget(binder.createAndBindUi(this));
	}

	public HasClickHandlers getAddButtonClickHandlers() {
		return newButton;
	}

	public HasClickHandlers getUpdateButtonClickHandlers() {
		return saveButton;
	}

	public HasClickHandlers getDeleteButtonClickHandlers() {
		return deleteButton;
	}

	public Widget getViewWidget() {
		return this;
	}

	@Override
	public void setDeleteVisible(boolean visible) {
		deleteButton.setVisible(visible);
	}

	@Override
	public void setUpdateButtonEnable(boolean enabled) {
		saveButton.setEnabled(enabled);	
	}

	@Override
	public void setAddButtonEnable(boolean enabled) {
		newButton.setEnabled(enabled);		
	}

}
