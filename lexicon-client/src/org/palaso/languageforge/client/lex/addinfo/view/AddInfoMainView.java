package org.palaso.languageforge.client.lex.addinfo.view;

import org.palaso.languageforge.client.lex.addinfo.presenter.AddInfoMainPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;


public class AddInfoMainView extends Composite implements
AddInfoMainPresenter.IView {

	private static MissInfoMainViewUiBinder uiBinder = GWT
			.create(MissInfoMainViewUiBinder.class);

	interface MissInfoMainViewUiBinder extends UiBinder<Widget, AddInfoMainView> {
	}

	@UiField(provided = true)
	IncompleteWordListView incompleteWordListView;

	@UiField(provided = true)
	IncompleteWordEditView incompleteWordEditView;

	@Inject
	public AddInfoMainView(IncompleteWordListView incompleteWordListView,
			IncompleteWordEditView incompleteWordEditView) {
		this.incompleteWordListView = incompleteWordListView;
		this.incompleteWordEditView = incompleteWordEditView;
		initWidget(uiBinder.createAndBindUi(this));
	}

}
