package org.palaso.languageforge.client.lex.common;

import java.util.EnumMap;
import java.util.Map;

public enum ProjectPermissionType  implements IEnum  {

	// DO not change name definition here!!! used for JSON mapping
	// must match definitions in ProjectAccessDTO.php!!
	UNDEFINED			("0"),		// this is used in UI side only, so this key is not exist in server side.
	CAN_ADMIN			("1"),
	CAN_CREATE_ENTRY	("2"),
	CAN_EDIT_ENTRY		("3"),
	CAN_DELETE_ENTRY 	("4"),
	CAN_EDIT_REVIEW_ALL	("5"),
	CAN_EDIT_REVIEW_OWN	("6"),
	CAN_CREATE_REVIEW	("7"),
	CAN_REPLY_REVIEW 	("8");
	
    private String value;
	private static Map<ProjectPermissionType, String> table = new EnumMap<ProjectPermissionType, String>(
			ProjectPermissionType.class);
	
	// Initialize
	static {
		for (ProjectPermissionType value : ProjectPermissionType.values())
			table.put(value, value.getValue());
	}
	
    ProjectPermissionType(String value) {
        this.value = value;
    }


	@Override
	public String getValue() {
		return this.value;
	}

	public static ProjectPermissionType getFromValue(String value) {
		if (table.containsValue(value)) {
			for (ProjectPermissionType key : ProjectPermissionType.values()) {
				if (key.getValue().equals(value)) {
					return key;
				}
			}
		}
		return UNDEFINED;
	}

}
