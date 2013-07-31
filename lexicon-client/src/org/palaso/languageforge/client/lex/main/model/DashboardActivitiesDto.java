package org.palaso.languageforge.client.lex.main.model;

import org.palaso.languageforge.client.lex.model.BaseDto;

import com.google.gwt.core.client.JsArrayInteger;

public class DashboardActivitiesDto extends BaseDto<DashboardActivitiesDto> {

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


}
