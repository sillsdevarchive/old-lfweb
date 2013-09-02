package org.palaso.languageforge.client.lex.common.enums;

import java.util.EnumMap;
import java.util.Map;

public enum SettingTaskNameType implements IEnum {

	// DO not change name definition here!!! used for JSON mapping
	UNDEFINED("Undefined"),
	DASHBOARD("Dashboard"), 
	REVIEW("Review"),
	DICTIONARY("Dictionary"), 
	ADDMISSINGINFO("AddMissingInfo"), 
	GATHERWORDLIST("GatherWordList"),
	GATHERWORDSBYSEMANTICDOMAINS("GatherWordsBySemanticDomains"), 
	ADVANCEDHISTORY("AdvancedHistory"), 
	NOTESBROWSER("NotesBrowser"),
	CONFIGURESETTINGS("ConfigureSettings");


	private String value;
	private static Map<SettingTaskNameType, String> table = new EnumMap<SettingTaskNameType, String>(
			SettingTaskNameType.class);

	// Initialize
	static {
		for (SettingTaskNameType value : SettingTaskNameType.values())
			table.put(value, value.getValue());
	}

	SettingTaskNameType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public static SettingTaskNameType getFromValue(String value) {
		if (table.containsValue(value)) {
			for (SettingTaskNameType key : SettingTaskNameType.values()) {
				if (key.getValue().equals(value)) {
					return key;
				}
			}
		}
		return UNDEFINED;
	}

}
