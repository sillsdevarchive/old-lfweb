package org.palaso.languageforge.client.lex.common.enums;

import java.util.EnumMap;
import java.util.Map;

public enum ConversationAnnotationType  implements IEnum  {

	
	// DO not change name definition here!!! used for JSON mapping
	UNDEFINED("undefined"),
	QUESTION("question"),
	MERGECONFLICT("mergeConflict");

    private String value;
	private static Map<ConversationAnnotationType, String> table = new EnumMap<ConversationAnnotationType, String>(
			ConversationAnnotationType.class);
	
	// Initialize
	static {
		for (ConversationAnnotationType value : ConversationAnnotationType.values())
			table.put(value, value.getValue());
	}
	
    ConversationAnnotationType(String value) {
        this.value = value;
    }


	@Override
	public String getValue() {
		return this.value;
	}

	public static ConversationAnnotationType getFromValue(String value) {
		if (table.containsValue(value)) {
			for (ConversationAnnotationType key : ConversationAnnotationType.values()) {
				if (key.getValue().equals(value)) {
					return key;
				}
			}
		}
		return UNDEFINED;
	}

}
