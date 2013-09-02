package org.palaso.languageforge.client.lex.common.enums;

import org.palaso.languageforge.client.lex.common.I18nConstants;;

public enum ActivityTimeRangeType implements IEnum {

	DAY_30(I18nConstants.STRINGS.ActivityTimeRangeType_Up_to_30_days()), 
	DAY_90(I18nConstants.STRINGS.ActivityTimeRangeType_Up_to_90_days()), 
	DAY_365(I18nConstants.STRINGS.ActivityTimeRangeType_Up_to_year()),
	DAY_ALL(I18nConstants.STRINGS.ActivityTimeRangeType_All());

	private String value;

	ActivityTimeRangeType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
