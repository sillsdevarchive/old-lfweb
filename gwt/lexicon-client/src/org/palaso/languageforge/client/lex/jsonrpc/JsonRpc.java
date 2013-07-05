/*
 * Copyright 2006 Florian Fankhauser f.fankhauser@gmail.com
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

package org.palaso.languageforge.client.lex.jsonrpc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A simple JSON de- and encoder. And a json-rpc client.
 * 
 * @author Flo
 * 
 */
public class JsonRpc implements DispatchAsync {

	private int requestCounter = 0;

	private int requestSerial = 1;

	private int requestTimeout = 10 * 1000; // time out at 10 sec

	private String requestCookie = null;

	private static Set<JsonRpcRequestStateListener> requestStateListeners = new HashSet<JsonRpcRequestStateListener>();;

	public JsonRpc() {

	}

	protected void increaseRequestCounter(boolean isBackgroundRequest) {
		requestCounter++;
		if (requestCounter > 0) {
			fireRequestStateChanged(true,isBackgroundRequest);
		}
	}

	protected void decreaseRequestCounter(boolean isBackgroundRequest) {
		requestCounter--;
		if (requestCounter == 0) {
			fireRequestStateChanged(false,isBackgroundRequest);
		}
		if (requestCounter < 0)
			requestCounter = 0;
	}

	protected void fireRequestStateChanged(boolean requestRunning, boolean isBackgroundRequest) {
		// TODO this can be coded in a more generic observer / observable
		// pattern
		for (Iterator<JsonRpcRequestStateListener> iter = requestStateListeners
				.iterator(); iter.hasNext();) {
			JsonRpcRequestStateListener listener = (JsonRpcRequestStateListener) iter
					.next();
			listener.requestStateChanged(requestRunning, isBackgroundRequest);
		}
	}

	/**
	 * Add a listener when you are interested if a request is currently running.
	 * You can use this to easily show an hide an ajax-activity-indicator-image.
	 * 
	 * @param listener
	 */
	// TODO this can be coded in a more generic observer / observable pattern
	public void addReqestStateListener(JsonRpcRequestStateListener listener) {
		if (!requestStateListeners.contains(listener)) {
			requestStateListeners.add(listener);
		}
	}

	// TODO this can be coded in a more generic observer / observable pattern
	public void removeActivityListener(JsonRpcRequestStateListener listener) {
		if (requestStateListeners.contains(listener)) {
			requestStateListeners.remove(listener);
		}
	}

	/**
	 * Set timeout for json-rpc request.
	 * 
	 * @param millis
	 *            Timeout in milliseconds for request to complete. Specify 0 for
	 *            no timeout.
	 */
	public void setTimeout(int millis) {
		requestTimeout = millis;
	}

	/**
	 * Optional name of a cookie that gets replicated in the request as X-Cookie
	 * header. Can be useful for authentication purposes.
	 * 
	 * @param cookieName
	 *            The name of the cookie to replicate.
	 */
	public void setCookieToReplicate(String cookieName) {
		requestCookie = cookieName;
	}

	/**
	 * Executes a json-rpc request.
	 * 
	 * @param <R>
	 * 
	 * @param <R>
	 * 
	 * @param url
	 *            The location of the service
	 * @param username
	 *            The username for basic authentification
	 * @param password
	 *            The password for basic authentification
	 * @param method
	 *            The method name
	 * @param params
	 *            An array of objects containing the parameters
	 * @param callback
	 *            A callbackhandler like in gwt's rpc.
	 */

	// NOTE We will use the Action to 3encode our request and decode the
	// response
	// of *the object* (DTO)
	// NOTE This class will add the rpc elements which wrap the request and
	// repsonse.
	public <A extends Action<R>, R> void execute(final A action,
			final AsyncCallback<R> callback) {

		RequestCallback handler = new RequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				try {
					if (response.getStatusCode() != 200) {
						String errMsg = "";
						if (response.getStatusCode() == 0) {
							errMsg = "Error: Can not connect to server, this may be a problem of network or server down.";
						} else {
							errMsg = "Error: Unexpected server or network error ["
									+ String.valueOf(response.getStatusCode())
									+ "]: " + response.getStatusText();
						}
						RuntimeException runtimeException = new RuntimeException(
								errMsg);
						callback.onFailure(runtimeException);
						return;
					}

					String resp = response.getText();

					if (resp.equals("")) {
						RuntimeException runtimeException = new RuntimeException(
								"Error: Received empty result from server, this could be a server fail.");
						callback.onFailure(runtimeException);
					}

					JsonRpcResponse<R> reply = action.decodeResponse(resp);

					if (reply == null) { // This implies a decode error. Perhaps
											// it would be better if the action
											// doing the decode threw a more
											// precise exception
						RuntimeException runtimeException = new RuntimeException(
								"Error: Received data can not decoded, data: "
										+ response.getText());
						callback.onFailure(runtimeException);
					}

					if (reply.getResponseModel().isErrorResponse()) {
						RuntimeException runtimeException = new RuntimeException(
								"Error: Server failed with message: "
										+ reply.getResponseModel().getError());
						callback.onFailure(runtimeException);
					} else if (reply.getResponseModel().isSuccessfulResponse()) {
						R result = (R) reply.getResponseModel().getResult();
						callback.onSuccess(result);
					} else {
						RuntimeException runtimeException = new RuntimeException(
								"Error: Received data has syntax error: "
										+ response.getText());
						callback.onFailure(runtimeException);
					}
				} catch (RuntimeException e) {
					callback.onFailure(e);
				} finally {
					decreaseRequestCounter(action.isBackgroundRequest());
				}
			}

			public void onError(Request request, Throwable exception) {
				try {
					if (exception instanceof RequestTimeoutException) {
						RuntimeException runtimeException = new RuntimeException(
								"Error: Connection timeout, server didn't respose.");
						callback.onFailure(runtimeException);
					} else {
						RuntimeException runtimeException = new RuntimeException(
								"Error: A unknown error happened when try to send request to server");
						callback.onFailure(runtimeException);
					}
				} catch (RuntimeException e) {
					callback.onFailure(e);
				} finally {
					decreaseRequestCounter(action.isBackgroundRequest());
				}
			}

		};

		increaseRequestCounter(action.isBackgroundRequest());

		// The request builder. Currently we only attempt a request once.
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				action.getFullServiceUrl());
		if (requestTimeout > 0)
			builder.setTimeoutMillis(requestTimeout);
		builder.setHeader("Content-Type", "application/json; charset=UTF-8");

		String body = action.getRequestModelAsJsonString(requestSerial++);

		if (body == null)
			builder.setHeader("Content-Length", "0");
		// builder.setHeader("Content-Length", Integer.toString(body.length()));
		if (requestCookie != null)
			if (Cookies.getCookie(requestCookie) != null)
				builder.setHeader("X-Cookie", Cookies.getCookie(requestCookie));

		try {
			builder.sendRequest(body, handler);
		} catch (RequestException exception) {
			handler.onError(null, exception);
		}
	}
}