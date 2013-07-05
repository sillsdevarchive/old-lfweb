package org.palaso.languageforge.client.lex.model.settings.inputsystems;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

public class SettingInputSystemElementCollationDto extends JavaScriptObject {

	protected SettingInputSystemElementCollationDto() {

	};

	public final static SettingInputSystemElementCollationDto getNew() {
		SettingInputSystemElementCollationDto entry = SettingInputSystemElementCollationDto
				.createObject().cast();
		entry.initialize();
		return entry;
	}

	protected final void initialize() {

	}

	public final native String getBaseAliasSource()
	/*-{
		if (this.hasOwnProperty('base')) {
			if (this.base.hasOwnProperty('alias')) {
				if (this.base.alias.hasOwnProperty('source')) {
					return this.base.alias.source;
				} else {
					return '';
				}
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getSpecialSortRulesTypeValue()
	/*-{
		if (this.hasOwnProperty('special')) {
			if (this.special.hasOwnProperty('palaso:sortRulesType')) {
				if (this.special['palaso:sortRulesType']
						.hasOwnProperty('value')) {
					return this.special['palaso:sortRulesType'].value;
				} else {
					return '';
				}
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public static final SettingInputSystemElementCollationDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public static final String encode(
			SettingInputSystemElementCollationDto object) {
		return new JSONObject(object).toString();
	}

}
