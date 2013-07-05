package org.palaso.languageforge.client.lex.common;

import java.util.EnumMap;
import java.util.Map;

public enum SettingFieldClassNameType  implements IEnum  {

	
	// DO not change name definition here!!! used for JSON mapping
	UNDEFINED("Undefined"),
	LEXENTRY("LexEntry"),
	LEXSENSE("LexSense"),
	LEXEXAMPLESENTENCE("LexExampleSentence"),
	PALASODATAOBJECT("PalasoDataObject");
	
    private String value;
	private static Map<SettingFieldClassNameType, String> table = new EnumMap<SettingFieldClassNameType, String>(
			SettingFieldClassNameType.class);
	
	// Initialize
	static {
		for (SettingFieldClassNameType value : SettingFieldClassNameType.values())
			table.put(value, value.getValue());
	}
	
    SettingFieldClassNameType(String value) {
        this.value = value;
    }


	@Override
	public String getValue() {
		return this.value;
	}

	public static SettingFieldClassNameType getFromValue(String value) {
		if (table.containsValue(value)) {
			for (SettingFieldClassNameType key : SettingFieldClassNameType.values()) {
				if (key.getValue().equals(value)) {
					return key;
				}
			}
		}
		return UNDEFINED;
	}

}
