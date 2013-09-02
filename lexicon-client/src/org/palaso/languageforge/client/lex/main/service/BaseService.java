package org.palaso.languageforge.client.lex.main.service;

import org.palaso.languageforge.client.lex.jsonrpc.DispatchAsync;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpc;
import org.palaso.languageforge.client.lex.main.service.actions.GetIanaDataAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetSettingInputSystemsAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetSettingUserFieldsSettingAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetSettingUserSettingsAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetSettingUserTasksSettingAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetUsersListAction;
import org.palaso.languageforge.client.lex.main.service.actions.UpdateProjectNameAction;
import org.palaso.languageforge.client.lex.main.service.actions.UpdateSettingInputSystemsAction;
import org.palaso.languageforge.client.lex.main.service.actions.UpdateSettingUserFieldsSettingAction;
import org.palaso.languageforge.client.lex.main.service.actions.UpdateSettingUserTasksSettingAction;
import org.palaso.languageforge.client.lex.model.IanaDto;
import org.palaso.languageforge.client.lex.model.ProjectDto;
import org.palaso.languageforge.client.lex.model.UserListDto;
import org.palaso.languageforge.client.lex.model.UserSettingsDto;
import org.palaso.languageforge.client.lex.model.settings.fields.SettingFieldsDto;
import org.palaso.languageforge.client.lex.model.settings.inputsystems.SettingInputSystemsDto;
import org.palaso.languageforge.client.lex.model.settings.tasks.SettingTasksDto;

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
	public void getSettingUserFieldsSetting(String userId,
			AsyncCallback<SettingFieldsDto> asyncCallback) {
		GetSettingUserFieldsSettingAction action = new GetSettingUserFieldsSettingAction(
				userId);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void getSettingUserTasksSetting(String userId,
			AsyncCallback<SettingTasksDto> asyncCallback) {
		GetSettingUserTasksSettingAction action = new GetSettingUserTasksSettingAction(
				userId);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void getSettingUserSettings(String userId,
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
	public void updateProjectName(String projectName, String projectId,
			AsyncCallback<ProjectDto> asyncCallback) {
		UpdateProjectNameAction action = new UpdateProjectNameAction(projectId,
				projectName);
		remoteAsync.execute(action, asyncCallback);
	}

}
