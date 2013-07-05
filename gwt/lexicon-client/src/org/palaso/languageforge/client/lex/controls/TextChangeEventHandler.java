package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.event.shared.EventHandler;

public interface TextChangeEventHandler extends EventHandler {
	void onTextChange(TextChangeEvent event);
}