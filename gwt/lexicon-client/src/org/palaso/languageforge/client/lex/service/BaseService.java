package org.palaso.languageforge.client.lex.service;

import org.palaso.languageforge.client.lex.jsonrpc.DispatchAsync;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpc;
import org.palaso.languageforge.client.lex.model.IanaDto;
import org.palaso.languageforge.client.lex.model.ProjectDto;
import org.palaso.languageforge.client.lex.model.UserDto;
import org.palaso.languageforge.client.lex.model.UserListDto;
import org.palaso.languageforge.client.lex.model.UserSettingsDto;
import org.palaso.languageforge.client.lex.model.settings.fields.SettingFieldsDto;
import org.palaso.languageforge.client.lex.model.settings.inputsystems.SettingInputSystemsDto;
import org.palaso.languageforge.client.lex.model.settings.tasks.SettingTasksDto;
import org.palaso.languageforge.client.lex.service.actions.AddUserToProjectAction;
import org.palaso.languageforge.client.lex.service.actions.GetIanaDataAction;
import org.palaso.languageforge.client.lex.service.actions.GetSettingInputSystemsAction;
import org.palaso.languageforge.client.lex.service.actions.GetSettingUserFieldsSettingAction;
import org.palaso.languageforge.client.lex.service.actions.GetSettingUserSettingsAction;
import org.palaso.languageforge.client.lex.service.actions.GetSettingUserTasksSettingAction;
import org.palaso.languageforge.client.lex.service.actions.GetUsersListAction;
import org.palaso.languageforge.client.lex.service.actions.InviteFriendByEmailAction;
import org.palaso.languageforge.client.lex.service.actions.MemberAutoSuggestAction;
import org.palaso.languageforge.client.lex.service.actions.RapidUserCreationAction;
import org.palaso.languageforge.client.lex.service.actions.RemoveUserFromProjectAction;
import org.palaso.languageforge.client.lex.service.actions.UpdateProjectNameAction;
import org.palaso.languageforge.client.lex.service.actions.UpdateSettingInputSystemsAction;
import org.palaso.languageforge.client.lex.service.actions.UpdateSettingUserFieldsSettingAction;
import org.palaso.languageforge.client.lex.service.actions.UpdateSettingUserTasksSettingAction;
import org.palaso.languageforge.client.lex.service.actions.UpdateUserAccessRoleAction;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Singleton;

/**
 * LexService represents the implementation of ILexService
 * 
 * @author xin
 * 
 */
@Singleton
public class BaseService implements IBaseService {

	protected DispatchAsync remoteAsync;

	public BaseService() {
		remoteAsync = new JsonRpc();
	}

	public JsonRpc getJsonRpc() {
		return (JsonRpc) remoteAsync;
	}

	@Override
	public void getUsersListInProject(AsyncCallback<UserListDto> asyncCallback) {
		GetUsersListAction action = new GetUsersListAction();
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void getIanaData(AsyncCallback<IanaDto> asyncCallback) {
		GetIanaDataAction action = new GetIanaDataAction();
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void getSettingInputSystems(
			AsyncCallback<SettingInputSystemsDto> asyncCallback) {
		GetSettingInputSystemsAction action = new GetSettingInputSystemsAction();
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void updateSettingInputSystems(SettingInputSystemsDto list,
			AsyncCallback<SettingInputSystemsDto> asyncCallback) {
		UpdateSettingInputSystemsAction action = new UpdateSettingInputSystemsAction(
				list);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void getSettingUserFieldsSetting(int userId,
			AsyncCallback<SettingFieldsDto> asyncCallback) {
		GetSettingUserFieldsSettingAction action = new GetSettingUserFieldsSettingAction(
				userId);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void getSettingUserTasksSetting(int userId,
			AsyncCallback<SettingTasksDto> asyncCallback) {
		GetSettingUserTasksSettingAction action = new GetSettingUserTasksSettingAction(
				userId);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void getSettingUserSettings(int userId,
			AsyncCallback<UserSettingsDto> asyncCallback) {
		GetSettingUserSettingsAction action = new GetSettingUserSettingsAction(
				userId);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void updateSettingUserTasksSetting(String userId,
			SettingTasksDto tasksDto,
			AsyncCallback<SettingTasksDto> asyncCallback) {

		UpdateSettingUserTasksSettingAction action = new UpdateSettingUserTasksSettingAction(
				userId, tasksDto);

		remoteAsync.execute(action, asyncCallback);

	}

	@Override
	public void updateSettingUserFieldsSetting(String userId,
			SettingFieldsDto fieldsDto,
			AsyncCallback<SettingFieldsDto> asyncCallback) {
		UpdateSettingUserFieldsSettingAction action = new UpdateSettingUserFieldsSettingAction(
				userId, fieldsDto);

		remoteAsync.execute(action, asyncCallback);

	}

	@Override
	public void updateProjectName(String projectName, int projectId,
			AsyncCallback<ProjectDto> asyncCallback) {
		UpdateProjectNameAction action = new UpdateProjectNameAction(projectId,
				projectName);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void getMembersForAutoSuggest(String searchString, int indexFrom,
			int indexTo, AsyncCallback<UserListDto> asyncCallback) {

		MemberAutoSuggestAction action = new MemberAutoSuggestAction(
				searchString, indexFrom, indexTo);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void addUserToProject(int userId, int projectId,
			AsyncCallback<UserListDto> asyncCallback) {
		AddUserToProjectAction action = new AddUserToProjectAction(userId,
				projectId);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void removeUserFromProjectAction(int userId, int projectId,
			AsyncCallback<UserListDto> asyncCallback) {
		RemoveUserFromProjectAction action = new RemoveUserFromProjectAction(
				userId, projectId);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void updateUserAccessGrant(UserDto userDto, int projectId,
			AsyncCallback<UserDto> asyncCallback) {
		UpdateUserAccessRoleAction action = new UpdateUserAccessRoleAction(
				userDto, projectId);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void inviteFriendByEmail(int projectId, String emailAddress,
			String msg, AsyncCallback<String> asyncCallback) {
		InviteFriendByEmailAction action = new InviteFriendByEmailAction(
				projectId, emailAddress, msg);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void rapidUserCreationAction(int projectId, String newName,
			AsyncCallback<UserListDto> asyncCallback) {
		RapidUserCreationAction action = new RapidUserCreationAction(newName,
				projectId);
		remoteAsync.execute(action, asyncCallback);
	}
}
