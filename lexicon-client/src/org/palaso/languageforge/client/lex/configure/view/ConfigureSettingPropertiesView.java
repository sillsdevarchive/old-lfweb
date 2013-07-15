package org.palaso.languageforge.client.lex.configure.view;

import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingPropertiesPresenter.IConfigureSettingPropertiesView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class ConfigureSettingPropertiesView extends Composite implements
		IConfigureSettingPropertiesView {

	interface Binder extends UiBinder<Widget, ConfigureSettingPropertiesView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField TextBox txtProjectName;
	@UiField TextBox txtProjectType;

	public ConfigureSettingPropertiesView() {
		initWidget(binder.createAndBindUi(this));
	}

	public Widget getWidget() {
		return this.asWidget();
	}

	@Override
	public TextBox getNameTextBox() {
		return txtProjectName;
	}

	@Override
	public TextBox getTypeTextBox() {
		return txtProjectType;
	}
	


}
