package org.palaso.languageforge.client.lex.common;

import org.palaso.languageforge.client.lex.model.IanaBaseDataDto;
import org.palaso.languageforge.client.lex.model.settings.inputsystems.SettingInputSystemElementCollationsDto;

public class SettingInputSystemItem {
	private IanaBaseDataDto ianaObj = null;
	private Identifier identifier = null;
	private SettingInputSystemElementCollationsDto collations = null;

	
	
	public SettingInputSystemItem() {
		setIdentifier(new Identifier());
	}

	public IanaBaseDataDto getIanaObj() {
		return ianaObj;
	}

	public void setIanaObj(IanaBaseDataDto data) {
		ianaObj = data;
	}

	public String getDisplayText() {
		String innerIdText = "";
		switch (identifier.getSpecial()) {
		case NONE:
			break;
		case IPA_TRANSCRIPTION:
		case VOICE:
		case SCRIPT_REGION_VARIANT:
			if (!identifier.getIdLangNamePart().trim().isEmpty()) {
				innerIdText = " (" + identifier.getIdLangNamePart() + ")";
			}
			break;
		default:
			break;
		}
		if (ianaObj != null) {
			return ianaObj.getDescription() + innerIdText;
		} else {
			return "Language Not Listed" + innerIdText;
		}
	}

	public String getIdLangCode() {
		String langPart = identifier.getIdLangCodePart();
		if (ianaObj != null) {
			if (langPart.isEmpty()) {
				return ianaObj.getSubtag();
			} else {
				return ianaObj.getSubtag() + "-"
						+ identifier.getIdLangCodePart();
			}
		} else {
			if (langPart.isEmpty()) {
				return "Language Not Listed";
			} else {
				return "Language Not Listed" + "-"
						+ identifier.getIdLangCodePart();
			}
		}
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public SettingInputSystemElementCollationsDto getCollations() {
		return collations;
	}

	public void setCollations(SettingInputSystemElementCollationsDto collations) {
		this.collations = collations;
	}

	public class Identifier {
		private String abbreviation = "";
		private InputSystemIdSpecialType special = InputSystemIdSpecialType.NONE;
		private IanaBaseDataDto region = null;
		private IanaBaseDataDto script = null;
		private String variant = "";
		private InputSystemIdSpecialPurposeType purpose = InputSystemIdSpecialPurposeType.UNSPECIFIED;

		protected Identifier() {

		}

		public String getIdLangNamePart() {
			switch (special) {
			case NONE:
				return "";
			case IPA_TRANSCRIPTION:
				switch (purpose) {
				case UNSPECIFIED:
					return "IPA";

				case ETIC:
					return "IPA-etic";

				case EMIC:
					return "IPA-emic";
				}

			case VOICE:
				return "Voice";
			case SCRIPT_REGION_VARIANT:
				if (script != null && region != null) {
					return script.getSubtag() + "-" + region.getSubtag();
				} else if (script != null) {
					return script.getSubtag();
				} else if (region != null) {
					return region.getSubtag();
				}
			default:
				return "";
			}
		}

		public String getIdLangCodePart() {
			switch (special) {
			case NONE:
				return "";
			case IPA_TRANSCRIPTION:
				// LangaugeCode-fonipa[-x-etic|-x-emic]
				switch (purpose) {
				case UNSPECIFIED:
					return "fonipa";

				case ETIC:
					return "fonipa-x-etic";

				case EMIC:
					return "fonipa-x-emic";
				}

			case VOICE:
				// LanguageCode-Zxxx-x-audio
				return "Zxxx-x-audio";
			case SCRIPT_REGION_VARIANT:
				// LangaugeCode-<Script Code>-<Region Code>-x-<Tidy Variant>
				// Tidy Variant is the variant string with each space delimited
				// token limited to 8 characters, and the spaces replaced with
				// '-'

				if (variant.trim().isEmpty()) {
					return getIdLangNamePart();
				} else {
					String variant_tmp = variant.replace(" ", "-");
					if (variant_tmp.length() > 8) {
						variant_tmp = variant_tmp.substring(0, 8);
					}
					return getIdLangNamePart() + "-x-" + variant_tmp;
				}
			default:
				return "";
			}
		}

		public InputSystemIdSpecialPurposeType getPurpose() {
			return purpose;
		}

		public void setPurpose(InputSystemIdSpecialPurposeType purpose) {
			this.purpose = purpose;
		}

		public String getVariant() {
			if (variant == null) {
				return "";
			}
			return variant;
		}

		public void setVariant(String variant) {
			this.variant = variant;
		}

		public IanaBaseDataDto getRegion() {
			return region;
		}

		public void setRegion(IanaBaseDataDto region) {
			this.region = region;
		}

		public InputSystemIdSpecialType getSpecial() {
			return special;
		}

		public void setSpecial(InputSystemIdSpecialType special) {
			this.special = special;
		}

		public String getAbbreviation() {
			if (abbreviation == null) {
				return "";
			}
			return abbreviation;
		}

		public void setAbbreviation(String abbreviation) {
			this.abbreviation = abbreviation;
		}

		public IanaBaseDataDto getScript() {
			return script;
		}

		public void setScript(IanaBaseDataDto script) {
			this.script = script;
		}

	}

	public class Sorting {

	}
}
