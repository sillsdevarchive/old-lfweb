package org.palaso.languageforge.client.lex.configure.presenter;


import org.palaso.languageforge.client.lex.configure.ConfigureEventBus;
import org.palaso.languageforge.client.lex.configure.view.ConfigureMainView;
import org.palaso.languageforge.client.lex.main.service.ILexService;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = ConfigureMainView.class)
public class ConfigureMainPresenter
		extends
		BasePresenter<ConfigureMainPresenter.IConfigureMainView,ConfigureEventBus> {
	@Inject
	public ILexService lexService;


	public void onGoToConfigureSettings() {
		eventBus.startSettings(view.getWidgetPanel());
	}

	public interface IConfigureMainView {
		public SimplePanel getWidgetPanel();
	}
}
