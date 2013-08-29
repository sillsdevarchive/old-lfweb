package org.palaso.languageforge.client.lex.model;

import org.palaso.languageforge.client.lex.common.ConsoleLog;

import com.google.gwt.core.client.JavaScriptObject;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Base64;

// Dto uses for current environment data from server
public class CurrentEnvironmentDto extends JavaScriptObject {

	protected CurrentEnvironmentDto() {
	};

	protected final native static String getCurrentUserBase64() /*-{
		return $wnd.clientEnvironment.currentUser;
	}-*/;


	protected final native static void setCurrentUserBase64(String userBase64) /*-{
		$wnd.clientEnvironment.currentUser=userBase64;
	}-*/;
	
	protected final native static String getCurrentProjectBase64() /*-{
		return $wnd.clientEnvironment.currentProject;
	}-*/;

	protected final native static String getCurrentProjectAccessBase64() /*-{
		return $wnd.clientEnvironment.rights;
	}-*/;
	
	protected final native static String setCurrentProjectAccessBase64(String accessBase64) /*-{
		$wnd.clientEnvironment.rights=accessBase64;
	}-*/;
	
	public final static UserDto getCurrentUser() {
		if (getCurrentUserBase64() == null) {
			return null;
		}
		byte[] decodedJson= Base64.decode(getCurrentUserBase64());
		String decodedJsonString = new String(decodedJson);
		return UserDto.decode(decodedJsonString);
	}

	public final static void setCurrentUser(UserDto userDto) {
		if (userDto == null) {
			return;
		}
		String jsonString = UserDto.encode(userDto);
		String base64String=new String(Base64.encode(jsonString.getBytes()));
		setCurrentUserBase64(base64String);
	}
	
	public final static ProjectAccessDto getCurrentProjectAccess() {
		if (getCurrentProjectAccessBase64() == null) {
			return null;
		}
		byte[] decodedJson= Base64.decode(getCurrentProjectAccessBase64());
		String decodedJsonString = new String(decodedJson);
		ConsoleLog.log(decodedJsonString);
		return ProjectAccessDto.decode(decodedJsonString);
	}

	public final static void setCurrentProject(ProjectAccessDto accessDto) {
		if (accessDto == null) {
			return;
		}
		String jsonString = ProjectAccessDto.encode(accessDto);
		String base64String=new String(Base64.encode(jsonString.getBytes()));
		setCurrentProjectAccessBase64(base64String);
	}
	
	public final static ProjectDto getCurrentProject() {
		if (getCurrentProjectBase64() == null) {
			return null;
		}
		byte[] decodedJson= Base64.decode(getCurrentProjectBase64());
		String decodedJsonString = new String(decodedJson);
		return ProjectDto.decode(decodedJsonString);
	}
}
