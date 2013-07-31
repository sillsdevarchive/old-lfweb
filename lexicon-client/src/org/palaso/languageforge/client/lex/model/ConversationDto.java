package org.palaso.languageforge.client.lex.model;

import org.palaso.languageforge.client.lex.common.AnnotationMessageStatusType;
import org.palaso.languageforge.client.lex.common.ConversationAnnotationType;

import com.google.gwt.core.client.JsArray;

public class ConversationDto extends BaseDto<ConversationDto> {

	protected ConversationDto() {
	}

	public final native String getGuid()/*-{
		return this.guid;
	}-*/;

	public final native String getRefData()/*-{
		return this.ref;
	}-*/;

	public final native String getAuthor()/*-{
		return this.author;
	}-*/;

	public final native int getPhpTimeStamp()/*-{
		return this.date;
	}-*/;

	public final native String getComment()/*-{
		return this.comment;
	}-*/;

	public final native String getStatusString()/*-{
		return this.status;
	}-*/;

	public final  AnnotationMessageStatusType getStatus(){
		try {
			return AnnotationMessageStatusType.getFromValue(getStatusString()
					.trim());
		} catch (IllegalArgumentException e) {
			return AnnotationMessageStatusType.UNDEFINED;
		}
	}	
	
	public final native String getConversationClassString()/*-{
		return this.conclass;
	}-*/;

	public final  ConversationAnnotationType getConversationClass(){
		try {
			return ConversationAnnotationType.getFromValue(getConversationClassString()
					.trim());
		} catch (IllegalArgumentException e) {
			return ConversationAnnotationType.UNDEFINED;
		}
	}
	
	public final native JsArray<ConversationDto> getChildren()/*-{
		return this.children;
	}-*/;

}
