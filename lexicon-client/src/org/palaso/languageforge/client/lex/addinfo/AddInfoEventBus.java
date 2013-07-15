package org.palaso.languageforge.client.lex.addinfo;

import org.palaso.languageforge.client.lex.common.EntryFieldType;
import org.palaso.languageforge.client.lex.addinfo.presenter.AddInfoMainPresenter;
import org.palaso.languageforge.client.lex.addinfo.presenter.ButtonBarControlPresenter;
import org.palaso.languageforge.client.lex.addinfo.presenter.IncompleteWordEditPresenter;
import org.palaso.languageforge.client.lex.addinfo.presenter.IncompleteWordListPresenter;
import org.palaso.languageforge.client.lex.addinfo.presenter.AddInfoNavigationBarPresenter;
import org.palaso.languageforge.client.lex.addinfo.view.AddInfoMainView;
import org.palaso.languageforge.client.lex.main.LexGinModule;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;

@Events(startView = AddInfoMainView.class, module = AddInfoModule.class, ginModules = LexGinModule.class)
@Debug(logLevel = LogLevel.DETAILED)
public interface AddInfoEventBus extends EventBus {

	@Event(handlers = AddInfoMainPresenter.class)
	void openAddInfoAddMoreMeanings();

	@Event(handlers = AddInfoMainPresenter.class)
	void openAddInfoAddMoreExamples();

	@Event(handlers = AddInfoMainPresenter.class)
	void openAddInfoAddMorePartsOfSpeech();

	@Event(handlers = AddInfoMainPresenter.class)
	void goToLexMissingInfo(EntryFieldType i);

	@Event(handlers = IncompleteWordEditPresenter.class)
	void resetEditorView();

	@Event(handlers = { IncompleteWordListPresenter.class })
	void startModule(EntryFieldType i);

	@Event(handlers = { IncompleteWordListPresenter.class })
	void clientDataRefresh(EntryFieldType i);

	@Event(handlers = { AddInfoNavigationBarPresenter.class })
	void setNavStatus(int startIndex, int endIndex, int numberOfElements);

	@Event(handlers = IncompleteWordListPresenter.class)
	void next();

	@Event(handlers = IncompleteWordListPresenter.class)
	void previous();

	@Event(handlers = IncompleteWordListPresenter.class)
	void first();

	@Event(handlers = IncompleteWordListPresenter.class)
	void last();

	@Event(forwardToParent = true)
	void changeBody(Widget body);

	@Event(handlers = IncompleteWordEditPresenter.class)
	void wordSelected(String id);

	@Event(forwardToParent = true)
	void handleError(Throwable caught);

	@Event(handlers = ButtonBarControlPresenter.class)
	void setButtonControl();

	@Event(handlers = IncompleteWordEditPresenter.class)
	void wordUpdated();

	@Event(forwardToParent = true)
	void displayMessage(String message);

	@Event(handlers = IncompleteWordListPresenter.class)
	void previousWord();

	@Event(handlers = IncompleteWordListPresenter.class)
	void nextWord();

	@Event(handlers = IncompleteWordListPresenter.class)
	void setNavigationButtonStatus();

	@Event(handlers = IncompleteWordEditPresenter.class)
	void noWordsToDisplay();
	
	@Event(handlers = ButtonBarControlPresenter.class)
	void setNextButtonEnabled(boolean enabled);

	@Event(handlers = ButtonBarControlPresenter.class)
	void setPrevButtonEnabled(boolean enabled);

	
	
}