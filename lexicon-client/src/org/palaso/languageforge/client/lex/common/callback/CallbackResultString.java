package org.palaso.languageforge.client.lex.common.callback;

public class CallbackResultString extends CallbackResult {
	protected CallbackResultString() {
	}

	public final native String getReturnValue() /*-{
		return this.value;
	}-*/;
}