package org.palaso.languageforge.client.lex.jsonrpc;

import com.google.gwt.core.client.JsonUtils;

public class JsonRpcResponseImpl<R> implements JsonRpcResponse<R> {

	private ResponseModel<R> responseModel = null;

	public JsonRpcResponseImpl(String json) {
		responseModel = decode(json);
	}

	private  ResponseModel<R> decode(String json) {
		return JsonUtils.safeEval(json);
	}
	
	@Override
	public ResponseModel<R> getResponseModel() {
		return responseModel;
	}

}
