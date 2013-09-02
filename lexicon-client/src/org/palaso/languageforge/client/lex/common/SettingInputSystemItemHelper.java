package org.palaso.languageforge.client.lex.common;

import java.util.Date;
import java.util.SortedMap;

import org.palaso.languageforge.client.lex.common.enums.InputSystemIdSpecialPurposeType;
import org.palaso.languageforge.client.lex.common.enums.InputSystemIdSpecialType;
import org.palaso.languageforge.client.lex.model.IanaBaseDataDto;
import org.palaso.languageforge.client.lex.model.settings.inputsystems.SettingInputSystemElementCollationsDto;
import org.palaso.languageforge.client.lex.model.settings.inputsystems.SettingInputSystemElementDto;

import com.google.gwt.i18n.client.DateTimeFormat;

public class SettingInputSystemItemHelper {
	SortedMap<String, IanaBaseDataDto> regionsList;
	SortedMap<String, IanaBaseDataDto> scriptTypeList;
	SortedMap<String, IanaBaseDataDto> languagesList;

	public SettingInputSystemItemHelper(SortedMap<String, IanaBaseDataDto> regionsList,
			SortedMap<String, IanaBaseDataDto> scriptTypeList, SortedMap<String, IanaBaseDataDto> languagesList) {
		this.regionsList = regionsList;
		this.scriptTypeList = scriptTypeList;
		this.languagesList = languagesList;
	}

	public SettingInputSystemItem getSettingInputSystemItemFromDto(SettingInputSystemElementDto dto) {

		String languageKey = dto.getIdentity().getLanguageType().toLowerCase();
		return createSettingInputSystemItem(getLanguage(languageKey), dto.getSpecial().getPalasoAbbreviationValue(),
				dto.getIdentity().getVariantType(), getSpecialType(dto), getSpecialPurposeType(dto),
				getScriptType(dto), getRegionType(dto), dto.getCollations());

	}

