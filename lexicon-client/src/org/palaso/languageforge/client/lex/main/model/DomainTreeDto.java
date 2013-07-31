package org.palaso.languageforge.client.lex.main.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;

public class DomainTreeDto extends JavaScriptObject {

	protected DomainTreeDto() {
	}

	public final native String getKey()/*-{
		return this.key;
	}-*/;

	public final native String getGuid()/*-{
		return this.guid;
	}-*/;

	public final native void setPercent(float percent)/*-{
		this.percent = percent;
	}-*/;

	public final native float getPercent()/*-{
		if ('percent' in this) {
			return this.percent;
		} else {
			return 0.0;
		}
	}-*/;

	public final native JsArray<DomainTreeDto> getChildren()/*-{
		return this.children;
	}-*/;

	public static final DomainTreeDto decode(String json){
		return JsonUtils.safeEval(json);
	}

}
