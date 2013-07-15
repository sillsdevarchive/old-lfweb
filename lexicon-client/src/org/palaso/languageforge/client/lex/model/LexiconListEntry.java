package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

/**
 * this contains some basic infomation of a word which will show on list.
 */
public class LexiconListEntry extends JavaScriptObject {

	protected LexiconListEntry() {
	}
	
	/**
	 * Returns the first form of the word.
	 * @return String
	 */
	public final String getWordFirstForm() {
		MultiText entry = getEntry();
		return entry.getFirstForm();
	}

	public final String getMeaningFirstForm() {
		if (getMeaningCount() == 0) {
			return "";
		}
		MultiText meaning = getMeaning(0);
		return meaning.getFirstForm();
	}
	
	public final native String getId() /*-{
		return this.guid;
	}-*/;

	public final native void setId(String id) /*-{
		this.guid = id;		
	}-*/;

	public final native MultiText getEntry() /*-{
		return this.entry;
	}-*/;
	
	public final native void setEntry(MultiText word) /*-{
		this.entry = word;
	}-*/;
	
	public final native int getMeaningCount() /*-{
		var len = (this.meanings == null) ? 0 : this.meanings.length;
		return len;
	}-*/;

	public final native MultiText getMeaning(int index) /*-{
		return this.meanings[index];
	}-*/;

	public final native void setMeaning(MultiText meaning) /*-{
		this.meanings = [];
		this.meanings.push(meaning);
	}-*/;

	public static final String encode(LexiconListEntry object) {
		return new JSONObject(object).toString();
	}

}