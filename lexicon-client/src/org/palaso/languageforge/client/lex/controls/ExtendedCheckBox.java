package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.i18n.shared.DirectionEstimator;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class ExtendedCheckBox extends CheckBox {

	boolean styled = false;
	private Widget oldParent = null;

	private InputElement checkBoxInputElement = null;
	private LabelElement checkBoxLabelElement = null;

	protected interface JsResource extends ClientBundle {
		@Source("ExtendedCheckBox.js")
		TextResource ExtendedCheckBoxJsResource();

	}
//
//	public interface Resources extends ClientBundle {
//		  public static final Resources INSTANCE =
//		      GWT.create(Resources.class);
//		 
//		  @NotStrict
//		  @Source("ExtendedCheckBox.css")
//		  public CssResource css();
//		}
	
	static {
		//Resources.INSTANCE.css().ensureInjected();
		JsResource bundle = GWT.create(JsResource.class);
		JavascriptInjector
				.inject(bundle.ExtendedCheckBoxJsResource().getText());
	}

	public ExtendedCheckBox() {
		super();
		init();
	}

	public ExtendedCheckBox(SafeHtml label) {
		super(label);
		init();
	}

	public ExtendedCheckBox(String label) {
		super(label);
		init();
	}

	public ExtendedCheckBox(String label, DirectionEstimator dir) {
		super(label, dir);
		init();
	}

	public ExtendedCheckBox(String label, Direction dir) {
		super(label, dir);
		init();
	}

	public ExtendedCheckBox(String label, Boolean asHtml) {
		super(label, asHtml);
		init();
	}

	private void init() {
		checkBoxInputElement = (InputElement) this.getElement().getFirstChild();
		checkBoxLabelElement = (LabelElement) this.getElement().getLastChild();
	}

	public void fireClick() 
	{
		fireClickInternal(checkBoxInputElement.getId());
	};
	
	@Override
	public void onLoad() {
		if (!styled) {
			oldParent = this.getParent();
			stylingCheckBox(checkBoxInputElement, checkBoxLabelElement);
			styled = true;
		}
	}

	private static native void stylingCheckBox(InputElement inputElement,
			LabelElement labelElement) /*-{
		$wnd.stylingCheckBox(inputElement, labelElement);
	}-*/;
	
	private static native void fireClickInternal(String checkBoxID) /*-{
		$wnd.rechangeCheckboxes(checkBoxID);
	}-*/;
}
