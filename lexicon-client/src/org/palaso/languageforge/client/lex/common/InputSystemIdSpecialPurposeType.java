package org.palaso.languageforge.client.lex.common;


public enum InputSystemIdSpecialPurposeType implements IEnum  {

	UNSPECIFIED("UNSPECIFIED"),
	ETIC("ETIC"),
	EMIC("EMIC");
	
    private String value;
    
    InputSystemIdSpecialPurposeType(String value) {
        this.value = value;
    }

	@Override
	public String getValue() {
		return this.value;
	}
	
	
	

}

