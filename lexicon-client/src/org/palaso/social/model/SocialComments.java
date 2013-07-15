package org.palaso.social.model;

import com.google.gwt.core.client.JavaScriptObject;


/*
 * TODO: DS 2011-11 class not in use?
 */

// An overlay type
public class SocialComments extends JavaScriptObject {

	// Must have protected ctor with zero args
	protected SocialComments() {
	}

	// JSNI overlay methods
	public final native String getAuthor() /*-{
		return this.a;
	}-*/;

	public final native String getDate() /*-{
		return this.d;
	}-*/;

	public final native String getComment() /*-{
		return this.t;
	}-*/;

	// Note, though, that methods aren't required to be JSNI

}
