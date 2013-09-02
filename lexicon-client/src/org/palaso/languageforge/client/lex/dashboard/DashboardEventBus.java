package org.palaso.languageforge.client.lex.dashboard;


import org.palaso.languageforge.client.lex.common.enums.EntryFieldType;
import org.palaso.languageforge.client.lex.dashboard.presenter.DashboardMainPresenter;
import org.palaso.languageforge.client.lex.dashboard.view.DashboardMainView;
import org.palaso.languageforge.client.lex.main.LexGinModule;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;

@Events(startView = DashboardMainView.class, module = DashboardModule.class, ginModules = LexGinModule.class)
@Debug(logLevel = LogLevel.DETAILED)
public interface DashboardEventBus extends EventBus {
	
	@Event(handlers = DashboardMainPresenter.class)
	void goToDashboard();
	
	@Event (handlers = DashboardMainPresenter.class) 
	public void mainWindowResize(ResizeEvent event); 
	
	@Event(forwardToParent = true)
	void changeBody(Widget body);

	@Event(forwardToParent = true)
	void displayMessage(String message);

	@Event(forwardToParent = true)
	void handleError(Throwable caught);

	 @Event(forwardToParent = true)
	 void goToLexMissingInfo(EntryFieldType i);

	@Event(forwardToParent = true)
	void openBrowseAddMoreWord();

}