package org.palaso.languageforge.client.lex.model.settings.inputsystems;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

/**
 * 
 * Specification: https://sites.google.com/site/ldmlspec/xml-format
 */

public class SettingInputSystemElementDto extends JavaScriptObject {

	protected SettingInputSystemElementDto() {
	};

	protected final void initialize() {
		setCollations(SettingInputSystemElementCollationsDto.getNew());
		setIdentity(SettingInputSystemElementIdentityDto.getNew());
		setSpecial(SettingInputSystemElementSpecialDto.getNew());
	}

	public final static SettingInputSystemElementDto getNew() {
		SettingInputSystemElementDto entry = SettingInputSystemElementDto
				.createObject().cast();
		entry.initialize();
		return entry;
	}

	public final native SettingInputSystemElementIdentityDto getIdentity() /*-{
		if (this.hasOwnProperty('ldml')) {
			if (this.ldml.hasOwnProperty('identity')) {
				return this.ldml.identity;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}-*/;

	private final native void setIdentity(
			SettingInputSystemElementIdentityDto dto) /*-{
		if (!this.hasOwnProperty('ldml')) {
			this.ldml = new Object();
		}
		this.ldml.identity = dto;
	}-*/;

	public final native SettingInputSystemElementCollationsDto getCollations() /*-{
		if (this.hasOwnProperty('ldml')) {
			if (this.ldml.hasOwnProperty('collations')) {
				return this.ldml.collations;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}-*/;

	public final native void setCollations(
			SettingInputSystemElementCollationsDto dto) /*-{
		if (!this.hasOwnProperty('ldml')) {
			this.ldml = new Object();
		}
		this.ldml.collations = dto;
	}-*/;

	public final native SettingInputSystemElementSpecialDto getSpecial() /*-{
		if (this.hasOwnProperty('ldml')) {
			if (this.ldml.hasOwnProperty('special')) {
				return this.ldml.special;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}-*/;

	private final native void setSpecial(SettingInputSystemElementSpecialDto dto) /*-{
		if (!this.hasOwnProperty('ldml')) {
			this.ldml = new Object();
		}
		this.ldml.special = dto;
	}-*/;

	public static final SettingInputSystemElementDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public static final String encode(SettingInputSystemElementDto object) {
		return new JSONObject(object).toString();
	}
}
