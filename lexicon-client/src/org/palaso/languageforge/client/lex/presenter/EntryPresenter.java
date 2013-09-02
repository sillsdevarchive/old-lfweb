/**
 * A presenter for Entry in the Dictionary, contain word, senses & examples
 * @author Kandasamy
 * @version 0.1
 * @since June 2011
 */
package org.palaso.languageforge.client.lex.presenter;

import java.util.ArrayList;

import org.palaso.languageforge.client.lex.common.PermissionManager;
import org.palaso.languageforge.client.lex.common.enums.DomainPermissionType;
import org.palaso.languageforge.client.lex.common.enums.OperationPermissionType;
import org.palaso.languageforge.client.lex.model.FieldSettings;
import org.palaso.languageforge.client.lex.model.LexiconEntryDto;
import org.palaso.languageforge.client.lex.model.MultiText;
import org.palaso.languageforge.client.lex.model.Sense;
import org.palaso.languageforge.client.lex.presenter.MultiTextPresenter.IMultiTextView;
import org.palaso.languageforge.client.lex.presenter.SensePresenter.ISenseView;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;

public class EntryPresenter extends
		ModelPairBase<EntryPresenter.IEntryView, LexiconEntryDto> {
	private ArrayList<SensePresenter> sensePresenters = new ArrayList<SensePresenter>();
	private boolean singleNewMeaning = false;
	private boolean singleNewExample = false;
	private boolean wordEndPaddingCell = true;
	private FieldSettings fieldSettings = FieldSettings.fromWindow();
	private MultiTextPresenter wordPresenter;

	/**
	 * Interface created for the entry view These functions must be implemented
	 * in view class.
	 */
	public interface IEntryView {
		void addEntryMultiText(String label,
				MultiTextPresenter.IMultiTextView view, boolean addEndingSpaceCell);

		void addSense(ISenseView senseView);

		void addSenseBody(ISenseView senseView);

		IMultiTextView createMultiTextView();

		ISenseView createSenseView();

		void clear();

		HasClickHandlers getAddNewSenseClickHandlers();

		void setAddNewButtonVisible(boolean visible);

		void removeSense(ISenseView view);
	}

	/**
	 * constructor for EntryPresenter with view and model as arguments
	 * 
	 */
	public EntryPresenter(IEntryView view, LexiconEntryDto model,
			FieldSettings fieldSettings) {
		this(view, model, fieldSettings, false, false,true);
	}

	/**
	 * constructor for EntryPresenter with view and model as arguments
	 * 
	 */
	public EntryPresenter(IEntryView view, LexiconEntryDto model,
			FieldSettings fieldSettings, boolean isSingleNewMeaning,
			boolean isSingleNewExample, boolean addWordEndingSpaceCell) {
		super(view, model);
		view.setAddNewButtonVisible(false);
		this.fieldSettings = fieldSettings;
		singleNewMeaning = isSingleNewMeaning;
		singleNewExample = isSingleNewExample;
		wordEndPaddingCell = addWordEndingSpaceCell;
		addWord();
		// Senses
		if (fieldSettings.value("Definition").getEnabled()) {
			for (int i = 0, n = model.getSenseCount(); i < n; ++i) {
				createSensePresenterInView(model.getSense(i),
						getMeaningLabelText(i));
			}
		}
		
		if ((PermissionManager.getPermission(DomainPermissionType.DOMAIN_PROJECTS, OperationPermissionType.CAN_EDIT_OTHER))) {
			if (!singleNewMeaning) {
				showNewSenseBlock();
			} else {
				addNewMeaning();
			}
		}
	}

	/**
	 * getting the Label values from settings for meaning with count as argument
	 * 
	 * @return Label as String
	 */
	private String getMeaningLabelText(int i) {
		return fieldSettings.value("Definition").getLabel() + " " + (i + 1);
	}

	private void addWord() {
		if (fieldSettings.value("Word").getEnabled()) {
			MultiText wordMultiText = MultiText
					.createFromSettings(fieldSettings.value("Word"));

			MultiText labelMultiText = MultiText
					.createFromSettings(fieldSettings.value("Word"));

			JsArrayString keys = wordMultiText.keys();
			for (int i = 0, n = keys.length(); i < n; ++i) {
				String language = keys.get(i);
				String languageValue = model.getEntry().value(language);
				if (languageValue != null) {
					wordMultiText.setValue(language, languageValue);
				}
				labelMultiText.setValue(language, fieldSettings.value("Word")
						.getAbbreviations().get(i));
			}
			wordPresenter = new MultiTextPresenter(view.createMultiTextView(),
					wordMultiText, labelMultiText);

			view.addEntryMultiText(fieldSettings.value("Word").getLabel(),
					wordPresenter.getView(),wordEndPaddingCell);

			wordPresenter.setEnabled(!fieldSettings.value("Word")
					.isReadonlyField());
		}
	}

	public MultiTextPresenter getWordPresenter()
	{
		return wordPresenter;
	}
	
	/**
	 * To add the new meaning for each sense and create event for create
	 * multiple meanings
	 * 
	 */
	private void addNewMeaning() {
		if (fieldSettings.value("NewMeaning").getEnabled()) {
			Sense sense = Sense.createFromSettings(fieldSettings);
			if (singleNewMeaning) {
				view.setAddNewButtonVisible(false);
			}
			model.addSense(sense);
			updateMeaningLabelText();

			createSensePresenterInView(sense, fieldSettings.value("NewMeaning")
					.getLabel());
		} else {
			view.setAddNewButtonVisible(false);
		}

	}

	private void updateMeaningLabelText() {
		for (SensePresenter presenter : sensePresenters) {
			presenter.view.getMeaningLabel().setText(
					getMeaningLabelText(sensePresenters.indexOf(presenter)));
		}
	}

	private void showNewSenseBlock() {
		if (fieldSettings.value("NewMeaning").getEnabled()) {
			view.setAddNewButtonVisible(true);
		}
		view.getAddNewSenseClickHandlers().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addNewMeaning();
			}
		});
	}

	private void createSensePresenterInView(Sense sense, String label) {
		final SensePresenter presenter = new SensePresenter(
				view.createSenseView(), sense, this.fieldSettings,
				singleNewMeaning, singleNewExample);
		sensePresenters.add(presenter);
		presenter.view.getMeaningLabel().setText(label);
		view.addSense(presenter.getView());
		final EntryPresenter entryPresenter = this;
		presenter.getRemoveSenseClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						sensePresenters.remove(presenter);
						entryPresenter.model.removeSense(presenter.getModel());
						view.removeSense(presenter.getView());
						updateMeaningLabelText();
					}
				});
	}

	/**
	 * To update the values entered by user to model class
	 * 
	 */
	public void updateModel() {
		wordPresenter.updateModel();
		this.model.setEntry(wordPresenter.getModel());
		for (int i = 0; i < sensePresenters.size(); ++i) {
			sensePresenters.get(i).updateModel();
		}
	}
	/**
	 * a simple validation for check at last we have one word in the word field.
	 * @return boolean
	 */
	public boolean validate() {
		wordPresenter.updateModel();
		JsArrayString keys = wordPresenter.getModel().keys();
		
		for (int i = 0, n = keys.length(); i < n; ++i) {
			String language = keys.get(i);
			String value = wordPresenter.getModel().value(language);
			if (value.trim().length()>0)
			{
				return true;
			}

		}
		return false;
	}
}
