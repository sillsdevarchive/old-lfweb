package org.palaso.languageforge.client.lex.service;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpc;
import org.palaso.languageforge.client.lex.model.IanaDto;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.ProjectDto;
import org.palaso.languageforge.client.lex.model.UserDto;
import org.palaso.languageforge.client.lex.model.UserListDto;
import org.palaso.languageforge.client.lex.model.UserSettingsDto;
import org.palaso.languageforge.client.lex.model.settings.fields.SettingFieldsDto;
import org.palaso.languageforge.client.lex.model.settings.inputsystems.SettingInputSystemsDto;
import org.palaso.languageforge.client.lex.model.settings.tasks.SettingTasksDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Singleton;

@Singleton
public class BaseServiceTest implements IBaseService {


	private final native LexiconEntryDto getEntry() /*-{
		return $wnd.jsonLexicalEntry;
	}-*/;


	@Override
	public void getUsersListInProject(AsyncCallback<UserListDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getIanaData(AsyncCallback<IanaDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getSettingInputSystems(
			AsyncCallback<SettingInputSystemsDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSettingInputSystems(SettingInputSystemsDto list,
			AsyncCallback<SettingInputSystemsDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getSettingUserFieldsSetting(int userId,
			AsyncCallback<SettingFieldsDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getSettingUserTasksSetting(int userId,
			AsyncCallback<SettingTasksDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getSettingUserSettings(int userId,
			AsyncCallback<UserSettingsDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSettingUserTasksSetting(String userId,
			SettingTasksDto tasksDto,
			AsyncCallback<SettingTasksDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSettingUserFieldsSetting(String userId,
			SettingFieldsDto fieldsDto,
			AsyncCallback<SettingFieldsDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProjectName(String projectName, int projectId,
			AsyncCallback<ProjectDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getMembersForAutoSuggest(String searchString, int indexFrom,
			int indexTo, AsyncCallback<UserListDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUserToProject(int userId, int projectId,
			AsyncCallback<UserListDto> asyncCallback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserFromProjectAction(int userId, int projectId,
			AsyncCallback<UserListDto> asyncCallback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserAccessGrant(UserDto userDto, int projectId,
			AsyncCallback<UserDto> asyncCallback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inviteFriendByEmail(int projectId, String emailAddress,
			String msg, AsyncCallback<String> asyncCallback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rapidUserCreationAction(int projectId, String newName,
			AsyncCallback<UserListDto> asyncCallback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JsonRpc getJsonRpc() {
		// TODO Auto-generated method stub
		return null;
	}

}
