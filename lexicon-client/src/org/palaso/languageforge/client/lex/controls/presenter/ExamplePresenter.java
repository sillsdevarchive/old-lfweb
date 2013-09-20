/**
 * A presenter for Example in the Dictionary, contain example & translation.
 * @author Kandasamy
 * @version 0.1
 * @since June 2011
 */

package org.palaso.languageforge.client.lex.controls.presenter;

import org.palaso.languageforge.client.lex.controls.presenter.MultiTextPresenter.IMultiTextView;
import org.palaso.languageforge.client.lex.model.Example;
import org.palaso.languageforge.client.lex.model.FieldSettings;
import org.palaso.languageforge.client.lex.model.MultiText;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ExamplePresenter extends
		ModelPairBase<ExamplePresenter.IExampleView, Example> {

	private MultiTextPresenter examplePresenter;
	private MultiTextPresenter translationPresenter;
	private FieldSettings fieldSettings;
	private boolean singleNewExample = false;

	/**
	 * Interface created for the example view These functions must be
	 * implemented in view class.
	 */
	public interface IExampleView {
		IMultiTextView getExampleMultiText();

		IMultiTextView getTranslationMultiText();

		Label getExampleLabel();

		Label getTranslationLabel();

		void setExamplePanelVisible(boolean visible);

		void setTranslationPanelVisible(boolean visible);

		void setRemoveButtonVisible(boolean visible);

		HasClickHandlers getRemoveButtonClickHandlers();

		Widget getWidget();
	}

	/**
	 * constructor for ExamplePresenter with view and model as arguments
	 * 
	 * @param isSingleNewExample
	 * 
	 */
	public ExamplePresenter(IExampleView view, Example model,
			FieldSettings fieldSettings, boolean isSingleNewExample,
			boolean showCommentBtn) {
		this(view, model, new MultiTextPresenter(view.getExampleMultiText(),
				model.getExample(),
				MultiText.createLabelFromSettings(fieldSettings
						.value("Translation")), showCommentBtn), fieldSettings,
				isSingleNewExample, showCommentBtn);
	}

	/**
	 * constructor for ExamplePresenter with view , model & MultiTextPresenter
	 * as arguments
	 * 
	 */
	public ExamplePresenter(IExampleView view, Example model,
			MultiTextPresenter examplePresenter, FieldSettings fieldSettings,
			boolean isSingleNewExample, boolean showCommentBtn) {
		super(view, model);
		singleNewExample = isSingleNewExample;
		this.fieldSettings = fieldSettings;
		this.examplePresenter = examplePresenter;
		if (this.fieldSettings.value("Translation").getEnabled()) {
			translationPresenter = new MultiTextPresenter(
					view.getTranslationMultiText(), model.getTranslation(),
					MultiText.createLabelFromSettings(this.fieldSettings
							.value("Translation")), showCommentBtn);
			translationPresenter.setEnabled(!fieldSettings.value("Translation")
					.isReadonlyField());
		}
		view.setRemoveButtonVisible(!singleNewExample);

		examplePresenter.setEnabled(!fieldSettings.value("Example")
				.isReadonlyField());

	}

	public HasClickHandlers getRemoveButtonClickHandlers() {
		return view.getRemoveButtonClickHandlers();
	}

	/**
	 * To update the values entered by user to model class
	 * 
	 */
	public void updateModel() {
		examplePresenter.updateModel();
		if (fieldSettings.value("Translation").getEnabled()) {
			translationPresenter.updateModel();
		}
	}
	

	public void addCommentClickHandler(ClickHandler handler) {
		examplePresenter.addCommentClickHandler(handler);
		translationPresenter.addCommentClickHandler(handler);
	}
}