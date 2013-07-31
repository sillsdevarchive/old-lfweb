package org.palaso.languageforge.client.lex.model.settings.tasks;

import org.palaso.languageforge.client.lex.common.SettingTaskNameType;
import org.palaso.languageforge.client.lex.model.BaseDto;
public class SettingTasksTaskElementDto extends BaseDto<SettingTasksTaskElementDto> {

	protected SettingTasksTaskElementDto() {
	};

	public final static SettingTasksTaskElementDto getNew() {
		SettingTasksTaskElementDto entry = SettingTasksTaskElementDto.createObject().cast();
		entry.initialize();
		return entry;
	}

	protected final void initialize() {
		setVisible(false);
	}

	public final native String getTaskNameAsString() /*-{
		if (this.hasOwnProperty('taskName')) {
			return this.taskName;
		} else {
			return '';
		}
	}-*/;

	public final SettingTaskNameType getTaskName() {
		try {
			return SettingTaskNameType.getFromValue(getTaskNameAsString().trim());
		} catch (IllegalArgumentException e) {
			return SettingTaskNameType.UNDEFINED;
		}
	}

	public final native String getVisibleAsString() /*-{
		if (this.hasOwnProperty('visible')) {
			return this.visible;
		} else {
			return '';
		}
	}-*/;

	public final boolean getVisible() {
		if (getVisibleAsString().trim().equalsIgnoreCase("True")) {
			return true;
		} else {
			return false;
		}
	}

	public final native void setVisible(boolean visible) /*-{
		this.visible = visible ? "true" : "false";
	}-*/;

	public final native String getLabel() /*-{
		if (this.hasOwnProperty('label')) {
			if (this.label.hasOwnProperty('$')) {
				return this.label['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getLongLabel() /*-{
		if (this.hasOwnProperty('longLabel')) {
			if (this.longLabel.hasOwnProperty('$')) {
				return this.longLabel['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getDescription() /*-{
		if (this.hasOwnProperty('description')) {
			if (this.description.hasOwnProperty('$')) {
				return this.description['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getField() /*-{
		if (this.hasOwnProperty('field')) {
			if (this.field.hasOwnProperty('$')) {
				return this.field['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getShowFields() /*-{
		if (this.hasOwnProperty('showFields')) {
			if (this.showFields.hasOwnProperty('$')) {
				return this.showFields['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getReadOnly() /*-{
		if (this.hasOwnProperty('readOnly')) {
			if (this.readOnly.hasOwnProperty('$')) {
				return this.readOnly['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getWritingSystemsToMatch() /*-{
		if (this.hasOwnProperty('writingSystemsToMatch')) {
			if (this.writingSystemsToMatch.hasOwnProperty('$')) {
				return this.writingSystemsToMatch['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getWritingSystemsWhichAreRequired() /*-{
		if (this.hasOwnProperty('writingSystemsWhichAreRequired')) {
			if (this.writingSystemsWhichAreRequired.hasOwnProperty('$')) {
				return this.writingSystemsWhichAreRequired['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getShowMeaningFieldAsString() /*-{
		if (this.hasOwnProperty('showMeaningField')) {
			if (this.showMeaningField.hasOwnProperty('$')) {
				return this.showMeaningField['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final boolean getShowMeaningField() {
		if (getShowMeaningFieldAsString().trim().equalsIgnoreCase("True")) {
			return true;
		} else {
			return false;
		}
	}

	public final native void setShowMeaningField(boolean visible) /*-{
		if (!this.hasOwnProperty('showMeaningField')) {
			this.showMeaningField = new Object();
		}
		if (!this.showMeaningField.hasOwnProperty('$')) {
			this.showMeaningField['$'] = false;
		}
		this.showMeaningField['$'] = visible ? "true" : "false";
	}-*/;

	public final native String getShowGlossFieldAsString() /*-{
		if (this.hasOwnProperty('showGlossField')) {
			if (this.showGlossField.hasOwnProperty('$')) {
				return this.showGlossField['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final boolean getShowGlossField() {
		if (getShowGlossFieldAsString().trim().equalsIgnoreCase("True")) {
			return true;
		} else {
			return false;
		}
	}

	public final native void setShowGlossField(boolean visible) /*-{
		if (!this.hasOwnProperty('showGlossField')) {
			this.showGlossField = new Object();
		}
		if (!this.showGlossField.hasOwnProperty('$')) {
			this.showGlossField['$'] = false;
		}
		this.showGlossField['$'] = visible ? "true" : "false";
	}-*/;

