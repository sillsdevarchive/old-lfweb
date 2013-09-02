package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.LexiconListDto;

public class GetListAction extends JsonRpcAction<LexiconListDto> {

	private int beginIndex=0;
	private int endIndex=0;
	public GetListAction(int beginIndex, int endIndex) {
		super("getList",2);
		this.beginIndex=beginIndex;
		this.endIndex=endIndex;
	}

	@Override
	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return String.valueOf(beginIndex);
		case 1:
			return String.valueOf(endIndex);
		}
		return null;
	}

}
