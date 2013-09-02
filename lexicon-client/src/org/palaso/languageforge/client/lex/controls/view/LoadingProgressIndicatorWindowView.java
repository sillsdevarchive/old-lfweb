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
package org.palaso.languageforge.client.lex.controls.view;

import org.palaso.languageforge.client.lex.controls.HasWidgetsDialogBox;
import org.palaso.languageforge.client.lex.controls.presenter.LoadingProgressIndicatorWindowPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

/**
 * The top panel, which contains the 'welcome' message and various links.
 */
@Singleton
public class LoadingProgressIndicatorWindowView extends HasWidgetsDialogBox implements
		LoadingProgressIndicatorWindowPresenter.IView {

	interface Binder extends
			UiBinder<Widget, LoadingProgressIndicatorWindowView> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	Image imageBox;
	
	public LoadingProgressIndicatorWindowView() {
		setWidget(binder.createAndBindUi(this));
		//setText("Please wait...");
		setGlassEnabled(true);//This will show the dialog 
		imageBox.setUrl(GWT.getModuleBaseURL() +"/images/loading.gif");
		this.hide();	// close it first
	}
	
	public Widget getWidget() {
		return this.asWidget();
	}

	public void showDialogBox() {
		center();
		this.show();
	}

	public void hideDialogBox() {
		this.hide();
	}

}
