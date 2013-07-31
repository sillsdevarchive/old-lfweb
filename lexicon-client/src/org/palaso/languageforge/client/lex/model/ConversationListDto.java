package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JsArray;

public class ConversationListDto extends JsArray<ConversationDto> {

	protected ConversationListDto() {
	};

	private final native void initialize() /*-{
		this.entries = [];
	}-*/;


	public final native JsArray<ConversationDto> getEntries() /*-{
		return this.entries;
	}-*/;

	public final native JsArray<ConversationDto> addEntry(
			ConversationDto newEntry) /*-{
		this.entries.push(newEntry);
	}-*/;
}