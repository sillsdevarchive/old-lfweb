package org.palaso.languageforge.client.lex.main.presenter;

import org.palaso.languageforge.client.lex.main.MainEventBus;
import org.palaso.languageforge.client.lex.main.service.ILexService;
import org.palaso.languageforge.client.lex.main.view.LexMainView;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = LexMainView.class)
public class LexMainPresenter extends BasePresenter<LexMainPresenter.ILexMainView, MainEventBus> {

	@Inject
	public ILexService lexService;

	public interface ILexMainView {

		void changeBody(Widget body);

		void showMessage(String text, boolean success);

		void clearAllmessage();

	}

	public void onDisplayMessage(String message) {
		// view.displayText(message);
		view.showMessage(message, true);
	}

	public void onHandleError(Throwable reason) {
		// view.displayErrorMessage(reason.getMessage());
		view.showMessage(reason.getMessage(), false);
	}

	public void onClearMessageBox() {
		view.clearAllmessage();
	}

	public void onChangeBody(Widget body) {
		view.changeBody(body);
	}

}