	public SettingInputSystemElementDto getSettingInputSystemElementDtoFromItem(SettingInputSystemItem sisItem) {

		SettingInputSystemElementDto elementDto = SettingInputSystemElementDto.getNew();
		elementDto.getIdentity()
				.setGenerationDate(DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
		if (sisItem.getIanaObj() != null) {
			// listed language
			elementDto.getIdentity().setLanguageType(sisItem.getIanaObj().getSubtag());
			elementDto.getSpecial().setPalasoLanguageNameValue(sisItem.getIanaObj().getDescription());
		} else {
			// not listed language...
			elementDto.getSpecial().setPalasoLanguageNameValue("Language Not Listed");

		}
		elementDto.getSpecial().setPalasoVersionValue("2");
		if (sisItem.getIdentifier() != null) {
			sisItem.getIdentifier().getSpecial();
			sisItem.getIdentifier().getPurpose();
			if (sisItem.getIdentifier().getAbbreviation() != null) {
				elementDto.getSpecial().setPalasoAbbreviationValue(sisItem.getIdentifier().getAbbreviation());
			}

			if (sisItem.getIdentifier().getScript() != null) {
				elementDto.getIdentity().setScriptType(sisItem.getIdentifier().getScript().getSubtag());
			}
			if (sisItem.getIdentifier().getRegion() != null) {
				elementDto.getIdentity().setTerritoryType(sisItem.getIdentifier().getRegion().getSubtag());
			}
			if (sisItem.getIdentifier().getSpecial() == InputSystemIdSpecialType.NONE) {
				elementDto.getIdentity().setVariantType("");
			} else if (sisItem.getIdentifier().getSpecial() == InputSystemIdSpecialType.SCRIPT_REGION_VARIANT) {
				elementDto.getIdentity().setVariantType(sisItem.getIdentifier().getVariant());
			} else {
				if (sisItem.getIdentifier().getSpecial() == InputSystemIdSpecialType.IPA_TRANSCRIPTION) {
					switch (sisItem.getIdentifier().getPurpose()) {
					case UNSPECIFIED:
						elementDto.getIdentity().setVariantType("fonipa");
						break;
					case EMIC:
						elementDto.getIdentity().setVariantType("fonipa-x-emic");
						break;
					case ETIC:
						elementDto.getIdentity().setVariantType("fonipa-x-etic");
						break;
					}
				} else if (sisItem.getIdentifier().getSpecial() == InputSystemIdSpecialType.VOICE) {
					elementDto.getIdentity().setVariantType("x-audio");
				}

			}
		}
		// Collations not in use, but we can not drop it. so attach it back
		if (sisItem.getCollations() != null) {
			elementDto.setCollations(sisItem.getCollations());
		}
		return elementDto;
	}

	public SettingInputSystemItem getNewSettingInputSystemItem(String languageKey, InputSystemIdSpecialType special,
			SettingInputSystemElementCollationsDto collations) {
		return getNewSettingInputSystemItem(getLanguage(languageKey), special, collations);
	}

	public SettingInputSystemItem getNewSettingInputSystemItem(IanaBaseDataDto ianaData,
			InputSystemIdSpecialType special, SettingInputSystemElementCollationsDto collations) {
		return createSettingInputSystemItem(ianaData, "", "", special, InputSystemIdSpecialPurposeType.UNSPECIFIED,
				null, null, collations);
	}

	public SettingInputSystemItem createSettingInputSystemItem(IanaBaseDataDto ianaData, String abbreviation,
			String variant, InputSystemIdSpecialType specialtype, InputSystemIdSpecialPurposeType purposeType,
			IanaBaseDataDto script, IanaBaseDataDto region, SettingInputSystemElementCollationsDto collations) {

		SettingInputSystemItem newItem = new SettingInputSystemItem();
		newItem.setIanaObj(ianaData);

		if (abbreviation.isEmpty()) {
			if (ianaData != null) {
				newItem.getIdentifier().setAbbreviation(ianaData.getSubtag());
			}
		} else {
			newItem.getIdentifier().setAbbreviation(abbreviation);
		}

		newItem.getIdentifier().setVariant(variant);
		newItem.getIdentifier().setSpecial(specialtype);
		newItem.getIdentifier().setPurpose(purposeType);
		newItem.setCollations(collations);
		if (script != null) {
			newItem.getIdentifier().setScript(script);

		}
		if (region != null) {
			newItem.getIdentifier().setRegion(region);

		}
		return newItem;
	}

	private InputSystemIdSpecialType getSpecialType(SettingInputSystemElementDto element) {
		String variant = element.getIdentity().getVariantType();

		if (variant.startsWith("fonipa")) {
			return InputSystemIdSpecialType.IPA_TRANSCRIPTION;
		} else if (variant.endsWith("-audio")) {
			return InputSystemIdSpecialType.VOICE;
		} else if (getScriptType(element) != null || getRegionType(element) != null) {
			return InputSystemIdSpecialType.SCRIPT_REGION_VARIANT;
		}

		return InputSystemIdSpecialType.NONE;
	}

	private IanaBaseDataDto getLanguage(String key) {
		if (languagesList.get(key) == null) {
			return languagesList.get("qaa");
		}
		return languagesList.get(key);
	}

	private IanaBaseDataDto getScriptType(SettingInputSystemElementDto element) {
		String key = element.getIdentity().getScriptType();
		return scriptTypeList.get(key);
	}

	private IanaBaseDataDto getRegionType(SettingInputSystemElementDto element) {
		String key = element.getIdentity().getTerritoryType();
		return regionsList.get(key);
	}

	private InputSystemIdSpecialPurposeType getSpecialPurposeType(SettingInputSystemElementDto element) {
		String variant = element.getIdentity().getVariantType();

		if (getSpecialType(element) == InputSystemIdSpecialType.IPA_TRANSCRIPTION) {
			if (variant.indexOf("etic") > 6) {
				return InputSystemIdSpecialPurposeType.ETIC;
			} else if (variant.indexOf("emic") > 6) {
				return InputSystemIdSpecialPurposeType.EMIC;
			} else {
				return InputSystemIdSpecialPurposeType.UNSPECIFIED;
			}
		}

		return InputSystemIdSpecialPurposeType.UNSPECIFIED;
	}
}
