package org.palaso.languageforge.client.lex.browse.edit;

import org.palaso.languageforge.client.lex.model.LexiconListDto;
import org.palaso.languageforge.client.lex.browse.edit.presenter.AutoSuggestPresenter;
import org.palaso.languageforge.client.lex.browse.edit.presenter.BrowseAndEditMainPresenter;
import org.palaso.languageforge.client.lex.browse.edit.presenter.ButtonBarControlPresenter;
import org.palaso.languageforge.client.lex.browse.edit.presenter.LexBrowseEditListPresenter;
import org.palaso.languageforge.client.lex.browse.edit.presenter.LexBrowseEditPresenter;
import org.palaso.languageforge.client.lex.browse.edit.presenter.BrowseAndEditNavigationBarPresenter;
import org.palaso.languageforge.client.lex.browse.edit.view.BrowseAndEditMainView;
import org.palaso.languageforge.client.lex.main.LexGinModule;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Forward;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;

@Events(startView = BrowseAndEditMainView.class, module = BrowseAndEditModule.class, ginModules = LexGinModule.class)
@Debug(logLevel = LogLevel.DETAILED)
// @Filters( filterClasses = LexDBEEventFilter.class, filterForward = false )
public interface BrowseAndEditEventBus extends EventBus {

	// Parent event handling (Start and Forward)
	@Start
	@Event(handlers = { LexBrowseEditListPresenter.class, LexBrowseEditPresenter.class,
			ButtonBarControlPresenter.class, AutoSuggestPresenter.class, BrowseAndEditNavigationBarPresenter.class })
	void start();

	@Forward
	@Event(handlers = BrowseAndEditMainPresenter.class)
	void forward();

	// Module entry handlers
	@Event(handlers = BrowseAndEditMainPresenter.class)
	void goToLexDicBrowseAndEdit();

	@Event(handlers = { LexBrowseEditListPresenter.class })
	void clientDataRefresh(boolean isUpdate);

	@Event(handlers = LexBrowseEditListPresenter.class)
	void resultListDTO(LexiconListDto result);

	@Event(forwardToParent = true)
	void displayMessage(String message);

	@Event(forwardToParent = true)
	void changeBody(Widget body);

	@Event(handlers = LexBrowseEditPresenter.class)
	void wordSelected(String id);

	@Event(forwardToParent = true)
	void handleError(Throwable caught);

	@Event(handlers = LexBrowseEditListPresenter.class)
	void next();

	@Event(handlers = LexBrowseEditListPresenter.class)
	void previous();

	@Event(handlers = LexBrowseEditListPresenter.class)
	void first();

	@Event(handlers = LexBrowseEditListPresenter.class)
	void last();

	@Event(handlers = LexBrowseEditListPresenter.class)
	void reloadList();

	@Event(handlers = LexBrowseEditPresenter.class)
	void wordCreated();

	@Event(handlers = LexBrowseEditPresenter.class)
	void wordUpdated();

	@Event(handlers = LexBrowseEditPresenter.class)
	void wordDeleted();

	@Event(handlers = BrowseAndEditMainPresenter.class)
	void openBrowseAddMoreWord();

	@Event(handlers = ButtonBarControlPresenter.class)
	void setDeleteButtonVisible(boolean visible);

	@Event(handlers = ButtonBarControlPresenter.class)
	void setUpdateButtonEnable(boolean enabled);

	@Event(handlers = ButtonBarControlPresenter.class)
	void setAddButtonEnable(boolean enabled);
	
	@Event(handlers = { BrowseAndEditNavigationBarPresenter.class })
	void setNavStatus(int startIndex, int endIndex, int numberOfElements);
	
}
