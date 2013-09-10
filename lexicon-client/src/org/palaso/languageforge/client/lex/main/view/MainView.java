package org.palaso.languageforge.client.lex.main.view;

import org.palaso.languageforge.client.lex.main.presenter.MainPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Inject;

public class MainView extends Composite implements MainPresenter.IView {

	interface Binder extends UiBinder<SimplePanel, MainView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	// @UiField( provided = true )
	// TopPanel topPanel;
	@UiField(provided = true)
	LexMainView lexMainView;

	// @UiField( provided = true )
	// MailListView mailList;
	// @UiField( provided = true )
	// ShortcutsView shortcuts;
	// @UiField( provided = true )
	// MailDetailView mailDetail;

	@Inject
	public MainView(TopPanel topPanel, LexMainView lexMainView /*
																 * ,
																 * ShortcutsView
																 * shortcuts,
																 * MailListView
																 * mailList,
																 * MailDetailView
																 * mailDetail
																 */) {
		// this.topPanel = topPanel;
		this.lexMainView = lexMainView;
		// this.shortcuts = shortcuts;
		// this.mailList = mailList;
		// this.mailDetail = mailDetail;
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void displayErrorMessage(String message) {
		Window.alert(message);
	}

	@Override
	public void displayText(String message) {
		Window.alert(message);
	}


}
