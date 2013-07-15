/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.palaso.languageforge.client.lex.controls.JavascriptInjector;



/**
 * A {@link Cell} used to render a drop-down list.
 */
public class ThemedComboBoxCell extends AbstractInputCell<String, String> {

	interface Template extends SafeHtmlTemplates {
		@Template("<option value=\"{0}\">{0}</option>")
		SafeHtml deselected(String option);

		@Template("<option value=\"{0}\" selected=\"true\" >{0}</option>")
		SafeHtml selected(String option);
	}

	private static Integer refIdIndex = 0;
	private static Template template;

	private HashMap<String, Integer> indexForOption = new HashMap<String, Integer>();

	private final List<String> options;

	protected interface JsResource extends ClientBundle {
		@Source("ThemedComboBoxCell.js")
		TextResource ExtendedComboBoxJsResource();
	}
	

	static {
		//Resources.INSTANCE.css().ensureInjected();
		JsResource bundle = GWT.create(JsResource.class);
		JavascriptInjector
				.inject(bundle.ExtendedComboBoxJsResource().getText());
	}
	
	/**
	 * Construct a new {@link ThemedComboBoxCell} with the specified options.
	 * 
	 * @param options
	 *            the options in the cell
	 */
	public ThemedComboBoxCell(List<String> options) {
		super("change");
		if (template == null) {
			template = GWT.create(Template.class);
		}
		this.options = new ArrayList<String>(options);
		int index = 0;
		for (String option : options) {
			indexForOption.put(option, index++);
		}
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event,
			ValueUpdater<String> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		String type = event.getType();
		if ("change".equals(type)) {
			Object key = context.getKey();
			SelectElement select = parent.getFirstChild().cast();
			String newValue = options.get(select.getSelectedIndex());
			setViewData(key, newValue);
			finishEditing(parent, newValue, key, valueUpdater);
			if (valueUpdater != null) {
				valueUpdater.update(newValue);
			}
		}
	}

	@Override
	public void render(Context context, String value, SafeHtmlBuilder sb) {
		// Get the view data.
		Object key = context.getKey();
		String viewData = getViewData(key);
		if (viewData != null && viewData.equals(value)) {
			clearViewData(key);
			viewData = null;
		}
		String uid="cell_tm_cmb_" +  refIdIndex.toString();
		refIdIndex++;
		int selectedIndex = getSelectedIndex(viewData == null ? value : viewData);
	    sb.appendHtmlConstant("<select class=\"ext_comboboxOuttaHere\" tabindex=\"-1\" id=\""+ uid + "\">");
		int index = 0;
		String selectedOption = "";
		for (String option : options) {
			if (index++ == selectedIndex) {
				selectedOption = option;
				sb.append(template.selected(option));
			} else {
				sb.append(template.deselected(option));
			}
		}
		sb.appendHtmlConstant("</select>");

		sb.appendHtmlConstant("<div style=\"width: 400px;\" class=\"ext_comboboxselectArea gwt-ListBox\" id=\"sarea" + uid +"\">");
		sb.appendHtmlConstant("<span class=\"ext_comboboxleft\"></span>");
		sb.appendHtmlConstant("<span class=\"disabled\" style=\"display: none;\"></span>");
		sb.appendHtmlConstant("<span id=\"mySelectText" + uid +"\" class=\"ext_comboboxcenter\">" + selectedOption
				+ "</spahttp://ww1.sinaimg.cn/bmiddle/a03ee9f2gw1e1q2bg8p1kj.jpgn>");
		sb.appendHtmlConstant("<a href=\"javascript:cell_showOptions('" + uid +"',false)\" class=\"ext_comboboxselectButton\"></a>");
		sb.appendHtmlConstant("</div>");

		getOptionBox(sb, uid);

	}

	private void getOptionBox(SafeHtmlBuilder sb, String uid) {
		sb.appendHtmlConstant("<div style=\"width: auto; margin-top: 40px;\" class=\"ext_comboboxoptionsDivInvisible ext_comboboxdrop-gwt-ListBox\" id=\"optionsDiv" + uid +"\">");
		sb.appendHtmlConstant("<div class=\"ext_comboboxselect-top\">");
		sb.appendHtmlConstant("<div class=\"ext_comboboxselect-top-left\"></div>");
		sb.appendHtmlConstant("<div class=\"ext_comboboxselect-top-right\"></div>");
		sb.appendHtmlConstant("</div>");
		sb.appendHtmlConstant("<div class=\"ext_comboboxselect-center\">");
		sb.appendHtmlConstant("<div class=\"ext_comboboxselect-center-right\"></div>");
		sb.appendHtmlConstant("<ul id=\"optionsULTag" + uid +"\">");
		int index = 0;
		for (String option : options) {
			sb.appendHtmlConstant("<li><a href=\"javascript:cell_showOptions('" + uid +"'); cell_selectMe('" + uid +"'," + index
					+ ",'" + uid +"');\">" + option + "</a></li>");
			index++;
		}
		sb.appendHtmlConstant("</div>");
		sb.appendHtmlConstant("<div class=\"ext_comboboxselect-bottom\">");
		sb.appendHtmlConstant("<div class=\"ext_comboboxselect-bottom-left\"></div>");
		sb.appendHtmlConstant("<div class=\"ext_comboboxselect-bottom-right\"></div>");
		sb.appendHtmlConstant("</div>");
		sb.appendHtmlConstant("</div>");
	}

	private int getSelectedIndex(String value) {
		Integer index = indexForOption.get(value);
		if (index == null) {
			return -1;
		}
		return index.intValue();
	}
}
