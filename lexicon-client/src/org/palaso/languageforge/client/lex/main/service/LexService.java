package org.palaso.languageforge.client.lex.main.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import org.palaso.languageforge.client.lex.common.AnnotationMessageStatusType;
import org.palaso.languageforge.client.lex.common.AutoSuggestPresenterOption;
import org.palaso.languageforge.client.lex.common.AutoSuggestPresenterOptionResultSet;
import org.palaso.languageforge.client.lex.common.ConversationAnnotationType;
import org.palaso.languageforge.client.lex.common.EntryFieldType;
import org.palaso.languageforge.client.lex.common.PermissionManager;
import org.palaso.languageforge.client.lex.common.ProjectPermissionType;
import org.palaso.languageforge.client.lex.common.Tools;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.AutoSuggestOptions;
import org.palaso.languageforge.client.lex.model.ConversationDto;
import org.palaso.languageforge.client.lex.model.ConversationListDto;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.LexiconEntryListDto;
import org.palaso.languageforge.client.lex.model.LexiconListDto;
import org.palaso.languageforge.client.lex.model.LexiconListEntry;
import org.palaso.languageforge.client.lex.model.ResultDto;
import org.palaso.languageforge.client.lex.service.BaseService;
import org.palaso.languageforge.client.lex.util.UUID;
import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.common.MissinginfoType;
import org.palaso.languageforge.client.lex.main.model.DashboardActivitiesDto;
import org.palaso.languageforge.client.lex.main.model.DomainQuestionDto;
import org.palaso.languageforge.client.lex.main.model.DomainTreeDto;
import org.palaso.languageforge.client.lex.main.service.actions.AutoSuggestAction;
import org.palaso.languageforge.client.lex.main.service.actions.DeleteEntryAction;
import org.palaso.languageforge.client.lex.main.service.actions.GatherWordsFromTextAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetCommentsAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetDashboardDataAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetDomainQuestionAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetDomainTreeListAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetEntryAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetIsDashboardUpdateToolRunningAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetListAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetMissingInfoListAction;
import org.palaso.languageforge.client.lex.main.service.actions.GetWordsListForGatherWordAction;
import org.palaso.languageforge.client.lex.main.service.actions.SaveEntryAction;
import org.palaso.languageforge.client.lex.main.service.actions.SaveNewCommentAction;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Singleton;

/**
 * LexService represents the implementation of ILexService
 * 
 * @author xin
 * 
 */
@Singleton
public class LexService extends BaseService implements ILexService {

	private LexServiceCache lexServiceCache = new LexServiceCache();

	private HashMap<Object, AutoSuggestPresenterOption> autoSuggestCache = new HashMap<Object, AutoSuggestPresenterOption>();
	private String lastAutoSuggestKeyword = "";

	private long cacheLifeTimeLimit = 0l;
	private int lastRangeBegin = 0;

	public LexService() {
		super();
	}

	@Override
	public void getEntriesWithMissingFieldsAsList(
			final EntryFieldType entryFieldType, boolean forceReload,
			final AsyncCallback<Collection<LexiconListEntry>> asyncCallback) {

		JsonRpcAction<LexiconListDto> action = null;
		// check if the cache has expired already.
		if (cacheLifeTimeLimit <= new Date().getTime()) {
			forceReload = true;
		}

		// check if cache is filled, if not, call to server to get data
		// if forceReload is set, always call to server to get new data
		if (forceReload || lexServiceCache.isCacheFilled()) {
			if (entryFieldType==EntryFieldType.DEFINITION)
			{
				action = new GetMissingInfoListAction(
						MissinginfoType.MEANING);
			}else if (entryFieldType==EntryFieldType.EXAMPLESENTENCE)
			{
				action = new GetMissingInfoListAction(
						MissinginfoType.EXAMPLE);
			}else if (entryFieldType==EntryFieldType.POS)
			{
				action = new GetMissingInfoListAction(
						MissinginfoType.GRAMMATICAL);
			}
			AsyncCallback<LexiconListDto> internalAsyncCallback = new AsyncCallback<LexiconListDto>() {
				@Override
				public void onFailure(Throwable caught) {
					asyncCallback.onFailure(caught);
				}

				@Override
				public void onSuccess(LexiconListDto result) {

					JsArray<LexiconListEntry> listEntry = result.getEntries();

					// clear exist data from cache
					lexServiceCache.clearAll();
					// keep current list type
					lexServiceCache.setCacheType(entryFieldType);
					cacheLifeTimeLimit = new Date().getTime()
							+ Constants.CACHE_LIFE_TIME;
					for (int i = 0; i < listEntry.length(); i++) {
						lexServiceCache.putLexListEntryIntoCache(
								listEntry.get(i).getId(), listEntry.get(i));
					}
					asyncCallback.onSuccess(lexServiceCache
							.getLexListEntryCollectionFromCache());
				}
			};
			remoteAsync.execute(action, internalAsyncCallback);
		} else {
			asyncCallback.onSuccess(lexServiceCache
					.getLexListEntryCollectionFromCache());
		}

	}

