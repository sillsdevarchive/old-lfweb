package org.palaso.languageforge.client.lex.main.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsonUtils;

public class DashboardActivitiesDto extends JavaScriptObject {

	protected DashboardActivitiesDto() {
	}

	public final native JsArrayInteger getActivityDate()/*-{
		return this.activityDate;
	}-*/;

	public final native JsArrayInteger getEntryActivities()/*-{
		return this.entryActivities;
	}-*/;

	public final native JsArrayInteger getExampleActivities()/*-{
		return this.exampleActivities;
	}-*/;

	public final native JsArrayInteger getPartOfSpeechActivities()/*-{
		return this.partOfSpeechActivities;
	}-*/;

	public final native JsArrayInteger getDefinitionActivities()/*-{
		return this.definitionActivities;
	}-*/;

	public final native int getTargetCount()/*-{
		return this.targetCount;
	}-*/;

	public final native int getStatsWordCount()/*-{
		return this.statsWordCount;
	}-*/;

	public final native int getStatsPos()/*-{
		return this.statsPos;
	}-*/;

	public final native int getStatsMeanings()/*-{
		return this.statsMeanings;
	}-*/;

	public final native int getStatsExamples()/*-{
		return this.statsExamples;
	}-*/;

	public static final  DashboardActivitiesDto decode(String json){
		return JsonUtils.safeEval(json);
	}

}
