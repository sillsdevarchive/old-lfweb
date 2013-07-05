package org.palaso.languageforge.client.lex.review;


import org.palaso.languageforge.client.lex.main.LexGinModule;
import org.palaso.languageforge.client.lex.review.presenter.ReviewMainPresenter;
import org.palaso.languageforge.client.lex.review.view.ReviewMainView;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;

@Events(startView = ReviewMainView.class, module = ReviewModule.class, ginModules = LexGinModule.class)
@Debug(logLevel = LogLevel.DETAILED)
public interface ReviewEventBus extends EventBus {
	
	@Event(handlers = ReviewMainPresenter.class)
	void goToReviewRecentChanges();
	
	
	@Event(forwardToParent = true)
	void changeBody(Widget body);

	@Event(forwardToParent = true)
	void displayMessage(String message);

	@Event(forwardToParent = true)
	void handleError(Throwable caught);


	@Event(forwardToParent = true)
	void openNewWindow(String url);
	

}