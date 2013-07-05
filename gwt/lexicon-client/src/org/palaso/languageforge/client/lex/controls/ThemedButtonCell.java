package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;

/**
 * A {@link Cell} used to render a button.
 */
public class ThemedButtonCell extends AbstractSafeHtmlCell<String> {

	/**
	 * Construct a new ButtonCell that will use a {@link SimpleSafeHtmlRenderer}
	 * .
	 */
	public ThemedButtonCell() {
		this(SimpleSafeHtmlRenderer.getInstance());
	}

	/**
	 * Construct a new ButtonCell that will use a given {@link SafeHtmlRenderer}
	 * .
	 * 
	 * @param renderer
	 *            a {@link SafeHtmlRenderer SafeHtmlRenderer<String>} instance
	 */
	public ThemedButtonCell(SafeHtmlRenderer<String> renderer) {
		super(renderer, "click", "keydown");
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, String value,
			NativeEvent event, ValueUpdater<String> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		if ("click".equals(event.getType())) {
			EventTarget eventTarget = event.getEventTarget();
			if (!Element.is(eventTarget)) {
				return;
			}
			if (parent.getFirstChildElement().isOrHasChild(
					Element.as(eventTarget))) {
				// Ignore clicks that occur outside of the main element.
				onEnterKeyDown(context, parent, value, event, valueUpdater);
			}
		}
	}

	@Override
	public void render(Context context, SafeHtml data, SafeHtmlBuilder sb) {
		sb.appendHtmlConstant("<button class=\"gwt-Button\" type=\"button\" tabindex=\"-1\" style=\"float:none;\" >");
		if (data != null) {
			sb.append(data);
		}
		sb.appendHtmlConstant("</button>");
	}

	@Override
	protected void onEnterKeyDown(Context context, Element parent,
			String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
		if (valueUpdater != null) {
			valueUpdater.update(value);
		}
	}
}
