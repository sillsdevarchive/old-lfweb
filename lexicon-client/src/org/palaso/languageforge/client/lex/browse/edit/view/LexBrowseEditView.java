package org.palaso.languageforge.client.lex.browse.edit.view;

import org.palaso.languageforge.client.lex.controls.presenter.EntryPresenter;
import org.palaso.languageforge.client.lex.controls.view.EntryView;
import org.palaso.languageforge.client.lex.browse.edit.presenter.LexBrowseEditPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabBar.Tab;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class LexBrowseEditView extends Composite implements
		LexBrowseEditPresenter.IView {

	interface LexDBEEditViewUiBinder extends UiBinder<Widget, LexBrowseEditView> {
	}

	private static LexDBEEditViewUiBinder binder = GWT
			.create(LexDBEEditViewUiBinder.class);

	interface SelectionStyle extends CssResource {
		String feedbackstyle();
		String feedbackstylefalse();
	}
	@UiField
	SimplePanel panel;

	@UiField
	SimplePanel buttonControl;
		
	@UiField
	Frame entryDisplayPanel;
	
	@UiField
	Frame commentsPanel;
	
	@UiField
	FlowPanel bowserPanel;
	@UiField
	FlowPanel commentPanel;
	@UiField
	TabLayoutPanel tabPanel;

	
	@Inject
	public LexBrowseEditView(ButtonBarControlView buttonControlView) {
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
	public Frame getEntryDisplayPanel() {
		return entryDisplayPanel;
	}

	@Override
	public FlowPanel getBowserPanel() {
		return bowserPanel;
	}

	@Override
	public FlowPanel getCommentPanel() {
		return commentPanel;
	}

	@Override
	public TabLayoutPanel getTabPanel() {
		return tabPanel;
	}

	@Override
	public Frame getEntryCommentPanel() {
		return commentsPanel;
	}

}
