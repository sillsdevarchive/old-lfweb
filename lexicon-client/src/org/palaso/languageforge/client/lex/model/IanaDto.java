package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

/**
 * this is root class of IANA JSON data collections
 * 
 */

public class IanaDto extends JavaScriptObject {

	protected IanaDto() {
	};

	public final native JsArray<IanaBaseDataDto> getLanguages() /*-{
		return this.languages.list;
	}-*/;

	public final native JsArray<IanaBaseDataDto> getExtlangs() /*-{
		return this.extlangs.list;
	}-*/;

	public final native JsArray<IanaBaseDataDto> getScripts() /*-{
		return this.scripts.list;
	}-*/;

	public final native JsArray<IanaBaseDataDto> getRegions() /*-{
		return this.regions.list;
	}-*/;

	public final native JsArray<IanaBaseDataDto> getVariants() /*-{
		return this.variants.list;
	}-*/;

	public final native JsArray<IanaBaseDataDto> getGrandfathereds() /*-{
		return this.grandfathereds.list;
	}-*/;

	public final native JsArray<IanaBaseDataDto> getRedundants() /*-{
		return this.redundants.list;
	}-*/;

	/**
	 * Utility to render a String into an object.
	 * 
	 * @param json
	 * @return IANADto
	 */
	public static final IanaDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public static final String encode(IanaDto object) {
		return new JSONObject(object).toString();
	}

}
