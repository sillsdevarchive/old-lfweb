package org.palaso.languageforge.client.lex.configure.view;

import org.palaso.languageforge.client.lex.controls.ExtendedCheckBox;
import org.palaso.languageforge.client.lex.controls.ExtendedTextBox;
import org.palaso.languageforge.client.lex.controls.FastTree;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingFieldsPresenter.IConfigureSettingFieldsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class ConfigureSettingFieldsView extends Composite implements
		IConfigureSettingFieldsView {

	interface Binder extends UiBinder<Widget, ConfigureSettingFieldsView> {
	}
	
	
	@UiField FastTree inputSystemsTree;
	@UiField FastTree fieldsTree;
	
	@UiField Button btnDown;
	@UiField Button btnUp;

	@UiField ExtendedTextBox txtNameOfDisplay;
	@UiField ExtendedCheckBox chkboxNormallyHidden;
	@UiField TabLayoutPanel fieldTabPanel;
	
	
	private static final Binder binder = GWT.create(Binder.class);

	public ConfigureSettingFieldsView() {
		initWidget(binder.createAndBindUi(this));
	}

	public Widget getWidget() {
		return this.asWidget();
	}

	@Override
	public FastTree getInputSystemsTree() {
		return inputSystemsTree;
	}
	
	@Override
	public FastTree getFieldsTree() {
		return fieldsTree;
	}

	@Override
	public HasClickHandlers getInputSystemMoveUpClickHandlers() {
		return btnUp;
	}

	@Override
	public HasClickHandlers getInputSystemMoveDownClickHandlers() {
		return btnDown;
	}

	@Override
	public ExtendedTextBox getSetupNameTextBox() {
		return txtNameOfDisplay;
	}

	@Override
	public ExtendedCheckBox getSetupHideIfEmptyToggleCheckbox() {
		return chkboxNormallyHidden;
	}
	
	@Override
	public TabLayoutPanel getFieldDetailTabPanel()
	{
		return fieldTabPanel;
	}
}
