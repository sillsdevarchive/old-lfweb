package org.palaso.languageforge.client.lex.common.callback;

import com.google.gwt.core.client.JavaScriptObject;

public final class CallbackError extends JavaScriptObject {
	protected CallbackError() {
	}

	public final native String getError() /*-{
		return this.error;
	}-*/;
}
