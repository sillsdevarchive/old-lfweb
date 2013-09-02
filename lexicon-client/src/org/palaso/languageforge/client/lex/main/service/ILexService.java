package org.palaso.languageforge.client.lex.main.service;

import java.util.Collection;
import java.util.SortedMap;

import org.palaso.languageforge.client.lex.model.ConversationDto;
import org.palaso.languageforge.client.lex.model.ConversationListDto;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.LexiconEntryListDto;
import org.palaso.languageforge.client.lex.model.LexiconListEntry;
import org.palaso.languageforge.client.lex.model.ResultDto;
import org.palaso.languageforge.client.lex.common.enums.AnnotationMessageStatusType;
import org.palaso.languageforge.client.lex.common.enums.ConversationAnnotationType;
import org.palaso.languageforge.client.lex.common.enums.EntryFieldType;
import org.palaso.languageforge.client.lex.main.model.DashboardActivitiesDto;
import org.palaso.languageforge.client.lex.main.model.DomainQuestionDto;
import org.palaso.languageforge.client.lex.main.model.DomainTreeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The RPC interface of the Lexicon client.
 * 
 */
public interface ILexService extends IBaseService {

	/**
	 * Gets an entry. Attempts to fetch from the local cache, fetches from the
	 * remote server if not found in local cache.
	 * 
	 * @param key
	 * @param asyncCallback
	 */
	void getEntry(String key, AsyncCallback<LexiconEntryDto> asyncCallback);

	/**
	 * Gets a list of entries depending on parameter entryFieldType. Reads from
	 * the local cache or the remote server depending on parameter forceReload.
	 * 
	 * 
	 * 
	 * @param asyncCallback
	 * @param entryFieldType
	 *            the type of field to be fetched
	 * @param forceReload
	 *            set to true in order to fetch from remote server and update
	 *            the local cache accordingly
	 */
	void getEntriesWithMissingFieldsAsList(final EntryFieldType entryFieldType,
			boolean forceReload,
			AsyncCallback<Collection<LexiconListEntry>> asyncCallback);

	/**
	 * Gets a list of all entries. Reads from the local cache or the remote
	 * server depending on parameter forceReload.
	 * 
	 * getEntriesWithMissingFieldsAsList and getAllEntriesAsList use the same
	 * cache!
	 * 
	 * 
	 * 
	 * @param asyncCallback
	 * @param forceReload
	 *            set to true in order to fetch from remote server and update
	 *            the local cache accordingly
	 */
	void getAllEntriesAsList(boolean forceReload, int rangeBegin,
			AsyncCallback<Collection<LexiconListEntry>> asyncCallback);

	/**
	 * Deletes an entry from the local cache (if present) and the remote server
	 * 
	 * Note: A once deleted entry might reappear if another user/other session
	 * edits it. This mechanism also depends on user priority.
	 * 
	 * 
	 * @param key
	 * @param asyncCallback
	 */
	void deleteEntry(String key, String mercurialSHA, AsyncCallback<ResultDto> asyncCallback);

	/**
	 * Adds a new entry to the local cache and the remote server.
	 * 
	 * OR
	 * 
	 * Updates an already existing entry in the local cache (if present) and on
	 * the remote server. The entry is added to the local cache if not present
	 * yet.
	 * 
	 * @param entry
	 * @param asyncCallback
	 */
	void saveEntry(LexiconEntryDto entry,
			AsyncCallback<ResultDto> asyncCallback);

	/**
	 * Search word by user input.
	 * 
	 * @param searchString
	 * @param indexFrom
	 * @param limit
	 * @param asyncCallback
	 */
	void getWordsForAutoSuggest(
		String searchString, int indexFrom, int limit, AsyncCallback<SortedMap<String, String>> asyncCallback
	);

	/**
	 * Gathers words from a string and adds it to the dictionary (to the remote
	 * server only, not to the local cache)
	 * 
	 * @param string
	 * @param asyncCallback
	 *            reports the number of words added if successful
	 */
	void gatherWordsFromText(String string, String uploadedFileName,
			AsyncCallback<ResultDto> asyncCallback);

	/**
	 * Gets the number of records present in the local cache. The number
	 * returned depends on - calls to getEntry() - calls to getEntryList() -
	 * calls to deleteEntry() - calls to saveEntry()
	 * 
	 * @return number of records present in the local cache
	 */
	int getEntriesRecordCount();

	/**
	 * Returns all records present in the local cache. The records returned
	 * depend on calls to - calls to getEntry() - calls to getEntryList() -
	 * calls to deleteEntry() - calls to saveEntry()
	 * 
	 * @return records present in local cache
	 */
	Collection<LexiconListEntry> getEntryCollection();

	/**
	 * Gets a list of all entries from word source pack file.
	 * 
	 * @param asyncCallback
	 */
	void getNewWordListForGatherWord(
			AsyncCallback<LexiconEntryListDto> asyncCallback);

	/**
	 * Gets the number of all records present on server. The number returned
	 * depends on - calls to getAllEntriesAsList()
	 * 
	 * @return number of all records present on server
	 */
	public int getEntryCountAvailableOnServer();

	/**
	 * Returns a tree structure containing the keys of all the semantic domains
	 * 
	 * @param asyncCallback
	 */
	void getDomainTreeList(AsyncCallback<DomainTreeDto> asyncCallback);

	/**
	 * Returns a semantic domain's question by guid
	 * 
	 * @param asyncCallback
	 */
	void getDomainQuestion(String guid,
			AsyncCallback<DomainQuestionDto> asyncCallback);

	/**
	 * Returns a set of review by filters
	 * 
	 * @param asyncCallback
	 */
	void getComments(AnnotationMessageStatusType messageStatus,
			ConversationAnnotationType type, int startIndex, int limitation,
			boolean isRecentChanges,
			AsyncCallback<ConversationListDto> asyncCallback);

	/**
	 * save an new comment
	 * 
	 * @param string
	 * 
	 * @param asyncCallback
	 */
	void saveNewComments(AnnotationMessageStatusType messageStatus, boolean isStatusReviewed, boolean isStatusTodo,
			String parentGuid, String commentMessage, boolean isRootMessage,
			AsyncCallback<ConversationDto> asyncCallback);

	/**
	 * get dashboard statistic data
	 * 
	 * @param actTimeRange
	 *            the time range will used to show line chart.
	 * 
	 * @param asyncCallback
	 */
	void getDashboardData(int actTimeRange,
			AsyncCallback<DashboardActivitiesDto> asyncCallback);
	

	/**
	 * get a flag that means does server updating dashboard data or not
	 * 
	 */
	void getIsDashboardUpdateToolRunning(AsyncCallback<ResultDto> asyncCallback);

	/**
	 * to remove a entry from client cache in case it is not needed.
	 * @param id
	 */
	void removeEntryFromCache(String id);
	
	/**
	 * to reset the cache to empty
	 */
	void resetCache();
	
	/**
	 * get a words count from server
	 * #this will not pass cache
	 */
	
	void getWordCountInDatabase(AsyncCallback<ResultDto> asyncCallback);

}
