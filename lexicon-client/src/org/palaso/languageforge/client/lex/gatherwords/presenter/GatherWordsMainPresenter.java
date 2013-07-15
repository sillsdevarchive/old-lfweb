package org.palaso.languageforge.client.lex.gatherwords.presenter;

import org.palaso.languageforge.client.lex.gatherwords.GatherWordsEventBus;
import org.palaso.languageforge.client.lex.gatherwords.view.GatherWordsMainView;
import org.palaso.languageforge.client.lex.main.service.ILexService;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = GatherWordsMainView.class)
public class GatherWordsMainPresenter
		extends
		BasePresenter<GatherWordsMainPresenter.IGatherWordsMainView, GatherWordsEventBus> {
	@Inject
	public ILexService lexService;


	public void onGoToGatherFromTexts() {
		eventBus.startFromText(view.getWidgetPanel());
	}

	public void onGoToGatherFromSemanticDomains() {
		eventBus.startFromSemanticDomain(view.getWidgetPanel());
	}
	
	public void onGoToGatherFromWordList() {
		eventBus.startWordList(view.getWidgetPanel());
	}

	public interface IGatherWordsMainView {
		public SimplePanel getWidgetPanel();
	}
}
