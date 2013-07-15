package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class ExtendedComboBox extends ListBox {

	boolean styled = false;
	boolean isItemUpdating = false;
	private static Integer refIdIndex = 0;
	private Widget oldParent = null;

	protected interface JsResource extends ClientBundle {
		@Source("ExtendedComboBox.js")
		TextResource ExtendedComboBoxJsResource();
	}
	
//	public interface Resources extends ClientBundle {
//		  public static final Resources INSTANCE =
//		      GWT.create(Resources.class);
//		  @NotStrict
//		  @Source("ExtendedComboBox.css")
//		  public CssResource css();
//		}
//	
	static {
		//Resources.INSTANCE.css().ensureInjected();
		JsResource bundle = GWT.create(JsResource.class);
		JavascriptInjector
				.inject(bundle.ExtendedComboBoxJsResource().getText());
	}

	public ExtendedComboBox() {
		super(false);
		this.getElement().setId("ex_cmb_" + refIdIndex.toString());
		refIdIndex++;
	}

	public ExtendedComboBox(boolean NOTINUSE) {
		super(false);
		this.getElement().setId("ex_cmb_" + refIdIndex.toString());
		refIdIndex++;
	}

	@Override
	public void onLoad() {
		if (!styled) {
			oldParent = this.getParent();
			stylingSelect(this.getElement());
			styled = true;
		}
	}

	@Override
	public void clear() {
		super.clear();
		if (styled) {
			optionsChanged(this.getElement().getId());
		}
	}

	@Override
	public void addItem(String item) {
		super.addItem(item);
		if (styled && !isItemUpdating) {
			optionsChanged(this.getElement().getId());
		}
	}

	@Override
	public void addItem(String item, Direction dir) {
		super.addItem(item, dir);
		if (styled && !isItemUpdating) {
			optionsChanged(this.getElement().getId());
		}
	}

	@Override
	public void addItem(String item, String value) {
		super.addItem(item, value);
		if (styled && !isItemUpdating) {
			optionsChanged(this.getElement().getId());
		}
	}

	@Override
	public void addItem(String item, Direction dir, String value) {
		super.addItem(item, dir, value);
		if (styled && !isItemUpdating) {
			optionsChanged(this.getElement().getId());
		}
	}

	@Override
	public void setItemSelected(int index, boolean selected) {
		super.setItemSelected(index, selected);
		if (styled && !isItemUpdating) {
			updateSelectSelectedValue(this.getElement().getId());
		}
	}

	@Override
	public void setSelectedIndex(int index) {
		super.setSelectedIndex(index);
		if (styled && !isItemUpdating) {
			updateSelectSelectedValue(this.getElement().getId());
		}
	}

	public void beginUpdateItem() {
		isItemUpdating = true;
	}

	public void endUpdateItem() {
		isItemUpdating = false;
		if (styled) {
			optionsChanged(this.getElement().getId());
		}
	}

	private static native void stylingSelect(Element selectElement) /*-{
		$wnd.stylingSelect(selectElement);
	}-*/;

	private static native void updateSelectSelectedValue(String objId) /*-{
		$wnd.updateSelectSelectedValue(objId);
	}-*/;

	private static native void optionsChanged(String objId) /*-{
		$wnd.refreshSelectOptions(objId);
	}-*/;

}
