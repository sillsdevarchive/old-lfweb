package org.palaso.languageforge.client.lex.common.enums;


public enum EntryFieldType  implements IEnum  {

	ENTRYLEXICALFORM("ENTRYLEXICALFORM"),
	DEFINITION("DEFINITION"),
	NEWDEFINITION("NEWDEFINITION"),
	POS("POS"),
	EXAMPLESENTENCE("EXAMPLESENTENCE"),
	NEWEXAMPLESENTENCE("NEWEXAMPLESENTENCE"),
	EXAMPLETRANSLATION("EXAMPLETRANSLATION");
	
	
    private String value;
    
    EntryFieldType(String value) {
        this.value = value;
    }


	@Override
	public String getValue() {
		return this.value;
	}

}
