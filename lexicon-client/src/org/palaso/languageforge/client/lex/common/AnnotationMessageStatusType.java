package org.palaso.languageforge.client.lex.common;

import java.util.EnumMap;
import java.util.Map;

public enum AnnotationMessageStatusType  implements IEnum  {

	
	// DO not change name definition here!!! used for JSON mapping
	UNDEFINED("undefined"),
	CLOSED("closed"),
	UNCLOSED("unclosed");

    private String value;
	private static Map<AnnotationMessageStatusType, String> table = new EnumMap<AnnotationMessageStatusType, String>(
			AnnotationMessageStatusType.class);
	
	// Initialize
	static {
		for (AnnotationMessageStatusType value : AnnotationMessageStatusType.values())
			table.put(value, value.getValue());
	}
	
    AnnotationMessageStatusType(String value) {
        this.value = value;
    }


	@Override
	public String getValue() {
		return this.value;
	}

	public static AnnotationMessageStatusType getFromValue(String value) {
		if (table.containsValue(value)) {
			for (AnnotationMessageStatusType key : AnnotationMessageStatusType.values()) {
				if (key.getValue().equals(value)) {
					return key;
				}
			}
		}
		return UNDEFINED;
	}

}
