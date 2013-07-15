/**
 * A presenter for button control for the Dictionary browse and edit
 * @author Kandasamy
 * @version 0.1
 * @since July 2011
 */
package org.palaso.languageforge.client.lex.browse.edit.presenter;

import org.palaso.languageforge.client.lex.browse.edit.BrowseAndEditEventBus;
import org.palaso.languageforge.client.lex.browse.edit.view.ButtonBarControlView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = ButtonBarControlView.class)
public class ButtonBarControlPresenter extends BasePresenter<ButtonBarControlPresenter.IButtonControlView, BrowseAndEditEventBus> {

	public interface IButtonControlView {
		HasClickHandlers getUpdateButtonClickHandlers();
		HasClickHandlers getAddButtonClickHandlers();
		HasClickHandlers getDeleteButtonClickHandlers();

		void setDeleteVisible(boolean visible);
		
		void setUpdateButtonEnable(boolean enabled);
		
		void setAddButtonEnable(boolean enabled);
	}

	public void onStart() {
		// TODO We should determine if this control should be visible at all based on user privs here. CP 2012-10
		view.setUpdateButtonEnable(false); // always disable it at first.
	}
	
	public void onSetDeleteButtonVisible(boolean visible)
	{
		view.setDeleteVisible(visible);
	}
	
	public void onSetUpdateButtonEnable(boolean enabled)
	{
		view.setUpdateButtonEnable(enabled);
	}
	
	public void onSetAddButtonEnable(boolean enabled)
	{
		view.setAddButtonEnable(enabled);
	}
	@Override
	public void bind() {
		/**
		 * Event created for add a new entry into the dictionary, wordCreated
		 * function called from LexDBEEditPresenter
		 */
		view.getAddButtonClickHandlers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.wordCreated();
			}

		});
		/**
		 * Event created for saving a new/existing entry into the dictionary,
		 * wordUpdated function called from LexDBEEditPresenter
		 */
		view.getUpdateButtonClickHandlers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.wordUpdated();
			}
		});
		/**
		 * Event created for deleting an entry from the dictionary, wordDeleted
		 * function called from LexDBEEditPresenter
		 */
		view.getDeleteButtonClickHandlers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.wordDeleted();
			}
		});
	}

}
