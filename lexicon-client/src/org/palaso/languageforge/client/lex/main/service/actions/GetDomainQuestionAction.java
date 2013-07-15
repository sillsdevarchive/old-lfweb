package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.main.model.DomainQuestionDto;

public class GetDomainQuestionAction extends JsonRpcAction<DomainQuestionDto> {
	
	private String guid="";
	
	public GetDomainQuestionAction(String domainGuid){
		super(Constants.LEX_API_PATH, Constants.LEX_API, "getDomainQuestion",1);
		guid=domainGuid;
	}

	@Override
	public String encodeParam(int i){
		return guid;
	}
}
