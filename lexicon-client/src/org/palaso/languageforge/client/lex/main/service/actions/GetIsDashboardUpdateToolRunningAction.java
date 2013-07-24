package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.ResultDto;
import org.palaso.languageforge.client.lex.common.Constants;

public class GetIsDashboardUpdateToolRunningAction extends JsonRpcAction<ResultDto> {

	public GetIsDashboardUpdateToolRunningAction() {
		super(Constants.LEX_API_PATH, Constants.LEX_API, "getDashboardUpdateRunning",0);
		isBackgroundRequestRun=true;
	}

	@Override
	public String encodeParam(int i) {
		return null;
	}

}
