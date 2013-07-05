package org.palaso.languageforge.client.lex.main.presenter;

import org.palaso.languageforge.client.lex.service.BaseService;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcRequestStateListener;
import org.palaso.languageforge.client.lex.main.MainEventBus;
import org.palaso.languageforge.client.lex.main.service.ILexService;
import org.palaso.languageforge.client.lex.main.view.MainView;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = MainView.class)
public class MainPresenter extends BasePresenter<MainPresenter.IView, MainEventBus> {

	@Inject
	public ILexService lexService;

	public interface IView {

		void displayErrorMessage(String message);

		void displayText(String message);

	}

	@Override
	public void bind() {
		((BaseService) lexService).getJsonRpc().addReqestStateListener(new JsonRpcRequestStateListener() {

			@Override
			public void requestStateChanged(boolean requestRunning, boolean isBackgroundRequest) {
				if (!isBackgroundRequest) { // will not show indicator if it is a background request
					if (requestRunning) {
						eventBus.showLoadingIndicator();
					} else {
						eventBus.hideLoadingIndicator();
					}
				}

			}
		});
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				eventBus.mainWindowResize(event);
			}
		});
	}

	public void onErrorOnLoad(Throwable reason) {
		view.displayErrorMessage(reason.getMessage());
	}

	public void onOpenNewWindow(String url) {
		com.google.gwt.user.client.Window.open(url, "_blank", "");
	}

	public void onReloadLex() {
		com.google.gwt.user.client.Window.Location.reload();
	}

}
