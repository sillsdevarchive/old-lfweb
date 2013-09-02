package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.common.enums.MissinginfoType;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.LexiconListDto;

public class GetMissingInfoListAction extends JsonRpcAction<LexiconListDto> {

	private MissinginfoType missinginfoType = MissinginfoType.UNDEFINED;
	public GetMissingInfoListAction(MissinginfoType missinginfo) {
		super("getMissingInfo",1);
		this.missinginfoType = missinginfo;
	}

	@Override
	public String encodeParam(int i) {
		return String.valueOf(missinginfoType.ordinal());
	}
}
