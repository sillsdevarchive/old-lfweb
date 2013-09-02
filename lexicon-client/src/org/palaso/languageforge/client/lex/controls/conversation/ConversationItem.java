package org.palaso.languageforge.client.lex.controls.conversation;

import java.util.Date;

import org.palaso.languageforge.client.lex.common.MessageFormat;
import org.palaso.languageforge.client.lex.common.enums.AnnotationMessageStatusType;
import org.palaso.languageforge.client.lex.controls.ExtendedCheckBox;
import org.palaso.languageforge.client.lex.util.UUID;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SimplePanel;

public class ConversationItem extends ComplexPanel {

	private boolean isRootComment = false;
	private boolean isNewComment = false;
	private boolean isMarked = false;
	private boolean isTodo = false;
	private boolean isReviewed = false;
	private String refGuid = "";
	/*
	 * a comment has follow parts - header - left check mark - comment container
	 * - footer + buttons
	 */

	private SimplePanel panelHeader = null;
	private SimplePanel panelLeftCheckMarker = null;
	private SimplePanel panelCommentContainer = null;
	private SimplePanel panelFooter = null;
	private RichTextArea commentTextbox = null;
	private Label footerLabel = null;
	private Button btnExpendAllReplies = null;
	private ExtendedCheckBox chkTodo = null;
	private ExtendedCheckBox chkReviewed = null;
	private ExtendedCheckBox chkResolved = null;
	private Button btnAddComment = null;
	private static String imgGreenChecked;
	private static String CONTROL_UUID;
	private ConversationControl conversationControl = null;

	static {
		imgGreenChecked = GWT.getModuleBaseURL() + "/images/ConversationControlGreenChecked.png";
		CONTROL_UUID = UUID.uuid();
	}

	protected ConversationItem() {
		this.isNewComment = true;
		setElement(DOM.createElement("LI"));
		setStyleName("conversation-sub-panel");
		setStyleName("conversation-new", true);
		buildStructure();
	}

	public ConversationItem(String guid, boolean isRootComment) {
		this.isRootComment = isRootComment;
		this.refGuid = guid;
		setElement(DOM.createElement("LI"));
		setStyleName("conversation-sub-panel");

		if (this.isRootComment) {
			setStyleName("conversation-root", true);
		}
		buildStructure();
	}

	protected HasClickHandlers getAddCommentButtonClickHandlers() {
		return btnAddComment;
	}

	protected HasClickHandlers getExpendedClickHandlers() {
		return btnExpendAllReplies;
	}

	public void setFooterText(String text) {
		footerLabel.setText(text);
	}

	@SuppressWarnings("deprecation")
	public void setFooterTextbyDatetimeAndUser(Date date, String userName) {
		String timeString = "";
		Date currentDatetime = new Date();

		if (currentDatetime.getYear() > date.getYear()) {
			int valueDiff = currentDatetime.getYear() - date.getYear();

			String isPlural = valueDiff > 1 ? "years" : "year";

			timeString = MessageFormat.format("{0} {1} ago", valueDiff, isPlural);
		} else if (currentDatetime.getMonth() > date.getMonth()) {
			int valueDiff = currentDatetime.getMonth() - date.getMonth();

			String isPlural = valueDiff > 1 ? "months" : "month";

			timeString = MessageFormat.format("{0} {1} ago", valueDiff, isPlural);
		} else if (currentDatetime.getDate() > date.getDate()) {
			int valueDiff = currentDatetime.getDate() - date.getDate();

			String isPlural = valueDiff > 1 ? "days" : "day";

			timeString = MessageFormat.format("{0} {1} ago", valueDiff, isPlural);
		} else if (currentDatetime.getHours() > date.getHours()) {
			int valueDiff = currentDatetime.getHours() - date.getHours();

			String isPlural = valueDiff > 1 ? "hours" : "hour";

			timeString = MessageFormat.format("{0} {1} ago", valueDiff, isPlural);
		} else if (currentDatetime.getMinutes() > date.getMinutes()) {
			int valueDiff = currentDatetime.getMinutes() - date.getMinutes();

			String isPlural = valueDiff > 1 ? "minutes" : "minute";

			timeString = MessageFormat.format("{0} {1} ago", valueDiff, isPlural);
		} else {
			timeString = "just now";
		}

		footerLabel.setText(MessageFormat.format("By {0} {1}.", userName, timeString));

	}

	public String getFooterText() {
		return footerLabel.getText();
	}

	public void setCommentHtml(String html) {
		panelCommentContainer.getElement().setInnerHTML(html);
	}

	public String getCommentHtml() {
		return panelCommentContainer.getElement().getInnerHTML();
	}

