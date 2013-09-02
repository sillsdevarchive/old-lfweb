package org.palaso.languageforge.client.lex.main.view;

import org.palaso.languageforge.client.lex.main.presenter.LexMainPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
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
		final SimplePanel messagePanel = new SimplePanel();
		if (success) {
			messagePanel.setStyleName("feedbackSuccess feedback_message");
		} else {
			messagePanel.addStyleName("feedbackError feedback_message");
		}

		FlowPanel rowCon = new FlowPanel();
		SimplePanel messageInnerPanel = new SimplePanel();
		messageInnerPanel.setStyleName("lex-dc-row");
		rowCon.getElement().setId("firefox-bug-fix");
		messageInnerPanel.add(rowCon);

		SimplePanel btnPanel = new SimplePanel();
		SimplePanel widgetPanel = new SimplePanel();
		SimplePanel endingSpacePanel = new SimplePanel();
		btnPanel.setStyleName("lex-dc-column feedback_message_closer_cell");
		btnPanel.setWidth("30px");
		widgetPanel.setStyleName("lex-dc-column");
		widgetPanel.getElement().setId("c2");

		endingSpacePanel.setStyleName("lex-dc-column");
		endingSpacePanel.getElement().setId("c3");

		Button btnClose = new Button();
		btnClose.addStyleName("small-button");
		btnClose.setWidth("20");
		btnClose.setText("X");
		btnClose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				messageList.remove(messagePanel);
			}
		});

		if (success) {
			// will close message automatic
			Timer t = new Timer() {
				public void run() {
					messageList.remove(messagePanel);
				}
			};
			// close message box in 5 seconds.
			t.schedule(5000);
		}
		widgetPanel.add(new HTMLPanel(text));
		btnPanel.add(btnClose);

		rowCon.add(btnPanel);
		rowCon.add(widgetPanel);
		rowCon.add(endingSpacePanel);
		messagePanel.add(messageInnerPanel);
		messageList.add(messagePanel);
	}

}
