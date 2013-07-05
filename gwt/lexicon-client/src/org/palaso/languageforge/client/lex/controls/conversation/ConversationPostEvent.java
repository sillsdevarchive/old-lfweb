package org.palaso.languageforge.client.lex.controls.conversation;

import com.google.gwt.event.dom.client.ClickEvent;

public class ConversationPostEvent extends ClickEvent {

    private ConversationItem conversationItem =null;
    private ConversationItem rootConversationItem =null;
    
    public ConversationPostEvent(ConversationItem item, ConversationItem rootCommentItem) {
    	conversationItem= item;
    	rootConversationItem=rootCommentItem;
    }
  
    public ConversationItem getConversationItem()
    {
    	return conversationItem;
    }
    
    public ConversationItem getRootConversationItem()
    {
    	return rootConversationItem;
    }
}
