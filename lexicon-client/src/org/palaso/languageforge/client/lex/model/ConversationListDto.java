package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

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

	public static final ConversationListDto decode(String json) {
		json = json.trim() == "" ? json = "[]" : json;
		return JsonUtils.safeEval(json);
	}

	public static final  String encode(ConversationListDto object) {
		return new JSONObject(object).toString();
	}

}