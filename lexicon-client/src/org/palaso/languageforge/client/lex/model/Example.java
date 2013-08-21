package org.palaso.languageforge.client.lex.model;

import com.google.gwt.core.client.JsArray;

//An overlay type
public class Example extends BaseDto<Example> {

	// Must have protected ctor with zero args
	protected Example() {
	}

	public final static Example createFromSettings(FieldSettings settings) {
		Example result = Example.createObject().cast();
		result.setExample(MultiText.createFromSettings(settings
				.value("Example")));
		result.setTranslation(MultiText.createFromSettings(settings
				.value("Translation")));
		return result;
	}

	public static final JsArray<Example> createExampleArray() {
		JsArray<Example> examples = createArray().cast();
		return examples;
	}

	// JSNI overlay methods
	public final native MultiText getExample() /*-{
		//hacks #1090
		if (this.example == null || this.example.length == 0) {
			this.example = {};
		}
		return this.example;
	}-*/;

	public final native MultiText getTranslation() /*-{
		//hacks #1090
		if (this.translation == null || this.translation.length == 0) {
			this.translation = {};
		}
		return this.translation;
	}-*/;

	public final native void setExample(MultiText newExample) /*-{
		this.example = newExample;
	}-*/;

	public final native void setTranslation(MultiText value) /*-{
		this.translation = value;
	}-*/;

	public final native EntryMetadataDto getMetadata() /*-{
		//hacks #1090
		if (this.metadata == null || this.metadata.length == 0) {
			this.metadata = {};
		}
		return this.metadata;
	}-*/;

	public final native void setMetadata(EntryMetadataDto metadata) /*-{
		this.metadata = metadata;
	}-*/;

}