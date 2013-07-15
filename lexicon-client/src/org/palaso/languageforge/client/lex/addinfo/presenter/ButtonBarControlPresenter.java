/**
 * A presenter for button control for the Dictionary browse and edit
 * @author Kandasamy
 * @version 0.1
 * @since July 2011
 */
package org.palaso.languageforge.client.lex.addinfo.presenter;

import org.palaso.languageforge.client.lex.addinfo.AddInfoEventBus;
import org.palaso.languageforge.client.lex.addinfo.view.ButtonBarControlView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = ButtonBarControlView.class)
public class ButtonBarControlPresenter
		extends
		BasePresenter<ButtonBarControlPresenter.IButtonControlView, AddInfoEventBus> {

	public interface IButtonControlView {
		HasClickHandlers getUpdateButtonClickHandlers();

		HasClickHandlers getPrevButtonClickHandlers();

		HasClickHandlers getNextButtonClickHandlers();
		
		void setPrevButtonEnabled(boolean enabled);
		
		void setNextButtonEnabled(boolean enabled);

	}

	public void onSetPrevButtonEnabled(boolean enabled){
		view.setPrevButtonEnabled(enabled);
	}
	
	public void onSetNextButtonEnabled(boolean enabled){
		view.setNextButtonEnabled(enabled);
	}
	
	public void onSetButtonControl() {
		/**
		 *	Event created for move previous entry in the dictionary, 
		 * 	previousWord function called from MissInfoListPresenter
		 */
		view.getPrevButtonClickHandlers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.previousWord();
			}

		});
		/**
		 *	Event created for saving a new/existing entry into the dictionary,
		 * 	wordUpdated function called from LexDBEEditPresenter
		 */
		view.getUpdateButtonClickHandlers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.wordUpdated();
			}
		});
		/**
		 *	Event created for move next entry in the dictionary,
		 * 	nextWord function called from MissInfoListPresenter
		 */
		view.getNextButtonClickHandlers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.nextWord();
			}
		});
	}

}
