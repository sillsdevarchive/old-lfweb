package org.palaso.languageforge.client.lex.model.settings.tasks;

import org.palaso.languageforge.client.lex.model.BaseDto;

import com.google.gwt.core.client.JsArray;

public class SettingTasksDto extends BaseDto<SettingTasksDto> {

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

	public static final native SettingTasksDto getCurrentUserSetting() /*-{
		return $wnd.taskSettings;
	}-*/;

	public static final native void applyToCurrentUser(SettingTasksDto object) /*-{
		$wnd.taskSettings = object;
	}-*/;
}
