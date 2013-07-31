package org.palaso.languageforge.client.lex.main.model;

import org.palaso.languageforge.client.lex.model.BaseDto;

import com.google.gwt.core.client.JsArrayString;

public class DomainQuestionDto extends BaseDto<DomainQuestionDto> {

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
}
