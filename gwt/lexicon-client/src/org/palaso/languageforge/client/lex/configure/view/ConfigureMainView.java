package org.palaso.languageforge.client.lex.configure.view;

import org.palaso.languageforge.client.lex.configure.presenter.ConfigureMainPresenter.IConfigureMainView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class ConfigureMainView extends Composite implements
		IConfigureMainView {

	interface Binder extends UiBinder<Widget, ConfigureMainView> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	@UiField
	SimplePanel lblPanelCol;

	public ConfigureMainView() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public SimplePanel getWidgetPanel() {
		return lblPanelCol;
	}

}
