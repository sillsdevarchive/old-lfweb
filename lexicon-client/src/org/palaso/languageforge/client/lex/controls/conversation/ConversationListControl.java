package org.palaso.languageforge.client.lex.controls.conversation;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class ConversationListControl extends ComplexPanel {

	private Element uListElementRoot = null;
	private String emptyListMessage = "";
	private List<ConversationControl> controlList = new ArrayList<ConversationControl>();


	public ConversationListControl(String emptyListMessage) {
		uListElementRoot = Document.get().createULElement();
		this.emptyListMessage = emptyListMessage;
		setElement(uListElementRoot);
		setStyleName("conversation-panel");
		addStyleName("conversation-panel-no-item");
		uListElementRoot.setInnerText(this.emptyListMessage);
	}

	public ConversationListControl() {
		uListElementRoot = Document.get().createULElement();
		this.emptyListMessage = "";
		setElement(uListElementRoot);
		setStyleName("conversation-panel");
		addStyleName("conversation-panel-no-item");
	}

	@Override
	public void add(Widget w) {
		if (super.getChildren().size() == 0) {
			if (this.getStyleName().indexOf("conversation-panel-no-item") > 0) {
				this.removeStyleName("conversation-panel-no-item");
				uListElementRoot.setInnerText("");
			}
		}
		super.add(w, (com.google.gwt.user.client.Element) uListElementRoot);
		if (w instanceof ConversationControl) {
			controlList.add((ConversationControl) w);
		}
	}

	public void addItem(ConversationControl item) {
		if (super.getChildren().size() == 0) {
			if (this.getStyleName().indexOf("conversation-panel-no-item") > 0) {
				this.removeStyleName("conversation-panel-no-item");
				uListElementRoot.setInnerText("");
			}
		}
		super.add(item, (com.google.gwt.user.client.Element) uListElementRoot);
		controlList.add(item);
	}

	public void setAllExpended(boolean isAllExpended) {
		for (ConversationControl item : controlList) {
			item.setExpended(isAllExpended);
		}
	}

	public void setEmptyListMessage(String message)
	{
		this.emptyListMessage=message;
		if (super.getChildren().size() == 0) {
			uListElementRoot.setInnerText(this.emptyListMessage);
		}
	}
	
	@Override
	public void clear() {
		controlList.clear();
		super.clear();
		if (this.getStyleName().indexOf("conversation-panel-no-item") <= 0) {
			this.addStyleName("conversation-panel-no-item");
			uListElementRoot.setInnerText(this.emptyListMessage);
		}
	}

}