	@Override
	public void getAllEntriesAsList(
			boolean forceReload, 
			int rangeBegin,
			final AsyncCallback<Collection<LexiconListEntry>> asyncCallback
	) {

		int rangeEnd = rangeBegin + Constants.VISIBLE_WORDS_PER_PAGE_COUNT;
		int entryCount = entryAvailableOnServer + newEntryCount;
		
		boolean cacheMissHit = false;
		
		if (entryCount > 0 && rangeEnd > entryCount) {
			rangeEnd = entryCount;
		}
		lastRangeBegin = rangeBegin;
		JsonRpcAction<LexiconListDto> action = null;
		// check if the cache has expired already.
		if (cacheLifeTimeLimit <= new Date().getTime()) {
			forceReload = true;
		}

		if (rangeBegin < 0) {
			rangeBegin = 0;
			rangeEnd = entryCount;
			forceReload = true;
		} else {
			// cache miss hit reload
			if (!(loadedIndexBegin <= rangeBegin && loadedIndexEnd >= rangeEnd)) {
				cacheMissHit = true;
				// pre load few pages
				rangeEnd = rangeBegin + (Constants.VISIBLE_WORDS_PER_PAGE_COUNT * Constants.PRE_LOAD_PAGES);
			}

			if (rangeEnd > entryAvailableOnServer && entryAvailableOnServer!=0) {
				rangeEnd = entryAvailableOnServer;
			}

		}
		final boolean chkForceReload = forceReload;
		// check if cache is filled, if not, call to server to get data
		// if forceReload is set, always call to server to get new data
		if (forceReload || cacheMissHit || !lexServiceCache.isCacheFilled()) {
			action = new GetListAction(rangeBegin, rangeEnd);
			AsyncCallback<LexiconListDto> internalAsyncCallback = new AsyncCallback<LexiconListDto>() {
				@Override
				public void onFailure(Throwable caught) {
					asyncCallback.onFailure(caught);
				}

				@Override
				public void onSuccess(LexiconListDto result) {
					JsArray<LexiconListEntry> listEntry = result.getEntries();
					entryAvailableOnServer = result.getEntryCount();
					// loadedIndexBegin = result.getEntryBeginIndex();
					loadedIndexEnd = result.getEntryEndIndex();
					// clear exist data from cache
					if (chkForceReload) {
						// Temporarily not clear the cache to let new words persist when starting a new project with 0 entries.
						// Working on a better fix CP 2012-10
//						lexServiceCache.clearAll();
//						lexServiceCache.setCacheType(null);
						cacheLifeTimeLimit = new Date().getTime()
								+ Constants.CACHE_LIFE_TIME;
					}
					for (int i = 0; i < listEntry.length(); i++) {
						lexServiceCache.putLexListEntryIntoCache(
								listEntry.get(i).getId(), listEntry.get(i));
					}
					asyncCallback.onSuccess(lexServiceCache.getLexListEntryCollectionFromCache());
				}
			};
			remoteAsync.execute(action, internalAsyncCallback);
		} else {
			asyncCallback.onSuccess(lexServiceCache.getLexListEntryCollectionFromCache());
		}

	}

