package org.palaso.languageforge.client.lex.configure;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.palaso.languageforge.client.lex.common.IPersistable;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureMainPresenter;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingFieldsPresenter;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingInputSystemsPresenter;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingMembersPresenter;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingPropertiesPresenter;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingTasksPresenter;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingsPresenter;
import org.palaso.languageforge.client.lex.configure.presenter.InviteFriendByEmailPresenter;
import org.palaso.languageforge.client.lex.configure.presenter.LanguageCodeLookupPresenter;
import org.palaso.languageforge.client.lex.configure.view.ConfigureMainView;
import org.palaso.languageforge.client.lex.main.LexGinModule;
import org.palaso.languageforge.client.lex.model.IanaBaseDataDto;
import org.palaso.languageforge.client.lex.model.UserDto;
import org.palaso.languageforge.client.lex.model.UserListDto;
import org.palaso.languageforge.client.lex.model.settings.fields.SettingFieldsFieldElementDto;
import org.palaso.languageforge.client.lex.model.settings.inputsystems.SettingInputSystemsDto;
import org.palaso.languageforge.client.lex.model.settings.tasks.SettingTasksTaskElementDto;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;

@Debug(logLevel = LogLevel.DETAILED)
@Events(startView = ConfigureMainView.class, module = ConfigureModule.class, ginModules = LexGinModule.class)
public interface ConfigureEventBus extends EventBus {

	@Event(handlers = ConfigureMainPresenter.class)
	void goToConfigureSettings();

	@Event(handlers = ConfigureSettingsPresenter.class)
	public void startSettings(SimplePanel panel);

	@Event(forwardToParent = true)
	void changeBody(Widget body);

	@Event(forwardToParent = true)
	void displayMessage(String message);

	@Event(forwardToParent = true)
	void handleError(Throwable caught);


	@Event(forwardToParent = true)
	void openNewWindow(String url);

	@Event(forwardToParent = true)
	void taskSettingChanged();

	@Event(forwardToParent = true)
	void reloadLex();

	@Event(handlers = ConfigureSettingPropertiesPresenter.class)
	void attachPropertiesView(SimplePanel tasksPanel);

	@Event(handlers = ConfigureSettingMembersPresenter.class)
	void attachMembersView(SimplePanel tasksPanel);

	@Event(handlers = ConfigureSettingTasksPresenter.class)
	void attachTasksView(SimplePanel tasksPanel);

	@Event(handlers = ConfigureSettingFieldsPresenter.class)
	void attachFieldsView(SimplePanel fieldsPanel);

	@Event(handlers = ConfigureSettingInputSystemsPresenter.class)
	void attachInputSystemsView(SimplePanel inputSystemsPanel);

	@Event(handlers = LanguageCodeLookupPresenter.class)
	void attachLanguageCodeLookupView(PopupPanel panel);

	@Event(handlers = ConfigureSettingInputSystemsPresenter.class)
	void addNewLanguageCodeToInputSystem(IanaBaseDataDto data);

	@Event(handlers = ConfigureSettingTasksPresenter.class)
	void fillTasks(JsArray<SettingTasksTaskElementDto> tasksDto);

	@Event(handlers = ConfigureSettingFieldsPresenter.class)
	void fillFields(JsArray<SettingFieldsFieldElementDto> fieldsDto);

	@Event(handlers = ConfigureSettingsPresenter.class)
	void updateUserInProjectList(UserListDto listDto);

	@Event(handlers = ConfigureSettingsPresenter.class)
	void getUserInProjectList(Map<String, UserDto> list);

	@Event(handlers = ConfigureSettingsPresenter.class)
	void getIanaLanguages(SortedMap<String, IanaBaseDataDto> list);

	@Event(handlers = ConfigureSettingsPresenter.class)
	void getIanaRegions(SortedMap<String, IanaBaseDataDto> list);

	@Event(handlers = ConfigureSettingsPresenter.class)
	void getIanaScripts(SortedMap<String, IanaBaseDataDto> list);

	@Event(handlers = ConfigureSettingsPresenter.class)
	void getIanaVariants(SortedMap<String, IanaBaseDataDto> list);

	@Event(handlers = ConfigureSettingsPresenter.class)
	void getSettingInputSystems(List<SettingInputSystemsDto> dtoInList);
 
	@Event(handlers = ConfigureSettingsPresenter.class)
	void setSettingInputSystems(SettingInputSystemsDto dto);
	
	@Event(handlers = ConfigureSettingsPresenter.class)
	void addSubControl(SimplePanel panel, IPersistable control);
	
	@Event(handlers = InviteFriendByEmailPresenter.class)
	void attachInviteFriendByEmailView(PopupPanel panel, String emailAddress);
	
	@Event(handlers = ConfigureSettingsPresenter.class)
	void reloadIana();
	
	@Event(handlers = ConfigureSettingFieldsPresenter.class)
	void fieldSettingsReloadIana();
}
