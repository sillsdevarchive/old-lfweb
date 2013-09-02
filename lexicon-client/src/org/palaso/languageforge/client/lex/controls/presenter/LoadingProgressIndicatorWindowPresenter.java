package org.palaso.languageforge.client.lex.controls.presenter;
import org.palaso.languageforge.client.lex.controls.view.LoadingProgressIndicatorWindowView;
import org.palaso.languageforge.client.lex.main.MainEventBus;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = LoadingProgressIndicatorWindowView.class)
public class LoadingProgressIndicatorWindowPresenter extends
		BasePresenter<LoadingProgressIndicatorWindowPresenter.IView, MainEventBus> {
	
	public interface IView {
		public Widget getWidget();
		public void showDialogBox();
		public void hideDialogBox();
	}

	@Override
	public void bind() {

	}

	public void onShowLoadingIndicator()
	{
		view.showDialogBox();
	}

	public void onHideLoadingIndicator()
	{
		view.hideDialogBox();
	}
}
