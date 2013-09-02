package org.palaso.languageforge.client.lex.common.enums;

import java.util.EnumMap;
import java.util.Map;

public enum OperationPermissionType  implements IEnum  {

	// DO not change name definition here!!! used for JSON mapping
	// must match definitions in ProjectAccessDTO.php!!
	UNDEFINED			("0"),		// this is used in UI side only, so this key is not exist in server side.
	CAN_CREATE			("1"),
	CAN_EDIT_OWN		("2"),
	CAN_EDIT_OTHER		("3"),
	CAN_DELETE_OWN 		("4"),
	CAN_DELETE_OTHER	("5"),
	CAN_LOCK			("6");
	
    private String value;
	private static Map<OperationPermissionType, String> table = new EnumMap<OperationPermissionType, String>(
			OperationPermissionType.class);
	
	// Initialize
	static {
		for (OperationPermissionType value : OperationPermissionType.values())
			table.put(value, value.getValue());
	}
	
    OperationPermissionType(String value) {
        this.value = value;
    }


	@Override
	public String getValue() {
		return this.value;
	}

	public static OperationPermissionType getFromValue(String value) {
		if (table.containsValue(value)) {
			for (OperationPermissionType key : OperationPermissionType.values()) {
				if (key.getValue().equals(value)) {
					return key;
				}
			}
		}
		return UNDEFINED;
	}

}
