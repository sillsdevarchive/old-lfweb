package org.palaso.languageforge.client.lex.common;

public enum SettingFieldVisibilityType  implements IEnum  {

	
	// DO not change name definition here!!! used for JSON mapping
	UNDEFINED("UNDEFINED"),
	VISIBLE("VISIBLE"),
	NORMALLYHIDDEN("NORMALLYHIDDEN");
	
    private String value;
    
    SettingFieldVisibilityType(String value) {
        this.value = value;
    }


	@Override
	public String getValue() {
		return this.value;
	}

}
