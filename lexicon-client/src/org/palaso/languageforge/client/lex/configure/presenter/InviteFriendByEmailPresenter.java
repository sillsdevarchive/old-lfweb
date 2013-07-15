package org.palaso.languageforge.client.lex.configure.presenter;

import org.palaso.languageforge.client.lex.configure.ConfigureEventBus;
import org.palaso.languageforge.client.lex.configure.view.InviteFriendByEmailView;
import org.palaso.languageforge.client.lex.model.CurrentEnvironmentDto;
import org.palaso.languageforge.client.lex.main.service.ILexService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = InviteFriendByEmailView.class)
public class InviteFriendByEmailPresenter
		extends
		BasePresenter<InviteFriendByEmailPresenter.IInviteFriendByEmailView, ConfigureEventBus> {
	@Inject
	public ILexService lexService;
	private String emailAddress;
	private PopupPanel popupPanel = null;

	public void bind() {
		view.getPopupCloseButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				popupPanel.hide();
			}
		});
		
		view.getInviteButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				lexService.inviteFriendByEmail(CurrentEnvironmentDto.getCurrentProject().getProjectId(), 
						emailAddress, view.getEmailMsgText().getText(),
						new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						eventBus.displayMessage(result);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						eventBus.handleError(caught);
					}
				});
				
				popupPanel.hide();
			}
		});
	}

	public void onAttachInviteFriendByEmailView(PopupPanel panel, String emailAddress) {
		this.popupPanel = panel;
		this.emailAddress=emailAddress;
		panel.setWidget(view.getWidget());
	}

	public interface IInviteFriendByEmailView {
		public Widget getWidget();

		public Button getInviteButton();

		public Anchor getPopupCloseButton();

		public TextArea getEmailMsgText();
	}

}
