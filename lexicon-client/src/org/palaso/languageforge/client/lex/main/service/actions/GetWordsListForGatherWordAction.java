package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.LexiconEntryListDto;

public class GetWordsListForGatherWordAction extends
		JsonRpcAction<LexiconEntryListDto> {

	public GetWordsListForGatherWordAction() {
		super("getListForGatherWord", 0);
	}

	@Override
	public String encodeParam(int i) {
		return null;
	}

}
