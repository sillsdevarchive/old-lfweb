package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TextBox;

public class ExtendedTextBox extends TextBox implements HasHandlers {

	private HandlerManager handlerManager;

	public ExtendedTextBox() {
		super();

		handlerManager = new HandlerManager(this);

		// For all browsers - catch onKeyUp
		sinkEvents(Event.ONKEYUP);

		// For IE and Firefox - catch onPaste
		sinkEvents(Event.ONPASTE);

		// For Opera - catch onInput
		publishOnInputHandler(this.getElement());
	}

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		switch (event.getTypeInt()) {
		case Event.ONKEYUP:
		case Event.ONPASTE: {
			// Scheduler needed so pasted data shows up in TextBox before we
			// fire event
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					fireEvent(new TextChangeEvent());
				}
			});
			break;
		}
		default:
			// Do nothing
		}
	}

	private native void publishOnInputHandler(Element element) /*-{
		var that = this;
		element.oninput = $entry(function(event) {
			that.@org.palaso.languageforge.client.lex.controls.ExtendedTextBox::onInputHandler(Lcom/google/gwt/user/client/Event;)(event);
			return false;
		});
	}-*/;

	private void onInputHandler(Event event) {
		fireEvent(new TextChangeEvent());
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
	}

	public HandlerRegistration addTextChangeEventHandler(
			TextChangeEventHandler handler) {
		return handlerManager.addHandler(TextChangeEvent.TYPE, handler);
	}
}
