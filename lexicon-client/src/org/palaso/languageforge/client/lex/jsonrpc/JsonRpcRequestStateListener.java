package org.palaso.languageforge.client.lex.jsonrpc;

public interface JsonRpcRequestStateListener {

	/**
	 * Notifies the listener that the requestRunning state has changed.
	 * <code>requestRunning</code> is true when there is at least one currently
	 * running request or false if there is no running request.
	 * 
	 * @param requestRunning
	 */
	void requestStateChanged(boolean requestRunning, boolean isBackgroundRequest);
}
