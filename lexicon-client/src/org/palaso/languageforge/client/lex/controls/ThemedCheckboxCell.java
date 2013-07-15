package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

/**
 * A {@link Cell} used to render a checkbox. The value of the checkbox may be
 * toggled using the ENTER key as well as via mouse click.
 */
public class ThemedCheckboxCell extends AbstractEditableCell<Boolean, Boolean> {

	private static String imgCheckbox_Checked;
	private static String imgCheckbox_Unchecked;
	static {
		imgCheckbox_Checked =  GWT.getModuleBaseURL() +"/images/checkbox_checked.png";
		imgCheckbox_Unchecked =  GWT.getModuleBaseURL() +"/images/checkbox_unchecked.png";
	}

	
	/**
	 * An html string representation of a checked input box.
	 */
	private static final SafeHtml INPUT_CHECKED = SafeHtmlUtils
			.fromSafeConstant("<div style=\"background: url(" + imgCheckbox_Checked + ") no-repeat 0 0;height: 20px; width: 20px; margin: 0pt auto;\"></div>");

	/**
	 * An html string representation of an unchecked input box.
	 */
	private static final SafeHtml INPUT_UNCHECKED = SafeHtmlUtils
			.fromSafeConstant("<div style=\"background: url(" + imgCheckbox_Unchecked + ") no-repeat 0 0;height: 20px; width: 20px; margin: 0pt auto;\"></div>");

	/**
	 * Construct a new {@link CheckboxCell}.
	 */
	public ThemedCheckboxCell() {
		super("change", "keydown", "click");
	}

	@Override
	public boolean dependsOnSelection() {
		return true;
	}

	@Override
	public boolean handlesSelection() {
		return true;
	}

	@Override
	public boolean isEditing(Context context, Element parent, Boolean value) {
		// A checkbox is never in "edit mode". There is no intermediate state
		// between checked and unchecked.
		return false;
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, Boolean value,
			NativeEvent event, ValueUpdater<Boolean> valueUpdater) {
		String type = event.getType();

		if ("click".equals(type)) {
			value = !value;
			setValue(context, parent, value);

			if (valueUpdater != null) {
				valueUpdater.update(value);
			}
		}
	}

	@Override
	public void render(Context context, Boolean value, SafeHtmlBuilder sb) {
		if (value) {
			sb.append(INPUT_CHECKED);
		} else {
			sb.append(INPUT_UNCHECKED);
		}
	}
}
