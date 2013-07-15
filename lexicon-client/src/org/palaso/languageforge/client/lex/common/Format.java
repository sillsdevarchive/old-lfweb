package org.palaso.languageforge.client.lex.common;

import java.io.Serializable;


public abstract class Format implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract String format (Object obj);
}

