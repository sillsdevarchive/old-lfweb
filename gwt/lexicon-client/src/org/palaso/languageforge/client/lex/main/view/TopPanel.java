/*
 * Copyright 2011 SIL Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.palaso.languageforge.client.lex.main.view;

import org.palaso.languageforge.client.lex.main.presenter.TopPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

/**
 * The top panel, which contains the 'welcome' message and various links.
 */
@Singleton
public class TopPanel extends Composite implements TopPresenter.IView {

	interface Binder extends UiBinder<Widget, TopPanel> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	Anchor aboutLink;
	@UiField
	Anchor homeLink;
	@UiField
	Anchor signOutLink;

	public TopPanel() {
		initWidget(binder.createAndBindUi(this));
	}

	public HasClickHandlers getAboutLinkClickHandlers() {
		return aboutLink;
	}

	public HasClickHandlers getHomeLinkClickHandlers() {
		return homeLink;
	}

	public HasClickHandlers getSignOutLinkClickHandlers() {
		return signOutLink;
	}

	public Widget getViewWidget() {
		return this;
	}

}
