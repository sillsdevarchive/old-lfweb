package org.palaso.languageforge.client.lex.service.actions;

import org.palaso.languageforge.client.lex.common.BaseConfiguration;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.UserListDto;

public class MemberAutoSuggestAction extends JsonRpcAction<UserListDto> {

	private String searchString;
	private int indexFrom;
	private int indexTo;

	public MemberAutoSuggestAction(String searchString, int indexFrom,
			int indexTo) {
		super(BaseConfiguration.getInstance().getLFApiPath(), BaseConfiguration.getInstance().getApiFileName(), "getMembersForAutoSuggest", 3);
		this.searchString = searchString;
		this.indexFrom = indexFrom;
		this.indexTo = indexTo;
	}

	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return searchString;
		case 1:
			return String.valueOf(indexFrom);
		case 2:
			return String.valueOf(indexTo);
		}
		return null;
	}
}