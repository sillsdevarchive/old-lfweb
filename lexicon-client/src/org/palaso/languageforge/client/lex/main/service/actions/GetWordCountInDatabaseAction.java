package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.ResultDto;

public class GetWordCountInDatabaseAction extends JsonRpcAction<ResultDto> {

	public GetWordCountInDatabaseAction() {
		super(Constants.LEX_API_PATH, Constants.LEX_API, "getWordCountInDatabaseAction", 0);
	}
	@Override
	public String encodeParam(int i) {
		return null;
	}

}
