package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

public class ProjectAccessDto extends JavaScriptObject {

	protected ProjectAccessDto() {
	};

	private final native void initialize() /*-{

	}-*/;

	public final static ProjectAccessDto getNew() {
		ProjectAccessDto entry = ProjectAccessDto.createObject().cast();
		entry.initialize();
		return entry;
	}

	public final native String getActiveRole() /*-{
		return this.activerole;
	}-*/;
	
	public final native JsArrayNumber getPermissions() /*-{
		return this.grants;
	}-*/;	

	public final native JsDictionaryString<String> getAllAvailableRoles() /*-{
		return this.availableroles;
	}-*/;
	
	public static final ProjectAccessDto decode(String json) {
		json = json.trim() == "" ? json = "[]" : json;
		return JsonUtils.safeEval(json);
	}

	public static final String encode(ProjectAccessDto object){
		return new JSONObject(object).toString();
	}

	
}
