package org.palaso.languageforge.client.lex.controls.presenter;

public class ModelPairBase<V, M> {

	protected V view;
	protected M model;

	protected ModelPairBase(V view, M model) {
		this.view = view;
		this.model = model;
	}

	public V getView() {
		return view;
	}

	public M getModel() {
		return model;
	}

}
