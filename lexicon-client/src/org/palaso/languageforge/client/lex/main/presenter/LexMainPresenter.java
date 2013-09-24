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
		view.showMessage(getMessage(reason), false);
	}

	private static String getMessage (Throwable throwable) {
	    String ret="";
	    while (throwable!=null) {
	            if (throwable instanceof com.google.gwt.event.shared.UmbrellaException){
	                    for (Throwable thr2 :((com.google.gwt.event.shared.UmbrellaException)throwable).getCauses()){
	                            if (ret != "")
	                                    ret += "\nCaused by: ";
	                            ret += thr2.toString();
	                            ret += "\n  at "+getMessage(thr2);
	                    }
	            } else if (throwable instanceof com.google.web.bindery.event.shared.UmbrellaException){
	                    for (Throwable thr2 :((com.google.web.bindery.event.shared.UmbrellaException)throwable).getCauses()){
	                            if (ret != "")
	                                    ret += "\nCaused by: ";
	                            ret += thr2.toString();
	                            ret += "\n  at "+getMessage(thr2);
	                    }
	            } else {
	                    if (ret != "")
	                            ret += "\nCaused by: ";
	                    ret += throwable.toString();
	                    for (StackTraceElement sTE : throwable.getStackTrace())
	                            ret += "\n  at "+sTE;
	            }
	            throwable = throwable.getCause();
	    }

	    return ret;
	}
	
	public void onClearMessageBox() {
		view.clearAllmessage();
	}

	public void onChangeBody(Widget body) {
		view.changeBody(body);
	}

}
