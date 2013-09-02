package org.palaso.languageforge.client.lex.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.settings.fields.SettingFieldsDto;

public class GetSettingUserFieldsSettingAction extends
		JsonRpcAction<SettingFieldsDto> {

	String value;

	public GetSettingUserFieldsSettingAction(String userId) {
		super("getUserFieldsSetting", 1);
		value = userId;
	}

	@Override
	public String encodeParam(int i) {
			return value;
	}

}
