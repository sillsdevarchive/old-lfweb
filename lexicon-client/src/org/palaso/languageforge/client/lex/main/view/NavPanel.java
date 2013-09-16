package org.palaso.languageforge.client.lex.main.view;

import org.palaso.languageforge.client.lex.main.presenter.NavPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class NavPanel extends Composite implements NavPresenter.INavView {

	interface Binder extends UiBinder<Widget, NavPanel> {
	}

	interface SelectionStyle extends CssResource {
		String boldTitle();
	}

	private static final Binder binder = GWT.create(Binder.class);

	@UiField
	HTMLPanel dashBoardPanel;
	@UiField
	HTMLPanel gatherWordsPanel;
	@UiField
	HTMLPanel addInformationPanel;
	@UiField
	HTMLPanel configurePanel;
	@UiField
	HTMLPanel reviewRecentChangesPanel;
	@UiField
	HTMLPanel gatherFromTextsPanel;
	@UiField
	HTMLPanel gatherFromSemanticDomainsPanel;
	@UiField
	HTMLPanel gatherFromWordListPanel;
	@UiField
	HTMLPanel reviewBrowseEditPanel;

	@UiField
	HTMLPanel addMeaningPanel;
	@UiField
	HTMLPanel addGrammaticalPanel;
	@UiField
	HTMLPanel addExamplePanel;

	// @UiField
	// Anchor gatherFromOtherLanguage;

	@UiField
	Anchor gatherFromSemanticDomains;
	
	@UiField
	Anchor gatherFromTexts;

	@UiField
	Anchor gatherFromWordList;

	 @UiField
	 Anchor reviewRecentChanges;

	// @UiField
	// Anchor reviewToDo;

	@UiField
	Anchor reviewBrowseEdit;

	@UiField
	Anchor addMeaning;

	@UiField
	Anchor addExample;

	@UiField
	Anchor addGrammatical;

	@UiField
	Anchor settings;

	@UiField
	Anchor dashBoard;
	
	@UiField
	Element dashBoardPanelLi;
	
	@UiField
	Element gatherWordsLi;
	
	@UiField
	Element configurePanelLi;
	

	@UiField
	Element addInformationPanelLi;
	


	public NavPanel() {
		initWidget(binder.createAndBindUi(this));
	}

	public Widget getViewWidget() {
		return this;
	}

	// @Override
	// public HasClickHandlers getGatherFromOtherLanguageClicker() {
	// return gatherFromOtherLanguage;
	// }

	@Override
	public HasClickHandlers getGatherFromTextsClickHandlers() {
		return gatherFromTexts;
	}

	@Override
	public HasClickHandlers getGatherFromWordListClickHandlers() {
		return gatherFromWordList;
	}

	 @Override
	 public HasClickHandlers getReviewRecentChangesClickHandlers() {
	 return reviewRecentChanges;
	 }
	//
	// @Override
	// public HasClickHandlers getReviewToDo() {
	// return reviewToDo;
	// }

	@Override
	public HasClickHandlers getReviewBrowseEditClickHandlers() {
		return reviewBrowseEdit;
	}

	@Override
	public HasClickHandlers getAddMeaningClickHandlers() {
		return addMeaning;
	}

	@Override
	public HasClickHandlers getAddExampleClickHandlers() {
		return addExample;
	}

	@Override
	public HasClickHandlers getAddGrammaticalClickHandlers() {
		return addGrammatical;
	}

	@Override
	public HasClickHandlers getSettingsClickHandlers() {
		return settings;
	}

	private void boldTitle(Anchor e) {
		e.addStyleName("mainNavBarboldTitle");
	}

	private void normalTitle(Anchor e) {
		e.removeStyleName("mainNavBarboldTitle");
	}

	@Override
	public void setBoldStyleAddGrammatical(boolean bold) {
		if (bold) {
			boldTitle(addGrammatical);
		} else {
			normalTitle(addGrammatical);
		}
	}

	@Override
	public void setBoldStyleAddMeaning(boolean bold) {
		if (bold) {
			boldTitle(addMeaning);
		} else {
			normalTitle(addMeaning);
		}
	}

	@Override
	public void setBoldStyleAddExample(boolean bold) {
		if (bold) {
			boldTitle(addExample);
		} else {
			normalTitle(addExample);
		}
	}

	@Override
	public void setBoldStyleReviewBrowseEdit(boolean bold) {
		if (bold) {
			boldTitle(reviewBrowseEdit);
		} else {
			normalTitle(reviewBrowseEdit);
		}
	}

	// @Override
	// public void setBoldStyleGatherFromOtherLanguage(boolean bold) {
	// if (bold)
	// {
	// boldTitle(gatherFromOtherLanguage);
	// }else
	// {
	// normalTitle(gatherFromOtherLanguage);
	// }
	// }

	@Override
	public void setBoldStyleReviewRecentChanges(boolean bold) {
		if (bold) {
			boldTitle(reviewRecentChanges);
		} else {
			normalTitle(reviewRecentChanges);
		}
	}
	
	@Override
	public void setBoldStyleGatherFromTexts(boolean bold) {
		if (bold) {
			boldTitle(gatherFromTexts);
		} else {
			normalTitle(gatherFromTexts);
		}
	}

	@Override
	public void setBoldStyleGatherFromWordList(boolean bold) {
		if (bold) {
			boldTitle(gatherFromWordList);
		} else {
			normalTitle(gatherFromWordList);
		}
	}

	@Override
	public void setBoldStyleSettings(boolean bold) {
		if (bold) {
			boldTitle(settings);
		} else {
			normalTitle(settings);
		}

	}


	@Override
	public void setReviewRecentChangesVisible(boolean visible) {
		reviewRecentChangesPanel.setVisible(visible);
		checkIsAllGatherWordsMenuItemVisible();
	}
	
	@Override
	public void setGatherFromTextsVisible(boolean visible) {
		gatherFromTextsPanel.setVisible(visible);
		checkIsAllGatherWordsMenuItemVisible();
	}

	@Override
	public void setGatherFromWordListVisible(boolean visible) {
		gatherFromWordListPanel.setVisible(visible);
		checkIsAllGatherWordsMenuItemVisible();
	}

	@Override
	public void setReviewBrowseEditVisible(boolean visible) {
		reviewBrowseEditPanel.setVisible(visible);
		checkIsAllGatherWordsMenuItemVisible();
	}

	@Override
	public void setGatherWordsMenuVisible(boolean visible) {
		gatherWordsPanel.setVisible(visible);
	}



	@Override
	public void setConfigureMenuVisible(boolean visible) {
		configurePanel.setVisible(visible);
	}

	@Override
	public void setAddInformationMenuVisible(boolean visible) {
		addInformationPanel.setVisible(visible);
	}

	@Override
	public void setAddMeaningVisible(boolean visible) {
		addMeaningPanel.setVisible(visible);
		checkIsAddInformationMenuItemVisible();
	}

	@Override
	public void setAddGrammaticalPanelVisible(boolean visible) {
		addGrammaticalPanel.setVisible(visible);
		checkIsAddInformationMenuItemVisible();
	}

	@Override
	public void setAddExamplePanelVisible(boolean visible) {
		addExamplePanel.setVisible(visible);
		checkIsAddInformationMenuItemVisible();
	}


	private void checkIsAllGatherWordsMenuItemVisible() {
		if (reviewBrowseEditPanel.isVisible() || gatherFromTextsPanel.isVisible()
				|| gatherFromWordListPanel.isVisible()
				|| reviewBrowseEditPanel.isVisible() || gatherFromSemanticDomainsPanel.isVisible()) {
			setGatherWordsMenuVisible(true);
		} else {
			setGatherWordsMenuVisible(false);
		}
	}

	private void checkIsAddInformationMenuItemVisible() {
		if (addGrammaticalPanel.isVisible() || addMeaningPanel.isVisible()
				|| addExamplePanel.isVisible()) {
			setAddInformationMenuVisible(true);
		} else {
			setAddInformationMenuVisible(false);
		}
	}

	@Override
	public boolean getReviewRecentChangesVisible() {
		return reviewRecentChangesPanel.isVisible();
	}

	
	@Override
	public boolean getGatherFromTextsVisible() {
		return gatherFromTextsPanel.isVisible();
	}

	@Override
	public boolean getGatherFromWordListVisible() {
		return gatherFromWordListPanel.isVisible();
	}

	@Override
	public boolean getReviewBrowseEditVisible() {
		return reviewBrowseEditPanel.isVisible();
	}

	@Override
	public boolean getAddMeaningVisible() {
		return addMeaningPanel.isVisible();
	}

	@Override
	public boolean getAddGrammaticalPanelVisible() {
		return addGrammaticalPanel.isVisible();
	}

	@Override
	public boolean getAddExamplePanelVisible() {
		return addExamplePanel.isVisible();
	}

	@Override
	public void setGatherWordsMenuExpended(boolean expended) {
		if (expended)
		{
			if (gatherWordsLi.getClassName().indexOf("active")<0)
			{
				gatherWordsLi.addClassName("active");
			}
		}else
		{
			gatherWordsLi.removeClassName("active");
		}		
	}


	@Override
	public void setConfigureMenuExpended(boolean expended) {
		if (expended)
		{
			if (configurePanelLi.getClassName().indexOf("active")<0)
			{
				configurePanelLi.addClassName("active");
			}
		}else
		{
			configurePanelLi.removeClassName("active");
		}
	}

	@Override
	public void setAddInformationMenuExpended(boolean expended) {
		if (expended)
		{
			if (addInformationPanelLi.getClassName().indexOf("active")<0)
			{
				addInformationPanelLi.addClassName("active");
			}
		}else
		{
			addInformationPanelLi.removeClassName("active");
		}
	}

	@Override
	public HasClickHandlers getGatherFromSemanticDomainsClickHandlers() {
		return gatherFromSemanticDomains;
	}

	@Override
	public void setBoldStyleGatherFromSemanticDomains(boolean bold) {
		if (bold) {
			boldTitle(gatherFromSemanticDomains);
		} else {
			normalTitle(gatherFromSemanticDomains);
		}
	}

	@Override
	public void setGatherFromSemanticDomainsVisible(boolean visible) {
		gatherFromSemanticDomainsPanel.setVisible(visible);
		checkIsAllGatherWordsMenuItemVisible();
	}

	@Override
	public boolean getGatherFromSemanticDomainsVisible() {
		return gatherFromSemanticDomainsPanel.isVisible();
	}

	@Override
	public boolean getConfigureMenuVisible() {
		return configurePanel.isVisible();
	}


	@Override
	public HasClickHandlers getDashboardClickHandlers() {
		return dashBoard;
	}

	@Override
	public void setBoldStyleDashboard(boolean bold) {
		if (bold) {
			boldTitle(dashBoard);
		} else {
			normalTitle(dashBoard);
		}
		
	}

	@Override
	public boolean getDashboardMenuVisible() {
		return dashBoardPanel.isVisible();
	}

	@Override
	public void setDashboardMenuExpended(boolean expended) {
		if (expended)
		{
			if (dashBoardPanelLi.getClassName().indexOf("active")<0)
			{
				dashBoardPanelLi.addClassName("active");
			}
		}else
		{
			dashBoardPanelLi.removeClassName("active");
		}
		
	}

	@Override
	public void setDashboardMenuVisible(boolean visible) {
		dashBoardPanel.setVisible(visible);
	}

}
