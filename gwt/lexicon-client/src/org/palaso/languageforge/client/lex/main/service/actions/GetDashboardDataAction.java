package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.main.model.DashboardActivitiesDto;

public class GetDashboardDataAction extends
		JsonRpcAction<DashboardActivitiesDto> {

	private int actTimeRange = 0;

	public GetDashboardDataAction(int actTimeRange) {
		super(Constants.LEX_API_PATH, Constants.LEX_API, "getDashboardData", 1);
		this.actTimeRange = actTimeRange;
	}

	@Override
	public String encodeParam(int i) {

		return String.valueOf(actTimeRange);
	}
}