	@Override
	public void getEntry(final String key,
			final AsyncCallback<LexiconEntryDto> asyncCallback) {

		boolean forceReload = false;
		if (cacheLifeTimeLimit <= new Date().getTime()) {
			// cache expired, reload list first!
			forceReload = true;
			if (lexServiceCache.getCacheType() != null) {
				getEntriesWithMissingFieldsAsList(
						lexServiceCache.getCacheType(), true,
						new AsyncCallback<Collection<LexiconListEntry>>() {

							@Override
							public void onSuccess(
									Collection<LexiconListEntry> result) {
								// reload entry.
								getEntry(key, asyncCallback);
							}

							@Override
							public void onFailure(Throwable caught) {
								asyncCallback.onFailure(caught);
							}
						});
			} else {
				getAllEntriesAsList(true, lastRangeBegin,
						new AsyncCallback<Collection<LexiconListEntry>>() {

							@Override
							public void onSuccess(
									Collection<LexiconListEntry> result) {
								// reload entry.
								getEntry(key, asyncCallback);
							}

							@Override
							public void onFailure(Throwable caught) {
								asyncCallback.onFailure(caught);
							}
						});
			}
			return;
		}

		if (lexServiceCache.getLexEntryDtoFromCache(key) == null || forceReload) {
			AsyncCallback<LexiconEntryDto> internalAsyncCallback = new AsyncCallback<LexiconEntryDto>() {
				@Override
				public void onFailure(Throwable caught) {
					asyncCallback.onFailure(caught);
				}

				@Override
				public void onSuccess(LexiconEntryDto result) {
					lexServiceCache.putLexEntryDtoIntoCache(key, result);
					asyncCallback.onSuccess(result);
				}
			};
			GetEntryAction action = new GetEntryAction(key);
			remoteAsync.execute(action, internalAsyncCallback);
		} else {
			asyncCallback.onSuccess(lexServiceCache
					.getLexEntryDtoFromCache(key));
		}
	}

	@Override
	public void saveEntry(final LexiconEntryDto entry,
			final AsyncCallback<ResultDto> asyncCallback) {
		boolean allowEdit=false;
		if (PermissionManager.getPermission(ProjectPermissionType.CAN_EDIT_ENTRY)) {
			allowEdit=true;
		}
		
		if (!allowEdit) {
			// normally should not array here, only for protection
			asyncCallback.onFailure(new Throwable(
					"User not allow to edit or create entry"));
			return;
		}
		AsyncCallback<ResultDto> internalAsyncCallback = new AsyncCallback<ResultDto>() {
			@Override
			public void onFailure(Throwable caught) {
				asyncCallback.onFailure(caught);
			}

			@Override
			public void onSuccess(ResultDto result) {

				// update cache if it is present
				lexServiceCache.putLexEntryDtoIntoCache(entry.getId(), entry);

				asyncCallback.onSuccess(result);
			}
		};

		SaveEntryAction action = null;

		if (entry.getId() == null) {
			action = new SaveEntryAction(entry, "new");
			entry.setGuid(UUID.uuid());
			newEntryCount++;
		} else {
			action = new SaveEntryAction(entry, "update");
		}
		remoteAsync.execute(action, internalAsyncCallback);
	}

	@Override
	public void deleteEntry(final String key, final String mercurialSHA,
			final AsyncCallback<ResultDto> asyncCallback) {
		boolean allowDelete=false;
		if (PermissionManager.getPermission(ProjectPermissionType.CAN_DELETE_ENTRY)) {
			allowDelete=true;
		}
		if (!allowDelete) {
			// normally should not array here, only for protection
			asyncCallback.onFailure(new Throwable(
					"User not allow to delete entry"));
			return;
		}
		DeleteEntryAction action = new DeleteEntryAction(key, mercurialSHA);
		AsyncCallback<ResultDto> internalAsyncCallback = new AsyncCallback<ResultDto>() {
			@Override
			public void onFailure(Throwable caught) {
				asyncCallback.onFailure(caught);
			}

			@Override
			public void onSuccess(ResultDto result) {
				// remove object from cache, if it is present
				lexServiceCache.removeLexEntryFromCache(key);
				if (entryAvailableOnServer > 0) {
					entryAvailableOnServer--;
				}
				asyncCallback.onSuccess(result);
			}
		};
		remoteAsync.execute(action, internalAsyncCallback);
	}

