package org.palaso.languageforge.client.lex.configure.view;

import org.palaso.languageforge.client.lex.controls.ExtendedComboBox;
import org.palaso.languageforge.client.lex.controls.FastTree;
import org.palaso.languageforge.client.lex.configure.presenter.ConfigureSettingInputSystemsPresenter.IConfigureSettingInputSystemsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.touch.client.Point;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class ConfigureSettingInputSystemsView extends Composite implements
		IConfigureSettingInputSystemsView {

	interface Binder extends UiBinder<Widget, ConfigureSettingInputSystemsView> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	@UiField FastTree tree;
	@UiField HTMLPanel ipaBlock;
	@UiField HTMLPanel scriptregionvariantBlock;
	@UiField HTMLPanel voiceBlock;

//	@UiField SimplePanel customSortPanel;
//	@UiField SimplePanel sortAsAnotherPanel;
	
	@UiField Button btnNew;
	@UiField Button btnMore;	
	
	@UiField ExtendedComboBox inputSystemIdentifierSpecialListBox;
	@UiField ExtendedComboBox inputSystemLangIdPurposeListBox;

	
	@UiField ExtendedComboBox inputSystemScriptListBox;
	@UiField ExtendedComboBox inputSystemRegionListBox;
	
	@UiField Label identifiersLblLangNameRight;
	@UiField Label identifiersLblLangNameLeft;
	
	@UiField TextBox txtAbbreviation;
	@UiField TextBox txtLangIdVariant;
	
	@UiField Anchor readmoreIPA;
	@UiField Anchor readmoreLangID;
	
	
	public ConfigureSettingInputSystemsView() {
		initWidget(binder.createAndBindUi(this));
	}

	public Widget getWidget() {
		return this.asWidget();
	}

	@Override
	public FastTree getFastTree() {
		return tree;
	}

	@Override
	public HTMLPanel getIPAPanel() {
		return ipaBlock;
	}

	@Override
	public HTMLPanel getScriptRegionVariantBlock() {
		return scriptregionvariantBlock;
	}

	@Override
	public HTMLPanel getVoiceBlock() {
		return voiceBlock;
	}

	@Override
	public void setIPAPanelVisable(boolean visible) {
		ipaBlock.setVisible(visible);
	}

	@Override
	public void setScriptRegionVariantBlockVisable(boolean visible) {
		scriptregionvariantBlock.setVisible(visible);
	}

	@Override
	public void setVoiceBlockVisable(boolean visible) {
		voiceBlock.setVisible(visible);
	}

	@Override
	public SimplePanel getCustomSortPanel() {
		//return customSortPanel;
		//TODO function not implemented yet
		return null;
	}

	@Override
	public SimplePanel getSortAsAnotherPanel() {
		//return sortAsAnotherPanel;
		//TODO function not implemented yet
		return null;
	}

	@Override
	public void setCustomSortPanel(boolean visible) {
		//customSortPanel.setVisible(visible);
		//TODO function not implemented yet
	}

	@Override
	public void setSortAsAnotherPanel(boolean visible) {
		//sortAsAnotherPanel.setVisible(visible);
		//TODO function not implemented yet
	}

	@Override
	public HasClickHandlers getBtnMoreClickHandlers() {
		return btnMore;
	}

	@Override
	public HasClickHandlers getBtnNewClickHandlers() {
		return btnNew;
	}

	@Override
	public Point getBtnMoreLocation() {
		return new Point(btnMore.getAbsoluteLeft(), btnMore.getAbsoluteTop());
	}

	@Override
	public Point getBtnNewLocation() {
		return new Point(btnNew.getAbsoluteLeft(), btnNew.getAbsoluteTop());
	}

	
	@Override
	public ExtendedComboBox getSpecialListBox() {
		return inputSystemIdentifierSpecialListBox;
	}

	@Override
	public void setLongLanguageNameLabel(String text) {
		identifiersLblLangNameLeft.setText(text);
		
	}

	@Override
	public void setLanguageCodeLabel(String text) {
		identifiersLblLangNameRight.setText(text);
		
	}

	@Override
	public ExtendedComboBox getSpecialPurposeListBox() {
		return inputSystemLangIdPurposeListBox;
	}

	@Override
	public ExtendedComboBox getSpecialScriptListBox() {
		return inputSystemScriptListBox;
	}

	@Override
	public ExtendedComboBox getSpecialRegionListBox() {
		return inputSystemRegionListBox;
	}

	@Override
	public TextBox getAbbreviationTextBox() {
		return txtAbbreviation;
	}
	
	@Override
	public TextBox getVariantTextBox() {
		return txtLangIdVariant;
	}

	@Override
	public HasClickHandlers getReadMoreIPAClickHandlers() {
		return readmoreIPA;
	}

	@Override
	public HasClickHandlers getReadMoreLangIdClickHandlers() {
		return readmoreLangID;
	}

}
