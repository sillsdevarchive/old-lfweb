package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.IanaDto;

public class GetIanaDataAction extends JsonRpcAction<IanaDto> {

	public GetIanaDataAction() {
		super("getIANAData",0);
	}

	@Override
	public String encodeParam(int i) {
		return null;
	}
}
