package org.palaso.languageforge.client.lex.common;

import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;

public class CustomUncaughtExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void onUncaughtException(Throwable e) {
		Throwable unwrapped = unwrap(e);
		Window.alert(unwrapped.toString() + " " + getStacktrace(unwrapped));            
	}

	public Throwable unwrap(Throwable e) {
		if (e instanceof UmbrellaException) {
			UmbrellaException ue = (UmbrellaException) e;
			if (ue.getCauses().size() == 1) {
				return unwrap(ue.getCauses().iterator().next());
			}
		}
		return e;
	}

	/**
	 * Note: Doesn't work if Outputstyle is set to Obfuscated when we GWT
	 * compile the Java code. Select "Detailed" for best results.
	 * 
	 * @param throwable
	 * @return
	 */
	public static String getStacktrace(Throwable throwable) {
		StringBuffer result = new StringBuffer();
		StackTraceElement[] elements = throwable.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			StackTraceElement element = elements[i];
			result.append(element.getFileName());
			result.append(" ");
			result.append(element.getClassName());
			result.append(".");
			result.append(element.getMethodName());
			result.append(" line ");
			result.append(element.getLineNumber());
		}
		return result.toString();
	}
}
