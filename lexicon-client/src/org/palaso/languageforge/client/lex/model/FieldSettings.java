package org.palaso.languageforge.client.lex.model;


public class FieldSettings extends JsDictionaryString<FieldSettingsEntry> {

	protected FieldSettings() {
	}

	public native final static FieldSettings fromWindow() /*-{
		return $wnd.fieldSettings;
	}-*/;

	public native final static FieldSettings fromWindowForAddMeaning() /*-{
		return $wnd.fieldSettingsForAddMeaning;
	}-*/;

	public native final static FieldSettings fromWindowForAddPartOfSpeech() /*-{
		return $wnd.fieldSettingsForAddPOS;
	}-*/;

	public native final static FieldSettings fromWindowForAddExample() /*-{
		return $wnd.fieldSettingsForAddExample;
	}-*/;

	public native final static FieldSettings fromWindowForrGatherWordFromList() /*-{
		return $wnd.fieldSettingsForGatherWordFromList;
	}-*/;

	public native final static FieldSettings fromWindowForrGatherWordFromSemanticDomain() /*-{
		return $wnd.fieldSettingsForGatherWordFromSemanticDomain;
	}-*/;
	
	public static final native void applyToCurrentUserForWindow(FieldSettings object) /*-{
		$wnd.fieldSettings = object;
	}-*/;

}
