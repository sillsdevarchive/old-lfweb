package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * this class reference to a Part of speech
 */
public class LexiconPosition extends JavaScriptObject {

	protected LexiconPosition() {
	};

	public final native String getValue() /*-{
		return this.value;
	}-*/;

}