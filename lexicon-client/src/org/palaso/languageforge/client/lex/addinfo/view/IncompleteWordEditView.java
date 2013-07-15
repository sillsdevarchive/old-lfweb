package org.palaso.languageforge.client.lex.addinfo.view;

import org.palaso.languageforge.client.lex.presenter.EntryPresenter;
import org.palaso.languageforge.client.lex.view.EntryView;
import org.palaso.languageforge.client.lex.addinfo.presenter.IncompleteWordEditPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IncompleteWordEditView extends Composite implements
		IncompleteWordEditPresenter.IView {

	interface MissInfoEditViewUiBinder extends
			UiBinder<Widget, IncompleteWordEditView> {
	}

	private static MissInfoEditViewUiBinder binder = GWT
			.create(MissInfoEditViewUiBinder.class);

	interface SelectionStyle extends CssResource {
		String feedbackstyle();

		String feedbackstylefalse();
	}

	@UiField
	SimplePanel panel;

	@UiField
	SimplePanel buttonControl;

	@UiField
	SimplePanel feedback;

	@Inject
	public IncompleteWordEditView(ButtonBarControlView buttonControlView) {
		initWidget(binder.createAndBindUi(this));
		buttonControl.setWidget(buttonControlView);
	}

	public EntryPresenter.IEntryView createDictionaryView(boolean userAccess) {
		if (userAccess) {
			buttonControl.setVisible(true);
		}
		EntryView view = new EntryView();
		panel.setWidget(view);
		return view;
	}

	@Override
	public void destroyDictionaryView() {
		panel.clear();
		feedback.clear();
		feedback.setStyleName("");
		buttonControl.setVisible(false);
	}

	
	@Override
	public void showNotification(String text, boolean success) {
		feedback.clear();
		if (success) {
			feedback.setStyleName("feedbackSuccess");
		} else {
			feedback.addStyleName("feedbackError");
		}
		feedback.add(new Label(text));
	}

}
