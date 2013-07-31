package org.palaso.languageforge.client.lex.model.settings.tasks;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

public class SettingTasksDto extends JavaScriptObject {

	protected SettingTasksDto() {
	};

	private final native void initialize() /*-{
		this.tasks = new Object();
		this.tasks.task = [];
	}-*/;

	public final static SettingTasksDto getNew() {
		SettingTasksDto entry = SettingTasksDto.createObject().cast();
		entry.initialize();
		return entry;
	}

	public final native JsArray<SettingTasksTaskElementDto> getEntries() /*-{
		return this.tasks.task;
	}-*/;

	public final native JsArray<SettingTasksTaskElementDto> addEntry(
			SettingTasksTaskElementDto newEntry) /*-{
		this.tasks.task.push(newEntry);
	}-*/;

	public static final SettingTasksDto decode(String json) {
		json = json.trim() == "" ? json = "[]" : json;
		return JsonUtils.safeEval(json);
	}

	public static final String encode(SettingTasksDto object) {
		return new JSONObject(object).toString();
	}

	public static final native SettingTasksDto getCurrentUserSetting() /*-{
		return $wnd.taskSettings;
	}-*/;

	public static final native void applyToCurrentUser(SettingTasksDto object) /*-{
		$wnd.taskSettings = object;
	}-*/;
}
