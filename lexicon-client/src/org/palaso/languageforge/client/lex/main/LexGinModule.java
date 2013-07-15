package org.palaso.languageforge.client.lex.main;

import org.palaso.languageforge.client.lex.main.service.ILexService;
import org.palaso.languageforge.client.lex.main.service.LexService;

import com.google.gwt.inject.client.AbstractGinModule;

public class LexGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		// bind(ILexService.class).to(LexServiceTest.class);
		bind(ILexService.class).to(LexService.class);
	}

}
