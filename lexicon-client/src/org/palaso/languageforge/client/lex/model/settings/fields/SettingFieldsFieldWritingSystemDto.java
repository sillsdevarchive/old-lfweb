package org.palaso.languageforge.client.lex.model.settings.fields;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

public class SettingFieldsFieldWritingSystemDto extends JavaScriptObject {

	protected SettingFieldsFieldWritingSystemDto() {
	};

	private final native void initialize() /*-{
		this["$"] = new Object();
	}-*/;

	public final static SettingFieldsFieldWritingSystemDto getNew() {
		SettingFieldsFieldWritingSystemDto entry = SettingFieldsFieldWritingSystemDto
				.createObject().cast();
		entry.initialize();
		return entry;
	}

	public final native String getId() /*-{
		if (this.hasOwnProperty('$')) {
			return this['$'];
		} else {
			return '';
		}
	}-*/;

	public final native String setId(String id) /*-{
		if (!this.hasOwnProperty('$')) {
			this['$'] = new object();
		}
		this['$'] = id;
	}-*/;

	public final native boolean isReadOnly() /*-{
		return this.hasOwnProperty("readOnly");
	}-*/;

	public static final SettingFieldsFieldWritingSystemDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public static final String encode(
			SettingFieldsFieldWritingSystemDto object) {
		return new JSONObject(object).toString();
	}

}
