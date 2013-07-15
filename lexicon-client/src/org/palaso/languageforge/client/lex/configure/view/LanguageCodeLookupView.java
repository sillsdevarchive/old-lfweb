package org.palaso.languageforge.client.lex.configure.view;

import org.palaso.languageforge.client.lex.controls.ExtendedTextBox;
import org.palaso.languageforge.client.lex.configure.presenter.LanguageCodeLookupPresenter.ILanguageCodeLookupView;
import org.palaso.languageforge.client.lex.model.IanaBaseDataDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class LanguageCodeLookupView extends Composite implements
		ILanguageCodeLookupView {

	interface Binder extends UiBinder<Widget, LanguageCodeLookupView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField(provided = true)
	CellTable<IanaBaseDataDto> languageCodeTable = new CellTable<IanaBaseDataDto>();
	
	@UiField(provided=true)
	SimplePager languageCodeTablePager = new SimplePager();
	
	@UiField
	ExtendedTextBox txtLanguageLookUpSearch;
	
	@UiField
	Button btnAddLanguage;
	
	@UiField Anchor readmoreCode6391;
	
	@UiField Anchor readmoreCode6393;
	
	public LanguageCodeLookupView() {
		initWidget(binder.createAndBindUi(this));
	}

	public Widget getWidget() {
		return this.asWidget();
	}

	@Override
	public CellTable<IanaBaseDataDto> getLanguageCodeTable() {
		return languageCodeTable;
	}

	@Override
	public SimplePager getTablePager() {
		return languageCodeTablePager;
	}

	@Override
	public ExtendedTextBox getLanguageLookUpSearchTextbox() {
		return txtLanguageLookUpSearch;
	}

	@Override
	public HasClickHandlers addAddButtonClickHandlers() {
		return btnAddLanguage;
	}

	@Override
	public void setAddButtonEnabled(boolean enabled) {
		btnAddLanguage.setEnabled(enabled);
	}

	@Override
	public HasClickHandlers getReadMoreCode6391ClickHandlers() {
		return readmoreCode6391;
	}

	@Override
	public HasClickHandlers getReadMoreCode6393ClickHandlers() {
		return readmoreCode6393;
	}

}
