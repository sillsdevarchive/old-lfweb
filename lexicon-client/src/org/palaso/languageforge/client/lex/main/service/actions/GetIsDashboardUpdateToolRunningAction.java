package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.ResultDto;

public class GetIsDashboardUpdateToolRunningAction extends JsonRpcAction<ResultDto> {

	public GetIsDashboardUpdateToolRunningAction() {
		super("getDashboardUpdateRunning",0);
		isBackgroundRequestRun=true;
	}

	@Override
	public String encodeParam(int i) {
		return null;
	}

}
