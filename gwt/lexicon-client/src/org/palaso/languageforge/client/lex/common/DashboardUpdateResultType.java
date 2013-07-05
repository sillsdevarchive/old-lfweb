package org.palaso.languageforge.client.lex.common;

import java.util.EnumMap;
import java.util.Map;

import org.palaso.languageforge.client.lex.common.IEnum;

public enum DashboardUpdateResultType implements IEnum {

	// DO not change name definition here!!! used for JSON mapping
	STANDBY("0"), RUNNING("1"), UPDATED("2"), NOCHANGE("3");

	private String value;
	private static Map<DashboardUpdateResultType, String> table = new EnumMap<DashboardUpdateResultType, String>(
			DashboardUpdateResultType.class);

	// Initialize
	static {
		for (DashboardUpdateResultType value : DashboardUpdateResultType.values())
			table.put(value, value.getValue());
	}

	DashboardUpdateResultType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public static DashboardUpdateResultType getFromValue(String value) {
		if (table.containsValue(value)) {
			for (DashboardUpdateResultType key : DashboardUpdateResultType.values()) {
				if (key.getValue().equals(value)) {
					return key;
				}
			}
		}
		return STANDBY;
	}

}
