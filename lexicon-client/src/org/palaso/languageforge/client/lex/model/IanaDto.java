package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JsArray;

/**
 * this is root class of IANA JSON data collections
 * 
 */

public class IanaDto extends BaseDto<IanaDto> {

	protected IanaDto() {
	};

	public final native JsArray<IanaBaseDataDto> getLanguages() /*-{
		return this.languages.list;
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
	//----------------->>>>NOT IN USER
	public final native JsArray<IanaBaseDataDto> getExtlangs() /*-{
		return this.extlangs.list;
	}-*/;

	public final native JsArray<IanaBaseDataDto> getGrandfathereds() /*-{
		return this.grandfathereds.list;
	}-*/;

	public final native JsArray<IanaBaseDataDto> getRedundants() /*-{
		return this.redundants.list;
	}-*/;
	//<<<<-----------------NOT IN USER
}
