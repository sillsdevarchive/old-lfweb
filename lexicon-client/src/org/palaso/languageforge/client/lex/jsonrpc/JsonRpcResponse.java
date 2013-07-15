package org.palaso.languageforge.client.lex.jsonrpc;

public interface JsonRpcResponse<R> {

	public ResponseModel<R> getResponseModel();
}
