package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.model.ConversationDto;
import org.palaso.languageforge.client.lex.common.AnnotationMessageStatusType;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.common.Constants;

public class SaveNewCommentAction extends JsonRpcAction<ConversationDto> {

	private AnnotationMessageStatusType messageStatus;
	private boolean isStatusReviewed;
	private boolean isStatusTodo;
	private String parentGuid;
	private String commentMessage;
	private boolean isRootMessage;
	
	public SaveNewCommentAction(AnnotationMessageStatusType messageStatus, boolean isStatusReviewed, boolean isStatusTodo, String parentGuid,
			String commentMessage, boolean isRootMessage) {
		super(Constants.LEX_API_PATH, Constants.LEX_API, "saveNewComment", 6);
		
		this.messageStatus = messageStatus;
		this.parentGuid = parentGuid;
		this.commentMessage = commentMessage;
		this.isRootMessage = isRootMessage;
		this.isStatusReviewed = isStatusReviewed;
		this.isStatusTodo = isStatusTodo;
	}

	@Override
	public String encodeParam(int i) {
		switch (i) {
		case 0:
			if (this.messageStatus!=AnnotationMessageStatusType.UNDEFINED){
				return this.messageStatus.getValue();
			}else
			{
				return "";
			}
		case 1:
			
			if ( this.isStatusReviewed) {
				return AnnotationMessageStatusType.REVIEWED.getValue();
			} 
			else return "";
		case 2:
			if (this.isStatusTodo) {
				return AnnotationMessageStatusType.TODO.getValue();
			}
			else return "";
		case 3:
			return this.parentGuid;
		case 4:
			return this.commentMessage;
		case 5:
			if (isRootMessage){
				return "1";
			}else
			{
				return "0";
			}
		}
		return "";
	}
}
