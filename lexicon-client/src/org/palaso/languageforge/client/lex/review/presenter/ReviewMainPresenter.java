package org.palaso.languageforge.client.lex.review.presenter;

import java.util.Date;

import org.palaso.languageforge.client.lex.model.ConversationDto;
import org.palaso.languageforge.client.lex.model.ConversationListDto;
import org.palaso.languageforge.client.lex.common.AnnotationMessageStatusType;
import org.palaso.languageforge.client.lex.common.ConsoleLog;
import org.palaso.languageforge.client.lex.common.ConversationAnnotationType;
import org.palaso.languageforge.client.lex.controls.conversation.ConversationControl;
import org.palaso.languageforge.client.lex.controls.conversation.ConversationItem;
import org.palaso.languageforge.client.lex.controls.conversation.ConversationListControl;
import org.palaso.languageforge.client.lex.controls.conversation.ConversationPostClickHandler;
import org.palaso.languageforge.client.lex.controls.conversation.ConversationPostEvent;
import org.palaso.languageforge.client.lex.common.I18nConstants;
import org.palaso.languageforge.client.lex.main.service.ILexService;
import org.palaso.languageforge.client.lex.review.ReviewEventBus;
import org.palaso.languageforge.client.lex.review.view.ReviewMainView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = ReviewMainView.class)
public class ReviewMainPresenter extends
		BasePresenter<ReviewMainView, ReviewEventBus> {

	@Inject
	private ILexService lexService;

	private ConversationPostClickHandler newPostClickHandler;

	public interface IReviewView {
		public ConversationListControl getConversationControl();

		public ConversationListControl getConversationRecentChangesControl();

		public HasClickHandlers getConversationQuestionAllClickHandlers();

		public HasClickHandlers getConversationQuestionResolvedClickHandlers();

		public HasClickHandlers getConversationQuestionUnresolvedClickHandlers();

		public HasClickHandlers getConversationRecentAllClickHandlers();

		public HasClickHandlers getConversationRecentResolvedClickHandlers();

		public HasClickHandlers getConversationRecentUnresolvedClickHandlers();

		public HasClickHandlers getConversationRecentChangesClickHandlers();

		public HasClickHandlers getConversationRecentMergesClickHandlers();

	}

	public void onGoToReviewRecentChanges() {
		getQuestions(AnnotationMessageStatusType.UNDEFINED,
				ConversationAnnotationType.UNDEFINED);
		getRecentChanges(AnnotationMessageStatusType.UNDEFINED,
				ConversationAnnotationType.UNDEFINED);
	}

	@Override
	public void bind() {
		super.bind();

		newPostClickHandler = new ConversationPostClickHandler() {

			@Override
			public void onNewPostClicked(ConversationPostEvent event) {
				ConversationItem newConversationItem = event
						.getConversationItem();
				final ConversationItem rootConversationItem = event
						.getRootConversationItem();
				AnnotationMessageStatusType statusType = newConversationItem
						.getIsMarked() ? AnnotationMessageStatusType.CLOSED
						: AnnotationMessageStatusType.UNDEFINED;
				
			
				lexService.saveNewComments(statusType, newConversationItem
						.isReviewed(), newConversationItem
						.isTodo(), event.getRootConversationItem().getReferencedGuid(),
						newConversationItem.getNewCommentHtml(), false,
						new AsyncCallback<ConversationDto>() {

							@Override
							public void onSuccess(ConversationDto result) {
								ConversationControl cControl = rootConversationItem
										.getConversationControl();
								cControl.resetNewCommentBox();
								cControl.addItem(fillConversationItem(result,false));
							}

							@Override
							public void onFailure(Throwable caught) {
								eventBus.handleError(caught);
							}
						});
			}
		};

		view.getConversationQuestionAllClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getQuestions(AnnotationMessageStatusType.UNDEFINED,
						ConversationAnnotationType.QUESTION);

			}
		});

		view.getConversationQuestionResolvedClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						getQuestions(AnnotationMessageStatusType.CLOSED,
								ConversationAnnotationType.QUESTION);
					}
				});

		view.getConversationQuestionUnresolvedClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						getQuestions(AnnotationMessageStatusType.REVIEWED,
								ConversationAnnotationType.QUESTION);
					}
				});

		view.getConversationRecentAllClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getRecentChanges(AnnotationMessageStatusType.UNDEFINED,
						ConversationAnnotationType.UNDEFINED);

			}
		});

		view.getConversationRecentChangesClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getRecentChanges(AnnotationMessageStatusType.UNDEFINED,
						ConversationAnnotationType.QUESTION);
			}
		});

		view.getConversationRecentMergesClickHandlers().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getRecentChanges(AnnotationMessageStatusType.UNDEFINED,
						ConversationAnnotationType.MERGECONFLICT);
			}
		});

		view.getConversationRecentResolvedClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						getRecentChanges(AnnotationMessageStatusType.CLOSED,
								ConversationAnnotationType.UNDEFINED);
					}
				});

		view.getConversationRecentUnresolvedClickHandlers().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						getRecentChanges(AnnotationMessageStatusType.REVIEWED,
								ConversationAnnotationType.UNDEFINED);
					}
				});

	}

	private void getQuestions(AnnotationMessageStatusType status,
			ConversationAnnotationType annotationType) {
		lexService.getComments(status, annotationType, 0, 999, false,
				new AsyncCallback<ConversationListDto>() {

					@Override
					public void onSuccess(ConversationListDto result) {
						view.getConversationControl().setEmptyListMessage(
								I18nConstants.STRINGS.ReviewMainPresenter_No_question_in_list());
						fillConversations(view.getConversationControl(), result, true);

					}

					@Override
					public void onFailure(Throwable caught) {
						eventBus.handleError(caught);
					}
				});
	}

	private void getRecentChanges(AnnotationMessageStatusType status,
			ConversationAnnotationType annotationType) {
		lexService.getComments(status, annotationType, 0, 1, true,
				new AsyncCallback<ConversationListDto>() {
					@Override
					public void onSuccess(ConversationListDto result) {
						view.getConversationRecentChangesControl()
								.setEmptyListMessage(
										I18nConstants.STRINGS.ReviewMainPresenter_No_recent_change_in_list());
						fillConversations(view.getConversationRecentChangesControl(),
								result, true);
					}

					@Override
					public void onFailure(Throwable caught) {
						eventBus.handleError(caught);
					}
				});
	}

	private void fillConversations(ConversationListControl convListControl,
			ConversationListDto conListDto, boolean showNewComment) {
		convListControl.clear();
		for (int i = 0; i < conListDto.getEntries().length(); ++i) {
			ConversationDto entry = conListDto.getEntries().get(i);
			// the first level dto has only ref and Guid, messages are in
			// children
			ConversationControl control = new ConversationControl();
			control.setConversationPostClickHandler(newPostClickHandler);
			convListControl.addItem(control);

			for (int j = 0; j < entry.getChildren().length(); ++j) {
				ConversationDto reviewMessage = entry.getChildren().get(j);
				boolean isRootMsg = false;
				if (j == 0) {
					isRootMsg = true;
				}

				control.addItem(fillConversationItem(reviewMessage, isRootMsg));
			}

			if (showNewComment) {
				control.showNewComment();
			} else {
				control.closeNewComment();
			}
		}
		convListControl.setAllExpended(false);
		convListControl.getElement().setScrollTop(0);
		

	}

	private ConversationItem fillConversationItem(ConversationDto dto,
			Boolean isRoot) {
		ConversationItem reviewItem = new ConversationItem(dto.getGuid(),
				isRoot);
		reviewItem.setCommentHtml(dto.getComment());
		reviewItem.setFooterTextbyDatetimeAndUser(
				new Date(Long.valueOf(dto.getPhpTimeStamp()) * 1000),
				dto.getAuthor());
		reviewItem.setIsMarked(dto.getStatus()==AnnotationMessageStatusType.CLOSED);
		return reviewItem;
	}

}