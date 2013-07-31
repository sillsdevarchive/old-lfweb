package org.palaso.languageforge.client.lex.model;

import org.palaso.languageforge.client.lex.model.settings.fields.SettingFieldsFieldElementDto;
import org.palaso.languageforge.client.lex.model.settings.tasks.SettingTasksTaskElementDto;

import com.google.gwt.core.client.JsArray;

public class UserSettingsDto extends BaseDto<UserSettingsDto> {

	protected UserSettingsDto() {
	};

	private final native void initialize() /*-{
		this.fields = new Object();
		this.fields.field = [];

		this.tasks = new Object();
		this.tasks.task = [];
	}-*/;

	public final static UserSettingsDto getNew() {
		UserSettingsDto entry = UserSettingsDto.createObject().cast();
		entry.initialize();
		return entry;
	}

	public final native JsArray<SettingFieldsFieldElementDto> getFieldEntries() /*-{
		return this.fields.field;
	}-*/;

	public final native JsArray<SettingFieldsFieldElementDto> addFieldEntry(
			SettingFieldsFieldElementDto newEntry) /*-{
		this.fields.field.push(newEntry);
	}-*/;

	public final native JsArray<SettingTasksTaskElementDto> getTaskEntries() /*-{
		return this.tasks.task;
	}-*/;

	public final native JsArray<SettingTasksTaskElementDto> addTaskEntry(
			SettingTasksTaskElementDto newEntry) /*-{
		this.tasks.task.push(newEntry);
	}-*/;

}
