package org.palaso.languageforge.client.lex.model.settings.fields;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

public class SettingFieldsDto extends JavaScriptObject {

	protected SettingFieldsDto() {
	};

	private final native void initialize() /*-{
		this.fields = new Object();
		this.fields.field = [];
	}-*/;

	public final static SettingFieldsDto getNew() {
		SettingFieldsDto entry = SettingFieldsDto.createObject().cast();
		entry.initialize();
		return entry;
	}

	public final native JsArray<SettingFieldsFieldElementDto> getEntries() /*-{
		return this.fields.field;
	}-*/;

	public final native JsArray<SettingFieldsFieldElementDto> addEntry(
			SettingFieldsFieldElementDto newEntry) /*-{
		this.fields.field.push(newEntry);
	}-*/;

	public static final SettingFieldsDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public static final String encode(SettingFieldsDto object) {
		return new JSONObject(object).toString();
	}

}