	public final native String getShowPOSFieldAsString() /*-{
		if (this.hasOwnProperty('showPOSField')) {
			if (this.showPOSField.hasOwnProperty('$')) {
				return this.showPOSField['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final boolean getShowPOSField() {
		if (getShowPOSFieldAsString().trim().equalsIgnoreCase("True")) {
			return true;
		} else {
			return false;
		}
	}

	public final native void setShowPOSField(boolean visible) /*-{
		if (!this.hasOwnProperty('showPOSField')) {
			this.showPOSField = new Object();
		}
		if (!this.showPOSField.hasOwnProperty('$')) {
			this.showPOSField['$'] = false;
		}
		this.showPOSField['$'] = visible ? "true" : "false";
	}-*/;

	public final native String getShowExampleSentenceFieldAsString() /*-{
		if (this.hasOwnProperty('showExampleSentenceField')) {
			if (this.showExampleSentenceField.hasOwnProperty('$')) {
				return this.showExampleSentenceField['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final boolean getShowExampleSentenceField() {
		if (getShowExampleSentenceFieldAsString().trim().equalsIgnoreCase("True")) {
			return true;
		} else {
			return false;
		}
	}

	public final native void setShowExampleSentenceField(boolean visible) /*-{
		if (!this.hasOwnProperty('showExampleSentenceField')) {
			this.showExampleSentenceField = new Object();
		}
		if (!this.showExampleSentenceField.hasOwnProperty('$')) {
			this.showExampleSentenceField['$'] = false;
		}
		this.showExampleSentenceField['$'] = visible ? "true" : "false";
	}-*/;

	public final native String getShowExampleTranslationFieldAsString() /*-{
		if (this.hasOwnProperty('showExampleTranslationField')) {
			if (this.showExampleTranslationField.hasOwnProperty('$')) {
				return this.showExampleTranslationField['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final boolean getShowExampleTranslationField() {
		if (getShowExampleTranslationFieldAsString().trim().equalsIgnoreCase("True")) {
			return true;
		} else {
			return false;
		}
	}

	public final native void setShowExampleTranslationField(boolean visible) /*-{
		if (!this.hasOwnProperty('showExampleTranslationField')) {
			this.showExampleTranslationField = new Object();
		}
		if (!this.showExampleTranslationField.hasOwnProperty('$')) {
			this.showExampleTranslationField['$'] = false;
		}
		this.showExampleTranslationField['$'] = visible ? "true" : "false";
	}-*/;

	public final native String getSemanticDomainsQuestionFileName() /*-{
		if (this.hasOwnProperty('semanticDomainsQuestionFileName')) {
			if (this.semanticDomainsQuestionFileName.hasOwnProperty('$')) {
				return this.semanticDomainsQuestionFileName['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setSemanticDomainsQuestionFileName(String fileName) /*-{
		if (!this.hasOwnProperty('semanticDomainsQuestionFileName')) {
			this.semanticDomainsQuestionFileName = new Object();
		}
		if (!this.semanticDomainsQuestionFileName.hasOwnProperty('$')) {
			this.semanticDomainsQuestionFileName['$'] = false;
		}
		this.semanticDomainsQuestionFileName['$'] = fileName;
	}-*/;

	public final native String getWordListFileName() /*-{
		if (this.hasOwnProperty('wordListFileName')) {
			if (this.wordListFileName.hasOwnProperty('$')) {
				return this.wordListFileName['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getWordListWritingSystemId() /*-{
		if (this.hasOwnProperty('wordListWritingSystemId')) {
			if (this.wordListWritingSystemId.hasOwnProperty('$')) {
				return this.wordListWritingSystemId['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getTaskSpecifiedData() /*-{
		if (this.hasOwnProperty('taskspecifieddata')) {
			if (this.taskspecifieddata.hasOwnProperty('$')) {
				return this.taskspecifieddata['$'];
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native void setTaskSpecifiedData(String data) /*-{
		if (!this.hasOwnProperty('taskspecifieddata')) {
			this.taskspecifieddata = new Object();
		}
		if (!this.taskspecifieddata.hasOwnProperty('$')) {
			this.taskspecifieddata['$'] = new Object();
		}
		this.taskspecifieddata['$'] = data;
	}-*/;

	public final native void setTaskNameAsString(String name) /*-{
		this.taskName = name;
	}-*/;

	public final void setTaskName(SettingTaskNameType task) {
		setTaskNameAsString(task.getValue());
	}

	public final native String getTaskIndex() /*-{
		if (this.hasOwnProperty('index')) {
			return this.index;
		} else {
			return '';
		}
	}-*/;

	public final native void setTaskIndex(String index) /*-{
		this.index = index;
	}-*/;
}
