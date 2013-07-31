package org.palaso.languageforge.client.lex.model.settings.fields;

import org.palaso.languageforge.client.lex.model.BaseDto;

public class SettingFieldsFieldWritingSystemDto extends BaseDto<SettingFieldsFieldWritingSystemDto> {

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


}
