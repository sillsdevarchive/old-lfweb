package org.palaso.languageforge.client.lex.main.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;

import org.palaso.languageforge.client.lex.model.ConversationDto;
import org.palaso.languageforge.client.lex.model.ConversationListDto;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.LexiconEntryListDto;
import org.palaso.languageforge.client.lex.model.LexiconListDto;
import org.palaso.languageforge.client.lex.model.LexiconListEntry;
import org.palaso.languageforge.client.lex.model.ResultDto;
import org.palaso.languageforge.client.lex.service.BaseServiceTest;
import org.palaso.languageforge.client.lex.common.AnnotationMessageStatusType;
import org.palaso.languageforge.client.lex.common.ConversationAnnotationType;
import org.palaso.languageforge.client.lex.common.EntryFieldType;
import org.palaso.languageforge.client.lex.main.model.DashboardActivitiesDto;
import org.palaso.languageforge.client.lex.main.model.DomainQuestionDto;
import org.palaso.languageforge.client.lex.main.model.DomainTreeDto;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Singleton;

@Singleton
public class LexServiceTest extends BaseServiceTest implements ILexService {
	private final native LexiconListDto getWordList() /*-{
		return $wnd.jsonWordList;
	}-*/;

	private final native LexiconEntryDto getEntry() /*-{
		return $wnd.jsonLexicalEntry;
	}-*/;

	@Override
	public void getEntriesWithMissingFieldsAsList(
			final EntryFieldType missInfoType, boolean forceReload,
			AsyncCallback<Collection<LexiconListEntry>> asyncCallback) {
		JsArray<LexiconListEntry> listEntry = getWordList();
		ArrayList<LexiconListEntry> result = new ArrayList<LexiconListEntry>();
		for (int i = 0; i < listEntry.length(); i++) {
			result.add(listEntry.get(i));
		}

		asyncCallback.onSuccess(result);
	}

	@Override
	public void getEntry(String id, AsyncCallback<LexiconEntryDto> asyncCallback) {
		LexiconEntryDto result = getEntry();
		asyncCallback.onSuccess(result);
	}

	@Override
	public void saveEntry(LexiconEntryDto entry,
			AsyncCallback<ResultDto> asyncCallback) {
	}

	@Override
	public void deleteEntry(String id, String mercurialSHA, AsyncCallback<ResultDto> asyncCallback) {
	}

	@Override
	public void getWordsForAutoSuggest(String searchString, int indexFrom,
			int indexTo, AsyncCallback<SortedMap<String, String>> asyncCallback) {
	}

	@Override
	public void gatherWordsFromText(String string, String uploadedFileName,
			AsyncCallback<ResultDto> asyncCallback) {
	}

	@Override
	public Collection<LexiconListEntry> getEntryCollection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getAllEntriesAsList(boolean forceReload, int rangeBegin,
			AsyncCallback<Collection<LexiconListEntry>> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getNewWordListForGatherWord(
			AsyncCallback<LexiconEntryListDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getEntriesRecordCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEntryCountAvailableOnServer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getDomainTreeList(AsyncCallback<DomainTreeDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getDomainQuestion(String guid,
			AsyncCallback<DomainQuestionDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getComments(AnnotationMessageStatusType messageStatus,
			ConversationAnnotationType type, int startIndex, int limitation,
			boolean isRecentChanges,
			AsyncCallback<ConversationListDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveNewComments(AnnotationMessageStatusType messageStatus, boolean isStatusReviewed, boolean isStatusTodo,
			String parentGuid, String commentMessage, boolean isRootMessage,
			AsyncCallback<ConversationDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getDashboardData(int actTimeRange,
			AsyncCallback<DashboardActivitiesDto> asyncCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getIsDashboardUpdateToolRunning(AsyncCallback<ResultDto> asyncCallback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEntryFromCache(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getWordCountInDatabase(AsyncCallback<ResultDto> asyncCallback) {
		// TODO Auto-generated method stub
	}

}
