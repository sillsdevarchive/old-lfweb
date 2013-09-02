package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.settings.tasks.SettingTasksDto;

public class UpdateSettingUserTasksSettingAction extends
		JsonRpcAction<SettingTasksDto> {

	SettingTasksDto value;
	String userId;

	public UpdateSettingUserTasksSettingAction(String userId,
			SettingTasksDto value) {
		super("updateSettingTasks", 2);
		this.value = value;
		this.userId = userId;
	}

	@Override
	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return userId;
		case 1:
			return SettingTasksDto.encode(value);
		}
		return null;
	}

}
