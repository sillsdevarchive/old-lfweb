package org.palaso.languageforge.client.lex.common.callback;

import com.google.gwt.core.client.JavaScriptObject;


public class CallbackComm {
	 public static native JavaScriptObject createNativeCallback(ICallback<?> callback) /*-{
	    return callback == null ? null : $entry(function(x) {
	      callback.@org.palaso.languageforge.client.lex.common.callback.ICallback::onReturn(Lorg/palaso/languageforge/client/lex/common/callback/CallbackResult;)(x);
	    });
	  }-*/;
}
