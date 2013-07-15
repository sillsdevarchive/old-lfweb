package org.palaso.languageforge.client.lex.controls.conversation;

import com.google.gwt.event.shared.EventHandler;

public interface ConversationPostClickHandler extends EventHandler {

    void onNewPostClicked(ConversationPostEvent event);
}
