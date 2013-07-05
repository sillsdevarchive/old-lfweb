package org.palaso.languageforge.client.lex.configure.view;

import org.palaso.languageforge.client.lex.controls.ExtendedComboBox;
import org.palaso.languageforge.client.lex.controls.ExtendedTextBox;
import org.palaso.languageforge.client.lex.controls.FastTree;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingTasksPresenter.IConfigureSettingTasksView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class ConfigureSettingTasksView extends Composite implements
		IConfigureSettingTasksView {

	interface Binder extends UiBinder<Widget, ConfigureSettingTasksView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	FastTree tasksTree;
	
	@UiField
	ExtendedComboBox domainLanguageListBox;
	
	@UiField
	ExtendedComboBox activityTimeRangeListBox;
	
	@UiField
	ExtendedTextBox targerWordCountTextbox;
	
	@UiField
	FastTree subSettingTree;

	@UiField
	HTMLPanel subSettingSemanticDomainsHtmlPanel;

	@UiField
	HTMLPanel subSettingDashboardHtmlPanel;
	
	public ConfigureSettingTasksView() {
		initWidget(binder.createAndBindUi(this));
	}

	public Widget getWidget() {
		return this.asWidget();
	}

	public FastTree getTasksTree() {
		return tasksTree;
	}

	@Override
	public void setSubSettingSemanticDomainsPanelVisible(boolean visible) {
		subSettingSemanticDomainsHtmlPanel.setVisible(visible);
	}

	@Override
	public FastTree getSemanticDomainsSubSettingTree() {
		return subSettingTree;
	}

	@Override
	public ExtendedComboBox getDomainLanguageListBox() {
		return domainLanguageListBox;
	}

	@Override
	public void setSubSettingDashboardPanelVisible(boolean visible) {
		subSettingDashboardHtmlPanel.setVisible(visible);		
	}

	@Override
	public ExtendedComboBox getActivityTimeRangeListBox() {
		return activityTimeRangeListBox;
	}

	@Override
	public ExtendedTextBox getTargerWordCountTextbox() {
		return targerWordCountTextbox;
	}

}
