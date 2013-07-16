package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

public class ProjectDto extends JavaScriptObject {

	protected ProjectDto() {
	};

	// JSNI overlay methods
	public final native String getProjectName() /*-{
		return this.name;
	}-*/;

	public final native void setProjectName(String name) /*-{
		this.name = name;
	}-*/;

	public final native String getProjectId() /*-{
		return this.id;
	}-*/;

	public final native String getProjectTitle() /*-{
		return this.title;
	}-*/;

	public final native String getProjectType() /*-{
		return this.type;
	}-*/;

	public static final ProjectDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public static final String encode(ProjectDto object) {
		return new JSONObject(object).toString();
	}

}
