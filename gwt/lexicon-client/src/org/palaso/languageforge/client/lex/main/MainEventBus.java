package org.palaso.languageforge.client.lex.main;


import org.palaso.languageforge.client.lex.addinfo.AddInfoModule;
import org.palaso.languageforge.client.lex.browse.edit.BrowseAndEditModule;
import org.palaso.languageforge.client.lex.common.EntryFieldType;
import org.palaso.languageforge.client.lex.common.CustomLogger;
import org.palaso.languageforge.client.lex.common.IMainEventBus;
import org.palaso.languageforge.client.lex.gatherwords.GatherWordsModule;
import org.palaso.languageforge.client.lex.common.WindowResizeBroadcastInterface;
import org.palaso.languageforge.client.lex.configure.ConfigureModule;
import org.palaso.languageforge.client.lex.dashboard.DashboardModule;
import org.palaso.languageforge.client.lex.main.presenter.LexMainPresenter;
import org.palaso.languageforge.client.lex.main.presenter.MainPresenter;
import org.palaso.languageforge.client.lex.main.presenter.NavPresenter;
import org.palaso.languageforge.client.lex.main.presenter.TopPresenter;
import org.palaso.languageforge.client.lex.main.view.MainView;
import org.palaso.languageforge.client.lex.review.ReviewModule;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.annotation.module.ChildModule;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.client.annotation.module.DisplayChildModuleView;
import com.mvp4g.client.annotation.module.LoadChildModuleError;

@Events(startView = MainView.class, ginModules = LexGinModule.class)
@Debug(logLevel = LogLevel.DETAILED, logger = CustomLogger.class)
@ChildModules({
		@ChildModule(moduleClass = BrowseAndEditModule.class, async = false, autoDisplay = true),
		@ChildModule(moduleClass = AddInfoModule.class, async = false, autoDisplay = true),
		@ChildModule(moduleClass = GatherWordsModule.class, async = false, autoDisplay = true),
		@ChildModule(moduleClass = ConfigureModule.class, async = false, autoDisplay = true),
		@ChildModule(moduleClass = ReviewModule.class, async = false, autoDisplay = true),
		@ChildModule(moduleClass = DashboardModule.class, async = false, autoDisplay = true)})
// @Filters( filterClasses = {}, forceFilters = true )
// @PlaceService( CustomPlaceService.class )
public interface MainEventBus extends IMainEventBus{

	// @InitHistory
	// @Event
	@Start
	@Event(handlers = { TopPresenter.class, NavPresenter.class })
	void start();

	/* Navigation events */
	@Event(modulesToLoad = GatherWordsModule.class , navigationEvent = true)
	void goToGatherFromTexts();
	
	/* Navigation events */
	@Event(modulesToLoad = GatherWordsModule.class , navigationEvent = true)
	void goToGatherFromSemanticDomains();
	
	/* Navigation events */
	@Event(modulesToLoad = GatherWordsModule.class , navigationEvent = true)
	void goToGatherFromWordList();
	
	/* Navigation events */
	@Event(modulesToLoad = ConfigureModule.class , navigationEvent = true)
	void goToConfigureSettings();
	
	/* Navigation events */
	@Event(modulesToLoad = BrowseAndEditModule.class /*
											 * , handlers =
											 * LexMainPresenter.class
											 *//* , name = "companies" */, navigationEvent = true /*
																										 */)
	void goToLexDicBrowseAndEdit();


	
	// //use Integer instead of int here just to test passing object, in real
	// project, you should have int
	 @Event( modulesToLoad = AddInfoModule.class,  navigationEvent = true)
	 void goToLexMissingInfo(EntryFieldType i);

	/* Business events */
	@DisplayChildModuleView({ BrowseAndEditModule.class, AddInfoModule.class,GatherWordsModule.class,ConfigureModule.class,ReviewModule.class,DashboardModule.class })
	@Event(handlers = LexMainPresenter.class)
	void changeBody(Widget newBody);

	@LoadChildModuleError
	@Event(handlers = MainPresenter.class)
	void errorOnLoad(Throwable reason);
	
	@Event(handlers = LexMainPresenter.class)
	void clearMessageBox();
	
	@Event(handlers = LexMainPresenter.class)
	void displayMessage(String message);

	@Event(handlers = LexMainPresenter.class)
	void handleError(Throwable caught);

	@Event(handlers = MainPresenter.class)
	void openNewWindow(String url);
	
	@Event(handlers = NavPresenter.class)
	void taskSettingChanged();
	
	@Event(handlers = MainPresenter.class)
	void reloadLex();

	/* Navigation events */
	@Event(modulesToLoad = ReviewModule.class, navigationEvent = true)
	void goToReviewRecentChanges();

	/* Navigation events */
	@Event(modulesToLoad = DashboardModule.class, navigationEvent = true)
	void goToDashboard();
	
	
	@Event( modulesToLoad = BrowseAndEditModule.class,  navigationEvent = true)
	void openBrowseAddMoreWord();
	
	@Event(broadcastTo = WindowResizeBroadcastInterface.class, passive = true)
	void  mainWindowResize(ResizeEvent event);
}
