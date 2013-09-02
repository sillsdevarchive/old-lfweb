package org.palaso.languageforge.client.lex.common.enums;


public enum SettingFieldMultiplicityType  implements IEnum  {

	
	// DO not change name definition here!!! used for JSON mapping
	UNDEFINED("UNDEFINED"),
	ZEROOR1("ZEROOR1"),
	ZEROORMORE("ZEROORMORE");
	
    private String value;
    
    SettingFieldMultiplicityType(String value) {
        this.value = value;
    }


	@Override
	public String getValue() {
		return this.value;
	}

}
