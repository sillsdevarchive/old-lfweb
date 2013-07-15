package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JsArrayString;

// An overlay type
// e.g. { "en":"hello","fr":"bonjour"}
public class MultiText extends JsDictionaryString<String> {

	// private String _description;

	// Must have protected ctor with zero args
	protected MultiText() {
	}

	public final String getFirstForm() {
		JsArrayString keys = keys();
		if (keys.length() == 0) {
			return "";
		}
		return value(keys.get(0));
	}
	
	public static final MultiText createFromSettings(FieldSettingsEntry entry) {
		MultiText result = MultiText.createObject().cast();
		JsArrayString languages = entry.getLanguages();
		for (int i = 0, n = languages.length(); i < n; i++) {
			result.setValue(languages.get(i), "");
		}
		return result;
	}
	
	public static final MultiText createLabelFromSettings(FieldSettingsEntry entry) {
		MultiText result = MultiText.createObject().cast();
		JsArrayString languages = entry.getLanguages();
		JsArrayString labels = entry.getAbbreviations();
		for (int i = 0, n = languages.length(); i < n; i++) {
			result.setValue(languages.get(i), labels.get(i));
		}
		return result;
	}

}