package org.palaso.languageforge.client.lex.common.enums;

import java.util.EnumMap;
import java.util.Map;

public enum DomainPermissionType  implements IEnum  {

	// DO not change name definition here!!! used for JSON mapping
	// must match definitions in Server side!!
	UNDEFINED			("000"),		// this is used in UI side only, so this key is not exist in server side.
	DOMAIN_ANY			("100"),
	DOMAIN_USERS		("110"),
	DOMAIN_PROJECTS		("120"),
	DOMAIN_QUESTIONS	("130"),
	DOMAIN_ANSWERS		("140"),
	DOMAIN_COMMENTS		("150"),
	DOMAIN_LEX_ENTRY	("160");
	
    private String value;
	private static Map<DomainPermissionType, String> table = new EnumMap<DomainPermissionType, String>(
			DomainPermissionType.class);
	
	// Initialize
	static {
		for (DomainPermissionType value : DomainPermissionType.values())
			table.put(value, value.getValue());
	}
	
    DomainPermissionType(String value) {
        this.value = value;
    }


	@Override
	public String getValue() {
		return this.value;
	}

	public static DomainPermissionType getFromValue(String value) {
		if (table.containsValue(value)) {
			for (DomainPermissionType key : DomainPermissionType.values()) {
				if (key.getValue().equals(value)) {
					return key;
				}
			}
		}
		return UNDEFINED;
	}

}
