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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasAllKeyHandlers;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.FocusImpl;

/**
 * A standard hierarchical tree widget. The tree contains a hierarchy of
 * {@link FastTreeItem}s.
 * 
 * Explicitly call FastTree.addDefaultCSS() to include the default style sheet.
 * 
 * <p>
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.gwt-FastTree { the tree itself }</li>
 * <li>.gwt-FastTree .gwt-FastTreeItem { a tree item }</li>
 * <li>.gwt-FastTree .selection-bar {the selection bar used to highlight the
 * selected tree item}</li>
 * </ul>
 */
public class FastTree extends Panel implements HasWidgets, Focusable,
		HasAllFocusHandlers, HasFastTreeItems, HasAllMouseHandlers,
		HasAllKeyHandlers {

	private static final String STYLENAME_DEFAULT = "gwt-FastTree";

	private static final String STYLENAME_SELECTION = "selection-bar";

	private static FocusImpl impl = FocusImpl.getFocusImplForPanel();

	private static boolean hasModifiers(Event event) {
		boolean alt = event.getAltKey();
		boolean ctrl = event.getCtrlKey();
		boolean meta = event.getMetaKey();
		boolean shift = event.getShiftKey();

		return alt || ctrl || meta || shift;
	}

	private boolean lostMouseDown = true;
	/**
	 * Map of TreeItem.widget -> TreeItem.
	 */
	private final Map<Widget, FastTreeItem> childWidgets = new HashMap<Widget, FastTreeItem>();
	private FastTreeItem curSelection;
	private final Element focusable;
	private final FastTreeItem root;
	private Event keyDown;

	private Event lastKeyDown;
	private final PopupPanel popupPanel = new PopupPanel(true);

	private TreeItemContextClickHandler contextClickHandler;

	private TreeItemSelectedHandler itemSelectedHandler;

	/**
	 * Constructs a tree.
	 */
	public FastTree() {
		popupPanel.addStyleName("popup");
		setElement(DOM.createDiv());
		focusable = createFocusElement();
		setStyleName(focusable, STYLENAME_SELECTION);

		sinkEvents(Event.MOUSEEVENTS | Event.ONCLICK | Event.KEYEVENTS
				| Event.ONMOUSEUP | Event.ONMOUSEDOWN | Event.ONCONTEXTMENU);

		// The 'root' item is invisible and serves only as a container
		// for all top-level items.
		root = new FastTreeItem() {
			@Override
			public void addItem(FastTreeItem item) {
				super.addItem(item);

				DOM.appendChild(FastTree.this.getElement(), item.getElement());

				// Explicitly set top-level items' parents to null.
				item.setParentItem(null);

				// Use no margin on top-most items.
				DOM.setIntStyleAttribute(item.getElement(), "margin", 0);
			}

			@Override
			public void removeItem(FastTreeItem item) {
				if (!getChildren().contains(item)) {
					return;
				}

				// Update Item state.
				item.clearTree();
				item.setParentItem(null);
				getChildren().remove(item);

				DOM.removeChild(FastTree.this.getElement(), item.getElement());
			}
		};
		root.setTree(this);

		setStyleName(STYLENAME_DEFAULT);
		moveSelectionBar(curSelection);
	}

	/**
	 * Adds the widget as a root tree item.
	 * 
	 * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
	 * @param widget
	 *            widget to add.
	 */
	@Override
	public void add(Widget widget) {
		addItem(widget);
	}

	/**
	 * Adds an item to the root level of this tree.
	 * 
	 * @param item
	 *            the item to be added
	 */
	public void addItem(FastTreeItem item) {
		root.addItem(item);
	}

	/**
	 * Adds a simple tree item containing the specified text.
	 * 
	 * @param itemText
	 *            the text of the item to be added
	 * @return the item that was added
	 */
	public FastTreeItem addItem(String itemText) {
		FastTreeItem ret = new FastTreeItem(itemText);
		addItem(ret);

		return ret;
	}

	/**
	 * Adds a new tree item containing the specified widget.
	 * 
	 * @param widget
	 *            the widget to be added
	 */
	public FastTreeItem addItem(Widget widget) {
		return root.addItem(widget);
	}

	/**
	 * Clears all tree items from the current tree.
	 */
	@Override
	public void clear() {
		int size = root.getChildCount();
		for (int i = size - 1; i >= 0; i--) {
			root.getChild(i).remove();
		}
	}

	/**
	 * Ensures that the currently-selected item is visible, opening its parents
	 * and scrolling the tree as necessary.
	 */
	public void ensureSelectedItemVisible() {
		if (curSelection == null) {
			return;
		}

		FastTreeItem parent = curSelection.getParentItem();
		while (parent != null) {
			parent.setState(true);
			parent = parent.getParentItem();
		}
		moveFocus(curSelection);
	}

	public FastTreeItem getChild(int index) {
		return root.getChild(index);
	}

	public int getChildCount() {
		return root.getChildCount();
	}

	public int getChildIndex(FastTreeItem child) {
		return root.getChildIndex(child);
	}

	/**
	 * Gets the top-level tree item at the specified index.
	 * 
	 * @param index
	 *            the index to be retrieved
	 * @return the item at that index
	 */
	public FastTreeItem getItem(int index) {
		return root.getChild(index);
	}

	public LinkedList<FastTreeItem> getItems() {
		return root.getChildren();
	}

	/**
	 * Gets the number of items contained at the root of this tree.
	 * 
	 * @return this tree's item count
	 */
	public int getItemCount() {
		return root.getChildCount();
	}

	/**
	 * Gets the currently selected item.
	 * 
	 * @return the selected item
	 */
	public FastTreeItem getSelectedItem() {
		return curSelection;
	}

	public int getTabIndex() {
		return impl.getTabIndex(focusable);
	}

	public Iterator<Widget> iterator() {
		final Widget[] widgets = new Widget[childWidgets.size()];
		childWidgets.keySet().toArray(widgets);
		return WidgetIterators.createWidgetIterator(this, widgets);
	}

	public void setContextClickCallbackHandler(
			TreeItemContextClickHandler handler) {
		this.contextClickHandler = handler;
	}

	public void setItemSelectedCallbackHandler(TreeItemSelectedHandler handler) {
		this.itemSelectedHandler = handler;
	}

	@Override
	@SuppressWarnings("fallthrough")
	public void onBrowserEvent(Event event) {
		int eventType = DOM.eventGetType(event);

		if (event.getButton() == Event.BUTTON_RIGHT) {
			DOM.eventCancelBubble(event, true);
			DOM.eventPreventDefault(event);
		}

		switch (eventType) {
		case Event.ONCLICK: {
			Element e = DOM.eventGetTarget(event);
			if (shouldTreeDelegateFocusToElement(e)) {
				// The click event should have given focus to this element
				// already.
				// Avoid moving focus back up to the tree (so that focusable
				// widgets
				// attached to TreeItems can receive keyboard events).
			} else {
				if (!hasModifiers(event)) {
					clickedOnFocus(DOM.eventGetTarget(event));
				}
			}
			break;
		}

		case Event.ONMOUSEUP: {
			boolean left = event.getButton() == Event.BUTTON_LEFT;

			if (lostMouseDown) {
				// artificial mouse down due to IE bug where mouse downs are
				// lost.

				if (left && !hasModifiers(event)) {
					elementClicked(root, event);
				}

				if (event.getButton() == Event.BUTTON_RIGHT) {
					if (contextClickHandler != null) {

						Element target = DOM.eventGetTarget(event);
						ArrayList<Element> chain = new ArrayList<Element>();
						collectElementChain(chain, getElement(), target);
						FastTreeItem item = findItemByChain(chain, 0, root);
						if (item != null && item != root) {

							onSelection(item, false, true);

							DOM.eventCancelBubble(event, true);
							DOM.eventPreventDefault(event);
							int x = DOM.eventGetClientX(event);
							int y = DOM.eventGetClientY(event);

							TreeItemContextClickEvent contextEvent = new TreeItemContextClickEvent(
									item, popupPanel);
							contextClickHandler
									.onTreeItemContextClick(contextEvent);

							popupPanel.setPopupPosition(x, y);
							popupPanel.show();
							break;
						}
					}
				}
			}

			lostMouseDown = true;
			break;
		}
		case Event.ONMOUSEDOWN: {
			boolean left = event.getButton() == Event.BUTTON_LEFT;

			lostMouseDown = false;
			if (left && !hasModifiers(event)) {
				elementClicked(root, event);
			}
			if (!left) {
				lostMouseDown = true;
			}
			break;
		}

		case Event.ONKEYDOWN:
			keyDown = event;
			// Intentional fallthrough.
		case Event.ONKEYUP:
			if (eventType == Event.ONKEYUP) {
				// If we got here because of a key tab, then we need to make
				// sure the
				// current tree item is selected.
				if (DOM.eventGetKeyCode(event) == KeyCodes.KEY_TAB) {
					ArrayList<Element> chain = new ArrayList<Element>();
					collectElementChain(chain, getElement(),
							DOM.eventGetTarget(event));
					FastTreeItem item = findItemByChain(chain, 0, root);
					if (item != getSelectedItem()) {
						setSelectedItem(item, true);
					}
				}
			}

		case Event.ONCONTEXTMENU: {
			break;
		}
		// Intentional fall through.
		case Event.ONKEYPRESS: {

			if (hasModifiers(event)) {
				break;
			}

			// Trying to avoid duplicate key downs and fire navigation despite
			// missing key downs.
			if (eventType != Event.ONKEYUP) {
				if (lastKeyDown == null || (!lastKeyDown.equals(keyDown))) {
					keyboardNavigation(event);
				}
				if (eventType == Event.ONKEYPRESS) {
					lastKeyDown = null;
				} else {
					lastKeyDown = keyDown;
				}
			}
			if (DOMHelper.isArrowKey(DOM.eventGetKeyCode(event))) {
				DOM.eventCancelBubble(event, true);
				DOM.eventPreventDefault(event);
			}
			break;
		}
		}

		// We must call SynthesizedWidget's implementation for all other events.
		super.onBrowserEvent(event);
	}

	@Override
	public boolean remove(Widget w) {
		// Validate.
		FastTreeItem item = childWidgets.get(w);
		if (item == null) {
			return false;
		}

		// Delegate to TreeItem.setWidget, which performs correct removal.
		item.setWidget(null);
		return true;
	}

	/**
	 * Removes an item from the root level of this tree.
	 * 
	 * @param item
	 *            the item to be removed
	 */
	public void removeItem(FastTreeItem item) {
		root.removeItem(item);
	}

	/**
	 * Removes all items from the root level of this tree.
	 */
	public void removeItems() {
		while (getItemCount() > 0) {
			removeItem(getItem(0));
		}
	}

	public void setAccessKey(char key) {
		impl.setAccessKey(focusable, key);
	}

	public void setFocus(boolean focus) {
		if (focus) {
			impl.focus(focusable);
		} else {
			impl.blur(focusable);
		}
	}

	/**
	 * Selects a specified item.
	 * 
	 * @param item
	 *            the item to be selected, or <code>null</code> to deselect all
	 *            items
	 */
	public void setSelectedItem(FastTreeItem item) {
		setSelectedItem(item, true);
	}

	/**
	 * Selects a specified item.
	 * 
	 * @param item
	 *            the item to be selected, or <code>null</code> to deselect all
	 *            items
	 * @param fireEvents
	 *            <code>true</code> to allow selection events to be fired
	 */
	public void setSelectedItem(FastTreeItem item, boolean fireEvents) {
		if (item == null) {
			if (curSelection == null) {
				return;
			}
			curSelection.setSelection(false, fireEvents);
			curSelection = null;
			return;
		}

		onSelection(item, fireEvents, true);
	}

	public void setTabIndex(int index) {
		impl.setTabIndex(focusable, index);
	}

	/**
	 * Iterator of tree items.
	 */
	public Iterator<FastTreeItem> treeItemIterator() {
		List<FastTreeItem> accum = new ArrayList<FastTreeItem>();
		root.dumpTreeItems(accum);
		return accum.iterator();
	}

	@Override
	protected void doAttachChildren() {
		super.doAttachChildren();
		DOM.setEventListener(focusable, this);
	}

	@Override
	protected void doDetachChildren() {
		super.doDetachChildren();
		DOM.setEventListener(focusable, null);
	}

	protected FastTreeItem getRoot() {
		return root;
	}

	protected void keyboardNavigation(Event event) {
		// If nothing's selected, select the first item.
		if (curSelection == null) {
			if (root.getChildCount() > 0) {
				onSelection(root.getChild(0), true, true);
			}
			super.onBrowserEvent(event);
		} else {

			// Handle keyboard events if keyboard navigation is enabled

			switch (DOMHelper.standardizeKeycode(DOM.eventGetKeyCode(event))) {
			case KeyCodes.KEY_UP: {
				moveSelectionUp(curSelection);
				break;
			}
			case KeyCodes.KEY_DOWN: {
				moveSelectionDown(curSelection, true);
				break;
			}
			case KeyCodes.KEY_LEFT: {
				if (curSelection.isOpen()) {
					curSelection.setState(false);
				} else {
					FastTreeItem parent = curSelection.getParentItem();
					if (parent != null) {
						setSelectedItem(parent);
					}
				}
				break;
			}
			case KeyCodes.KEY_RIGHT: {
				if (!curSelection.isOpen()) {
					curSelection.setState(true);
				}
				// Do nothing if the element is already open.
				break;
			}
			}
		}
	}

	/**
	 * Moves the selection bar around the given {@link FastTreeItem}.
	 * 
	 * @param item
	 *            the item to move selection bar to
	 */
	protected void moveSelectionBar(FastTreeItem item) {
		if (item == null || item.isShowing() == false) {
			UIObject.setVisible(focusable, false);
			return;
		}
		// focusable is being used for highlight as well.
		// Get the location and size of the given item's content element
		// relative
		// to the tree.
		//Element selectedElem = item.getContentElem();
		//moveElementOverTarget(focusable, selectedElem);
		UIObject.setVisible(focusable, true);
	}

	@Override
	protected void onLoad() {
		if (getSelectedItem() != null) {
			moveSelectionBar(getSelectedItem());
		}
	}

	protected void onSelection(FastTreeItem item, boolean fireEvents,
			boolean moveFocus) {
		// 'root' isn't a real item, so don't let it be selected
		// (some cases in the keyboard handler will try to do this)
		if (item == root) {
			return;
		}

		if (curSelection == item) {
			return;
		}
		if (curSelection != null) {
			if (curSelection.beforeSelectionLost() == false) {
				return;
			}
//			if (itemSelectedHandler != null) {
//				itemSelectedHandler.onTreeItemSelected(item);
//			}
			curSelection.setSelection(false, fireEvents);
		}

		curSelection = item;

		if (curSelection != null) {
			if (moveFocus) {
				moveFocus(curSelection);
			} else {
				// Move highlight even if we do no not need to move focus.
				moveSelectionBar(curSelection);
			}

			if (itemSelectedHandler != null) {
				itemSelectedHandler.onTreeItemSelected(item);
			}
			// Select the item and fire the selection event.
			curSelection.setSelection(true, fireEvents);
		}
	}

	/**
	 * This method is called immediately before a widget will be detached from
	 * the browser's document.
	 */
	@Override
	protected void onUnload() {
	}

	/**
	 * This is called when a valid selectable element is clicked in the tree.
	 * Subclasses can override this method to decide whether or not FastTree
	 * should keep processing the element clicked. For example, a subclass may
	 * decide to return false for this method if selecting a new item in the
	 * tree is subject to asynchronous approval from other components of the
	 * application.
	 * 
	 * @returns true if element should be processed normally, false otherwise.
	 *          Default returns true.
	 */
	protected boolean processElementClicked(FastTreeItem item) {
		return true;
	}

	void adopt(Widget widget, FastTreeItem treeItem) {
		assert (!childWidgets.containsKey(widget));
		childWidgets.put(widget, treeItem);
		super.adopt(widget);
	}

	/*
	 * This method exists solely to support unit tests.
	 */
	Map<Widget, FastTreeItem> getChildWidgets() {
		return childWidgets;
	}

	void treeOrphan(Widget widget) {
		super.orphan(widget);

		// Logical detach.
		childWidgets.remove(widget);
	}

	private void clickedOnFocus(Element e) {
		// An element was clicked on that is not focusable, so we use the hidden
		// focusable to not shift focus.
		//moveElementOverTarget(focusable, e);
		impl.focus(focusable);
	}

	/**
	 * Collects parents going up the element tree, terminated at the tree root.
	 */
	private void collectElementChain(ArrayList<Element> chain, Element hRoot,
			Element hElem) {
		if ((hElem == null) || hElem.equals(hRoot)) {
			return;
		}

		collectElementChain(chain, hRoot, DOM.getParent(hElem));
		chain.add(hElem);
	}

	private Element createFocusElement() {
		Element e = impl.createFocusable();
		DOM.setStyleAttribute(e, "position", "absolute");
		DOM.appendChild(getElement(), e);
		DOM.sinkEvents(e, Event.FOCUSEVENTS | Event.ONMOUSEDOWN);
		// Needed for IE only
		DOM.setElementAttribute(e, "focus", "false");
		return e;
	}

	/**
	 * Disables the selection text on IE.
	 */
	private native void disableSelection(Element element)
	/*-{
		element.onselectstart = function() {
			return false;
		};
	}-*/;

	private void elementClicked(FastTreeItem root, Event event) {
		Element target = DOM.eventGetTarget(event);
		ArrayList<Element> chain = new ArrayList<Element>();
		collectElementChain(chain, getElement(), target);
		FastTreeItem item = findItemByChain(chain, 0, root);
		if (item != null) {
			if (item.isInteriorNode()
					&& item.getControlElement().equals(target)) {
				item.setState(!item.isOpen(), true);
				moveSelectionBar(curSelection);
				disableSelection(target);
			} else if (processElementClicked(item)) {
				onSelection(item, true,
						!shouldTreeDelegateFocusToElement(target));
			}
		}
	}

	private FastTreeItem findDeepestOpenChild(FastTreeItem item) {
		if (!item.isOpen()) {
			return item;
		}
		return findDeepestOpenChild(item.getChild(item.getChildCount() - 1));
	}

	private FastTreeItem findItemByChain(ArrayList<Element> chain, int idx,
			FastTreeItem root) {
		if (idx == chain.size()) {
			return root;
		}

		Element hCurElem = chain.get(idx);
		for (int i = 0, n = root.getChildCount(); i < n; ++i) {
			FastTreeItem child = root.getChild(i);
			if (child.getElement().equals(hCurElem)) {
				FastTreeItem retItem = findItemByChain(chain, idx + 1,
						root.getChild(i));
				if (retItem == null) {
					return child;
				}
				return retItem;
			}
		}

		return findItemByChain(chain, idx + 1, root);
	}

	private void moveElementOverTarget(Element movable, Element target) {
		int containerTop = getAbsoluteTop();

		int top = DOM.getAbsoluteTop(target) - containerTop;
		int height = DOM.getElementPropertyInt(target, "offsetHeight");

		// Set the element's position and size to exactly underlap the
		// item's content element.

		DOM.setStyleAttribute(movable, "height", height + "px");
		DOM.setStyleAttribute(movable, "top", top + "px");
	}

	/**
	 * Move the tree focus to the specified selected item.
	 * 
	 * @param selection
	 */
	private void moveFocus(FastTreeItem selection) {
		moveSelectionBar(selection);
		DOM.scrollIntoView(focusable);
		Focusable focusableWidget = selection.getFocusableWidget();
		if (focusableWidget != null) {
			focusableWidget.setFocus(true);
		} else {
			// Ensure Focus is set, as focus may have been previously delegated
			// by
			// tree.

			impl.focus(focusable);
		}
	}

	/**
	 * Moves to the next item, going into children as if dig is enabled.
	 */
	private void moveSelectionDown(FastTreeItem sel, boolean dig) {
		if (sel == root) {
			return;
		}
		FastTreeItem parent = sel.getParentItem();
		if (parent == null) {
			parent = root;
		}
		int idx = parent.getChildIndex(sel);

		if (!dig || !sel.isOpen()) {
			if (idx < parent.getChildCount() - 1) {
				onSelection(parent.getChild(idx + 1), true, true);
			} else {
				moveSelectionDown(parent, false);
			}
		} else if (sel.getChildCount() > 0) {
			onSelection(sel.getChild(0), true, true);
		}
	}

	/**
	 * Moves the selected item up one.
	 */
	private void moveSelectionUp(FastTreeItem sel) {
		FastTreeItem parent = sel.getParentItem();
		if (parent == null) {
			parent = root;
		}
		int idx = parent.getChildIndex(sel);

		if (idx > 0) {
			FastTreeItem sibling = parent.getChild(idx - 1);
			onSelection(findDeepestOpenChild(sibling), true, true);
		} else {
			onSelection(parent, true, true);
		}
	}

	private native boolean shouldTreeDelegateFocusToElement(Element elem)
	/*-{
		var name = elem.nodeName;
		return ((name == "SELECT") || (name == "INPUT") || (name == "TEXTAREA")
				|| (name == "OPTION") || (name == "BUTTON") || (name == "LABEL"));
	}-*/;

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return addDomHandler(handler, FocusEvent.getType());
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return addDomHandler(handler, BlurEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		return addDomHandler(handler, MouseDownEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		return addDomHandler(handler, MouseUpEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addDomHandler(handler, MouseOverEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return addDomHandler(handler, MouseMoveEvent.getType());
	}

	@Override
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		return addDomHandler(handler, MouseWheelEvent.getType());
	}

	@Override
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return addDomHandler(handler, KeyUpEvent.getType());
	}

	@Override
	public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
		return addDomHandler(handler, KeyDownEvent.getType());
	}

	@Override
	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return addDomHandler(handler, KeyPressEvent.getType());
	}
}

