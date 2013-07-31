package org.palaso.languageforge.client.lex.model.settings.fields;

import org.palaso.languageforge.client.lex.model.BaseDto;

import com.google.gwt.core.client.JsArray;

public class SettingFieldsDto extends BaseDto<SettingFieldsDto> {

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

}
