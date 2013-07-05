package org.palaso.languageforge.client.lex.configure.view;

import org.palaso.languageforge.client.lex.configure.presenter.InviteFriendByEmailPresenter.IInviteFriendByEmailView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class InviteFriendByEmailView extends Composite implements
		IInviteFriendByEmailView {

	interface Binder extends UiBinder<Widget, InviteFriendByEmailView> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	@UiField Anchor btnPopupClose;
	@UiField TextArea txtEmailMsg;
	@UiField Button btnSendInvite;
	

	public InviteFriendByEmailView() {
		initWidget(binder.createAndBindUi(this));
	}

	public Widget getWidget() {
		return this.asWidget();
	}


	@Override
	public Button getInviteButton() {
		return btnSendInvite;
	}

	@Override
	public Anchor getPopupCloseButton() {
		return btnPopupClose;
	}

	@Override
	public TextArea getEmailMsgText() {
		return txtEmailMsg;
	}

}
