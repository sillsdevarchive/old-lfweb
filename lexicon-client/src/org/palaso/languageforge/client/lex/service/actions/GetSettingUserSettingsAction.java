package org.palaso.languageforge.client.lex.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.UserSettingsDto;

public class GetSettingUserSettingsAction extends
		JsonRpcAction<UserSettingsDto> {

	String value;

	public GetSettingUserSettingsAction(String userId) {
		super("getUserSettings", 1);
		value = userId;
	}

	@Override
	public String encodeParam(int i) {
			return  value;
	}
}
