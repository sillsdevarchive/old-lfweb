package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.main.model.DomainTreeDto;

public class GetDomainTreeListAction extends JsonRpcAction<DomainTreeDto> {
	
	
	public GetDomainTreeListAction(){
		super(Constants.LEX_API_PATH, Constants.LEX_API, "getDomainTreeList",0);
	}

	@Override
	public String encodeParam(int i){
		return null;
	}
}
