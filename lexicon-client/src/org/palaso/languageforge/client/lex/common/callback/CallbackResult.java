package org.palaso.languageforge.client.lex.common.callback;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class CallbackResult extends JavaScriptObject {
	protected CallbackResult() {
	}

	public final native JavaScriptObject getAttachedData() /*-{
		return this.data;
	}-*/;
	
	public final native boolean isSuccess() /*-{
		return !!this.success;
	}-*/;

	public final CallbackError getError() {
		return this.cast();
	}
}
