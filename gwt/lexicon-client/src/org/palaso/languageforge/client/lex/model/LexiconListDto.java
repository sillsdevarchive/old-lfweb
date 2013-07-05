package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

/**
 *  this is a collection class which to be a array container of LexiconListEntry
 *
 */
public class LexiconListDto extends JsArray<LexiconListEntry> {

	protected LexiconListDto() {
	};

	private final native void initialize() /*-{
		this.entries = [];
	}-*/;

	public final static LexiconListDto createFromSettings(FieldSettings settings) {
		LexiconListDto result = LexiconListDto.createObject().cast();
		result.setEntryCount(0);
		result.initialize();
		return result;
	}

	public final native int getEntryCount() /*-{
		return this.count;
	}-*/;

	public final native int getEntryBeginIndex() /*-{
		return this.beginindex;
	}-*/;

	public final native int getEntryEndIndex() /*-{
		return this.endindex;
	}-*/;

	public final native void setEntryCount(int n) /*-{
		if (this.count == null)
			this.count = 0;
		this.count = n;
	}-*/;

	public final native JsArray<LexiconListEntry> getEntries() /*-{
		return this.entries;
	}-*/;

	public final native JsArray<LexiconListEntry> addEntry(
			LexiconListEntry newEntry) /*-{
		this.entries.push(newEntry);
	}-*/;

	/**
	 * Utility to render a String into an object.
	 * 
	 * @param json
	 * @return LexWordListDTO
	 */
	public static final LexiconListDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public static final String encode(LexiconListDto object) {
		return new JSONObject(object).toString();
	}

}