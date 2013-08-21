package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;

/**
 * The Dto uses for the definition / pos of a word entry, and it also contains
 * examples as a sub-part. This is a sub-part of LexiconEntryDto
 * 
 */
public class Sense extends BaseDto<Sense> {

	// Must have protected ctor with zero args
	protected Sense() {
	}

	public final static Sense createFromSettings(FieldSettings settings) {
		Sense result = Sense.createObject().cast();
		result.setDefinition(MultiText.createFromSettings(settings
				.value("Definition")));
		result.initialize();
		return result;
	}

	public static final JsArray<Sense> createSenseArray() {
		JsArray<Sense> senses = createArray().cast();
		return senses;
	}

	// JSNI overlay methods
	public final native MultiText getDefinition() /*-{

		//hacks #1090
		if (this.definition == null || this.definition.length == 0) {
			this.definition = {};
		}
		return this.definition;
	}-*/;

	public final native String getPOS() /*-{
		return this.POS;
	}-*/;

	public final native void setPOS(String v) /*-{
		this.POS = v;
	}-*/;

	public final native int getExampleCount() /*-{
		if (typeof (this.examples) != "undefined") {
			var len = (this.examples == null) ? 0 : this.examples.length;
			return len;
		} else {
			return 0;
		}
	}-*/;

	public final native JsArray<Example> getExamples() /*-{
		return this.examples;
	}-*/;

	public final native Example getExample(int index) /*-{
		return this.examples[index];
	}-*/;

	// Note, though, that methods aren't required to be JSNI
	public final String getDescription() {
		return getDefinition().value("en");
	}

	public final native void setDefinition(MultiText def) /*-{
		this.definition = def;
	}-*/;

	public final void addExample(Example example) {
		this.getExamples().push(example);
	};

	public final native void removeExample(Example example) /*-{
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
		var index = this.examples.indexOf(example);
		this.examples.splice(index, 1);
	}-*/;

	private final native void initialize() /*-{
		this.examples = [];
		this.POS = "";
	}-*/;

	public final native MultiText getNewExample() /*-{
		return this.examples[this.examples.length - 1].example;
	}-*/;

	public final native JsArray<LexiconPosition> getPOSList() /*-{
		return $wnd.settingsPartOfSpeech;
	}-*/;

	public static final String encode(Sense object) {
		return new JSONObject(object).toString();
	}

	public final native String getSemanticDomainName() /*-{
		return this.SemDomName;
	}-*/;

	public final native void setSemanticDomainName(String v) /*-{
		this.SemDomName = v;
	}-*/;

	public final native String getSemanticDomainValue() /*-{
		return this.SemDomValue;
	}-*/;

	public final native void setSemanticDomainValue(String v) /*-{
		this.SemDomValue = v;
	}-*/;

	public final native EntryMetadataDto getMetadata() /*-{
		//hacks #1090
		if (this.metadata == null || this.metadata.length == 0) {
			this.metadata = {};
		}
		return this.metadata;
	}-*/;

	public final native void setMetadata(EntryMetadataDto metadata) /*-{
		this.metadata = metadata;
	}-*/;

	public final native String getId() /*-{
		return this.id;
	}-*/;

	public final native void setId(String id) /*-{
		this.id = id;
	}-*/;
}