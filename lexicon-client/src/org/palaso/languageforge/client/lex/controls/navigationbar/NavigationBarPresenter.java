/**
 * A presenter for Navigation bar in the List for the Dictionary 
 * @author Kandasamy
 * @version 0.2
 * @since July 2011
 */
package org.palaso.languageforge.client.lex.controls.navigationbar;

import org.palaso.languageforge.client.lex.common.Constants;
import org.palaso.languageforge.client.lex.main.presenter.CommonNavBarPresenter;

import com.google.gwt.user.client.ui.Anchor;
import com.mvp4g.client.event.EventBus;

/**
 * A simple widget representing prev/next page navigation.
 */

public abstract class NavigationBarPresenter <T extends EventBus> extends CommonNavBarPresenter<NavigationBarPresenter.INavBarView ,T>
{

	public interface INavBarView {
		Anchor getPreviousButton();

		Anchor getNextButton();
		Anchor getFirstButton();
		Anchor getLastButton();
		void setNavText(String text);
	}
	
	public void onSetNavStatus(int startIndex, int endIndex, int numberOfElements) {
		boolean isPreviousEnable = (startIndex != 1 && numberOfElements!=0);
		boolean isNextEnable = ((startIndex + Constants.VISIBLE_WORDS_PER_PAGE_COUNT) <= numberOfElements);
		boolean isFirstEnable = (startIndex > Constants.VISIBLE_WORDS_PER_PAGE_COUNT);
		boolean isLastEnable = ((numberOfElements - Constants.VISIBLE_WORDS_PER_PAGE_COUNT) >= startIndex);

		setButtonEnableCss(view.getPreviousButton(), isPreviousEnable);
		setButtonEnableCss(view.getNextButton(), isNextEnable);
		setButtonEnableCss(view.getFirstButton(), isFirstEnable);
		setButtonEnableCss(view.getLastButton(), isLastEnable);

		view.setNavText("" + startIndex + " - " + endIndex + " of " + numberOfElements);
	}

	protected void setButtonEnableCss(Anchor anchor, boolean enabled) {
		if (enabled) {
			anchor.setStylePrimaryName("gwt-Anchor-Nav-Button");
		} else {
			anchor.setStylePrimaryName("gwt-Anchor-Nav-Button-disable");
		}
	}

}