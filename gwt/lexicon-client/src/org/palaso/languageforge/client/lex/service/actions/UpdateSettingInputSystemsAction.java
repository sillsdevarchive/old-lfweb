package org.palaso.languageforge.client.lex.service.actions;

import org.palaso.languageforge.client.lex.common.BaseConfiguration;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.settings.inputsystems.SettingInputSystemsDto;

public class UpdateSettingInputSystemsAction extends JsonRpcAction<SettingInputSystemsDto> {

	SettingInputSystemsDto value;
	
	public UpdateSettingInputSystemsAction(SettingInputSystemsDto value) {
		super(BaseConfiguration.getInstance().getLFApiPath(), BaseConfiguration.getInstance().getApiFileName(), "updateSettingInputSystems",1);
		this.value = value;
	}


	@Override
	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return SettingInputSystemsDto.encode(value);
		}
		return null;
	}

}
