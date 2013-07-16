/**
 * Copyright (C) 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.palaso.languageforge.client.lex.jsonrpc;

import org.palaso.languageforge.client.lex.model.CurrentEnvironmentDto;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.user.client.Window;

/**
 * Action definition for command pattern.
 * 
 * @author <a href="mailto:mail@raphaelbauer.com">rEyez</<a>
 * 
 * @param <R>
 *            The result. <A> The object to send while requesting => can be ""
 */

public abstract class JsonRpcAction<R> implements Action<R> {

	private String _method;
	private String _service;
	private String _servicePath;
	private int _parameterCount;
	private static String HOST_URL = "http://" + Window.Location.getHostName();
	protected boolean isBackgroundRequestRun = false;
	protected JsonRpcAction(String servicePath, String service, String method,
			int parameterCount) {
		_servicePath = servicePath;
		_service = service + "?p=" + CurrentEnvironmentDto.getCurrentProject().getProjectId() +"&u=" + CurrentEnvironmentDto.getCurrentUser().getId();
		_method = method;
		_parameterCount = parameterCount;
	}
	
	@Override
	public boolean isBackgroundRequest()
	{
		return isBackgroundRequestRun;
	}
	
	@Override
	public Method getHTTPMethod() {
		return RequestBuilder.POST;
	}

	@Override
	public String getService() {
		return _service;
	}

	@Override
	public String getFullServiceUrl() {

		if (!_servicePath.endsWith("/")) {
			return HOST_URL + _servicePath + "/" + _service;
		}
		return HOST_URL + _servicePath + _service;
	}

	@Override
	public String getServiceMethod() {
		return _method;
	}

	@Override
	public String encodeRequest() {
		return "";
	}

	@Override
	public int getParamCount() {
		return _parameterCount;
	}

	@Override
	public String encodeParam(int i) {
		return null;
	}

	@Override
	public JsonRpcResponseImpl<R> decodeResponse(String json) {
		JsonRpcResponseImpl<R> jsonRpcResponseImpl = new JsonRpcResponseImpl<R>(
				json);
		return jsonRpcResponseImpl;
	}

	@Override
	public RequestModel getRequestModel(int requestId) {
		RequestModel requestModel = RequestModel.getNew();
		requestModel.setMethod(getServiceMethod());
		for (int i = 0, n = getParamCount(); i < n; ++i) {
			requestModel.addParameter(encodeParam(i));
		}
		requestModel.setId(requestId);
		return requestModel;
	}

	@Override
	public String getRequestModelAsJsonString(int requestId) {
		return RequestModel.encode(getRequestModel(requestId));
	}
}
