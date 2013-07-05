package org.palaso.languageforge.client.lex.addinfo.presenter;

import org.palaso.languageforge.client.lex.common.EntryFieldType;
import org.palaso.languageforge.client.lex.addinfo.AddInfoEventBus;
import org.palaso.languageforge.client.lex.addinfo.view.AddInfoMainView;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = AddInfoMainView.class)
public class AddInfoMainPresenter extends
		BasePresenter<AddInfoMainView, AddInfoEventBus> {

	public interface IView {
	}

	public void onForward() {
	}

	public void onGoToLexMissingInfo(EntryFieldType i) {
		eventBus.startModule(i);
		eventBus.resetEditorView();
	}

	public void onOpenAddInfoAddMoreMeanings() {
		eventBus.startModule(EntryFieldType.DEFINITION);
	}

	public void onOpenAddInfoAddMoreExamples() {
		eventBus.startModule(EntryFieldType.EXAMPLESENTENCE);
	}

	public void onOpenAddInfoAddMorePartsOfSpeech() {
		eventBus.startModule(EntryFieldType.POS);
	}

}