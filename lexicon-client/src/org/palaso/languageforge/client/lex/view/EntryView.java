package org.palaso.languageforge.client.lex.view;

import org.palaso.languageforge.client.lex.presenter.EntryPresenter.IEntryView;
import org.palaso.languageforge.client.lex.presenter.MultiTextPresenter.IMultiTextView;
import org.palaso.languageforge.client.lex.presenter.SensePresenter.ISenseView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class EntryView extends Composite implements IEntryView {

	interface Binder extends UiBinder<Widget, EntryView> {
	}

	private static final Binder binder = GWT.create(Binder.class);
	@UiField
	HTMLPanel htmlWordPanel;
	@UiField
	HTMLPanel htmlSensePanel;
	@UiField
	Anchor addMoreMeaningLink;

	public EntryView() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void addEntryMultiText(String label, IMultiTextView view, boolean addEndingSpaceCell) {
		htmlWordPanel.add(getLabelWithEntryMultiTextRow(label, view, addEndingSpaceCell));
	}

	private SimplePanel getLabelWithEntryMultiTextRow(String label,
			IMultiTextView view, boolean addEndingSpaceCell) {
		SimplePanel rowPanel = new SimplePanel();
		FlowPanel rowCon = new FlowPanel();

		rowPanel.setStyleName("lex-dc-row");
		// rowPanel.getElement().setId("lex-dc-row");
		rowCon.getElement().setId("firefox-bug-fix");
		rowPanel.add(rowCon);

		SimplePanel textPanel = new SimplePanel();
		SimplePanel widgetPanel = new SimplePanel();
		SimplePanel endingSpacePanel = new SimplePanel();
		textPanel.setStyleName("lex-dc-column");
		textPanel.getElement().setId("c1");
		// textPanel.setWidth("130px");

		widgetPanel.setStyleName("lex-dc-column");
		widgetPanel.getElement().setId("c2");

		endingSpacePanel.setStyleName("lex-dc-column");
		endingSpacePanel.getElement().setId("c3-40PIX");

		Label textlabel = new Label();
		textlabel.setText(label);
		// textlabel.setWidth("130px");
		textlabel.setStyleName("entryViewLabel");

		widgetPanel.add(view.getWidget());
		textPanel.add(textlabel);

		rowCon.add(textPanel);
		rowCon.add(widgetPanel);
		if (addEndingSpaceCell){
			rowCon.add(endingSpacePanel);
		}
		return rowPanel;
	}

	@Override
	public void addSense(ISenseView senseView) {
		addSenseBody(senseView);
	}

	@Override
	public void addSenseBody(ISenseView senseView) {
		htmlSensePanel.add(senseView.getWidget());

	}

	@Override
	public IMultiTextView createMultiTextView() {
		return new MultiTextView();
	}

	@Override
	public ISenseView createSenseView() {
		return new SenseView();
	}

	@Override
	public void clear() {
		htmlWordPanel.clear();
		htmlSensePanel.clear();
	}

	@Override
	public HasClickHandlers getAddNewSenseClickHandlers() {
		return addMoreMeaningLink;
	}

	@Override
	public void setAddNewButtonVisible(boolean visible) {
		addMoreMeaningLink.setVisible(visible);
	}

	@Override
	public void removeSense(ISenseView view) {
		htmlSensePanel.remove(view.getWidget());
	}


}
