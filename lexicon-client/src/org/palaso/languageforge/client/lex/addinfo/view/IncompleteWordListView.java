package org.palaso.languageforge.client.lex.addinfo.view;

import org.palaso.languageforge.client.lex.addinfo.presenter.IncompleteWordListPresenter;
import org.palaso.languageforge.client.lex.controls.navigationbar.NavigationBarView;

import com.github.gwtbootstrap.client.ui.Bar;
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
 * 
 * 
 */
@Singleton
public class IncompleteWordListView extends Composite implements
		IncompleteWordListPresenter.IView {

	interface Binder extends UiBinder<Widget, IncompleteWordListView> {
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
	FlexTable table;

	@UiField
	SimplePanel scrollPanel;
	@UiField
	Bar  progressBar;

	@Inject
	public IncompleteWordListView(NavigationBarView navView) {
		initWidget(binder.createAndBindUi(this));
		pager.setWidget(navView);

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
		word = "<div class=\"browserlistcellDiv\">" + word + "<div>";

		// meaning= "<div class=\"browserlistcellDiv\">" + meaning + "<div>";

		table.setHTML(row, 0, word);
		// table.setHTML(row, 1, meaning);
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
			table.getRowFormatter().addStyleName(row, "wordlistselectedRow");
		} else {
			table.getRowFormatter().removeStyleName(row, "wordlistselectedRow");
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

	@Override
	public SimplePanel getScrollPanel() {
		return scrollPanel;
	}

	@Override
	public Bar getProgressLabel() {
		return progressBar;
	}

}
