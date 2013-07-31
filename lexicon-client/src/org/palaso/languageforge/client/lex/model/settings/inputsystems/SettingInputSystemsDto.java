package org.palaso.languageforge.client.lex.model.settings.inputsystems;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

public class SettingInputSystemsDto extends JavaScriptObject {

	protected SettingInputSystemsDto() {
	};

	private final native void initialize() /*-{
		this.list = [];
	}-*/;

	public final static SettingInputSystemsDto getNew() {
		SettingInputSystemsDto entry = SettingInputSystemsDto.createObject()
				.cast();
		entry.initialize();
		return entry;
	}

	public final native JsArray<SettingInputSystemElementDto> getEntries() /*-{
		return this.list;
	}-*/;

	public final native JsArray<SettingInputSystemElementDto> addEntry(
			SettingInputSystemElementDto newEntry) /*-{
		this.list.push(newEntry);
	}-*/;

	public static final SettingInputSystemsDto decode(String json) {
		json = json.trim() == "" ? json = "[]" : json;
		return JsonUtils.safeEval(json);
	}

	public static final String encode(SettingInputSystemsDto object) {
		return new JSONObject(object).toString();
	}

}
