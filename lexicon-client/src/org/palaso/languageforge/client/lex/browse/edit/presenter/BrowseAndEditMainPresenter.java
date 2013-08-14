package org.palaso.languageforge.client.lex.browse.edit.presenter;

import org.palaso.languageforge.client.lex.browse.edit.BrowseAndEditEventBus;
import org.palaso.languageforge.client.lex.browse.edit.view.BrowseAndEditMainView;
import org.palaso.languageforge.client.lex.common.ConsoleLog;
import org.palaso.languageforge.client.lex.main.service.ILexService;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = BrowseAndEditMainView.class)
public class BrowseAndEditMainPresenter extends
		BasePresenter<BrowseAndEditMainPresenter.IView, BrowseAndEditEventBus> {
	@Inject
	public ILexService LexService;
	public interface IView {
		Widget getAsWidget();
	}

	public void onForward() {
	}

	public void onGoToLexDicBrowseAndEdit() {
		ConsoleLog.log("onGoToLexDicBrowseAndEdit reset cache");
		LexService.resetCache();
		eventBus.clientDataRefresh(false);
	}

	
	public void onOpenBrowseAddMoreWord(){
		//view.getAsWidget().getParent().addStyleName("div-no-hide");
	}
}
