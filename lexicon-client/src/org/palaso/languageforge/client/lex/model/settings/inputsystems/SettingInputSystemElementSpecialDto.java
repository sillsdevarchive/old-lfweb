package org.palaso.languageforge.client.lex.model.settings.inputsystems;

import org.palaso.languageforge.client.lex.model.BaseDto;

public class SettingInputSystemElementSpecialDto extends BaseDto<SettingInputSystemElementSpecialDto> {

	protected SettingInputSystemElementSpecialDto() {
	};

	public final static SettingInputSystemElementSpecialDto getNew() {
		SettingInputSystemElementSpecialDto entry = SettingInputSystemElementSpecialDto
				.createObject().cast();
		entry.initialize();
		return entry;
	}

	protected final void initialize() {

		this.setPalasoAbbreviationValue("");
		this.setPalasoLanguageNameValue("");
		this.setPalasoVersionValue("");
	}

	public final native String getPalasoAbbreviationValue()
	/*-{
		if (this.hasOwnProperty('palaso:abbreviation')) {
			if (this['palaso:abbreviation'].hasOwnProperty('value')) {
				return this['palaso:abbreviation'].value;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setPalasoAbbreviationValue(String string) /*-{
		if (!this.hasOwnProperty('palaso:abbreviation')) {
			this['palaso:abbreviation'] = new Object();
		}
		this['palaso:abbreviation'].value = string;
		if (!this['palaso:abbreviation'].hasOwnProperty('@xmlns')) {
			this['palaso:abbreviation']['@xmlns'] = new Object();
		}
		this['palaso:abbreviation']['@xmlns'].palaso = "urn:\/\/palaso.org\/ldmlExtensions\/v1";
	}-*/;

	public final native String getPalasoLanguageNameValue()
	/*-{
		if (this.hasOwnProperty('palaso:languageName')) {
			if (this['palaso:languageName'].hasOwnProperty('value')) {
				return this['palaso:languageName'].value;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setPalasoLanguageNameValue(String string) /*-{
		if (!this.hasOwnProperty('palaso:languageName')) {
			this['palaso:languageName'] = new Object();
		}
		this['palaso:languageName'].value = string;
		if (!this['palaso:languageName'].hasOwnProperty('@xmlns')) {
			this['palaso:languageName']['@xmlns'] = new Object();
		}
		this['palaso:languageName']['@xmlns'].palaso = "urn:\/\/palaso.org\/ldmlExtensions\/v1";
	}-*/;

	public final native String getPalasoVersionValue()
	/*-{
		if (this.hasOwnProperty('palaso:version')) {
			if (this['palaso:version'].hasOwnProperty('value')) {
				return this['palaso:version'].value;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setPalasoVersionValue(String string) /*-{
		if (!this.hasOwnProperty('palaso:version')) {
			this['palaso:version'] = new Object();
		}
		this['palaso:version'].value = string;
		if (!this['palaso:version'].hasOwnProperty('@xmlns')) {
			this['palaso:version']['@xmlns'] = new Object();
		}
		this['palaso:version']['@xmlns'].palaso = "urn:\/\/palaso.org\/ldmlExtensions\/v1";
	}-*/;
}
