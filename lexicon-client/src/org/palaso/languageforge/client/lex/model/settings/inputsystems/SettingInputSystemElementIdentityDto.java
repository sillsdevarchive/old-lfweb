package org.palaso.languageforge.client.lex.model.settings.inputsystems;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

public class SettingInputSystemElementIdentityDto extends JavaScriptObject {
	protected SettingInputSystemElementIdentityDto() {

	};

	public final static SettingInputSystemElementIdentityDto getNew() {
		SettingInputSystemElementIdentityDto entry = SettingInputSystemElementIdentityDto
				.createObject().cast();
		entry.initialize();
		return entry;
	}

	protected final void initialize() {
		this.setGenerationDate("");
		this.setLanguageType("");
		this.setScriptType("");
		this.setTerritoryType("");
		this.setVariantType("");
		this.setVersionNumber("");
	}

	public final native String getVersionNumber() /*-{
		if (this.hasOwnProperty('version')) {
			if (this.version.hasOwnProperty('number')) {
				return this.version.number;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setVersionNumber(String string) /*-{
		this.version = new Object();
		if (string == null) {
			string = "";
		}
		this.version.number = string;
	}-*/;

	public final native String getGenerationDate() /*-{
		if (this.hasOwnProperty('generation')) {
			if (this.generation.hasOwnProperty('date')) {
				return this.generation.date;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setGenerationDate(String string) /*-{
		this.generation = new Object();
		if (string == null) {
			string = "";
		}
		this.generation.date = string;
	}-*/;

	public final native String getLanguageType() /*-{
		if (this.hasOwnProperty('language')) {
			if (this.language.hasOwnProperty('type')) {
				return this.language.type;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setLanguageType(String string) /*-{
		this.language = new Object();
		if (string == null) {
			string = "";
		}
		this.language.type = string;
	}-*/;

	public final native String getScriptType() /*-{
		if (this.hasOwnProperty('script')) {
			if (this.script.hasOwnProperty('type')) {
				return this.script.type;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setScriptType(String string) /*-{
		this.script = new Object();
		if (string == null) {
			string = "";
		}
		this.script.type = string;
	}-*/;

	public final native String getTerritoryType() /*-{
		if (this.hasOwnProperty('territory')) {
			if (this.territory.hasOwnProperty('type')) {
				return this.territory.type;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setTerritoryType(String string) /*-{
		this.territory = new Object();
		if (string == null) {
			string = "";
		}
		this.territory.type = string;
	}-*/;

	public final native String getVariantType() /*-{
		if (this.hasOwnProperty('variant')) {
			if (this.variant.hasOwnProperty('type')) {
				return this.variant.type;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setVariantType(String string) /*-{
		this.variant = new Object();
		if (string == null) {
			string = "";
		}
		this.variant.type = string;
	}-*/;

	public static final SettingInputSystemElementIdentityDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public static final String encode(
			SettingInputSystemElementIdentityDto object) {
		return new JSONObject(object).toString();
	}

}
