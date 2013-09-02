package org.palaso.languageforge.client.lex.main.service;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpc;
import org.palaso.languageforge.client.lex.model.IanaDto;
import org.palaso.languageforge.client.lex.model.ProjectDto;
import org.palaso.languageforge.client.lex.model.UserListDto;
import org.palaso.languageforge.client.lex.model.UserSettingsDto;
import org.palaso.languageforge.client.lex.model.settings.fields.SettingFieldsDto;
import org.palaso.languageforge.client.lex.model.settings.inputsystems.SettingInputSystemsDto;
import org.palaso.languageforge.client.lex.model.settings.tasks.SettingTasksDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The RPC interface of the Lexicon client.
 * 
 */
public interface IBaseService {

	/**
	 * 
	 * @return instance of JsonRpc
	 */
	public JsonRpc getJsonRpc();


	/**
	 * Gets a list of all users from current project.
	 * 
	 * @param asyncCallback
	 */
	void getUsersListInProject(AsyncCallback<UserListDto> asyncCallback);

	/**
	 * Gets a datas of all IANA language registers
	 * 
	 * @param asyncCallback
	 */
	void getIanaData(AsyncCallback<IanaDto> asyncCallback);

	/**
	 * Gets all ldml data of selected project
	 * 
	 * @param asyncCallback
	 */
	void getSettingInputSystems(
			AsyncCallback<SettingInputSystemsDto> asyncCallback);

	/**
	 * update all ldml data of selected project
	 * 
	 * @param asyncCallback
	 */
	void updateSettingInputSystems(SettingInputSystemsDto list,
			AsyncCallback<SettingInputSystemsDto> asyncCallback);

	/**
	 * Gets WeSayConfig fields setting of specified user
	 * 
	 * @param asyncCallback
	 */
	void getSettingUserFieldsSetting(String userId,
			AsyncCallback<SettingFieldsDto> asyncCallback);

	/**
	 * Gets WeSayConfig tasks setting of specified user
	 * 
	 * @param asyncCallback
	 */
	void getSettingUserTasksSetting(String userId,
			AsyncCallback<SettingTasksDto> asyncCallback);

	/**
	 * Gets WeSayConfig both tasks and fields settings of specified user
	 * 
	 * @param asyncCallback
	 */
	void getSettingUserSettings(String userId,
			AsyncCallback<UserSettingsDto> asyncCallback);

	/**
	 * persist the user(s) tasks setting into server
	 * 
	 * @param userId
	 *            a user Id or a group of Id splited by '|' , "4|5|6"
	 * @param tasksDto
	 *            the tasks will saved
	 * @param asyncCallback
	 */
	void updateSettingUserTasksSetting(String userId, SettingTasksDto tasksDto,
			AsyncCallback<SettingTasksDto> asyncCallback);

	/**
	 * persist the user(s) fields setting into server
	 * 
	 * @param userId
	 *            a user Id or a group of Id splited by '|' , "4|5|6"
	 * @param fieldsDto
	 *            the fields will saved
	 * @param asyncCallback
	 */
	void updateSettingUserFieldsSetting(String userId,
			SettingFieldsDto fieldsDto,
			AsyncCallback<SettingFieldsDto> asyncCallback);

	/**
	 * 
	 * @param projectName
	 *            new project name which will send to server
	 * @param projectId
	 *            the project which need to update name
	 * @param asyncCallback
	 */
	void updateProjectName(String projectName, String projectId,
			AsyncCallback<ProjectDto> asyncCallback);


}
