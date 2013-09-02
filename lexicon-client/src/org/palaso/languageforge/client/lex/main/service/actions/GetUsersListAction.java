package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.CurrentEnvironmentDto;
import org.palaso.languageforge.client.lex.model.UserListDto;

public class GetUsersListAction extends JsonRpcAction<UserListDto> {

	public GetUsersListAction() {
		super("listUsersInProject",1);
	}

	@Override
	public String encodeParam(int i) {
		return CurrentEnvironmentDto.getCurrentProject().getProjectId();
	}

}
