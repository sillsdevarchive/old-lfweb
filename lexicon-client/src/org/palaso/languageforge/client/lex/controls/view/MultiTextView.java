package org.palaso.languageforge.client.lex.controls.view;

import java.util.HashMap;

import org.palaso.languageforge.client.lex.controls.presenter.MultiTextPresenter;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.Controls;
import com.github.gwtbootstrap.client.ui.InputAddOn;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class MultiTextView extends Composite implements
		MultiTextPresenter.IMultiTextView {

	private FlowPanel flowPanel;
	HashMap<String, TextBox> textsHashMap = new HashMap<String, TextBox>();
	HashMap<String, Button> commentsButtonsMap = new HashMap<String, Button>();

	public MultiTextView() {
		flowPanel = new FlowPanel();
		initWidget(flowPanel);
	}

	public Widget getWidget() {
		return this;
	}

	@Override
	public void addNewTextPanel(String form, String value, String label,
			boolean editable, boolean showCommentBtn) {
		ControlGroup group = createTextPanel(form, value, label, editable, showCommentBtn);
		flowPanel.add(group);
	}

	@Override
	public String getText(String form) {
		TextBox value = textsHashMap.get(form);
		if (value == null) {
			return null;
		} else {
			return value.getText();
		}
	}

	@Override
	public HandlerRegistration addBlurHandler(String form, BlurHandler handler) {
		return textsHashMap.get(form).addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addCommentClickHandler(String form,
			ClickHandler handler, String refId) {
		setCommentButtonRefId(form, refId);
		return commentsButtonsMap.get(form).addClickHandler(handler);
	}

	private ControlGroup createTextPanel(String form, String value,
			String label, boolean editable, boolean showCommentBtn) {
		ControlGroup group = new ControlGroup();
		Controls controls = new Controls();
		group.add(controls);

		TextBox textBox = new TextBox();
		textBox.setValue(value);
		textBox.setWidth("100%");
		FlowPanel panel = new FlowPanel();
		panel.setStyleName("multiTextRow");
		panel.add(textBox);
		textsHashMap.put(form, textBox);
		if (!editable) {
			textBox.setReadOnly(true);
		}

		InputAddOn inputAddOn = new InputAddOn();
		inputAddOn.setPrependText(label);
		controls.add(inputAddOn);
		inputAddOn.add(textBox);


		Button btnComment = new Button();
		commentsButtonsMap.put(form, btnComment);
		btnComment.setIcon(IconType.COMMENTS);
		
		if (showCommentBtn){
			inputAddOn.addAppendWidget(btnComment);
			btnComment.getParent().setStyleName("");
		}
		if (!editable) {
			textBox.setReadOnly(true);
			btnComment.setEnabled(false);
		}
		return group;
	}

	// private FlowPanel createTextReadOnlyPanel(String form, String value) {
	// FlowPanel panel = new FlowPanel();
	//
	// InlineLabel inlineLabel = new InlineLabel(value + " ");
	// panel.add(inlineLabel);
	//
	// return panel;
	// }

	@Override
	public void clear() {
		flowPanel.clear();
	}

	@Override
	public void setTextBoxesEnabled(boolean enabled) {
		for (TextBox textbox : textsHashMap.values()) {
			textbox.setEnabled(enabled);
		}

		for (Button btn : commentsButtonsMap.values()) {
			btn.setEnabled(enabled);
		}
	}

	@Override
	public void setCommentButtonRefId(String form, String refId) {
		commentsButtonsMap.get(form).setTarget(refId + "+" + form);
	}

}
