package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.DomainTreeDto;

public class GetDomainTreeListAction extends JsonRpcAction<DomainTreeDto> {
	
	
	public GetDomainTreeListAction(){
		super("getDomainTreeList",0);
	}

	@Override
	public String encodeParam(int i){
		return null;
	}
}
