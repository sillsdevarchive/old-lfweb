package org.palaso.languageforge.client.lex.main;

import org.palaso.languageforge.client.lex.controls.JSNIJQueryWrapper;
import org.palaso.languageforge.client.lex.controls.JavascriptInjector;
import org.palaso.languageforge.client.lex.common.CustomUncaughtExceptionHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.Mvp4gModule;

public class MainEntryPoint implements EntryPoint {

	protected interface JsResource extends ClientBundle {
		@Source("jquery.main.onlinetool.js")
		TextResource OnlinetoolScript();
	}

	static {
		JsResource bundle = GWT.create(JsResource.class);
		JavascriptInjector.inject(bundle.OnlinetoolScript().getText());
	}

	@Override
	public void onModuleLoad() {
		GWT.log("loading module " + this.getClass().getName());
		GWT.setUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());
		GWT.log("UncaughtExceptionHandler set");

		Mvp4gModule module = (Mvp4gModule) GWT.create(Mvp4gModule.class);
		module.createAndStartModule();
		RootPanel rootPanel = RootPanel.get("GWTContent");
		if (rootPanel != null) {
			RootPanel.get("GWTContent").add((Widget) module.getStartView());
		} else {
			RootLayoutPanel.get().add((Widget) module.getStartView());
			RootLayoutPanel.get().getElement().setId("GWTContent");
		}
		// this should be last line.
		// start JQuery for left menu
		JSNIJQueryWrapper.initializeJQueryOpenClose();
	}

}
