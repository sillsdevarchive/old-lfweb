package org.palaso.social.client;

import org.palaso.languageforge.client.lex.presenter.ModelPairBase;
import org.palaso.social.model.SocialData;

import com.google.gwt.core.client.JsArrayString;


/*
 * TODO: DS 2011-11 class not in use?
 */

public class SocialPresenter extends
		ModelPairBase<SocialPresenter.ISocialView, SocialData> {

	interface ISocialView {
		void addLikes(JsArrayString likes);

		void addNoLikes(JsArrayString noLikes);

		void addScore(int score);

	}

	public SocialPresenter(ISocialView view, SocialData model) {
		super(view, model);
	}

}
