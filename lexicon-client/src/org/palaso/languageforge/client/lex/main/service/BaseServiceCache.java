package org.palaso.languageforge.client.lex.main.service;

import java.util.Collection;
import java.util.HashMap;

import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.LexiconListEntry;

/**
 * The cache for the LexService. 
 * 
 * @author xin
 *
 */
class BaseServiceCache {

	private HashMap<String, LexiconEntryDto> lexEntryDtoCache = null;
	private HashMap<String, LexiconListEntry> lexListEntryCache = null;

	private boolean isCacheFilled = false;

	protected BaseServiceCache() {
		lexEntryDtoCache = new HashMap<String, LexiconEntryDto>();
		lexListEntryCache = new HashMap<String, LexiconListEntry>();
	}

	public void clearAll() {
		lexEntryDtoCache.clear();
		lexListEntryCache.clear();
		isCacheFilled = false;
	}

	public void putLexEntryDtoIntoCache(String id, LexiconEntryDto entryDto) {
		// create a new lexListEntry
		LexiconListEntry lexListEntry = LexiconListEntry.createObject().cast();
		lexListEntry.setId(entryDto.getId());
		lexListEntry.setEntry(entryDto.getEntry());
		
		lexListEntry.setMeaning(entryDto.getFirstMeaning());
		isCacheFilled = true;
		if (!lexEntryDtoCache.containsKey(id)) {
			// add the DTO to the cache
			lexEntryDtoCache.put(id, entryDto);
			// add the ListEntry to the cache
			lexListEntryCache.put(id, lexListEntry);
		}
	}

	public LexiconEntryDto removeLexEntryFromCache(String id) {
		lexListEntryCache.remove(id);
		return lexEntryDtoCache.remove(id);
	}

	public LexiconListEntry putLexListEntryIntoCache(String id, LexiconListEntry entry) {
		isCacheFilled = true;
		return lexListEntryCache.put(id, entry);
	}

	public int getSize() {
		return lexListEntryCache.size();
	}

	public boolean isCacheFilled() {
		return isCacheFilled;
	}

	public Collection<LexiconListEntry> getLexListEntryCollectionFromCache() {
		return lexListEntryCache.values();
	}

	public LexiconEntryDto getLexEntryDtoFromCache(String key) {
		return lexEntryDtoCache.get(key);
	}

}
