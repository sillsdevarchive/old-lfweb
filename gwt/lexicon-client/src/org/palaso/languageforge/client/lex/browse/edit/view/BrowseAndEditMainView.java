package org.palaso.languageforge.client.lex.browse.edit.view;

import org.palaso.languageforge.client.lex.browse.edit.presenter.BrowseAndEditMainPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class BrowseAndEditMainView extends Composite implements
		BrowseAndEditMainPresenter.IView {

	private static LexDBEMainViewUiBinder uiBinder = GWT
			.create(LexDBEMainViewUiBinder.class);

	interface LexDBEMainViewUiBinder extends UiBinder<Widget, BrowseAndEditMainView> {
	}

	@UiField(provided = true)
	LexBrowseEditListView lexDBEListView;

	@UiField(provided = true)
	LexBrowseEditView lexDBEEditView;

	@Inject
	public BrowseAndEditMainView(LexBrowseEditListView lexDBEListView,
			LexBrowseEditView lexDBEEditView) {
		this.lexDBEListView = lexDBEListView;
		this.lexDBEEditView = lexDBEEditView;
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getAsWidget() {
		return this.asWidget();
	}

}
