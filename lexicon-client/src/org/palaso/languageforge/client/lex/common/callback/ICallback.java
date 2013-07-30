package org.palaso.languageforge.client.lex.common.callback;

public interface ICallback<T extends CallbackResult> {
	void onReturn(T result);
}
