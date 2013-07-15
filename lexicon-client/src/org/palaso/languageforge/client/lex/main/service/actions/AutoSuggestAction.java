package org.palaso.languageforge.client.lex.main.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.model.AutoSuggestOptions;

public class AutoSuggestAction extends JsonRpcAction<AutoSuggestOptions> {

	private String searchString;
	private String fieldToSearch;
	private int indexFrom;
	private int limit;

	public AutoSuggestAction(
		String fieldToSearch,
		String searchString,
		int indexFrom,
		int limit
	) {
		super(Constants.LEX_API_PATH, Constants.LEX_API, "getWordsForAutoSuggest", 4);
		this.searchString = searchString;
		this.fieldToSearch = fieldToSearch;
		this.indexFrom = indexFrom;
		this.limit = limit;
	}

	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return fieldToSearch;
		case 1:
			return searchString;
		case 2:
			return new Integer(indexFrom).toString();
		case 3:
			return new Integer(limit).toString();
		}
		return null;
	}

}