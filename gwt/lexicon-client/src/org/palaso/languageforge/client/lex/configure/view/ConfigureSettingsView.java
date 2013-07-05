package org.palaso.languageforge.client.lex.configure.view;

import org.palaso.languageforge.client.lex.controls.ExtendedComboBox;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingsPresenter.IConfigureSettingsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.touch.client.Point;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class ConfigureSettingsView extends Composite implements
		IConfigureSettingsView {

	interface Binder extends UiBinder<Widget, ConfigureSettingsView> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	@UiField
	ExtendedComboBox userNameListBox;
	@UiField
	Button btnApply;
	@UiField
	SimplePanel inputSystemPanel;
	@UiField
	SimplePanel fieldsPanel;
	@UiField
	SimplePanel tasksPanel;
	@UiField
	SimplePanel propertiesPanel;
	@UiField
	SimplePanel membersPanel;
	@UiField
	TabLayoutPanel tabPanel;
	@UiField
	SimplePanel userNameListBoxPanel;
	public ConfigureSettingsView() {
		initWidget(binder.createAndBindUi(this));
		tabPanel.addStyleName("setting_tab_box_position");
	}

	public Widget getWidget() {
		return this.asWidget();
	}

	@Override
	public SimplePanel getInputSystemsPanel() {
		return inputSystemPanel;
	}

	@Override
	public SimplePanel getFieldsPanel() {
		return fieldsPanel;
	}

	@Override
	public SimplePanel getTasksPanel() {
		return tasksPanel;
	}

	@Override
	public SimplePanel getPropertiesPanel() {
		return propertiesPanel;
	}

	@Override
	public SimplePanel getMembersPanel() {
		return membersPanel;
	}
	
	@Override
	public HasClickHandlers getApplyClickedHandlers() {
		return btnApply;
	}

	@Override
	public ExtendedComboBox getUserListBox() {
		return userNameListBox;
	}

	@Override
	public SimplePanel getUserListBoxPanel() {
		return userNameListBoxPanel;
	}
	
	@Override
	public Point getBtnApplyLocation() {
		return new Point(btnApply.getAbsoluteLeft(), btnApply.getAbsoluteTop());
	}

	@Override
	public TabLayoutPanel getTabPanel() {
		return tabPanel;
	}

	@Override
	public void setApplyButtonVisible(boolean visible) {
		btnApply.setVisible(visible);	
	}

	
}
