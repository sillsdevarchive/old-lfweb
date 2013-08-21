package org.palaso.languageforge.client.lex.model;

/*
 * This is a readonly Dto object.
 * */
public class EntryMetadataDto extends BaseDto<EntryMetadataDto> {

	protected EntryMetadataDto() {
	};

	public final native String getCreatedbyId() /*-{
		if (this.hasOwnProperty('crid')) {
			if (this.crid != null) {
				return this.crid;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getCreatedby() /*-{
		if (this.hasOwnProperty('crname')) {
			if (this.crname != null) {
				return this.crname;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getCreatedDate() /*-{
		if (this.hasOwnProperty('crdate')) {
			if (this.crdate != null) {
				return this.crdate;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getModifiedById() /*-{
		if (this.hasOwnProperty('modid')) {
			if (this.modid != null) {
				return this.modid;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getModifiedBy() /*-{
		if (this.hasOwnProperty('modname')) {
			if (this.modname != null) {
				return this.modname;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

	public final native String getModifiedDate() /*-{
		if (this.hasOwnProperty('moddate')) {
			if (this.moddate != null) {
				return this.moddate;
			} else {
				return '';
			}
		} else {
			return '';
		}
	}-*/;

}
