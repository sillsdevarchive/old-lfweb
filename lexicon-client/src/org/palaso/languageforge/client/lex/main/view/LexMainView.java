package org.palaso.languageforge.client.lex.main.view;

import org.palaso.languageforge.client.lex.main.presenter.LexMainPresenter;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.event.ClosedEvent;
import com.github.gwtbootstrap.client.ui.event.ClosedHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class LexMainView extends Composite implements LexMainPresenter.ILexMainView {

	interface Binder extends UiBinder<Widget, LexMainView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField(provided = true)
	NavPanel navPanel;
	@UiField
	SimplePanel bodyPanel;
	@UiField
	FlowPanel messageList;

	@Inject
	public LexMainView(NavPanel navPanel) {
		this.navPanel = navPanel;
		initWidget(binder.createAndBindUi(this));
	}

	public Widget getViewWidget() {
		return this;
	}

	@Override
	public void changeBody(Widget body) {
		bodyPanel.setWidget(body);
	}

	@Override
	public void clearAllmessage() {
		messageList.clear();
	}

	@Override
	public void showMessage(String text, boolean success) {
		addNewMessageToBox(text, success);
	}

	private void addNewMessageToBox(String text, boolean success) {
		if (text == null) {
			text = "NULL";
		}
		final Alert messagePanel = new Alert();
		messagePanel.setText(text);
		messagePanel.setAnimation(true);
		messagePanel.setClose(true);
		if (success) {
			messagePanel.setType(AlertType.SUCCESS);
		} else {
			messagePanel.setType(AlertType.ERROR);
		}

		messagePanel.addClosedHandler(new ClosedHandler() {
			@Override
			public void onClosed(ClosedEvent closedEvent) {
				messageList.remove(messagePanel);
				
			}
		});
		if (success) {
			// will close message automatic
			Timer t = new Timer() {
				public void run() {
					messagePanel.close();
				}
			};
			// close message box in 5 seconds.
			t.schedule(5000);
		}
		messageList.add(messagePanel);
	}

}
