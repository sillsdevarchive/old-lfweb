package org.palaso.languageforge.client.lex.configure.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.palaso.languageforge.client.lex.controls.ExtendedTextBox;
import org.palaso.languageforge.client.lex.controls.TextChangeEvent;
import org.palaso.languageforge.client.lex.controls.TextChangeEventHandler;
import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.configure.ConfigureEventBus;
import org.palaso.languageforge.client.lex.configure.view.LanguageCodeLookupView;
import org.palaso.languageforge.client.lex.model.IanaBaseDataDto;
import org.palaso.languageforge.client.lex.main.service.ILexService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = LanguageCodeLookupView.class)
public class LanguageCodeLookupPresenter
		extends
		BasePresenter<LanguageCodeLookupPresenter.ILanguageCodeLookupView, ConfigureEventBus> {
	@Inject
	public ILexService lexService;

	// private SortedMap<String, IanaBaseDataDto> fullLanguageList;
	private PopupPanel popupPanel = null;
	private ArrayList<IanaBaseDataDto> ianaArray;

	SingleSelectionModel<IanaBaseDataDto> selectionModel = new SingleSelectionModel<IanaBaseDataDto>();

	public void bind() {
		view.getLanguageCodeTable().setSelectionModel(selectionModel);

		view.getReadMoreCode6391ClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.openNewWindow(Constants.URL_ISO_639_1);
			}
		});

		view.getReadMoreCode6393ClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.openNewWindow(Constants.URL_ISO_639_3);
			}
		});

		view.getLanguageLookUpSearchTextbox().addTextChangeEventHandler(
				new TextChangeEventHandler() {
					@Override
					public void onTextChange(TextChangeEvent event) {
						filterList(view.getLanguageLookUpSearchTextbox()
								.getText());
					}
				});

		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(
					com.google.gwt.view.client.SelectionChangeEvent event) {
				if (selectionModel.getSelectedObject() != null) {
					view.setAddButtonEnabled(true);
				} else {
					view.setAddButtonEnabled(false);
				}
			}

		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		view.addAddButtonClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.addNewLanguageCodeToInputSystem(selectionModel
						.getSelectedObject());
				popupPanel.hide();
			}
		});

		buildLanguageCodeTable();
	}

	public void onAttachLanguageCodeLookupView(PopupPanel panel) {
		popupPanel = panel;
		panel.setWidget(view.getWidget());
		
		view.getLanguageLookUpSearchTextbox().setText("");
		
		SortedMap<String, IanaBaseDataDto> IanaObjHashMap = new TreeMap<String, IanaBaseDataDto>();
		eventBus.getIanaLanguages(IanaObjHashMap);
		
		ianaArray = new ArrayList<IanaBaseDataDto>(IanaObjHashMap.values());
		showData(ianaArray);
		
		view.setAddButtonEnabled(false);
	}

	private boolean contains(String original, String filter) {
		return original.contains(filter);
	}

	private void filterList(String filter) {
		view.setAddButtonEnabled(false);
		if (filter.length() == 0) {
			showData(ianaArray);
		} else {
			List<IanaBaseDataDto> filtedList = new ArrayList<IanaBaseDataDto>();

			for (int i = 0; i < ianaArray.size(); i++) {
				IanaBaseDataDto item = ianaArray.get(i);

				if (contains(item.getDescription().toLowerCase(),
						filter.toLowerCase())) {

					filtedList.add(item);
				} else if (contains(item.getSubtag().toLowerCase(),
						filter.toLowerCase())) {

					filtedList.add(item);
				}
			}
			showData(filtedList);
		}
	}

	private void showData(final List<IanaBaseDataDto> languageList) {
		AsyncDataProvider<IanaBaseDataDto> provider = new AsyncDataProvider<IanaBaseDataDto>() {
			@Override
			protected void onRangeChanged(HasData<IanaBaseDataDto> display) {
				int start = display.getVisibleRange().getStart();
				int end = start + display.getVisibleRange().getLength();

				end = end >= languageList.size() ? languageList.size() : end;
				List<IanaBaseDataDto> sub = languageList.subList(start, end);
				updateRowData(start, sub);
			}
		};
		provider.addDataDisplay(view.getLanguageCodeTable());
		provider.updateRowCount(languageList.size(), true);
		view.getTablePager().setDisplay(view.getLanguageCodeTable());
	}

	private void buildLanguageCodeTable() {
		CellTable<IanaBaseDataDto> cellTable = view.getLanguageCodeTable();
		// Add a text column to show the name.
		TextColumn<IanaBaseDataDto> nameColumn = new TextColumn<IanaBaseDataDto>() {
			@Override
			public String getValue(IanaBaseDataDto object) {
				return object.getDescription();
			}
		};
		cellTable.setColumnWidth(nameColumn, "50%");
		cellTable.addColumn(nameColumn, I18nConstants.STRINGS.LanguageCodeLookupPresenter_Name());

		TextColumn<IanaBaseDataDto> codeColumn = new TextColumn<IanaBaseDataDto>() {
			@Override
			public String getValue(IanaBaseDataDto object) {
				return object.getSubtag();
			}
		};
		cellTable.setColumnWidth(codeColumn, "50%");
		cellTable.addColumn(codeColumn, I18nConstants.STRINGS.LanguageCodeLookupPresenter_Code());

	}

	public interface ILanguageCodeLookupView {
		public Widget getWidget();

		public CellTable<IanaBaseDataDto> getLanguageCodeTable();

		public SimplePager getTablePager();

		public ExtendedTextBox getLanguageLookUpSearchTextbox();

		public HasClickHandlers addAddButtonClickHandlers();

		public void setAddButtonEnabled(boolean enabled);

		public HasClickHandlers getReadMoreCode6391ClickHandlers();

		public HasClickHandlers getReadMoreCode6393ClickHandlers();

	}

}
