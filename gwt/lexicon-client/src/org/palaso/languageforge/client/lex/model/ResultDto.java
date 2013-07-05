package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
/*
 * This is a readonly Dto object.
 * */
public class ResultDto extends JavaScriptObject {

	protected ResultDto() {
	};

	// JSNI overlay methods
	public final native boolean isSucceed() /*-{
		return this.succeed;
	}-*/;

	public static final ResultDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

}
