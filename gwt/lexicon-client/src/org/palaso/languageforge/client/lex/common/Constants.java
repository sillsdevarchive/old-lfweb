package org.palaso.languageforge.client.lex.common;

import org.palaso.languageforge.client.lex.model.FieldSettings;

public final class Constants {

	public static final String LEX_API = "LexAPI.php";
	public static final String LEX_API_PATH = "/api/lex/";
	
	public static final int PRE_LOAD_PAGES = 3;
	
	public static final int VISIBLE_WORDS_PER_PAGE_COUNT = 20;
	public static final int SUGGEST_BOX_PAGE_SIZE = 10;
	public static final int SUGGEST_BOX_DELAY = 500;
	public static final int SUGGEST_BOX_MINIMUM_CHAR = 3;
	public static final Long CACHE_LIFE_TIME = 5l * 60l * 1000l; // 5 Minutes
	
	public static final int SUGGEST_BOX_MAX_RECORD_FROM_SERVER = 100;

	public static final String URL_IPA = "http://en.wikipedia.org/wiki/International_Phonetic_Alphabet";

	public static final String URL_LANGUAGE_TAGS = "http://www.w3.org/International/questions/qa-choosing-language-tags";

	public static final String URL_ISO_639_1 = "http://www.infoterm.info/standardization/iso_639_1_2002.php";

	public static final String URL_ISO_639_3 = "http://www.sil.org/iso639-3/";

	public static final String SEMANTIC_DOMAIN_QUESTIONS_FILE_PREFIX = "LocalizedLists-";

	public static String getFirstWordAbbreviation() {
		return FieldSettings.fromWindow().value("Word").getAbbreviations()
				.get(0);
	}
	/**
	 * 
	 * @return BCP47 code
	 */
	public static String getFirstWordType() {
		return FieldSettings.fromWindow().value("Word").getLanguages()
				.get(0);
	}
	
}
