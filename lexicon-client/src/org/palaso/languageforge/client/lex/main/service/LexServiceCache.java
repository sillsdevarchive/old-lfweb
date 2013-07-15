package org.palaso.languageforge.client.lex.main.service;

import java.util.Collection;
import java.util.HashMap;

import org.palaso.languageforge.client.lex.common.EntryFieldType;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.LexiconListEntry;

/**
 * The cache for the LexService. 
 * 
 * @author xin
 *
 */
class LexServiceCache {

	private HashMap<String, LexiconEntryDto> lexEntryDtoCache = null;
	private HashMap<String, LexiconListEntry> lexListEntryCache = null;

	private EntryFieldType cacheType = null;

	private boolean isCacheFilled = false;

	protected LexServiceCache() {
		lexEntryDtoCache = new HashMap<String, LexiconEntryDto>();
		lexListEntryCache = new HashMap<String, LexiconListEntry>();
	}

	public void clearAll() {
		cacheType=null;
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
		}
		if (!lexListEntryCache.containsKey(id)) {
			// add the ListEntry to the cache
			putLexListEntryIntoCache(id, lexListEntry);
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

	public EntryFieldType getCacheType() {
		return cacheType;
	}

	public void setCacheType(EntryFieldType value) {
		cacheType = value;
	}
}
