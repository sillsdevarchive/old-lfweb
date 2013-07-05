/**
 * A View for auto suggest box to search the word easily from the Dictionary
 * @author Kandasamy
 * @version 0.2
 * @since August 2011
 */

package org.palaso.languageforge.client.lex.browse.edit.view;

import org.palaso.languageforge.client.lex.browse.edit.presenter.AutoSuggestPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class AutoSuggestView extends Composite implements
		AutoSuggestPresenter.ISuggestBoxView {

	@UiTemplate("AutoSuggestView.ui.xml")
	interface Binder extends UiBinder<Widget, AutoSuggestView> {
	}
	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	SimplePanel suggestBoxContainer;

	public AutoSuggestView() {
		initWidget(binder.createAndBindUi(this));
	}

	public void setSuggestBox(SuggestBox suggestBox) {
		suggestBoxContainer.add(suggestBox);
	}


}
