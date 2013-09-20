/**
 * A presenter for sense in the Dictionary, contain meaning, examples, POS & translation.
 * @author Kandasamy
 * @version 0.1
 * @since June 2011
 */
package org.palaso.languageforge.client.lex.controls.presenter;

import java.util.ArrayList;

import org.palaso.languageforge.client.lex.common.ConsoleLog;
import org.palaso.languageforge.client.lex.common.PermissionManager;
import org.palaso.languageforge.client.lex.common.enums.DomainPermissionType;
import org.palaso.languageforge.client.lex.common.enums.OperationPermissionType;
import org.palaso.languageforge.client.lex.controls.ExtendedComboBox;
import org.palaso.languageforge.client.lex.controls.presenter.ExamplePresenter.IExampleView;
import org.palaso.languageforge.client.lex.controls.presenter.MultiTextPresenter.IMultiTextView;
import org.palaso.languageforge.client.lex.model.Example;
import org.palaso.languageforge.client.lex.model.FieldSettings;
import org.palaso.languageforge.client.lex.model.LexiconPosition;
import org.palaso.languageforge.client.lex.model.MultiText;
import org.palaso.languageforge.client.lex.model.Sense;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SensePresenter extends
		ModelPairBase<SensePresenter.ISenseView, Sense> {

	private MultiTextPresenter meaningPresenter;
	private ArrayList<ExamplePresenter> examplePresenters = new ArrayList<ExamplePresenter>();
	private boolean singleNewMeaning = false;
	private boolean singleNewExample = false;
	private boolean showCommentButton = true;
	private FieldSettings fieldSettings = FieldSettings.fromWindow();

	/**
	 * Interface created for the sense view These functions must be implemented
	 * in view class.
	 */
	public interface ISenseView {
		void addExampleAndTranslation(String exampleLabel,
				String translatioLabel, IExampleView view);

		void removeExampleAndTranslation(IExampleView view);

		IMultiTextView createMultiTextView();

		IExampleView createExampleView();

		IMultiTextView getMeaningMultiText();

		String getSelectedPOS();
		
		HasClickHandlers getPosCommentClick();
		
		void setPosCommentVisible(boolean visible);
		
		Label getPosLabel();

		Widget getWidget(); // Exposing a Widget to be used by a View not a
							// Presenter

		ExtendedComboBox getPartOfSpeechListBox();

		Label getMeaningLabel();

		HasClickHandlers getAddNewExampleClickHandlers();

		HasClickHandlers getRemoveSenseClickHandlers();

		void setMeaningLabelVisible(boolean visible);

		void setAddNewButtonVisible(boolean visible);

		void setRemoveSenseVisible(boolean visible);

		void setPartOfSpeechPanelVisible(boolean visible);

	}

	/**
	 * constructor for SensePresenter with view , model & MultiTextPresenter as
	 * arguments
	 * 
	 */
	public SensePresenter(ISenseView view, Sense model,
			FieldSettings fieldSettings, boolean isSingleNewMeaning,
			boolean isSingleNewExample, boolean showCommentBtn) {
		super(view, model);
		singleNewMeaning = isSingleNewMeaning;
		singleNewExample = isSingleNewExample;
		showCommentButton = showCommentBtn;
		view.setAddNewButtonVisible(false);
		this.fieldSettings = fieldSettings;
		MultiText meaningMultiText = MultiText.createFromSettings(fieldSettings
				.value("Definition"));

		MultiText meaningLabelMultiText = MultiText
				.createFromSettings(fieldSettings.value("Definition"));

		JsArrayString keys = meaningMultiText.keys();
		for (int j = 0, k = keys.length(); j < k; ++j) {
			String language = keys.get(j);
			meaningLabelMultiText
					.setValue(language, fieldSettings.value("Definition")
							.getAbbreviations().get(j));
		}

		this.meaningPresenter = new MultiTextPresenter(
				view.getMeaningMultiText(), model.getDefinition(),
				meaningLabelMultiText, showCommentButton);

		meaningPresenter.setEnabled(!fieldSettings.value("Definition")
				.isReadonlyField());

		// Part of speech
		JsArray<LexiconPosition> list = getPOSList();

		ConsoleLog.log("POS->Enabled: " + fieldSettings.value("POS").getEnabled());
		if (fieldSettings.value("POS").getEnabled()) {
			ConsoleLog.log("POS->Visible: True");
			view.setPartOfSpeechPanelVisible(true);
			boolean isPartOsSpeechEnabled = true;
			if (PermissionManager.getPermission(DomainPermissionType.DOMAIN_LEX_ENTRY, OperationPermissionType.CAN_EDIT_OTHER)) {
				isPartOsSpeechEnabled = true;
			} else {
				isPartOsSpeechEnabled = false;
			}
			if (fieldSettings.value("POS").isReadonlyField()) {
				isPartOsSpeechEnabled = false;
			}
			addPartofSpeech(fieldSettings.value("POS").getLabel(), list,
					model.getPOS(), isPartOsSpeechEnabled);
		} else {
			view.setPartOfSpeechPanelVisible(false);
		}
		ConsoleLog.log("Example->Enabled: " + fieldSettings.value("Example").getEnabled());
		if (fieldSettings.value("Example").getEnabled()) {
			for (int i = 0, n = model.getExampleCount(); i < n; ++i) {
				createExamplePresenterInView(model.getExample(i),
						exampleLabel(examplePresenters.size()), fieldSettings
								.value("Translation").getLabel());
			}
		}

		if (fieldSettings.value("NewExample").getEnabled()) {
			if (PermissionManager.getPermission(DomainPermissionType.DOMAIN_LEX_ENTRY, OperationPermissionType.CAN_EDIT_OTHER)) {
				if (!singleNewMeaning) {
					showNewExampleBlock();
				} else {
					Example newExample = Example
							.createFromSettings(fieldSettings);
					this.getModel().addExample(newExample);
					createExamplePresenterInView(newExample,
							fieldSettings.value("NewExample").getLabel(),
							fieldSettings.value("Translation").getLabel());
				}
			}
		} else {
			view.setAddNewButtonVisible(false);
		}
		if (!singleNewMeaning) {
			view.setRemoveSenseVisible(true);
		} else {
			view.setRemoveSenseVisible(false);
		}
	}

	private void showNewExampleBlock() {

		// Add New Example
		view.setAddNewButtonVisible(true);
		// addNewExample(navPresenter);
		// Add new example event
		final SensePresenter sensePresenter = this;
		view.getAddNewExampleClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						updateExampleLabelText();
						Example newExample = Example
								.createFromSettings(fieldSettings);
						sensePresenter.getModel().addExample(newExample);
						createExamplePresenterInView(newExample, fieldSettings
								.value("NewExample").getLabel(), fieldSettings
								.value("Translation").getLabel());
					}
				});
	}

	private void updateExampleLabelText() {
		for (ExamplePresenter examplePresenter : examplePresenters) {
			examplePresenter.view.getExampleLabel().setText(
					exampleLabel(examplePresenters.indexOf(examplePresenter)));
		}
	}

	/**
	 * <<<<<<< local getting the Label values from settings for example with
	 * count as argument
	 * 
	 * @return Label as String
	 */
	private String exampleLabel(int i) {
		return fieldSettings.value("Example").getLabel() + " " + (i + 1);
	}

	private void createExamplePresenterInView(Example example,
			String exampleLabel, String translationLabel) {
		final ExamplePresenter presenter = new ExamplePresenter(
				view.createExampleView(), example, fieldSettings,
				singleNewExample, showCommentButton);
		examplePresenters.add(presenter);
		view.addExampleAndTranslation(exampleLabel, translationLabel,
				presenter.getView());
		final SensePresenter sensePresenter = this;
		presenter.getView().setTranslationPanelVisible(
				fieldSettings.value("Translation").getEnabled());
		presenter.getRemoveButtonClickHandlers().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						examplePresenters.remove(presenter);
						sensePresenter.model.removeExample(presenter.getModel());
						view.removeExampleAndTranslation(presenter.getView());

						updateExampleLabelText();
					}
				});
	}

	/**
	 * To update the values entered by user to model class
	 * 
	 */
	public void updateModel() {
		meaningPresenter.updateModel();
		if (fieldSettings.value("POS").getEnabled() && !fieldSettings.value("POS").isReadonlyField()) {
			model.setPOS(view.getSelectedPOS());
		}
		for (int i = 0, n = examplePresenters.size(); i < n; ++i) {
			examplePresenters.get(i).updateModel();
		}
	}

	private void addPartofSpeech(String label, JsArray<LexiconPosition> list,
			String value, boolean editable) {
		ExtendedComboBox partOfSpeechComboBox = view.getPartOfSpeechListBox();
		view.setPosCommentVisible(showCommentButton);
		if (list == null) {
			return;
		}
		if (editable) {
			// Add the options to the drop down list.
			for (int i = 0; i < list.length(); i++) {
				partOfSpeechComboBox.addItem(list.get(i).getValue());
			}
			// Make the current value appear selected.
			int selected = 0;
			for (int i = 0; i < partOfSpeechComboBox.getItemCount(); i++) {
				if (partOfSpeechComboBox.getValue(i).equals(value)) {
					selected = i;
					break; // No need to continue.
				}
			}
			partOfSpeechComboBox.setItemSelected(selected, true);
			view.getPosLabel().setText(label);
		} else {
			TextBox textBox = new TextBox();
			
			if (value == null || value.isEmpty() || value.equalsIgnoreCase("Select")) {
				// TODO: i18n
				value = "Undefined";
			}
			
			textBox.setValue(value);
			textBox.setWidth("auto");
			textBox.setReadOnly(true);
			partOfSpeechComboBox.getElement().getParentNode()
					.appendChild(textBox.getElement());
			partOfSpeechComboBox.removeFromParent();
			view.getPosLabel().setText(label);
		}

	}

	public HasClickHandlers getRemoveSenseClickHandlers() {
		return view.getRemoveSenseClickHandlers();
	}

	/**
	 * To get the list of speech available in settings
	 * 
	 */
	private JsArray<LexiconPosition> getPOSList() {
		return model.getPOSList();
	}

	public void addCommentClickHandler(ClickHandler handler) {
		meaningPresenter.addCommentClickHandler(handler);
		view.getPosCommentClick().addClickHandler(handler);
		for (int i = 0; i < examplePresenters.size(); ++i) {
			examplePresenters.get(i).addCommentClickHandler(handler);
		}
	}
}