	@Override
	public void getWordsForAutoSuggest(
		final String searchString,
		final int indexFrom, int limit,
		final AsyncCallback<SortedMap<String, String>> asyncCallback
	) {

		// Don't always get Suggest list from server, because client cache and
		// server may already be out of sync. Also the time to fetch gives a 
		// noticable delay to the user.

		boolean useCached = false;
		boolean localCacheMissed = false;
		if (searchString.length() > Constants.SUGGEST_BOX_MINIMUM_CHAR - 1) {
			if (searchString.substring(0,
					Constants.SUGGEST_BOX_MINIMUM_CHAR - 1).equalsIgnoreCase(
					lastAutoSuggestKeyword)) {
				useCached = true;
			} else {
				useCached = false;
				lastAutoSuggestKeyword = searchString.substring(0,
						Constants.SUGGEST_BOX_MINIMUM_CHAR - 1);
			}
		}

		if (useCached) {
			SortedMap<String, String> result = new TreeMap<String, String>();

			AutoSuggestPresenterOption[] obj = autoSuggestCache.values().toArray(new AutoSuggestPresenterOption[0]);
			int ncount = obj.length < indexFrom + Constants.VISIBLE_WORDS_PER_PAGE_COUNT ? obj.length : indexFrom + Constants.VISIBLE_WORDS_PER_PAGE_COUNT;

			for (int i = indexFrom; i < ncount; i++) {
				AutoSuggestPresenterOption option = obj[i];
				String word = option.getName();
				int simTextEvlValue = Tools.getLevenshteinDistance(
						searchString.toLowerCase(), word);
				if (((word.length() - simTextEvlValue) * 100) / word.length() >= 50) {
					result.put(String.valueOf(option.getValue()), option.getName());
				}
			}

			if (obj.length > 0 && result.size() == 0) {
				// cache misses
				localCacheMissed = true;
			} else {
				// cache hit
				asyncCallback.onSuccess(result);
			}
		}
		if (!useCached || localCacheMissed) {
			AutoSuggestAction action = new AutoSuggestAction(
				org.palaso.languageforge.client.lex.common.Constants.getFirstWordType(),
				searchString,
				indexFrom,
				org.palaso.languageforge.client.lex.common.Constants.SUGGEST_BOX_MAX_RECORD_FROM_SERVER
			);
			AsyncCallback<AutoSuggestOptions> internalAsyncCallback = new AsyncCallback<AutoSuggestOptions>() {

				@Override
				public void onSuccess(AutoSuggestOptions result) {

					JSONValue val = JSONParser.parseLenient(AutoSuggestOptions
							.encode(result));
					JSONObject obj = val.isObject();
					int totSize = (int) obj
							.get(AutoSuggestPresenterOptionResultSet.TOTAL_SIZE)
							.isNumber().doubleValue();
					AutoSuggestPresenterOptionResultSet options = new AutoSuggestPresenterOptionResultSet(
							totSize);
					JSONArray optionsArray = obj.get(
							AutoSuggestPresenterOptionResultSet.OPTIONS)
							.isArray();
					autoSuggestCache.clear();
					if (options.getTotalSize() > 0 && optionsArray != null) {

						for (int i = 0; i < optionsArray.size(); i++) {
							if (optionsArray.get(i) == null) {

								continue;
							}
							JSONObject jsonOpt = optionsArray.get(i).isObject();
							AutoSuggestPresenterOption option = new AutoSuggestPresenterOption();
							if (jsonOpt
									.get(AutoSuggestPresenterOptionResultSet.DISPLAY_NAME) == null) {
								continue;
							}
							option.setName(jsonOpt
									.get(AutoSuggestPresenterOptionResultSet.DISPLAY_NAME)
									.isString().stringValue());

							if (jsonOpt
									.get(AutoSuggestPresenterOptionResultSet.VALUE) == null) {
								continue;
							}
							option.setValue(jsonOpt
									.get(AutoSuggestPresenterOptionResultSet.VALUE)
									.isString().stringValue());
							options.addOption(option);
							autoSuggestCache.put(option.getValue(), option);
						}
					}

					SortedMap<String, String> suggestResult1 = new TreeMap<String, String>();

					AutoSuggestPresenterOption[] optionArray = autoSuggestCache
							.values()
							.toArray(new AutoSuggestPresenterOption[0]);

					int ncount = optionArray.length < indexFrom
							+ Constants.VISIBLE_WORDS_PER_PAGE_COUNT ? optionArray.length
							: indexFrom + Constants.VISIBLE_WORDS_PER_PAGE_COUNT;

					for (int i = indexFrom; i < ncount; i++) {
						AutoSuggestPresenterOption option = optionArray[i];

						suggestResult1.put(String.valueOf(option.getValue()),
								option.getName());

					}
					asyncCallback.onSuccess(suggestResult1);
				}

				@Override
				public void onFailure(Throwable caught) {
					asyncCallback.onFailure(caught);
				}
			};

			remoteAsync.execute(action, internalAsyncCallback);
		}
	}

