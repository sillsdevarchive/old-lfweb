package org.palaso.languageforge.client.lex.model.settings.fields;

import org.palaso.languageforge.client.lex.common.SettingFieldClassNameType;
import org.palaso.languageforge.client.lex.common.SettingFieldDataType;
import org.palaso.languageforge.client.lex.common.SettingFieldMultiplicityType;
import org.palaso.languageforge.client.lex.common.SettingFieldVisibilityType;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

public class SettingFieldsFieldElementDto extends JavaScriptObject {

	protected SettingFieldsFieldElementDto() {
	};

	public final static SettingFieldsFieldElementDto getNew() {
		SettingFieldsFieldElementDto entry = SettingFieldsFieldElementDto
				.createObject().cast();
		entry.initialize();
		return entry;
	}

	protected final void initialize() {

		// this.setVersionNumber("");
	}

	public final native String getClassNameAsString() /*-{
		if (this.hasOwnProperty('className')) {
			if (this.className.hasOwnProperty('$')) {
				return this.className['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final SettingFieldClassNameType getClassName() {
		try {
			return SettingFieldClassNameType
					.getFromValue(getClassNameAsString().trim());
		} catch (IllegalArgumentException e) {
			return SettingFieldClassNameType.UNDEFINED;
		}
	}

	public final SettingFieldDataType getDataType() {
		try {
			return SettingFieldDataType.valueOf(getDataTypeAsString().trim()
					.toUpperCase());
		} catch (IllegalArgumentException e) {
			return SettingFieldDataType.UNDEFINED;
		}
	}

	public final native String getDataTypeAsString() /*-{
		if (this.hasOwnProperty('dataType')) {
			if (this.dataType.hasOwnProperty('$')) {
				return this.dataType['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getDisplayName() /*-{
		if (this.hasOwnProperty('displayName')) {
			if (this.displayName.hasOwnProperty('$')) {
				return this.displayName['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setDisplayName(String string) /*-{

		if (!this.hasOwnProperty('displayName')) {
			this.displayName = new Object();
		}
		if (string == null) {
			string = "";
		}
		this.displayName['$'] = string;
	}-*/;

	public final boolean getEnabled() {
		if (getEnabledAsString().trim().equalsIgnoreCase("True")) {
			return true;
		} else {
			return false;
		}
	}

	public final native void setEnabled(boolean enabled) /*-{
		if (!this.hasOwnProperty('enabled')) {
			this.enabled = new object();
		}
		if (enabled) {
			this.enabled['$'] = 'True';
		} else {
			this.enabled['$'] = 'False';
		}
	}-*/;

	public final native String getEnabledAsString() /*-{
		if (this.hasOwnProperty('enabled')) {
			if (this.enabled.hasOwnProperty('$')) {
				return this.enabled['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getFieldName() /*-{
		if (this.hasOwnProperty('fieldName')) {
			if (this.fieldName.hasOwnProperty('$')) {
				return this.fieldName['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final boolean getMultiParagraph() {
		if (getMultiParagraphAsString().trim().equalsIgnoreCase("True")) {
			return true;
		} else {
			return false;
		}
	}

	public final native String getMultiParagraphAsString() /*-{
		if (this.hasOwnProperty('multiParagraph')) {
			if (this.multiParagraph.hasOwnProperty('$')) {
				return this.multiParagraph['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final boolean getSpellCheckingEnabled() {
		if (getSpellCheckingEnabledAsString().trim().equalsIgnoreCase("True")) {
			return true;
		} else {
			return false;
		}
	}

	public final native String getSpellCheckingEnabledAsString() /*-{
		if (this.hasOwnProperty('spellCheckingEnabled')) {
			if (this.spellCheckingEnabled.hasOwnProperty('$')) {
				return this.spellCheckingEnabled['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final SettingFieldMultiplicityType getMultiplicity() {
		try {
			return SettingFieldMultiplicityType
					.valueOf(getMultiplicityAsString().trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			return SettingFieldMultiplicityType.UNDEFINED;
		}
	}

	public final native String getMultiplicityAsString() /*-{
		if (this.hasOwnProperty('multiplicity')) {
			if (this.multiplicity.hasOwnProperty('$')) {
				return this.multiplicity['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getOptionsListFile() /*-{
		if (this.hasOwnProperty('optionsListFile')) {
			if (this.optionsListFile.hasOwnProperty('$')) {
				return this.optionsListFile['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setVisibility(boolean visibile) /*-{
		if (!this.hasOwnProperty('visibility')) {
			this.visibility = new object();
		}
		if (visibile) {
			this.visibility['$'] = "Visible";
		} else {
			this.visibility['$'] = "NormallyHidden";
		}
	}-*/;

