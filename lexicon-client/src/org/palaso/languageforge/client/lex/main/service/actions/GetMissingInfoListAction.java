package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.common.MissinginfoType;
import org.palaso.languageforge.client.lex.model.LexiconListDto;

public class GetMissingInfoListAction extends JsonRpcAction<LexiconListDto> {

	private MissinginfoType missinginfoType = MissinginfoType.UNDEFINED;
	public GetMissingInfoListAction(MissinginfoType missinginfo) {
		super(Constants.LEX_API_PATH, Constants.LEX_API, "getMissingInfo",1);
		this.missinginfoType = missinginfo;
	}

	@Override
	public String encodeParam(int i) {
		return String.valueOf(missinginfoType.ordinal());
	}
}
