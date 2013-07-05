package org.palaso.languageforge.client.lex.gatherwords;


import org.palaso.languageforge.client.lex.gatherwords.presenter.GatherWordsFromSemanticDomainPresenter;
import org.palaso.languageforge.client.lex.gatherwords.presenter.GatherWordsFromTextPresenter;
import org.palaso.languageforge.client.lex.gatherwords.presenter.GatherWordsFromWordListPresenter;
import org.palaso.languageforge.client.lex.gatherwords.presenter.GatherWordsMainPresenter;
import org.palaso.languageforge.client.lex.gatherwords.view.GatherWordsMainView;
import org.palaso.languageforge.client.lex.main.LexGinModule;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;


@Debug(logLevel = LogLevel.DETAILED)
@Events(startView = GatherWordsMainView.class, module = GatherWordsModule.class, ginModules = LexGinModule.class)
public interface GatherWordsEventBus extends EventBus {
	
    @Event(handlers = GatherWordsMainPresenter.class)
    void goToGatherFromTexts();
    
    
    @Event(handlers = GatherWordsMainPresenter.class)
    void goToGatherFromSemanticDomains();
    
    @Event(handlers = GatherWordsMainPresenter.class)
    void goToGatherFromWordList();
    
    @Event(handlers = GatherWordsFromSemanticDomainPresenter.class)
    public void startFromSemanticDomain(SimplePanel panel);
    
    @Event(handlers = GatherWordsFromTextPresenter.class)
    public void startFromText(SimplePanel panel);
    
    @Event(handlers = GatherWordsFromWordListPresenter.class)
    public void startWordList(SimplePanel panel);

    @Event(forwardToParent = true)
    void changeBody(Widget body);

    @Event(forwardToParent = true)
    void displayMessage(String message);

    @Event(forwardToParent = true)
    void handleError(Throwable caught);

}
