package org.palaso.languageforge.client.lex.configure.view;

import java.util.ArrayList;
import java.util.List;

import org.palaso.languageforge.client.lex.controls.ThemedButtonCell;
import org.palaso.languageforge.client.lex.controls.ThemedComboBoxCell;
import org.palaso.languageforge.client.lex.model.CurrentEnvironmentDto;
import org.palaso.languageforge.client.lex.model.UserDto;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingMembersPresenter.IConfigureSettingMembersView;

import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.Resources;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class ConfigureSettingMembersView extends Composite implements IConfigureSettingMembersView {

	interface Binder extends UiBinder<Widget, ConfigureSettingMembersView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	SimplePanel memberSuggestBoxPanel;

	@UiField
	Button btnAdd;

	@UiField
	Button btnSuggestClean;

	@UiField(provided = true)
	CellTable<UserDto> memberTable = new CellTable<UserDto>();

	@UiField(provided = true)
	SimplePager memberTablePager = new SimplePager(TextLocation.CENTER,
			(Resources) GWT.create(SimplePager.Resources.class), false, 1000, true);

	private TextColumn<UserDto> columnName;
	private Column<UserDto, String> columnRole;

	private Column<UserDto, String> columnRemove;

	public ConfigureSettingMembersView() {
		initWidget(binder.createAndBindUi(this));
	}

	public Widget getWidget() {
		return this.asWidget();
	}

	@Override
	public CellTable<UserDto> getMemberTable() {
		return memberTable;
	}

	@Override
	public SimplePanel getMemberSuggestBoxPanel() {
		return memberSuggestBoxPanel;
	}

	@Override
	public Button getSuggestBoxClearBtn() {
		return btnSuggestClean;
	}

	@Override
	public Button getAddBtn() {
		return btnAdd;
	}

	@Override
	public SimplePager getTablePager() {
		return memberTablePager;
	}

	@Override
	public void createMemberTable() {
		memberTablePager.setPageSize(6);
		memberTable.setPageSize(6);
		// Add lex-dc-column to show the name.

		columnName = new TextColumn<UserDto>() {
			@Override
			public String getValue(UserDto object) {
				return object.getName();
			}
		};

		List<String> items = new ArrayList<String>();
		for (int i = 0; i < CurrentEnvironmentDto.getCurrentProjectAccess().getAllAvailableRoles().keys().length(); i++) {
			String key = CurrentEnvironmentDto.getCurrentProjectAccess().getAllAvailableRoles().keys().get(i);
			String role = CurrentEnvironmentDto.getCurrentProjectAccess().getAllAvailableRoles().value(key);

			items.add(role);
		}

		AbstractInputCell editTypeComboBox = new ThemedComboBoxCell(items);

		if (isIEVersionSupport()) {
			editTypeComboBox = new ThemedComboBoxCell(items);
		} else {
			editTypeComboBox = new SelectionCell(items);
		}

		columnRole = new Column<UserDto, String>(editTypeComboBox) {

			@Override
			public String getValue(UserDto object) {

				return "" + object.getRoleName();
			}

		};

		columnRemove = new Column<UserDto, String>(new ThemedButtonCell()) {
			@Override
			public String getValue(UserDto object) {
				return I18nConstants.STRINGS.ConfigureSettingMembersPresenter_Remove();
			}
		};

		columnName.setSortable(false);
		columnRole.setSortable(false);

		memberTable.setColumnWidth(columnName, "300px");
		memberTable.setColumnWidth(columnRole, "auto");

		memberTable.setColumnWidth(columnRemove, "150px");

		memberTable.addColumn(columnName, I18nConstants.STRINGS.ConfigureSettingMembersView_Name());
		memberTable.addColumn(columnRole, I18nConstants.STRINGS.ConfigureSettingMembersView_Role());

		memberTable.addColumn(columnRemove, " ");

	}

	@Override
	public TextColumn<UserDto> getNameColumn() {
		return columnName;
	}

	@Override
	public Column<UserDto, String> getRoleColumn() {
		return columnRole;
	}

	@Override
	public Column<UserDto, String> getRemoveColumn() {
		return columnRemove;
	}

	private static boolean isIEVersionSupport() {
		String agentString = Navigator.getUserAgent().toUpperCase();
		if (agentString.indexOf("MSIE 8") > 0 || agentString.indexOf("MSIE 7") > 0 || agentString.indexOf("MSIE 6") > 0) {
			return false;
		}
		return true;
	}

}
