package org.palaso.languageforge.client.lex.service.actions;

import org.palaso.languageforge.client.lex.common.BaseConfiguration;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.UserListDto;

public class RapidUserCreationAction extends JsonRpcAction<UserListDto> {

	private String userName;
	private int projectId;

	public RapidUserCreationAction(String userName, int projectId) {
		super(BaseConfiguration.getInstance().getLFApiPath(), BaseConfiguration.getInstance().getApiFileName(), "rapidUserMemberCreation", 2);
		this.userName = userName;
		this.projectId = projectId;
	}


	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return String.valueOf(projectId);
		case 1:
			return userName;
		}
		return null;
	}

}