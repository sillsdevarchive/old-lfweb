/**
 * A presenter for Navigation bar in the List for the Dictionary 
 * @author Kandasamy
 * @version 0.2
 * @since July 2011
 */
package org.palaso.languageforge.client.lex.browse.edit.presenter;

import org.palaso.languageforge.client.lex.browse.edit.BrowseAndEditEventBus;
import org.palaso.languageforge.client.lex.controls.navigationbar.NavigationBarPresenter;
import org.palaso.languageforge.client.lex.controls.navigationbar.NavigationBarView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.annotation.Presenter;

/**
 * A simple widget representing prev/next page navigation.
 */
@Presenter(view = NavigationBarView.class)
public class BrowseAndEditNavigationBarPresenter extends NavigationBarPresenter<BrowseAndEditEventBus> {
	
	@Override
	public void bind() {
		view.getPreviousButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.previous();
			}

		});

		view.getNextButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.next();
			}

		});

		view.getFirstButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.first();
			}

		});

		view.getLastButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.last();
			}

		});
	}



}
