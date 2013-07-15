package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public class JsDictionaryString<V> extends JavaScriptObject {
	// Must have protected ctor with zero args
	protected JsDictionaryString() {
	}

	// JSNI overlay methods
	public final native JsArrayString keys() /*-{
		var result = [];
		for ( var name in this) {
			if (this.hasOwnProperty(name))
				result.push(name);
		}
		return result;
	}-*/;

	public final native V value(String key) /*-{
		return this[key];
	}-*/;

	public final native void setValue(String key, V v) /*-{
		this[key] = v;
	}-*/;

	// Note, though, that methods aren't required to be JSNI
}
