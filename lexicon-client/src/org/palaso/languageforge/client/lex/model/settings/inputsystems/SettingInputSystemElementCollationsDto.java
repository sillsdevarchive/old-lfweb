package org.palaso.languageforge.client.lex.model.settings.inputsystems;

import com.google.gwt.core.client.JsArray;


public class SettingInputSystemElementCollationsDto extends
		JsArray<SettingInputSystemElementCollationDto> {

	protected SettingInputSystemElementCollationsDto() {
	};

	protected final void initialize() {

	}

	public final static SettingInputSystemElementCollationsDto getNew() {
		SettingInputSystemElementCollationsDto entry = SettingInputSystemElementCollationsDto
				.createObject().cast();
		entry.initialize();
		return entry;
	}

	public final native JsArray<SettingInputSystemElementCollationDto> getEntries() /*-{
		return this;
	}-*/;

	public final native JsArray<SettingInputSystemElementCollationDto> addEntry(
			SettingInputSystemElementCollationDto newEntry) /*-{
		this.push(newEntry);
	}-*/;

}
