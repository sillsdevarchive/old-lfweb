package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;

public class GetEntryForGatherWordAction extends JsonRpcAction<LexiconEntryDto> {

	private String id = "1";

	public GetEntryForGatherWordAction(String id) {
		super("getEntryForGatherWord", 1);
		this.id = id;
	}

	@Override
	public String encodeParam(int i) {
		return id;
	}

}
