package org.palaso.languageforge.client.lex.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean for total size and options
 */
public class AutoSuggestPresenterOptionResultSet {

	/** JSON key for Options */
	public static final String OPTIONS = "Options";
	/** JSON key for DisplayName */
	public static final String DISPLAY_NAME = "DisplayName";
	/** JSON key for Value */
	public static final String VALUE = "Value";

	/** JSON key for the size of the Results */
	public static final String TOTAL_SIZE = "TotalSize";

	private final List<AutoSuggestPresenterOption> options = new ArrayList<AutoSuggestPresenterOption>();
	private int totalSize;

	/**
	 * Constructor. Must pass in theindexTo total size.
	 * 
	 * @param totalSize
	 *            the total size of the template
	 */
	public AutoSuggestPresenterOptionResultSet(int totalSize) {
		setTotalSize(totalSize);
	}

	/**
	 * Add an option
	 * 
	 * @param option
	 *            - the Option to add
	 */
	public void addOption(AutoSuggestPresenterOption option) {
		options.add(option);
	}

	/**
	 * @return an array of Options
	 */
	public AutoSuggestPresenterOption[] getOptions() {
		return options.toArray(new AutoSuggestPresenterOption[options.size()]);
	}

	/**
	 * @param totalSize
	 *            The totalSize to set.
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * @return Returns the totalSize.
	 */
	public int getTotalSize() {
		return totalSize;
	}

}