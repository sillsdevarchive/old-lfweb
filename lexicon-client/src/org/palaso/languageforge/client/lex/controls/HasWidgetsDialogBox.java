package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class HasWidgetsDialogBox extends
		com.google.gwt.user.client.ui.DialogBox implements HasWidgets {
	@Override
	public void add(Widget w) {
		Widget widget = getWidget();
		if (widget != null && widget instanceof HasWidgets) {
			((HasWidgets) getWidget()).add(w);
		} else {
			super.add(w);
		}
	}
}