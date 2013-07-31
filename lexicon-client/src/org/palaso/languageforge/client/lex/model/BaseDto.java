package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

/**
 * this is root class of IANA JSON data collections
 * 
 */

abstract public class BaseDto<T> extends JavaScriptObject {

	protected BaseDto() {
	};

	public final static <T extends BaseDto<T>> T decode(String json) {
		json = json.trim() == "" ? json = "[]" : json;
		return JsonUtils.safeEval(json);
	}

	public final static <T extends BaseDto<T>> String encode(BaseDto<T> object) {
		return new JSONObject(object).toString();
	}

}
