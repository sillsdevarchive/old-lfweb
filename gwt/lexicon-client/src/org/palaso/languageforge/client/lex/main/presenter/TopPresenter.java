package org.palaso.languageforge.client.lex.main.presenter;

import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.main.MainEventBus;
import org.palaso.languageforge.client.lex.main.view.TopPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = TopPanel.class)
public class TopPresenter extends
		BasePresenter<TopPresenter.IView, MainEventBus> {

	public interface IView {
		HasClickHandlers getAboutLinkClickHandlers();

		HasClickHandlers getHomeLinkClickHandlers();

		HasClickHandlers getSignOutLinkClickHandlers();
	}

	@Override
	public void bind() {
		view.getAboutLinkClickHandlers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.displayMessage(I18nConstants.STRINGS.TopPresenter_About_clicked());
			}
		});

		view.getHomeLinkClickHandlers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.displayMessage(I18nConstants.STRINGS.TopPresenter_Home_clicked());
			}
		});

		view.getSignOutLinkClickHandlers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.displayMessage(I18nConstants.STRINGS.TopPresenter_Sign_Out_clicked());
			}
		});
	}

	public void onStart() {

	}

}
