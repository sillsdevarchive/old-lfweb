package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * A widget that displays text over a partly filled background
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.rwc-ProgressLabel { primary style }</li>
 * <li>.rwc-ProgressLabel .rwc-ProgressLabel-bar { the actual progress bar }
 * <li>.rwc-ProgressLabel .rwc-ProgressLabel-text { text on the bar }</li>
 * </ul>
 * 
 * @author David
 * 
 */
public class ProgressLabel extends Widget {

	static final String BASE_CLASS = "rwc-ProgressLabel";
	static final String BAR_CLASS = BASE_CLASS + "-bar";
	static final String TEXT_CLASS = BASE_CLASS + "-text";

	private Element barSpan = DOM.createSpan();
	private Element textSpan = DOM.createSpan();

	// private Element baseElement;

	public ProgressLabel() {

		Element baseElement = DOM.createDiv();
		baseElement.setClassName(BASE_CLASS);

		barSpan.setClassName(BAR_CLASS);
		barSpan.setAttribute("style", "width:" + 0 + "%");

		textSpan.setClassName(TEXT_CLASS);
		textSpan.setInnerText("");

		DOM.appendChild(baseElement, textSpan);
		DOM.appendChild(baseElement, barSpan);

		setElement(baseElement);

		setTitle("");
	}

	public void setPercent(int percent) {
		barSpan.setAttribute("style", "width:" + percent + "%");
	}

	public void setText(String text) {
		textSpan.setInnerText(text);
		setTitle(text);
	}

	public ProgressLabel(String text, int percent) {

		Element baseElement = DOM.createDiv();
		baseElement.setClassName(BASE_CLASS);

		barSpan.setClassName(BAR_CLASS);
		barSpan.setAttribute("style", "width:" + percent + "%");

		textSpan.setClassName(TEXT_CLASS);
		textSpan.setInnerText(text);

		DOM.appendChild(baseElement, textSpan);
		DOM.appendChild(baseElement, barSpan);

		setElement(baseElement);

		setTitle(text);
	}
}
