package org.palaso.languageforge.client.lex.common;

public class ConsoleLog {
	public native static void log(String message) /*-{
		console.log("- " + message);
	}-*/;
}