/**
 * A collection of convenience factories for creating iterators for widgets.
 * This mostly helps developers support {@link HasWidgets} without having to
 * implement their own {@link Iterator}.
 */
class WidgetIterators {

	/**
	 * Wraps an array of widgets to be returned during iteration.
	 * <code>null</code> is allowed in the array and will be skipped during
	 * iteration.
	 * 
	 * @param container
	 *            the container of the widgets in <code>contained</code>
	 * @param contained
	 *            the array of widgets
	 * @return the iterator
	 */
	static final Iterator<Widget> createWidgetIterator(
			final HasWidgets container, final Widget[] contained) {
		return new Iterator<Widget>() {
			int index = -1, last = -1;
			boolean widgetsWasCopied = false;
			Widget[] widgets = contained;

			{
				gotoNextIndex();
			}

			public boolean hasNext() {
				return (index < contained.length);
			}

			public Widget next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				last = index;
				final Widget w = contained[index];
				gotoNextIndex();
				return w;
			}

			public void remove() {
				if (last < 0) {
					throw new IllegalStateException();
				}

				if (!widgetsWasCopied) {
					widgets = copyWidgetArray(widgets);
					widgetsWasCopied = true;
				}

				container.remove(contained[last]);
				last = -1;
			}

			private void gotoNextIndex() {
				++index;
				while (index < contained.length) {
					if (contained[index] != null) {
						return;
					}
					++index;
				}
			}
		};
	}

	private static Widget[] copyWidgetArray(final Widget[] widgets) {
		final Widget[] clone = new Widget[widgets.length];
		for (int i = 0; i < widgets.length; i++) {
			clone[i] = widgets[i];
		}
		return clone;
	}

	private WidgetIterators() {
		// Not instantiable.
	}
}