	public boolean getIsResolvedCheck() {
		return isMarked;
	}

	public boolean getIsReviewedCheck() {
		return isReviewed;
	}

	public boolean getIsTodoCheck() {
		return isTodo;
	}

	public String getReferencedGuid() {
		return refGuid;
	}

	private void buildStructure() {
		panelHeader = new SimplePanel();
		panelLeftCheckMarker = new SimplePanel();
		panelCommentContainer = new SimplePanel();
		panelFooter = new SimplePanel();
		commentTextbox = new RichTextArea();
		footerLabel = new Label();
		btnExpendAllReplies = new Button();
		btnExpendAllReplies.setSize("20px", "20px");

		// Header
		panelHeader.add(getHeaderBlock());

		// BODY
		FlowPanel panelRowBody = new FlowPanel();
		FlowPanel panelRowInnerBody = new FlowPanel();
		panelRowBody.add(panelRowInnerBody);
		SimplePanel panelBodyLeftCell = new SimplePanel();
		SimplePanel panelBodyMidCell = new SimplePanel();
		SimplePanel panelBodyRightCell = new SimplePanel();

		panelRowInnerBody.add(panelBodyLeftCell);
		panelRowInnerBody.add(panelBodyMidCell);
		panelRowInnerBody.add(panelBodyRightCell);

		panelRowBody.setStyleName("item-body-row");
		panelRowInnerBody.getElement().setId("item-body-row-inner");

		panelBodyLeftCell.setStyleName("item-body-row-cells");
		panelBodyMidCell.setStyleName("item-body-row-cells");
		panelBodyMidCell.setWidth("100%");
		panelBodyRightCell.setStyleName("item-body-row-cells");

		panelBodyLeftCell.getElement().setId("item-body-row-left-cell");
		panelBodyMidCell.getElement().setId("item-body-row-right-cell");
		panelBodyRightCell.getElement().setId("item-body-row-right-cell");
		panelBodyLeftCell.add(getVoteBlock());

		if (isNewComment) {
			panelHeader.setStyleName("conversation-sub-header-new");
			panelCommentContainer.setStyleName("conversation-sub-comment-container-new");
			panelCommentContainer.setHeight("100px");
			panelFooter.setStyleName("conversation-sub-footer-new");
			commentTextbox.setStyleName("conversation-sub-comment-new");
			commentTextbox.setHeight("100px");
			panelCommentContainer.add(commentTextbox);
			chkTodo = new ExtendedCheckBox("To-do");
			chkTodo.setStyleName("conversation-checkBox-resolved");
			chkReviewed = new ExtendedCheckBox("This conversation is reviewed.");
			chkReviewed.setStyleName("conversation-checkBox-resolved");
			chkResolved = new ExtendedCheckBox("This conversation is resolved.");
			chkResolved.setStyleName("conversation-checkBox-resolved");

			btnAddComment = new Button("Add comment");
			btnAddComment.setWidth("120px");

			// FOOTER
			FlowPanel panelRowFooter = new FlowPanel();
			FlowPanel panelRowInnerFooter = new FlowPanel();
			panelRowFooter.add(panelRowInnerFooter);

			FlowPanel panelFooterLeftCell = new FlowPanel();
			FlowPanel panelFooterMidCell = new FlowPanel();
			FlowPanel panelFooterRightCell = new FlowPanel();

			panelRowInnerFooter.add(panelFooterLeftCell);
			panelRowInnerFooter.add(panelFooterMidCell);
			panelRowInnerFooter.add(panelFooterRightCell);

			panelRowFooter.setStyleName("item-footer-row");
			panelRowInnerFooter.getElement().setId("item-footer-row-inner");

			panelFooterLeftCell.setStyleName("item-footer-row-cells");
			panelFooterMidCell.setStyleName("item-footer-row-cells");
			panelFooterMidCell.setWidth("300px");
			panelFooterRightCell.setStyleName("item-footer-row-cells");

			panelFooterLeftCell.getElement().setId("item-footer-row-left-cell");
			panelFooterMidCell.getElement().setId("item-footer-row-right-cell");
			panelFooterRightCell.getElement().setId("item-footer-row-right-cell");

			panelFooterMidCell.add(chkTodo);
			panelFooterMidCell.add(chkReviewed);
			panelFooterMidCell.add(chkResolved);
			panelFooter.add(panelRowFooter);
			panelFooterRightCell.add(btnAddComment);
			panelBodyMidCell.add(panelCommentContainer);
			super.add(panelHeader, getElement());
			super.add(panelLeftCheckMarker, getElement());
			super.add(panelRowBody, getElement());
			super.add(panelFooter, getElement());
			chkReviewed.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					isReviewed = event.getValue();
				}
			});
			chkTodo.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					isTodo = event.getValue();
				}
			});
			chkResolved.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					isMarked = event.getValue();
				}
			});

		} else {
			panelHeader.setStyleName("conversation-sub-header");
			panelLeftCheckMarker.setStyleName("conversation-sub-checkmarker");
			panelCommentContainer.setStyleName("conversation-sub-comment-container");
			panelFooter.setStyleName("conversation-sub-footer");

			footerLabel.setStyleName("conversation-sub-footer-label");
			btnExpendAllReplies.setStyleName("conversation-expend-btn-collapsed");

			panelCommentContainer.getElement().setInnerHTML("");

			if (isRootComment) {

				panelCommentContainer.setStyleName("conversation-sub-comment-container-root");
				FlowPanel panelRow = new FlowPanel();
				FlowPanel panelRowInner = new FlowPanel();
				panelRow.add(panelRowInner);
				FlowPanel panelLeftCell = new FlowPanel();
				FlowPanel panelRightCell = new FlowPanel();

				panelRowInner.add(panelLeftCell);
				panelRowInner.add(panelRightCell);

				panelRow.setStyleName("item-footer-row");
				panelRowInner.getElement().setId("item-footer-row-inner");

				panelLeftCell.setStyleName("item-footer-row-cells");
				panelRightCell.setStyleName("item-footer-row-cells");

				panelLeftCell.getElement().setId("item-footer-row-left-cell");
				panelRightCell.getElement().setId("item-footer-row-right-cell");

				panelFooter.add(panelRow);
				panelLeftCell.add(footerLabel);
				panelRightCell.add(btnExpendAllReplies);
				panelBodyMidCell.add(panelCommentContainer);
				super.add(panelHeader, getElement());
				super.add(panelRowBody, getElement());
				super.add(panelFooter, getElement());

			} else {
				panelFooter.setStyleName("conversation-sub-footer-reply", true);
				panelFooter.add(footerLabel);
				panelBodyMidCell.add(panelCommentContainer);
				super.add(panelHeader, getElement());
				super.add(panelLeftCheckMarker, getElement());
				super.add(panelRowBody, getElement());
				super.add(panelFooter, getElement());
			}

		}

		// btnExpendAllReplies.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// Window.alert("Test");
		//
		// }
		// });
		//
		// if (btnPost != null) {
		// btnPost.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// Window.alert("Test");
		//
		// }
		// });
		// }
	}

	private void updateMarkerStatus() {
		if (isMarked) {
			panelLeftCheckMarker.addStyleName("conversation-sub-checkmarker-checked");
			panelLeftCheckMarker.getElement().setInnerHTML(
					"<img src=" + imgGreenChecked + " alt=\"Resolved\" height=\"48\" width=\"48\" />");
			if (isRootComment) {
				panelHeader.addStyleName("conversation-root-resolved-header");
				panelFooter.addStyleName("conversation-root-resolved-footer");
			}

		} else {
			panelLeftCheckMarker.getElement().setInnerHTML("");
			panelLeftCheckMarker.removeStyleName("conversation-sub-checkmarker-checked");

			if (isRootComment) {
				panelHeader.removeStyleName("conversation-root-resolved-header");
				panelFooter.removeStyleName("conversation-root-resolved-footer");
			}
		}
	}

	private FlowPanel getHeaderBlock() {
		// Outter
		SimplePanel panelHeaderOutterLeftCell = new SimplePanel();
		SimplePanel panelHeaderOutterMidCell = new SimplePanel();
		SimplePanel panelHeaderOutterRightCell = new SimplePanel();
		FlowPanel panelRowInnerHeaderOutter = new FlowPanel();
		FlowPanel panelRowHeader = new FlowPanel();

		panelRowHeader.add(panelRowInnerHeaderOutter);

		panelRowInnerHeaderOutter.add(panelHeaderOutterLeftCell);
		panelRowInnerHeaderOutter.add(panelHeaderOutterMidCell);
		panelRowInnerHeaderOutter.add(panelHeaderOutterRightCell);

		panelRowHeader.setStyleName("item-header-outter-row");
		panelRowInnerHeaderOutter.getElement().setId("item-header-outter-row-inner");

		panelHeaderOutterLeftCell.setStyleName("item-header-outter-row-cells");
		panelHeaderOutterMidCell.setStyleName("item-header-outter-row-cells");
		panelHeaderOutterMidCell.setWidth("100%");
		panelHeaderOutterRightCell.setStyleName("item-header-outter-row-cells");

		panelHeaderOutterLeftCell.getElement().setId("item-header-outter-row-left-cell");
		panelHeaderOutterMidCell.getElement().setId("item-header-outter-row-right-cell");
		panelHeaderOutterRightCell.getElement().setId("item-header-outter-row-right-cell");

		// Inner
		SimplePanel panelHeaderInnerLeftCell = new SimplePanel();
		SimplePanel panelHeaderInnerMidCell = new SimplePanel();
		SimplePanel panelHeaderInnerRightCell = new SimplePanel();
		FlowPanel panelRowInnerHeaderInner = new FlowPanel();
		FlowPanel panelRowHeaderInner = new FlowPanel();

		panelRowHeaderInner.add(panelRowInnerHeaderInner);

		panelRowInnerHeaderInner.add(panelHeaderInnerLeftCell);
		panelRowInnerHeaderInner.add(panelHeaderInnerMidCell);
		panelRowInnerHeaderInner.add(panelHeaderInnerRightCell);

		panelRowHeaderInner.setStyleName("item-header-inner-row");
		panelRowInnerHeaderInner.getElement().setId("item-header-inner-row-inner");

		panelHeaderInnerLeftCell.setStyleName("item-header-inner-row-cells");
		panelHeaderInnerMidCell.setStyleName("item-header-inner-row-cells");
		panelHeaderInnerMidCell.setWidth("100%");
		panelHeaderInnerRightCell.setStyleName("item-header-inner-row-cells");

		panelHeaderInnerLeftCell.getElement().setId("item-header-inner-row-left-cell");
		panelHeaderInnerMidCell.getElement().setId("item-header-inner-row-right-cell");
		panelHeaderInnerRightCell.getElement().setId("item-header-inner-row-right-cell");
		panelHeaderOutterMidCell.add(panelRowHeaderInner);

		// Controls:
		Label lblHeaderLeftDate = new Label("Test");
		Label lblHeaderMidStatus = new Label("Comment Status");
	//	Button lblHeaderRightStatusChooser = new Button("Status Drop down");
		
		panelHeaderInnerLeftCell.add(lblHeaderLeftDate);
		panelHeaderInnerMidCell.add(lblHeaderMidStatus);
//		panelHeaderInnerRightCell.add(lblHeaderRightStatusChooser);
//		
//		lblHeaderRightStatusChooser.getElement().setId("StatusChooser-" + CONTROL_UUID);
//		
//		$("#" + lblHeaderRightStatusChooser.getElement().getId()).as(Ui).button(
//				gwtquery.plugins.ui.widgets.Button.Options.create().icons(
//						gwtquery.plugins.ui.widgets.Button.Icons.create().primary("ui-icon-gear").secondary("ui-icon-triangle-1-s")));
		return panelRowHeader;
	}

	private FlowPanel getVoteBlock() {
		FlowPanel votePanel = new FlowPanel();
		votePanel.setStyleName("conversation-vote");
		Button voteUp = new Button();
		Button voteDown = new Button();
		Label votesLabel = new Label("0");
		voteUp.setStyleName("conversation-vote-up");
		voteDown.setStyleName("conversation-vote-down");
		votesLabel.setStyleName("conversation-vote-count");
		votePanel.add(voteUp);
		votePanel.add(votesLabel);
		votePanel.add(voteDown);
		return votePanel;
	}

	public boolean getIsRootComment() {
		return isRootComment;
	}

	public void setIsMarked(boolean marked) {
		isMarked = marked;
		updateMarkerStatus();
	}

	public boolean isTodo() {
		return isTodo;
	}

	public void setTodo(boolean isTodo) {
		this.isTodo = isTodo;
	}

	public boolean isReviewed() {
		return isReviewed;
	}

	public void setReviewed(boolean isReviewed) {
		this.isReviewed = isReviewed;
	}

	public boolean getIsMarked() {
		return isMarked;
	}

	public void setExpendedBtnDown(boolean isRepliesHidden) {
		if (isRepliesHidden) {
			btnExpendAllReplies.setStyleName("conversation-expend-btn-collapsed");
		} else {
			btnExpendAllReplies.setStyleName("conversation-expend-btn-expended");
		}

	}

	public void isExpendedBtnVisible(boolean visible) {
		btnExpendAllReplies.setVisible(visible);
	}

	public String getNewCommentHtml() {
		return commentTextbox.getHTML();
	}

	public void showResolvedCheckBox(boolean visible) {
		chkResolved.setVisible(visible);
	}

	public ConversationControl getConversationControl() {
		return this.conversationControl;
	}

	protected void setConversationControl(ConversationControl control) {
		this.conversationControl = control;
	}

	protected void setNewCommentHtml(String string) {
		commentTextbox.setHTML("");
	}
}