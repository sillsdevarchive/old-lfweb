package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.event.dom.client.ClickEvent;

public class TreeItemSelectedEvent extends ClickEvent {

    private FastTreeItem treeItem=null;


    public TreeItemSelectedEvent(FastTreeItem item) {
    	treeItem= item;
    }
    
    
    public FastTreeItem getFastTreeItem()
    {
    	return treeItem;
    }
    
}
