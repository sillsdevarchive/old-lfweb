package org.palaso.languageforge.client.lex.common.enums;

/**
 * An enum for the missing info type
 * This enum should alway match with MissingInfoType.php
 */
public enum MissinginfoType implements IEnum {

	UNDEFINED("UNDEFINED"), 
	MEANING("MEANING"), 
	GRAMMATICAL("GRAMMATICAL"),
	EXAMPLE("EXAMPLE");

	private String value;

	MissinginfoType(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
