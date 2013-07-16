package org.palaso.languageforge.client.lex.model;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

/**
 * The Dto uses for transfer basic user infomation, it can be logged-in user or other users in the system.
 */

public class UserDto extends JavaScriptObject {

	protected UserDto() {
	};

	// JSNI overlay methods
	public final native String getName() /*-{
		return this.name;
	}-*/;

	public final native String getId() /*-{
		return this.id;
	}-*/;
	
	public final native int getRoleName() /*-{
		return this.role;
	}-*/;

	public final native void setRoleName(String newRole) /*-{
		this.role = newRole;
	}-*/;
	
	public static final UserDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public static final String encode(UserDto object) {
		return new JSONObject(object).toString();
	}

}
