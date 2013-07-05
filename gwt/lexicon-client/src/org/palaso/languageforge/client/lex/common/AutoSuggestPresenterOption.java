package org.palaso.languageforge.client.lex.common;

/**
 * Bean for name-value pairs
 */

public class AutoSuggestPresenterOption {
	private String name;
	private Object value;

	/**
	 * No argument constructor
	 */
	public AutoSuggestPresenterOption() {
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
