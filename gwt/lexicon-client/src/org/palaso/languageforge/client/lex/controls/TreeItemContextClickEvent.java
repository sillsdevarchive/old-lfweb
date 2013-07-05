package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.PopupPanel;

public class TreeItemContextClickEvent extends ClickEvent {

    private FastTreeItem treeItem=null;
    private PopupPanel panel =null;
    private boolean isCancelled =false;

    public TreeItemContextClickEvent(FastTreeItem item, PopupPanel popupPanel) {
    	treeItem= item;
    	panel=popupPanel;
    }
    
    
    public FastTreeItem getFastTreeItem()
    {
    	return treeItem;
    }
    
    public PopupPanel getPopupPanel()
    {
    	return panel;
    }
    
    public void setCancelled(boolean cancelled)
    {
    	isCancelled=cancelled;
    }
    
    public void cancel()
    {
    	isCancelled=true;
    }
    
    public boolean isCancelled()
    {
    	return isCancelled;
    }
}
