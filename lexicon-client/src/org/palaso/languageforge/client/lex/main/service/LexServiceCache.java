package org.palaso.languageforge.client.lex.main.service;

import java.util.Collection;
import java.util.HashMap;

import org.palaso.languageforge.client.lex.common.ConsoleLog;
import org.palaso.languageforge.client.lex.common.enums.EntryFieldType;
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
			ConsoleLog.log("Put to cache by Key[putLexEntryDtoIntoCache]: " + id);
			// add the DTO to the cache
			lexEntryDtoCache.put(id, entryDto);
		}
		if (!lexListEntryCache.containsKey(id)) {
			// add the ListEntry to the cache
			putLexListEntryIntoCache(id, lexListEntry);
		}
	}

	public LexiconEntryDto removeLexEntryFromCache(String id) {
		ConsoleLog.log("Remove Key from cache: " + id);
		return lexEntryDtoCache.remove(id);
	}

	public LexiconListEntry putLexListEntryIntoCache(String id, LexiconListEntry entry) {
		ConsoleLog.log("Put to cache by Key[putLexListEntryIntoCache]: " + id);
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

	public boolean isEntryCacheHasIt(String key)
	{
		return lexEntryDtoCache.containsKey(key);
	}
	
	public LexiconEntryDto getLexEntryDtoFromCache(String key) {
		ConsoleLog.log("Get from cache by Key[getLexEntryDtoFromCache]: " + key);
		return lexEntryDtoCache.get(key);
	}

	public EntryFieldType getCacheType() {
		return cacheType;
	}

	public void setCacheType(EntryFieldType value) {
		cacheType = value;
	}
}
