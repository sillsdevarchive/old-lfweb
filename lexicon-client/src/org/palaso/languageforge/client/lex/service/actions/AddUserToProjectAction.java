package org.palaso.languageforge.client.lex.service.actions;

import org.palaso.languageforge.client.lex.common.BaseConfiguration;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.UserListDto;

public class AddUserToProjectAction extends JsonRpcAction<UserListDto> {

	private int userId;
	private int projectId;

	public AddUserToProjectAction(int userId, int projectId) {
		super(BaseConfiguration.getInstance().getLFApiPath(), BaseConfiguration.getInstance().getApiFileName(), "addUserToProjectForLex", 2);
		this.userId = userId;
		this.projectId = projectId;
	}

	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return String.valueOf(projectId);
		case 1:
			return String.valueOf(userId);
		}
		return null;
	}
}