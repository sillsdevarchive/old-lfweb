package org.palaso.languageforge.client.lex.model;

/**
 * The Dto uses for transfer basic user infomation, it can be logged-in user or other users in the system.
 */

public class UserDto extends BaseDto<UserDto> {

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


}
