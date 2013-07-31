package org.palaso.languageforge.client.lex.model;

/*
 * This is a readonly Dto object.
 * */
public class ResultDto extends BaseDto<ResultDto> {

	protected ResultDto() {
	};

	// JSNI overlay methods
	public final native boolean isSucceed() /*-{
		return this.succeed;
	}-*/;

	public final native String getCode() /*-{
		if (this.hasOwnProperty('code')) {
			return this.code;
		} else {
			return '';
		}
	}-*/;

}
