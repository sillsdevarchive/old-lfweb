package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JsArrayNumber;

public class ProjectAccessDto extends BaseDto<ProjectAccessDto> {

	protected ProjectAccessDto() {
	};

	private final native void initialize() /*-{

	}-*/;

	public final static ProjectAccessDto getNew() {
		ProjectAccessDto entry = ProjectAccessDto.createObject().cast();
		entry.initialize();
		return entry;
	}
	
	public final native JsArrayNumber getPermissions() /*-{
		return this.grants;
	}-*/;

}
