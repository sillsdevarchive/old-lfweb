/**
 * A presenter for Navigation bar in the List for the Dictionary 
 * @author Kandasamy
 * @version 0.2
 * @since July 2011
 */
package org.palaso.languageforge.client.lex.addinfo.presenter;

import org.palaso.languageforge.client.lex.addinfo.AddInfoEventBus;
import org.palaso.languageforge.client.lex.controls.navigationbar.NavigationBarPresenter;
import org.palaso.languageforge.client.lex.controls.navigationbar.NavigationBarView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.annotation.Presenter;

/**
 * A simple widget representing prev/next page navigation.
 */
@Presenter(view = NavigationBarView.class)
public class AddInfoNavigationBarPresenter extends NavigationBarPresenter<AddInfoEventBus>
{
    @Override
	public void bind() {
		view.getPreviousButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.previous();
				eventBus.setNavigationButtonStatus();
			}

		});

		view.getNextButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.next();
				eventBus.setNavigationButtonStatus();
			}

		});
		view.getFirstButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.first();
				eventBus.setNavigationButtonStatus();
			}

		});
		
		view.getLastButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.last();
				eventBus.setNavigationButtonStatus();
			}

		});
	}


}