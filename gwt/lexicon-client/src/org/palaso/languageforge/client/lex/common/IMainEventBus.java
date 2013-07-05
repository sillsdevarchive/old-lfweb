package org.palaso.languageforge.client.lex.common;

import org.palaso.languageforge.client.lex.presenter.LoadingProgressIndicatorWindowPresenter;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.module.AfterLoadChildModule;
import com.mvp4g.client.annotation.module.BeforeLoadChildModule;
import com.mvp4g.client.event.EventBus;

public interface IMainEventBus extends EventBus {
	@BeforeLoadChildModule
	@Event(handlers = LoadingProgressIndicatorWindowPresenter.class)
	void showLoadingIndicator();
	
	@AfterLoadChildModule
	@Event(handlers = LoadingProgressIndicatorWindowPresenter.class)
	void hideLoadingIndicator();
}
