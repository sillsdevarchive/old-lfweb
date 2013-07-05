package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.model.LexiconEntryListDto;

public class GetWordsListForGatherWordAction extends
		JsonRpcAction<LexiconEntryListDto> {

	public GetWordsListForGatherWordAction() {
		super(Constants.LEX_API_PATH, Constants.LEX_API, "getListForGatherWord", 0);
	}

	@Override
	public String encodeParam(int i) {
		return null;
	}

}
