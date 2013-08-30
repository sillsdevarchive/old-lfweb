package org.palaso.languageforge.client.lex.model;

import org.palaso.languageforge.client.lex.common.UserRoleType;

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

	public final UserRoleType getRole()
	{
		return UserRoleType.valueOf(getRoleAsString());
	}
	
	public final native JsArrayNumber getPermissions() /*-{
		return this.grants;
	}-*/;

	public final native String getRoleAsString() /*-{
		if (this.hasOwnProperty('role')) {
			if (this.role != null) {
				return this.role;
			} else {
				return 'UNDEFINED';
			}
		} else {
			return 'UNDEFINED';
		}

	}-*/;
}
