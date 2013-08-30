package org.palaso.languageforge.client.lex.common;

import java.util.EnumMap;
import java.util.Map;

public enum UserRoleType  implements IEnum {

	// DO not change name AND order definition here!!! used for JSON mapping to server : models\rights\Roles.php
	UNDEFINED("UNDEFINED"),  //0
	SYSTEM_ADMIN("system_admin"),//1
	PROJECT_ADMIN("project_admin"), //2
	USER("user"); //3


	private String value;
	private static Map<UserRoleType, String> table = new EnumMap<UserRoleType, String>(
			UserRoleType.class);

	// Initialize
	static {
		for (UserRoleType value : UserRoleType.values())
			table.put(value, value.getValue());
	}

	UserRoleType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}
	
	public static UserRoleType valueOf(int ordinal)
	{
		return UserRoleType.values()[ordinal];
	}

	public static UserRoleType getFromValue(String value) {
		if (table.containsValue(value)) {
			for (UserRoleType key : UserRoleType.values()) {
				if (key.getValue().equalsIgnoreCase(value)) {
					return key;
				}
			}
		}
		return UNDEFINED;
	}

}
