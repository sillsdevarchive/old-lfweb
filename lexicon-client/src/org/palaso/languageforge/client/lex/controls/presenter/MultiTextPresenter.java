package org.palaso.languageforge.client.lex.controls.presenter;

import java.util.HashMap;

import org.palaso.languageforge.client.lex.common.PermissionManager;
import org.palaso.languageforge.client.lex.common.enums.DomainPermissionType;
import org.palaso.languageforge.client.lex.common.enums.OperationPermissionType;
import org.palaso.languageforge.client.lex.model.MultiText;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class MultiTextPresenter extends ModelPairBase<MultiTextPresenter.IMultiTextView, MultiText> {

	MultiText labels = null;

	public interface IMultiTextView {
		void addNewTextPanel(String form, String value, String label, boolean editable);

		Widget getWidget();

		String getText(String language);

		HandlerRegistration addBlurHandler(String form, BlurHandler handler);

		void setTextBoxesEnabled(boolean enabled);

		void clear();

	}

	HashMap<String, HandlerRegistration> handlers = new HashMap<String, HandlerRegistration>();
	protected boolean isTextboxEditable = true;

	public MultiTextPresenter(IMultiTextView view, MultiText model, MultiText labels, boolean editable) {
		super(view, model);
		this.labels = labels;
		isTextboxEditable = editable;
		populateView();
	}

	public MultiTextPresenter(IMultiTextView view, MultiText model, MultiText labels) {
		super(view, model);
		this.labels = labels;
		isTextboxEditable = true;
		populateView();
	}

	// public MultiTextPresenter(IMultiTextView view, MultiText model,boolean
	// editable) {
	// super(view, model);
	// isTextboxEditable = editable;
	// populateView();
	// }
	//
	// public MultiTextPresenter(IMultiTextView view, MultiText model) {
	// super(view, model);
	// isTextboxEditable = true;
	// populateView();
	// }
	public void addBlurHandler(BlurHandler handler) {
		JsArrayString keys = model.keys();
		for (int i = 0, n = keys.length(); i < n; ++i) {
			String language = keys.get(i);
			handlers.put(language, view.addBlurHandler(language, handler));
		}
	}

	public void removeBlurHandler() {
		for (String language : handlers.keySet()) {
			handlers.get(language).removeHandler();
		}
		handlers.clear();
	}

	protected void populateView() {
		JsArrayString keys = model.keys();
		for (int i = 0, n = keys.length(); i < n; ++i) {
			String language = keys.get(i);
			String value = model.value(language);
			addTextRow(language, value);
		}
		// for those Key not in model, but in field settings (newly added)
		for (int i = 0, n = labels.keys().length(); i < n; ++i) {
			String language = labels.keys().get(i);
			if (model.value(language) == null) {
				addTextRow(language, "");
			}
		}
	}

	private void addTextRow(String language, String value) {
		if (PermissionManager.getPermission(DomainPermissionType.DOMAIN_LEX_ENTRY, OperationPermissionType.CAN_EDIT_OTHER)) {
			if (labels != null && labels.value(language) != null) {
				view.addNewTextPanel(language, value, labels.value(language), isTextboxEditable);
			} else {
				view.addNewTextPanel(language, value, language, isTextboxEditable);
			}
		} else {
			if (labels != null && labels.value(language) != null) {
				view.addNewTextPanel(language, value, labels.value(language), false);
			} else {
				view.addNewTextPanel(language, value, language, false);
			}
		}
	}

	public void updateModel() {
		JsArrayString keys = model.keys();
		for (int i = 0, n = keys.length(); i < n; ++i) {
			String language = keys.get(i);
			String value = view.getText(language);
			model.setValue(language, value);
		}
		// for those Key not in model, but in field settings (newly added)
		for (int i = 0, n = labels.keys().length(); i < n; ++i) {
			String language = labels.keys().get(i);
			if (model.value(language) == null) {
				String value = view.getText(language);
				model.setValue(language, value);
			}
		}
	}

	public void setEnabled(boolean enabled) {
		isTextboxEditable = enabled;
		view.setTextBoxesEnabled(enabled);
	}
}
