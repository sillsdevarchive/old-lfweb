package org.palaso.languageforge.client.lex.configure.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.palaso.languageforge.client.lex.common.AutoSuggestPresenterOption;
import org.palaso.languageforge.client.lex.common.AutoSuggestPresenterOptionResultSet;
import org.palaso.languageforge.client.lex.common.IEnum;
import org.palaso.languageforge.client.lex.common.IPersistable;
import org.palaso.languageforge.client.lex.common.MessageFormat;
import org.palaso.languageforge.client.lex.model.CurrentEnvironmentDto;
import org.palaso.languageforge.client.lex.model.UserDto;
import org.palaso.languageforge.client.lex.model.UserListDto;
import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.configure.ConfigureEventBus;
import org.palaso.languageforge.client.lex.configure.view.ConfigureSettingMembersView;
import org.palaso.languageforge.client.lex.main.service.ILexService;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = ConfigureSettingMembersView.class)
public class ConfigureSettingMembersPresenter extends
		BasePresenter<ConfigureSettingMembersPresenter.IConfigureSettingMembersView, ConfigureEventBus> implements
		IPersistable {
	@Inject
	public ILexService lexService;

	private ActionBtnMode actionBtnMode = ActionBtnMode.ADDTOMEMBER;
	private UserDto selectedSuggestUser = null;
	private int indexFrom = 0;
	private int indexTo = 0;
	private SuggestBox memberSuggestBox;
	private Map<String, Object> valueMap;

	private enum ActionBtnMode implements IEnum {
		INVITE("INVITE"), ADDTOMEMBER("ADDTOMEMBER"), CREATEMEMBER("CREATEMEMBER");

		private String value;

		ActionBtnMode(String value) {
			this.value = value;
		}

		@Override
		public String getValue() {
			return this.value;
		}

	}

	public interface IConfigureSettingMembersView {
		public Widget getWidget();

		public CellTable<UserDto> getMemberTable();

		public SimplePager getTablePager();

		public SimplePanel getMemberSuggestBoxPanel();

		public Button getSuggestBoxClearBtn();

		public Button getAddBtn();

		public void createMemberTable();

		public TextColumn<UserDto> getNameColumn();

		public Column<UserDto, String> getRoleColumn();

		public Column<UserDto, String> getRemoveColumn();

	}

	public void bind() {

		// SuggestBox
		view.createMemberTable();
		TextBoxBase textfield = new TextBox();
		final MemberSuggestOracle oracle = new MemberSuggestOracle();
		memberSuggestBox = new SuggestBox(oracle, textfield);
		memberSuggestBox.setWidth("300px");
		memberSuggestBox.setStyleName("gwt-TextBox");
		view.getMemberSuggestBoxPanel().setWidget(memberSuggestBox);
		valueMap = new HashMap<String, Object>();

		memberSuggestBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				Suggestion suggestion = event.getSelectedItem();
				if (suggestion instanceof MemberSuggestion) {
					MemberSuggestion memberSuggestion = (MemberSuggestion) suggestion;

					if (memberSuggestion.isNavigatorSuggestion()) {

						if (memberSuggestion.getValue().equals(MemberSuggestion.NEXT_VALUE)) {
							indexFrom = indexTo + 1;
							indexTo = indexFrom + Constants.SUGGEST_BOX_PAGE_SIZE;
						} else {
							indexFrom = indexFrom - Constants.SUGGEST_BOX_PAGE_SIZE - 1;
							indexTo = indexTo - Constants.SUGGEST_BOX_PAGE_SIZE;
							if (indexFrom < 0) {
								resetPageIndices();
							}
						}

						oracle.fetchSuggestions();
						return;
					} else {
						Object value = memberSuggestion.getValue();
						memberSuggestBox.getTextBox().setText(memberSuggestion.getName());

						filterList(memberSuggestion.getName());
						if (value instanceof UserDto) {
							// it is a User
							Map<Integer, UserDto> list = new HashMap<Integer, UserDto>();
							eventBus.getUserInProjectList(list);
							checkSuggestString(((UserDto) value).getName(), false);

							// check if this user is already in project
							if (list.containsKey(((UserDto) value).getId())) {
								view.getAddBtn().setEnabled(false);
							} else {
								view.getAddBtn().setEnabled(true);
								selectedSuggestUser = (UserDto) value;
							}

						} else {
							view.getAddBtn().setEnabled(false);
						}
					}
				} else {
					view.getAddBtn().setEnabled(false);
				}
			}
		});

		memberSuggestBox.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (valueMap.containsKey(memberSuggestBox.getTextBox().getText())) {
					selectedSuggestUser = (UserDto) valueMap.get(memberSuggestBox.getTextBox().getText());
					checkSuggestString(memberSuggestBox.getTextBox().getText(), false);
				} else {
					checkSuggestString(memberSuggestBox.getTextBox().getText(), true);
				}

				filterList(memberSuggestBox.getTextBox().getText());
			}
		});

		view.getSuggestBoxClearBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				memberSuggestBox.getTextBox().setText("");
				setAddButtonMode(false, false);
				refreshTable();
			}
		});

		view.getAddBtn().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (actionBtnMode == ActionBtnMode.INVITE) {
					PopupPanel popupPanel = new PopupPanel(true);
					popupPanel.addStyleName("popup-invite");
					popupPanel.setGlassEnabled(true);
					popupPanel.setModal(true);
					popupPanel.setAutoHideEnabled(false);
					eventBus.attachInviteFriendByEmailView(popupPanel, memberSuggestBox.getTextBox().getText());
					popupPanel.center();
					popupPanel.show();
				} else if (actionBtnMode == ActionBtnMode.ADDTOMEMBER) {
					if (selectedSuggestUser == null) {
						return;
					}

					lexService.addUserToProject(selectedSuggestUser.getId(), CurrentEnvironmentDto.getCurrentProject()
							.getProjectId(), new AsyncCallback<UserListDto>() {

						@Override
						public void onSuccess(UserListDto result) {
							if (result.getEntries().length() > 0) {
								eventBus.displayMessage(I18nConstants.STRINGS
										.ConfigureSettingMembersPresenter_User_added_to_project_successfully());
								eventBus.updateUserInProjectList(result);
								memberSuggestBox.getTextBox().setText("");
								setAddButtonMode(false, false);
								refreshTable();
								selectedSuggestUser = null;
							} else {
								eventBus.displayMessage(I18nConstants.STRINGS
										.ConfigureSettingMembersPresenter_Error_User_cannot_be_added_to_project());
							}

						}

						@Override
						public void onFailure(Throwable caught) {
							eventBus.handleError(caught);
						}
					});
				} else if (actionBtnMode == ActionBtnMode.CREATEMEMBER) {
					String newName = memberSuggestBox.getTextBox().getText();

					String message = MessageFormat.format(
							I18nConstants.STRINGS
									.ConfigureSettingMembersPresenter_A_new_member_named_X_will_be_created_and_added_to_the_project_continue(),
							new Object[] { newName });
					if (!Window.confirm(message)) {
						return;
					}
					lexService.rapidUserCreationAction(CurrentEnvironmentDto.getCurrentProject().getProjectId(),
							newName, new AsyncCallback<UserListDto>() {

								@Override
								public void onSuccess(UserListDto result) {

									if (result.getEntries().length() > 0) {
										eventBus.displayMessage(I18nConstants.STRINGS
												.ConfigureSettingMembersPresenter_User_added_to_project_successfully());
										eventBus.updateUserInProjectList(result);
										memberSuggestBox.getTextBox().setText("");
										setAddButtonMode(false, false);
										refreshTable();
									} else {
										eventBus.displayMessage(I18nConstants.STRINGS
												.ConfigureSettingMembersPresenter_Error_User_cannot_be_added_to_project());
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									eventBus.handleError(caught);
								}
							});

				}
			}
		});

		view.getRemoveColumn().setFieldUpdater(new FieldUpdater<UserDto, String>() {
			@Override
			public void update(int index, UserDto object, String value) {

				String message = MessageFormat.format(
						I18nConstants.STRINGS
								.ConfigureSettingMembersPresenter_You_will_remove_X_from_this_project_all_related_setting_will_be_deleted_continue(),
						new Object[] { object.getName() });
				if (!Window.confirm(message)) {
					return;
				}
				if (object.getId() == CurrentEnvironmentDto.getCurrentUser().getId()) {
					message =MessageFormat.format("You can not remove yourself \"{0}\" from project!", CurrentEnvironmentDto
							.getCurrentUser().getName());
					eventBus.handleError(new Exception(message));
					return;
				}
				lexService.removeUserFromProjectAction(object.getId(), CurrentEnvironmentDto.getCurrentProject()
						.getProjectId(), new AsyncCallback<UserListDto>() {

					@Override
					public void onSuccess(UserListDto result) {
						if (result.getEntries().length() > 0) {
							eventBus.updateUserInProjectList(result);
							setAddButtonMode(false, false);
							refreshTable();
						} else {
							// user tried to remove himself from the
							// project or removed by someone else
							eventBus.displayMessage(I18nConstants.STRINGS
									.ConfigureSettingMembersPresenter_Error_You_cannot_remove_this_user_from_the_project());
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						eventBus.handleError(caught);
					}
				});

			}
		});

		view.getRoleColumn().setFieldUpdater(new FieldUpdater<UserDto, String>() {

			@Override
			public void update(int index, UserDto userDto, String value) {

				userDto.setRoleName(value);
				lexService.updateUserAccessGrant(userDto, CurrentEnvironmentDto.getCurrentProject().getProjectId(),
						new AsyncCallback<UserDto>() {

							@Override
							public void onFailure(Throwable caught) {
								eventBus.handleError(caught);
							}

							@Override
							public void onSuccess(UserDto result) {
								refreshTable();
							}
						});
				// Window.alert("userDto" + userDto.getRoleName() + " value " +
				// value);
			}
		});
	}

	private void filterList(String filter) {
		if (filter == null || filter.length() == 0) {
			refreshTable();
		} else {
			List<UserDto> filtedList = new ArrayList<UserDto>();

			Map<Integer, UserDto> list = new HashMap<Integer, UserDto>();
			eventBus.getUserInProjectList(list);
			List<UserDto> orgList = new ArrayList<UserDto>(list.values());

			for (int i = 0; i < orgList.size(); i++) {
				UserDto item = orgList.get(i);

				if (contains(item.getName().toLowerCase(), filter.toLowerCase())) {

					filtedList.add(item);
				}
			}
			if (filtedList.size() > 0) {
				setAddButtonMode(false, false);
			}
			showData(filtedList);
		}
	}

	private boolean contains(String original, String filter) {
		return original.contains(filter);
	}

	public void onAttachMembersView(SimplePanel simplePanel) {
		simplePanel.clear();
		simplePanel.add(view.getWidget());
		eventBus.addSubControl(simplePanel, this);
		setAddButtonMode(false, false);
		refreshTable();
	}

	private void resetPageIndices() {
		indexFrom = 0;
		indexTo = indexFrom + Constants.SUGGEST_BOX_PAGE_SIZE - 1;
	}

	private void checkSuggestString(String text, boolean addNew) {
		if (text.trim().isEmpty()) {
			setAddButtonMode(false, addNew);
			return;
		}

		RegExp regExp = RegExp.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

		if (regExp.test(text)) {
			// it is a email
			setAddButtonMode(true, addNew);
			view.getAddBtn().setEnabled(true);
		} else {
			setAddButtonMode(false, addNew);
			view.getAddBtn().setEnabled(true);
		}
	}

	private void setAddButtonMode(boolean asInvite, boolean addNew) {
		if (asInvite) {
			view.getAddBtn().setText(I18nConstants.STRINGS.ConfigureSettingMembersPresenter_Invite());
			actionBtnMode = ActionBtnMode.INVITE;
		} else if (addNew) {
			view.getAddBtn().setText(I18nConstants.STRINGS.ConfigureSettingMembersPresenter_Create_member());
			actionBtnMode = ActionBtnMode.CREATEMEMBER;
		} else {
			view.getAddBtn().setText(I18nConstants.STRINGS.ConfigureSettingMembersPresenter_Add_to_project());
			actionBtnMode = ActionBtnMode.ADDTOMEMBER;
		}
		if (memberSuggestBox.getTextBox().getText().trim().isEmpty()) {
			view.getAddBtn().setEnabled(false);
			view.getSuggestBoxClearBtn().setEnabled(false);
		} else {
			// view.getAddBtn().setEnabled(true);
			view.getSuggestBoxClearBtn().setEnabled(true);
		}
	}

	private void refreshTable() {

		CellTable<UserDto> cellTable = view.getMemberTable();
		Map<Integer, UserDto> list = new HashMap<Integer, UserDto>();
		eventBus.getUserInProjectList(list);
		cellTable.setRowCount(list.values().size());
		showData(new ArrayList<UserDto>(list.values()));
	}

	private void showData(final List<UserDto> userList) {
		AsyncDataProvider<UserDto> provider = new AsyncDataProvider<UserDto>() {
			@Override
			protected void onRangeChanged(HasData<UserDto> display) {
				int start = display.getVisibleRange().getStart();
				int end = start + display.getVisibleRange().getLength();
				end = end >= userList.size() ? userList.size() : end;
				if (end >= start) {
					List<UserDto> sub = userList.subList(start, end);
					updateRowData(start, sub);
				}
				view.getTablePager().setVisible(userList.size() > 0);

			}
		};
		if (!provider.getDataDisplays().contains(view.getMemberTable())) {
			provider.addDataDisplay(view.getMemberTable());
		}

		provider.updateRowCount(userList.size(), true);
		view.getTablePager().setDisplay(view.getMemberTable());
	}

	@Override
	public boolean persistData(UserDto user) {
		// eventBus.showLoadingIndicator();

		return true;
	}

	@Override
	public boolean isMultiUserSupported() {
		return false;
	}

	@Override
	public boolean isPersistDataByParentSupported() {
		return false;
	}

	/**
	 * Retrieve Options (name-value pairs) that are suggested from the JSON-RPC
	 * server }
	 * 
	 * @param query
	 *            - the String search term
	 * @param from
	 *            - the 0-based begin index int
	 * @param to
	 *            - the end index inclusive int
	 * @param callback
	 *            - the OptionQueryCallback to handle the response
	 */
	private void queryOptions(final String query, final int from, final int to, final MemberQueryCallback callback) {

		lexService.getMembersForAutoSuggest(query, from, to + 1, new AsyncCallback<UserListDto>() {

			@Override
			public void onFailure(Throwable caught) {
				eventBus.handleError(caught);
			}

			@Override
			public void onSuccess(UserListDto result) {

				AutoSuggestPresenterOptionResultSet options = new AutoSuggestPresenterOptionResultSet(result
						.getEntries().length());

				for (int i = 0; i < result.getEntries().length(); i++) {
					UserDto userDto = result.getEntries().get(i);
					AutoSuggestPresenterOption option = new AutoSuggestPresenterOption();
					option.setName(userDto.getName());
					option.setValue(userDto);
					options.addOption(option);
				}
				callback.success(options);
			}
		});
	}

	private class MemberSuggestOracle extends SuggestOracle {

		private SuggestOracle.Request request;
		private SuggestOracle.Callback callback;
		private Timer timer;

		public MemberSuggestOracle() {
			timer = new Timer() {

				@Override
				public void run() {

					if (!memberSuggestBox.getText().trim().isEmpty()
							&& memberSuggestBox.getText().trim().length() > Constants.SUGGEST_BOX_MINIMUM_CHAR - 1) {
						fetchSuggestions();
					}
				}
			};

		}

		public void fetchSuggestions() {
			String query = request.getQuery();

			query = query.trim();
			if (query.length() > 0 && valueMap.get(query) == null) {

				queryOptions(query, indexFrom, indexTo, new MemberSuggestCallback(request, callback, query));
			}
		}

		@Override
		public boolean isDisplayStringHTML() {
			return true;
		}

		@Override
		public void requestSuggestions(Request request, Callback callback) {
			// This is the method that gets called by the SuggestBox whenever
			// some types into the text field
			this.request = request;
			this.callback = callback;

			// reset the indexes (b/c NEXT and PREV call getSuggestions
			// directly)
			resetPageIndices();

			// If the user keeps triggering this event (e.g., keeps typing),
			// cancel and restart the timer
			timer.cancel();
			timer.schedule(Constants.SUGGEST_BOX_DELAY);
		}

	}

	/**
	 * A custom callback that has the original SuggestOracle.Request and
	 * SuggestOracle.Callback
	 */
	private class MemberSuggestCallback extends MemberQueryCallback {
		private SuggestOracle.Request request;
		private SuggestOracle.Callback callback;
		private String query;

		MemberSuggestCallback(Request request, Callback callback, String query) {
			this.request = request;
			this.callback = callback;
			this.query = query;
		}

		public void success(AutoSuggestPresenterOptionResultSet optResults) {
			SuggestOracle.Response resp = new SuggestOracle.Response();
			List<MemberSuggestion> suggs = new ArrayList<MemberSuggestion>();
			int totSize = optResults.getTotalSize();

			if (totSize > 0) {

				// if not at the first page, show PREVIOUS
				if (indexFrom > 0) {
					MemberSuggestion prev = new MemberSuggestion(MemberSuggestion.PREVIOUS_VALUE, request.getQuery());
					suggs.add(prev);
				}

				Map<Integer, UserDto> list = new HashMap<Integer, UserDto>();
				eventBus.getUserInProjectList(list);

				// set the value into the suggestion in 2 steps
				// 1st: in project members
				for (AutoSuggestPresenterOption o : optResults.getOptions()) {
					if (suggs.size() >= Constants.SUGGEST_BOX_PAGE_SIZE) {
						break;
					}
					MemberSuggestion sugg = new MemberSuggestion(o.getName(), o.getValue(), request.getQuery(), query);
					if (sugg.value instanceof UserDto && list.containsKey(((UserDto) sugg.value).getId())) {
						suggs.add(sugg);
					}
				}

				// 2nd: non-members
				for (AutoSuggestPresenterOption o : optResults.getOptions()) {
					MemberSuggestion sugg = new MemberSuggestion(o.getName(), o.getValue(), request.getQuery(), query);
					if (suggs.size() >= Constants.SUGGEST_BOX_PAGE_SIZE) {
						break;
					}

					if (!(sugg.value instanceof UserDto)) {
						suggs.add(sugg);
					} else if (!list.containsKey(((UserDto) sugg.value).getId())) {
						suggs.add(sugg);
					}
				}

				// if there are more pages, show NEXT
				if (Constants.SUGGEST_BOX_PAGE_SIZE <= totSize + 1) {
					MemberSuggestion next = new MemberSuggestion(MemberSuggestion.NEXT_VALUE, request.getQuery());
					suggs.add(next);
				}

			}

			if (totSize == 0) {
				// no hit, create new one
				checkSuggestString(memberSuggestBox.getTextBox().getText(), true);
				view.getAddBtn().setEnabled(true);
			} else {
				boolean hasHit = false;
				for (AutoSuggestPresenterOption o : optResults.getOptions()) {
					if (o.getName().equalsIgnoreCase(memberSuggestBox.getTextBox().getText().trim())) {
						hasHit = true;
					}
				}
				if (hasHit) {
					checkSuggestString(memberSuggestBox.getTextBox().getText(), false);
				} else {
					checkSuggestString(memberSuggestBox.getTextBox().getText(), true);
					view.getAddBtn().setEnabled(true);
				}
			}

			// it's ok (and good) to pass an empty suggestion list back to the
			// suggest box's callback method
			// the list is not shown at all if the list is empty.
			resp.setSuggestions(suggs);
			callback.onSuggestionsReady(request, resp);
		}

		@Override
		public void error(Throwable excOptionResultSeteption) {

		}

	}

	/**
	 * A bean to serve as a custom suggestion so that the value is available and
	 * the replace will look like it is supporting multivalues
	 */
	private class MemberSuggestion implements SuggestOracle.Suggestion {
		private String display;
		private String replace;
		private Object value;
		private String name;
		private boolean isNavigator = false;

		public static final String NEXT_VALUE = "NEXT";
		public static final String PREVIOUS_VALUE = "PREVIOUS";

		MemberSuggestion(String nav, String currentTextValue) {
			if (NEXT_VALUE.equals(nav)) {
				display = "<div class=\"autocompleterNext\" title=\""
						+ I18nConstants.STRINGS.ConfigureSettingMembersPresenter_Next() + "\"></div>";
			} else {
				display = "<div class=\"autocompleterPrev\" title=\""
						+ I18nConstants.STRINGS.ConfigureSettingMembersPresenter_Previous() + "\"></div>";
			}
			replace = currentTextValue;
			value = nav;
			isNavigator = true;
		}

		MemberSuggestion(String displ, Object val, String replacePre, String query) {
			name = displ;
			int begin = displ.toLowerCase().indexOf(query.toLowerCase());
			if (begin >= 0) {
				int end = begin + query.length();
				String match = displ.substring(begin, end);
				display = displ.replaceFirst(match, "<b>" + match + "</b>");
			} else {
				// may not necessarily be a part of the query, for example if
				// "*" was typed.
				display = displ;
			}
			replace = replacePre;
			value = val;
			isNavigator = false;
		}

		public boolean isNavigatorSuggestion() {
			return isNavigator;
		}

		@Override
		public String getDisplayString() {
			return display;
		}

		@Override
		public String getReplacementString() {
			return replace;
		}

		/**
		 * Get the value of the option
		 * 
		 * @return value
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Get the name of the option. (when not multivalued, this will be the
		 * same as getReplacementString)
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}
	}

	/**
	 * An abstract class that handles success and error conditions from the REST
	 * call
	 */
	public abstract class MemberQueryCallback {
		abstract void success(AutoSuggestPresenterOptionResultSet optResults);

		abstract void error(Throwable exception);
	}

}
