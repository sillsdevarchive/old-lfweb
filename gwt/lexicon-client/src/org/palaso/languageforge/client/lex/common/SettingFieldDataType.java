package org.palaso.languageforge.client.lex.common;

import java.util.EnumMap;
import java.util.Map;

public enum SettingFieldDataType  implements IEnum  {

	
	// DO not change name definition here!!! used for JSON mapping
	UNDEFINED("Undefined"),
	MULTITEXT("MultiText"),
	OPTION("Option"),
	PICTURE("Picture"),
	OPTIONCOLLECTION("OptionCollection"),
	RELATIONTOONEENTRY("RelationToOneEntry");
	
    private String value;
	private static Map<SettingFieldDataType, String> table = new EnumMap<SettingFieldDataType, String>(
			SettingFieldDataType.class);
	
	// Initialize
	static {
		for (SettingFieldDataType value : SettingFieldDataType.values())
			table.put(value, value.getValue());
	}
	
    SettingFieldDataType(String value) {
        this.value = value;
    }


	@Override
	public String getValue() {
		return this.value;
	}

	public static SettingFieldDataType getFromValue(String value) {
		if (table.containsValue(value)) {
			for (SettingFieldDataType key : SettingFieldDataType.values()) {
				if (key.getValue().equals(value)) {
					return key;
				}
			}
		}
		return UNDEFINED;
	}

}
