package org.palaso.languageforge.client.lex.browse.edit.view;

import org.palaso.languageforge.client.lex.browse.edit.presenter.LexBrowseEditListPresenter;
import org.palaso.languageforge.client.lex.controls.navigationbar.NavigationBarView;

import com.github.gwtbootstrap.client.ui.InputAddOn;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A composite that displays a list of dictionary entries that can be selected.
 */
@Singleton
public class LexBrowseEditListView extends Composite implements
		LexBrowseEditListPresenter.IView {

	interface Binder extends UiBinder<Widget, LexBrowseEditListView> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();

		String FlexTableOddRow();

		String FlexTableEvenRow();
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	SimplePanel pager;
	@UiField
	SimplePanel suggestBox;
	@UiField
	FlexTable table;
	@UiField
	InputAddOn suggestBoxLabel;
	@UiField
	SimplePanel scrollPanel;
	@Inject
	public LexBrowseEditListView(AutoSuggestView sview , NavigationBarView navView) {
		initWidget(binder.createAndBindUi(this));
		initTable();
		suggestBox.setWidget(sview);
		pager.setWidget(navView);
//		formatter.setColSpan(0, 0, 2);
	}

	/**
	 * Initializes the table so that it contains enough rows for a full page of
	 * items. Also creates the images that will be used as 'read' flags.
	 */
	private void initTable() {

		// Initialize the table.
		table.getColumnFormatter().setWidth(0, "188px");
		table.getColumnFormatter().setWidth(1, "188px");
		

	}

	public void applyDataRowStyles() {
		FlexTable.RowFormatter rf = table.getRowFormatter();

		for (int row = 1; row < table.getRowCount(); ++row) {
			if ((row % 2) != 0) {
				rf.addStyleName(row, "wordlistFlexTableOddRow");
			} else {
				rf.addStyleName(row, "wordlistFlexTableEvenRow");
			}
		}
	}

	public void setRow(int row, String word, String meaning) {
		word= "<div class=\"browserlistcellDiv\">" + word + "<div>";
		
		meaning= "<div class=\"browserlistcellDiv\">" + meaning + "<div>";
		
		table.setHTML(row, 0, word);
		table.setHTML(row, 1, meaning);
//		table.setText(row, 0, word);
//		table.setText(row, 1, meaning);
		table.getCellFormatter().setWordWrap(row, 0, true);
		table.getCellFormatter().setWordWrap(row, 1, true);
		
		
	}

	public void clearList() {
		table.clear(true);
		table.removeAllRows();
	}

	public int getClickedRow(ClickEvent event) {
		Cell cell = table.getCellForEvent(event);
		return (cell == null) ? -1 : cell.getRowIndex();
	}

	public void selectRow(int row, boolean selected) {
		if (selected) {
			if ((row % 2) != 0) {
				table.getRowFormatter().removeStyleName(row,
						"wordlistFlexTableOddRow");
			} else {
				table.getRowFormatter().removeStyleName(row,
						"wordlistFlexTableEvenRow");
			}
			table.getRowFormatter().addStyleName(row,
					"wordlistselectedRow");
		} else {
			table.getRowFormatter().removeStyleName(row,
					"wordlistselectedRow");
			if ((row % 2) != 0) {
				table.getRowFormatter().addStyleName(row,
						"wordlistFlexTableOddRow");
			} else {
				table.getRowFormatter().addStyleName(row,
						"wordlistFlexTableEvenRow");
			}
		}
	}

	public HasClickHandlers getTableClickHandlers() {
		return table;
	}
	
	public void setSuggestBoxLabel(String text)
	{
		suggestBoxLabel.setPrependText(text);
	}

	@Override
	public SimplePanel getScrollPanel() {
		return scrollPanel;
	}


	
}
