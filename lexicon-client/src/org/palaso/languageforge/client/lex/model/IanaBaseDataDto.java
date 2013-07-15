package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JavaScriptObject;

/*
 * this is JSON map object of IANA data
 */

public class IanaBaseDataDto extends JavaScriptObject {

	protected IanaBaseDataDto() {

	}

	public final native String getSubtag() /*-{
		if (this.hasOwnProperty('st') && this.st != null) {
			return this.st;
		} else {
			return "";
		}
	}-*/;

	public final native String getDescription() /*-{
		if (this.hasOwnProperty('des') && this.des != null) {
			if (Object.prototype.toString.call(this.des) == "[object Array]") {
				return this.des.join(" / ");
			} else {
				return this.des;
			}

		} else {
			return "";
		}

	}-*/;

	public final native String getScope() /*-{
		if (this.hasOwnProperty('sc') && this.sc != null) {
			return this.sc;
		} else {
			return "";
		}
	}-*/;

	public final native String getSuppressScript() /*-{
		if (this.hasOwnProperty('ss') && this.ss != null) {
			return this.ss;
		} else {
			return "";
		}
	}-*/;

	public final native String getDeprecated() /*-{
		if (this.hasOwnProperty('dep') && this.dep != null) {
			return this.dep;
		} else {
			return "";
		}
	}-*/;

	public final native String getTag() /*-{
		if (this.hasOwnProperty('tg') && this.tg != null) {
			return this.tg;
		} else {
			return "";
		}
	}-*/;

	public final native String getPreferredValue() /*-{
		if (this.hasOwnProperty('pv') && this.pv != null) {
			return this.pv;
		} else {
			return "";
		}
	}-*/;

	public final native String getPrefix() /*-{
		if (this.hasOwnProperty('pf') && this.pf != null) {
			if (Object.prototype.toString.call(this.pf) == "[object Array]") {
				return this.pf.join(" / ");
			} else {
				return this.pf;
			}

			return this.pv;
		} else {
			return "";
		}
	}-*/;

	public final native String getMacrolanguage() /*-{
		if (this.hasOwnProperty('ma') && this.ma != null) {
			return this.ma;
		} else {
			return "";
		}
	}-*/;
}
