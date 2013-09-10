package org.palaso.languageforge.client.lex.review.view;

import org.palaso.languageforge.client.lex.controls.conversation.ConversationListControl;
import org.palaso.languageforge.client.lex.review.presenter.ReviewMainPresenter.IReviewView;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ReviewMainView extends Composite implements IReviewView {

	interface Binder extends UiBinder<Widget, ReviewMainView> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	@UiField
	ConversationListControl conversationListControl;
	@UiField
	ConversationListControl conversationRecentChangeListControl;
	@UiField
	Button btnQuestionAll;
	@UiField
	Button btnQuestionResolved;
	@UiField
	Button btnQuestionUnresolved;
	@UiField
	Button btnRecentAll;
	@UiField
	Button btnRecentResolved;
	@UiField
	Button btnRecentUnresolved;
	@UiField
	Button btnRecentChanges;
	@UiField
	Button btnRecentMerges;

	public ReviewMainView() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public ConversationListControl getConversationControl() {
		return conversationListControl;
	}

	@Override
	public ConversationListControl getConversationRecentChangesControl() {
		return conversationRecentChangeListControl;
	}

	@Override
	public HasClickHandlers getConversationQuestionAllClickHandlers() {

		return btnQuestionAll;
	}

	@Override
	public HasClickHandlers getConversationQuestionResolvedClickHandlers() {

		return btnQuestionResolved;
	}

	@Override
	public HasClickHandlers getConversationQuestionUnresolvedClickHandlers() {

		return btnQuestionUnresolved;
	}

	@Override
	public HasClickHandlers getConversationRecentAllClickHandlers() {

		return btnRecentAll;
	}

	@Override
	public HasClickHandlers getConversationRecentResolvedClickHandlers() {

		return btnRecentResolved;
	}

	@Override
	public HasClickHandlers getConversationRecentUnresolvedClickHandlers() {

		return btnRecentUnresolved;
	}

	@Override
	public HasClickHandlers getConversationRecentChangesClickHandlers() {

		return btnRecentChanges;
	}

	@Override
	public HasClickHandlers getConversationRecentMergesClickHandlers() {

		return btnRecentMerges;
	}

}
