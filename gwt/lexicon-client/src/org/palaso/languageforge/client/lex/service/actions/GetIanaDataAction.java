package org.palaso.languageforge.client.lex.service.actions;

import org.palaso.languageforge.client.lex.common.BaseConfiguration;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.IanaDto;

public class GetIanaDataAction extends JsonRpcAction<IanaDto> {

	public GetIanaDataAction() {
		super(BaseConfiguration.getInstance().getLFApiPath(), BaseConfiguration.getInstance().getApiFileName(), "getIANAData",0);
	}

	@Override
	public String encodeParam(int i) {
		return null;
	}
}
