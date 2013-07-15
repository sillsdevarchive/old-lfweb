package org.palaso.languageforge.client.lex.common;


public enum InputSystemIdSpecialType implements IEnum  {
	
	NONE("NONE"),
	IPA_TRANSCRIPTION("IPA_TRANSCRIPTION"),
	VOICE("VOICE"),
	SCRIPT_REGION_VARIANT("SCRIPT_REGION_VARIANT");
	
    private String value;
    
    InputSystemIdSpecialType(String value) {
        this.value = value;
    }

	@Override
	public String getValue() {
		return this.value;
	}

}

