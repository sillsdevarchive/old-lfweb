package org.palaso.languageforge.client.lex.gatherwords.view;

import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.controls.presenter.EntryPresenter.IEntryView;
import org.palaso.languageforge.client.lex.controls.view.EntryView;
import org.palaso.languageforge.client.lex.controls.ExtendedComboBox;
import org.palaso.languageforge.client.lex.gatherwords.presenter.GatherWordsFromSemanticDomainPresenter.IGatherWordsFromSemanticDomainView;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class GatherWordsFromSemanticDomainView extends Composite implements
		IGatherWordsFromSemanticDomainView {

	interface Binder extends UiBinder<Widget, GatherWordsFromSemanticDomainView> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	
	@UiField
	Label lblTitle;
	
	@UiField
	Label lblQuestion;
	
	@UiField
	ExtendedComboBox domainTreeListBox;
	
	@UiField(provided = true)
	EntryView dictEditView = new EntryView();


	@UiField
	Button btnPre;
	@UiField
	Button btnNext;
	@UiField
	Button btnAddWord;


	@UiField(provided = true)
	CellTable<LexiconEntryDto> cellTable = new CellTable<LexiconEntryDto>();
	public GatherWordsFromSemanticDomainView() {
		initWidget(binder.createAndBindUi(this));

	}


	@Override
	public Widget getWidget() {
		return this.asWidget();
	}


	@Override
	public ExtendedComboBox getDomainTreeListBox() {
		return domainTreeListBox;
	}


	@Override
	public void setPreBtnEnabled(boolean enabled) {
		btnPre.setEnabled(enabled);

	}

	@Override
	public void setNextBtnEnabled(boolean enabled) {
		btnNext.setEnabled(enabled);

	}

	@Override
	public void setAddWordBtnEnabled(boolean enabled) {
		btnAddWord.setEnabled(enabled);

	}

	@Override
	public HasClickHandlers preBtnClickHandlers() {
		return btnPre;
	}

	@Override
	public HasClickHandlers nextBtnClickHandlers() {
		return btnNext;
	}

	@Override
	public HasClickHandlers addWordBtnClickHandlers() {
		return btnAddWord;
	}


	@Override
	public Label getQuestionLabel() {
		return lblQuestion;
	}


	@Override
	public IEntryView getDictEditView() {
		return dictEditView;
	}


	@Override
	public CellTable<LexiconEntryDto> getRecentlyAddedTable() {
		return cellTable;
	}



}
