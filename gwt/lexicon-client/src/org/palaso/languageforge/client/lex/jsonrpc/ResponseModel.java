package org.palaso.languageforge.client.lex.jsonrpc;

import com.google.gwt.core.client.JavaScriptObject;

public class ResponseModel<R> extends JavaScriptObject {

	protected ResponseModel() {
	}

	// public static ResponseModel<?> decode(String json) {
	// return JsonUtils.safeEval(json);
	// }

	public final native int getId() /*-{
		return this.id;
	}-*/;

	public final native String getError() /*-{
		if (typeof (this.error) == "boolean") {
			if (this.result==true)
			{
				return "true";
			}else
			{
				return "false";
			}
		}
		return this.error;
	}-*/;

	public final native R getResult() /*-{
		if (typeof (this.result) == "boolean") {
			if (this.result==true)
			{
				return @java.lang.Boolean::TRUE
			}else
			{
				return @java.lang.Boolean::FALSE
			}
		} else if (typeof (this.result) == "string") {
			return this.result;
		} else if (typeof (this.result) == "number") {
			return @java.lang.Integer::valueOf(I)(this.result);
		} else {
			return this.result;
		}
	}-*/;

	public final native boolean hasResult() /*-{
		return this.hasOwnProperty("result");
	}-*/;

	public final boolean isSuccessfulResponse() {
		return (getError() == null || getError().equalsIgnoreCase("false")) && hasResult();
	}

	public final boolean isErrorResponse() {
		if (getError() != null && getResult() == null)
		{
			return true;
		}else
		{
			if (getError() != null && getError().equalsIgnoreCase("false"))
			{
				return false;
			}
		}
		return false;
	}

}
