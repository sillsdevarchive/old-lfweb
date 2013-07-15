package org.palaso.languageforge.client.lex.main.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public class DomainQuestionDto extends JavaScriptObject {

	protected DomainQuestionDto() {
	}

	public final native String getGuid()/*-{
		return this.guid;
	}-*/;

	public final native String getDescription()/*-{
		return this.description;
	}-*/;

	public final native JsArrayString getQuestions()/*-{
		return this.questions;
	}-*/;

	public final native JsArrayString getExampleWords()/*-{
		return this.exampleWords;
	}-*/;

	public final native JsArrayString getExampleSentences()/*-{
		return this.exampleSentences;
	}-*/;

	public static final native DomainQuestionDto decode(String json)/*-{
		return eval(json);
	}-*/;

}
