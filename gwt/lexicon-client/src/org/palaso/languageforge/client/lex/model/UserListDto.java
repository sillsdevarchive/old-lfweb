package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

/**
 * Dto uses as a array container of UsetDto. 
 * 
 */

public class UserListDto extends JavaScriptObject {
	
	protected UserListDto() {
	};
	
	private final native void initialize() /*-{
		this.List = [];
	}-*/;
		

	public final native JsArray<UserDto> getEntries() /*-{
		return this.List;
	}-*/;
	
	public final native JsArray<UserDto> addEntry(UserDto newEntry) /*-{
		this.List.push(newEntry);
	}-*/;
	
	
	/**
	 * Utility to render a String into an object.
	 * 
	 * @param json
	 * @return UserListDto
	 */
	public static final UserListDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public static final  String encode(UserListDto object){
		return new JSONObject(object).toString();
	}

}