	@Override
	public void gatherWordsFromText(String string, String uploadedFileName,
			AsyncCallback<ResultDto> asyncCallback) {
		GatherWordsFromTextAction action = new GatherWordsFromTextAction(
				string, uploadedFileName);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public Collection<LexiconListEntry> getEntryCollection() {
		if (!lexServiceCache.isCacheFilled()) {
			return new ArrayList<LexiconListEntry>();
		}
		return lexServiceCache.getLexListEntryCollectionFromCache();
	}

	@Override
	public void getNewWordListForGatherWord(
			AsyncCallback<LexiconEntryListDto> asyncCallback) {

		lexServiceCache.clearAll();
		lexServiceCache.setCacheType(null);
		cacheLifeTimeLimit = new Date().getTime() + Constants.CACHE_LIFE_TIME;

		GetWordsListForGatherWordAction action = new GetWordsListForGatherWordAction();
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public int getEntriesRecordCount() {
		if (!lexServiceCache.isCacheFilled()) {
			return 0;
		}
		return lexServiceCache.getSize();
	}

	@Override
	public void getDomainTreeList(AsyncCallback<DomainTreeDto> asyncCallback) {
		GetDomainTreeListAction action = new GetDomainTreeListAction();
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void getDomainQuestion(String guid,
			AsyncCallback<DomainQuestionDto> asyncCallback) {
		GetDomainQuestionAction action = new GetDomainQuestionAction(guid);
		remoteAsync.execute(action, asyncCallback);
	}

	// follows used by getAllEntriesAsList only.

	private int entryAvailableOnServer = 0;
	private int loadedIndexBegin = 0;
	private int loadedIndexEnd = 0;
	
	private int newEntryCount = 0;

	public int getEntryCountAvailableOnServer() {
		return entryAvailableOnServer + newEntryCount;
	}

	@Override
	public void getComments(AnnotationMessageStatusType messageStatus,
			ConversationAnnotationType type, int startIndex, int limitation,
			boolean isRecentChanges,
			AsyncCallback<ConversationListDto> asyncCallback) {
		GetCommentsAction action = new GetCommentsAction(messageStatus, type,
				startIndex, limitation, isRecentChanges);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void saveNewComments(AnnotationMessageStatusType messageStatus,
			String parentGuid, String commentMessage, boolean isRootMessage,
			AsyncCallback<ConversationDto> asyncCallback) {

		SaveNewCommentAction action = new SaveNewCommentAction(messageStatus,
				parentGuid, commentMessage, isRootMessage);
		remoteAsync.execute(action, asyncCallback);

	}

	@Override
	public void getDashboardData(int actTimeRange,
			AsyncCallback<DashboardActivitiesDto> asyncCallback) {
		GetDashboardDataAction action = new GetDashboardDataAction(actTimeRange);
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void getIsDashboardUpdateToolRunning(AsyncCallback<ResultDto> asyncCallback) {
		GetIsDashboardUpdateToolRunningAction action = new GetIsDashboardUpdateToolRunningAction();
		remoteAsync.execute(action, asyncCallback);
	}

	@Override
	public void removeEntryFromCache(String id) {
		if (lexServiceCache.isCacheFilled())
		{
			lexServiceCache.removeLexEntryFromCache(id);
		}
	}

}
