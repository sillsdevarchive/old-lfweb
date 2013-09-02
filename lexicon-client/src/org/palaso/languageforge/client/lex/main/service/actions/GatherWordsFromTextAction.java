package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.ResultDto;

public class GatherWordsFromTextAction extends JsonRpcAction<ResultDto> {

	private String words = "";
	private String uploadedFileName = "";
	public GatherWordsFromTextAction(String words,String uploadedFileName) {
		super("getGatherWords",2);
		this.words = words;
		this.uploadedFileName = uploadedFileName;
	}

	@Override
	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return this.words;
		case 1:
			return this.uploadedFileName;
		}
		return null;
	}

}
