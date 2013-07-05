package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.model.ConversationDto;
import org.palaso.languageforge.client.lex.common.AnnotationMessageStatusType;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.common.Constants;

public class SaveNewCommentAction extends JsonRpcAction<ConversationDto> {

	private AnnotationMessageStatusType messageStatus;
	private String parentGuid;
	private String commentMessage;
	private boolean isRootMessage;
	
	public SaveNewCommentAction(AnnotationMessageStatusType messageStatus, String parentGuid,
			String commentMessage, boolean isRootMessage) {
		super(Constants.LEX_API_PATH, Constants.LEX_API, "saveNewComment", 4);
		
		this.messageStatus = messageStatus;
		this.parentGuid = parentGuid;
		this.commentMessage = commentMessage;
		this.isRootMessage = isRootMessage;
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
			return this.parentGuid;
		case 2:
			return this.commentMessage;
		case 3:
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
