package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.common.enums.AnnotationMessageStatusType;
import org.palaso.languageforge.client.lex.common.enums.ConversationAnnotationType;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.ConversationListDto;

public class GetCommentsAction extends JsonRpcAction<ConversationListDto> {

	private AnnotationMessageStatusType messageStatus;
	private ConversationAnnotationType annotationType;
	private int startIndex;
	private int limit;
	private int isRecentChanges;
	
	public GetCommentsAction(AnnotationMessageStatusType messageStatus,
			ConversationAnnotationType type, int startIndex, int limitation, boolean isRecentChanges) {
		super("getComments", 5);
		this.messageStatus = messageStatus;
		this.annotationType = type;
		this.startIndex = startIndex;
		this.limit = limitation;
		if(isRecentChanges==true){
			this.isRecentChanges=1;
		}else
		{
			this.isRecentChanges=0;
		}
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
			if (this.annotationType!=ConversationAnnotationType.UNDEFINED){
				return this.annotationType.getValue();
			}else
			{
				return "";
			}
		case 2:
			return String.valueOf(startIndex);
		case 3:
			return String.valueOf(limit);
		case 4:
			return String.valueOf(isRecentChanges);
		}
		return "";
	}
}
