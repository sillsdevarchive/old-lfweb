package org.palaso.languageforge.client.lex.controls;

import com.google.gwt.event.shared.EventHandler;

public interface TreeItemContextClickHandler extends EventHandler {
    /**
     * Called when a cell receives a contextclick event.
     *
     * @param event the event
     */
    void onTreeItemContextClick(TreeItemContextClickEvent event);
}
