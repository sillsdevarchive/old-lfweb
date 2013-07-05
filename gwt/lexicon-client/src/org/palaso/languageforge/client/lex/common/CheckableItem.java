package org.palaso.languageforge.client.lex.common;

import java.util.LinkedList;


public class CheckableItem {
	
	private Object attachedData=null;
	private boolean checked=false;
	private boolean checkable=false;
	private boolean visible=true;
	private CheckableItem parent=null;
	private String displayText="";
	private String toolTip="";
	private LinkedList<CheckableItem> childs= new LinkedList<CheckableItem>();

	public CheckableItem()
	{

	}

	public CheckableItem(boolean checkable)
	{
		this.checkable=checkable;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isCheckable() {
		return checkable;
	}

	public void setCheckable(boolean checkable) {
		this.checkable = checkable;
	}

	public CheckableItem getParent() {
		return parent;
	}

	public void setParent(CheckableItem parent) {
		this.parent = parent;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public LinkedList<CheckableItem> getChilds() {
		return childs;
	}

	public void setChilds(LinkedList<CheckableItem> childs) {
		this.childs = childs;
	}
	
	public void addChild(CheckableItem child) {
		this.childs.add(child);
	}
	
	public void addChild(int index,CheckableItem child) {
		this.childs.add(index,child);
	}

	public Object getData() {
		return attachedData;
	}

	public void setData(Object attachedData) {
		this.attachedData = attachedData;
	}
}