	public final SettingFieldVisibilityType getVisibility() {
		try {
			return SettingFieldVisibilityType.valueOf(getVisibilityAsString()
					.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			return SettingFieldVisibilityType.UNDEFINED;
		}
	}

	public final native String getVisibilityAsString() /*-{
		if (this.hasOwnProperty('visibility')) {
			if (this.visibility.hasOwnProperty('$')) {
				return this.visibility['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native JsArray<SettingFieldsFieldWritingSystemDto> getNewWritingSystems() /*-{
		// do not check existing, create new always!
		this.writingSystems = new Object();
		this.writingSystems['id'] = new Array();
		return this.writingSystems['id'];
	}-*/;

	public final native JsArray<SettingFieldsFieldWritingSystemDto> getNewAvailableWritingSystems() /*-{
		// do not check existing, create new always!
		this.availableWritingSystems = new Object();
		this.availableWritingSystems['id'] = new Array();
		return this.availableWritingSystems['id'];
	}-*/;

	public final native JsArray<SettingFieldsFieldWritingSystemDto> getWritingSystems() /*-{
		if (this.hasOwnProperty('writingSystems')) {
			if (this.writingSystems.hasOwnProperty('id')) {
				if (Object.prototype.toString.call(this.writingSystems['id']) == "[object Array]") {
					return this.writingSystems['id'];
				} else {
					if (this.writingSystems.id.hasOwnProperty('$')) {
						var newObj = new Array();
						newObj[0] = new Object();
						newObj[0]['$'] = this.writingSystems.id['$']
						return newObj;
					} else {
						return new Array();
					}
				}
			} else {
				return new Array();
			}
		} else {
			return new Array();
		}
	}-*/;

	public final native JsArray<SettingFieldsFieldWritingSystemDto> getAvailableWritingSystems() /*-{

		if (this.hasOwnProperty('availableWritingSystems')) {
			if (this.availableWritingSystems.hasOwnProperty('id')) {
				if (Object.prototype.toString
						.call(this.availableWritingSystems['id']) == "[object Array]") {
					return this.availableWritingSystems['id'];
				} else {
					if (this.availableWritingSystems.id.hasOwnProperty('$')) {
						var newObj = new Array();
						newObj[0] = new Object();
						newObj[0]['$'] = this.availableWritingSystems.id['$']
						return newObj;
					} else {
						return new Array();
					}
				}
			} else {
				return new Array();
			}
		} else {
			return new Array();
		}
	}-*/;


	public final native String getFieldIndex() /*-{
		if (this.hasOwnProperty('index')) {
			return this.index;
		} else {
			return '';
		}
	}-*/;

	public final native void setFieldIndex(String index) /*-{
		this.index = index;
	}-*/;
	
	// public final native void setVersionNumber(String string) /*-{
	// this.version = new Object();
	// if (string == null) {
	// string = "";
	// }
	// this.version.number = string;
	// }-*/;

	public static final  SettingFieldsFieldElementDto decode(String json) {
		return JsonUtils.safeEval(json);
	}

	public static final String encode(SettingFieldsFieldElementDto object) {
		return new JSONObject(object).toString();
	}

}
