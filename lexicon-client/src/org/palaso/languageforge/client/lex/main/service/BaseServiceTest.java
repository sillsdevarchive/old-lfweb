package org.palaso.languageforge.client.lex.main.service;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpc;
import org.palaso.languageforge.client.lex.model.IanaDto;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.ProjectDto;
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
	public void getSettingUserFieldsSetting(String userId,
			AsyncCallback<SettingFieldsDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getSettingUserTasksSetting(String userId,
			AsyncCallback<SettingTasksDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getSettingUserSettings(String userId,
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
	public void updateProjectName(String projectName, String projectId,
			AsyncCallback<ProjectDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public JsonRpc getJsonRpc() {
		// TODO Auto-generated method stub
		return null;
	}

}
