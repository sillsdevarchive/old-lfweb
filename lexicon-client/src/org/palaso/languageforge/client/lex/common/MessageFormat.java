package org.palaso.languageforge.client.lex.common;

/**
 * Limited implementation of MessageFormat using JSNI. Only supports string
 * replacement, and not date or number formatting for arguments.
 * 
 * @author <a href="mailto:nicolas@apache.org">Nicolas De loof</a>
 */
public class MessageFormat extends Format {

	private static final long serialVersionUID = 1L;
	private String pattern;

	public MessageFormat(String pattern) {
		applyPattern(pattern);
	}

	public void applyPattern(String pattern) {
		this.pattern = pattern;
	}

	public static String format(String pattern, Object... arguments) {
		return doFormat(pattern, arguments);
	}

	public final String format(Object obj) {
		if (obj instanceof Object[]) {
			return doFormat(pattern, (Object[]) obj);
		}
		return doFormat(pattern, new Object[] { obj });
	}

	private static String doFormat(String s, Object[] arguments) {
		// A very simple implementation of format
		int i = 0;
		while (i < arguments.length) {
			String delimiter = "{" + i + "}";
			while (s.contains(delimiter)) {
				s = s.replace(delimiter, String.valueOf(arguments[i]));
			}
			i++;
		}
		return s;
	}
}
