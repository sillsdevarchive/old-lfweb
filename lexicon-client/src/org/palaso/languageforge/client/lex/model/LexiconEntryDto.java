package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

/**
 * This is root Dto of a word entry, if contains word itself and Senses and sub-parts
 */
public class LexiconEntryDto extends JavaScriptObject {

	// Must have protected ctor with zero args
	protected LexiconEntryDto() {

	}

	private final native void initialize() /*-{
		this.senses = [];
	}-*/;

	public final static LexiconEntryDto createFromSettings(
			FieldSettings settings) {
		LexiconEntryDto entry = LexiconEntryDto.createObject().cast();
		entry.setGuid(null);
		entry.setMercurialSHA(null);
		entry.setEntry(MultiText.createFromSettings(settings.value("Word")));
		entry.initialize();
		return entry;
	}

	public final String getWordFirstForm() {
		MultiText entry = getEntry();
		JsArrayString keys = entry.keys();
		return entry.value(keys.get(0));
	}
	
	// JSNI overlay methods
	public final native MultiText getEntry() /*-{
		return this.entry;
	}-*/;

	public final native String getId() /*-{
		return this.guid;
	}-*/;

	public final native String getMercurialSHA() /*-{
		return this.mercurialSHA;
	}-*/;

	public final native Sense getSense(int index) /*-{
		return this.senses[index];
	}-*/;

	public final native int getSenseCount() /*-{
		var len = (this.senses == null) ? 0 : this.senses.length;
		return len;
	}-*/;

	public final native JsArray<Sense> getSenses() /*-{
		return this.senses;
	}-*/;

	public static final LexiconEntryDto decode(String json) {
		json = json.trim() == "" ? json = "[]" : json;
		return JsonUtils.safeEval(json);
	}

	public static final String encode(LexiconEntryDto object) {
		return new JSONObject(object).toString();
	};

	public final native void setGuid(String guid) /*-{
		this.guid = guid;
	}-*/;

	public final native void setMercurialSHA(String sha) /*-{
		this.mercurialSHA = sha;
	}-*/;

	public final native void setEntry(MultiText word) /*-{
		this.entry = word;
	}-*/;

	public final native void addSense(Sense sense) /*-{
		this.senses.push(sense);
	}-*/;

	public final native void removeSense(Sense sense) /*-{
		//REF: https://developer.mozilla.org/en-US/docs/JavaScript/Reference/Global_Objects/Array/indexOf
		//old browser may not supports Array.index
		if (!Array.prototype.indexOf) {
			Array.prototype.indexOf = function(searchElement) {
				if (this == null) {
					throw new TypeError();
				}
				var t = Object(this);
				var len = t.length >>> 0;
				if (len === 0) {
					return -1;
				}
				var n = 0;
				if (arguments.length > 1) {
					n = Number(arguments[1]);
					if (n != n) { // shortcut for verifying if it's NaN
						n = 0;
					} else if (n != 0 && n != Infinity && n != -Infinity) {
						n = (n > 0 || -1) * Math.floor(Math.abs(n));
					}
				}
				if (n >= len) {
					return -1;
				}
				var k = n >= 0 ? n : Math.max(len - Math.abs(n), 0);
				for (; k < len; k++) {
					if (k in t && t[k] === searchElement) {
						return k;
					}
				}
				return -1;
			}
		}
		var index = this.senses.indexOf(sense);
		this.senses.splice(index, 1);
	}-*/;

	/**
	 * @return the first meaning (definition) of the entry OR an empty MultiText
	 *         if the entry doesn't contain any senses.
	 */
	public final MultiText getFirstMeaning() {

		if (getSenseCount() > 0) {
			// we do have a first meaning
			return getSense(0).getDefinition();
		} else {
			// return empty MultiText
			return MultiText.createFromSettings(FieldSettings.fromWindow().value("Definition"));
		}

	};

}