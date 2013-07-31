package org.palaso.languageforge.client.lex.model.settings.inputsystems;

import org.palaso.languageforge.client.lex.model.BaseDto;

import com.google.gwt.core.client.JsArray;

public class SettingInputSystemsDto extends BaseDto<SettingInputSystemsDto> {

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
}
