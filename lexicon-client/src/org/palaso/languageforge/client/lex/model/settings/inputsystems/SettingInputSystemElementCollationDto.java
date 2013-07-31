package org.palaso.languageforge.client.lex.model.settings.inputsystems;

import org.palaso.languageforge.client.lex.model.BaseDto;


public class SettingInputSystemElementCollationDto extends BaseDto<SettingInputSystemElementCollationDto> {

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

}
