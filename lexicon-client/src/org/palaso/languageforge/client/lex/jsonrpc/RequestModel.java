package org.palaso.languageforge.client.lex.jsonrpc;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class RequestModel extends JavaScriptObject {

	protected RequestModel() {
	};

	public final static RequestModel getNew() {
		RequestModel entry = RequestModel.createObject().cast();
		entry.initialize();
		return entry;
	}

	private final native void initialize() /*-{
		this.id = new Object();
		this.method = new Object();
		this.params = new Array();
	}-*/;

	public final native void setMethod(String method) /*-{
		if (!this.hasOwnProperty('method')) {
			this.method = new object();
		}
		this.method = method;
	}-*/;

	public final native void setId(int i) /*-{
		if (!this.hasOwnProperty('id')) {
			this.id = new object();
		}
		this.id = i;
	}-*/;

	public final native void addParameter(Object param) /*-{
		this.params.push(param);
	}-*/;

	public final static String encode(RequestModel object) {
		return new JSONObject(object).toString();
	}

}
