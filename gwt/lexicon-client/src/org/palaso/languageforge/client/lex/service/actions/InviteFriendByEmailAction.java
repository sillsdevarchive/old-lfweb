package org.palaso.languageforge.client.lex.service.actions;

import org.palaso.languageforge.client.lex.common.BaseConfiguration;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;

public class InviteFriendByEmailAction extends JsonRpcAction<String> {

	private int projectId;
	private String emailAddress;
	private String msg;

	public InviteFriendByEmailAction(int projectId, String emailAddress,
			String msg) {
		super(BaseConfiguration.getInstance().getLFApiPath(), BaseConfiguration.getInstance().getApiFileName(), "inviteByEmail", 3);
		this.projectId = projectId;
		this.emailAddress = emailAddress;
		this.msg = msg;
	}

	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return String.valueOf(projectId);
		case 1:
			return emailAddress;
		case 2:
			return msg;
		}
		return null;
	}
}