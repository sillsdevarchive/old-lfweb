package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JsArray;

/**
 *  this is a collection class which to be a array container of LexiconEntryDto
 *
 */
public class LexiconEntryListDto extends JsArray<LexiconEntryDto> {

	protected LexiconEntryListDto() {
	};

	private final native void initialize() /*-{
		this.entries = [];
	}-*/;

	public final static LexiconEntryListDto createFromSettings(
			FieldSettings settings) {
		LexiconEntryListDto result = LexiconEntryListDto.createObject().cast();
		result.setEntryCount(0);
		result.initialize();
		return result;
	}

	public final native int getEntryCount() /*-{
		return this.count;
	}-*/;

	public final native void setEntryCount(int n) /*-{
		if (this.count == null)
			this.count = 0;
		this.count = n;
	}-*/;

	public final native JsArray<LexiconEntryDto> getEntries() /*-{
		return this.entries;
	}-*/;

	public final native JsArray<LexiconEntryDto> addEntry(
			LexiconEntryDto newEntry) /*-{
		this.entries.push(newEntry);
	}-*/;


}