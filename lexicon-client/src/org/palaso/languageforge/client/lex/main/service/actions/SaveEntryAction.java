package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;

import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.ResultDto;

public class SaveEntryAction extends JsonRpcAction<ResultDto> {

	LexiconEntryDto value;
	String entryGuid;
	String isNewEntry;
	public SaveEntryAction(LexiconEntryDto value, String isNew) {
		super("saveEntry", 2);
		this.value = value;
		this.isNewEntry = isNew;
	}

	@Override
	public String encodeRequest() {
		return LexiconEntryDto.encode(value);
	}

	@Override
	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return LexiconEntryDto.encode(value);
		case 1:
			return isNewEntry.toString();
		}
		return null;
	}

}
