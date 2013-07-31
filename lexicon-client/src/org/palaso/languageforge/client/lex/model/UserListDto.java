package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JsArray;

/**
 * Dto uses as a array container of UsetDto. 
 * 
 */

public class UserListDto extends BaseDto<UserListDto> {
	
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
	
	
}
