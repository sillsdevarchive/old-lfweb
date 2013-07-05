package org.palaso.languageforge.client.lex.gatherwords.view;

import org.palaso.languageforge.client.lex.gatherwords.presenter.GatherWordsMainPresenter.IGatherWordsMainView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class GatherWordsMainView extends Composite implements
		IGatherWordsMainView {

	interface Binder extends UiBinder<Widget, GatherWordsMainView> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	@UiField
	SimplePanel lblPanelCol;

	public GatherWordsMainView() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public SimplePanel getWidgetPanel() {
		return lblPanelCol;
	}

}
