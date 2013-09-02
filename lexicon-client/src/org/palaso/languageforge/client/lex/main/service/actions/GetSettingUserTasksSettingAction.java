package org.palaso.languageforge.client.lex.main.service.actions;


import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.settings.tasks.SettingTasksDto;

public class GetSettingUserTasksSettingAction extends
		JsonRpcAction<SettingTasksDto> {

	String value;

	public GetSettingUserTasksSettingAction(String userId) {
		super("getUserTasksSetting", 1);
		value = userId;
	}

	@Override
	public String encodeParam(int i) {
			return value;
	}

}
