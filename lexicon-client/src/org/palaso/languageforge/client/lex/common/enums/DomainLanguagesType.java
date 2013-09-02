package org.palaso.languageforge.client.lex.common.enums;


public enum DomainLanguagesType  implements IEnum  {

	en("en"),
	es("es"),
	fr("fr"),
	hi("hi"),
	id("id"),
	km("km"),
	ne("ne"),
	ru("ru"),
	th("th"),
	ur("ur"),
	zh_CN("zh-CN");
	
    private String value;
    
    DomainLanguagesType(String value) {
        this.value = value;
    }


	@Override
	public String getValue() {
		return this.value;
	}

}
