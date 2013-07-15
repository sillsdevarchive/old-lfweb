package org.palaso.social.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

/*
 * TODO: DS 2011-11 class not in use?
 */

// An overlay type
public class SocialData extends JavaScriptObject {

	// Must have protected ctor with zero args
	protected SocialData() {
	}

	// JSNI overlay methods
	public final native JsArrayString getLikes() /*-{
		return this.Likes;
	}-*/;

	public final native JsArrayString getNoLikes() /*-{
		return this.NoLikes;
	}-*/;

	public final native int getScore() /*-{
		return this.Score;
	}-*/;

	public final native JsArray<SocialComments> getComments() /*-{
		return this.NoLikes;
	}-*/;

	// Note, though, that methods aren't required to be JSNI

}
