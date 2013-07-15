package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

public class AutoSuggestOptions extends JavaScriptObject {

	// Must have protected ctor with zero args
	protected AutoSuggestOptions() {

	}

	public static final AutoSuggestOptions decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public final static String encode(AutoSuggestOptions object) {
		return new JSONObject(object).toString();
	}

}
