/*
 * Copyright 2009 Google Inc.
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
package org.palaso.languageforge.client.lex.controls.navigationbar;



import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

/**
 * A simple widget representing prev/next page navigation.
 */
@Singleton
public class NavigationBarView extends Composite implements
NavigationBarPresenter.INavBarView {

	@UiTemplate("NavigationBarView.ui.xml")
	interface Binder extends UiBinder<Widget, NavigationBarView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	Element countLabel;
	@UiField
	Anchor nextButton;
	@UiField
	Anchor previousButton;
	@UiField
	Anchor firstButton;
	@UiField
	Anchor lastButton;

	public NavigationBarView() {
		initWidget(binder.createAndBindUi(this));
	}

	public Anchor getPreviousButton() {
		return previousButton;
	}

	public Anchor getNextButton() {
		return nextButton;
	}

	public Widget getViewWidget() {
		return this;
	}

	public void setNavText(String text) {
		countLabel.setInnerHTML(text);
	}
	@Override
	public Anchor getFirstButton() {
		return firstButton;
	}

	@Override
	public Anchor getLastButton() {
		return lastButton;
	}

}
