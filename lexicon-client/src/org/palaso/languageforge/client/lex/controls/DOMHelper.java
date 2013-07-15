/*
 * Copyright 2008 Google Inc.
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

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

/**
 * Useful DOM methods not included in {@link com.google.gwt.user.client.Window}
 * or {@link com.google.gwt.user.client.DOM}.
 * 
 */
public class DOMHelper {

	public static final int OTHER_KEY_UP = 63232;

	public static final int OTHER_KEY_DOWN = 63233;

	public static final int OTHER_KEY_LEFT = 63234;

	public static final int OTHER_KEY_RIGHT = 63235;

	/**
	 * Clones a DOM element.
	 */
	public static native Element clone(Element elem, boolean deep) /*-{
		return elem.cloneNode(deep);
	}-*/;

	/**
	 * Displays a message in a modal dialog box. Should eventually be moved to
	 * Window.
	 * 
	 * @param msg
	 *            the message to be displayed.
	 */
	public static native boolean confirm(String msg) /*-{
		return $wnd.confirm(msg);
	}-*/;

	public static Element getRecursiveFirstChild(Element elem, int index) {
		if (index == 0) {
			return elem;
		} else {
			return getRecursiveFirstChild(DOM.getFirstChild(elem), --index);
		}
	}

	public static boolean hasModifiers(Event event) {
		boolean alt = event.getAltKey();
		boolean ctrl = event.getCtrlKey();
		boolean meta = event.getMetaKey();
		boolean shift = event.getShiftKey();

		return alt || ctrl || meta || shift;
	}

	public static boolean isArrowKey(int code) {
		switch (code) {
		case OTHER_KEY_DOWN:
		case OTHER_KEY_RIGHT:
		case OTHER_KEY_UP:
		case OTHER_KEY_LEFT:
		case KeyCodes.KEY_DOWN:
		case KeyCodes.KEY_RIGHT:
		case KeyCodes.KEY_UP:
		case KeyCodes.KEY_LEFT:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Gets the first child. You must *KNOW* that the first child exists and is
	 * an element to use this method safely.
	 */
	public static native Element rawFirstChild(Element elem) /*-{
		return elem.firstChild;
	}-*/;

	/**
	 * Normalized key codes. Also switches KEY_RIGHT and KEY_LEFT in RTL
	 * languages.
	 */
	public static int standardizeKeycode(int code) {

		switch (code) {
		case OTHER_KEY_DOWN:
			code = KeyCodes.KEY_DOWN;
			break;
		case OTHER_KEY_RIGHT:
			code = KeyCodes.KEY_RIGHT;
			break;
		case OTHER_KEY_UP:
			code = KeyCodes.KEY_UP;
			break;
		case OTHER_KEY_LEFT:
			code = KeyCodes.KEY_LEFT;
			break;
		}
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			if (code == KeyCodes.KEY_RIGHT) {
				code = KeyCodes.KEY_LEFT;
			} else if (code == KeyCodes.KEY_LEFT) {
				code = KeyCodes.KEY_RIGHT;
			}
		}
		return code;
	}
}
