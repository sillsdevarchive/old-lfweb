package org.palaso.languageforge.client.lex.common;

import java.util.EnumMap;
import java.util.Map;

public enum UserRoleType  implements IEnum {
//	  	1 	anonymous user
//		2 	authenticated user
//		3 	siteuser
//		6 	project admin
//		7 	community admin
//		8 	contributor
//		9 	editor

	// DO not change name AND order definition here!!! used for JSON mapping AND DB->ROLE
	UNDEFINED("Undefined"),  //0
	ANONYMOUS_USER("Anonymous User"),//1
	AUTHENTICATED_USER("Authenticated User"), //2
	SITE_USER("Site User"), //3
	RESERVED_4("reserved4"),//4
	RESERVED_5("reserved5"),//5
	PROJECT_ADMIN("Project Admin"), //6
	COMMUNITY_ADMIN("Community Admin"),//7
	CONTRIBUTOR("Contributor"),//8
	EDITOR("Editor");//9


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
				if (key.getValue().equals(value)) {
					return key;
				}
			}
		}
		return UNDEFINED;
	}

}
