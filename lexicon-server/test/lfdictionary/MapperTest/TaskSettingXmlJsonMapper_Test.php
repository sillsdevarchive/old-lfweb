<?php
use libraries\lfdictionary\environment\LexiconProjectEnvironment;
use libraries\lfdictionary\mapper\TaskSettingXmlJsonMapper;

require_once(dirname(__FILE__) . '/../../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class TestOfTaskSettingXmlJsonMapper extends UnitTestCase {

	private $FINAL_RESULT_ARRAY = 'Array([tasks] => Array([task] => Array([0] => Array([taskName] => Dashboard[visible] => true[index] => 1)[1] => Array([taskName] => Dictionary[visible] => true[index] => 2)[2] => Array([taskName] => AddMissingInfo[visible] => false[label] => Array([$] => Meanings)[longLabel] => Array([$] => Add Meanings)[description] => Array([$] => Add meanings (senses) to entries where they are missing.)[field] => Array([$] => definition)[showFields] => Array([$] => definition)[readOnly] => Array([$] => semantic-domain-ddp4)[writingSystemsToMatch] => Array()[writingSystemsWhichAreRequired] => Array()[index] => 3)[3] => Array([taskName] => AddMissingInfo[visible] => false[label] => Array([$] => Parts of Speech)[longLabel] => Array([$] => Add Parts of Speech)[description] => Array([$] => Add parts of speech to senses where they are missing.)[field] => Array([$] => POS)[showFields] => Array([$] => POS)[readOnly] => Array([$] => definition, ExampleSentence)[writingSystemsToMatch] => Array()[writingSystemsWhichAreRequired] => Array()[index] => 4)[4] => Array([taskName] => AddMissingInfo[visible] => false[label] => Array([$] => Example Sentences)[longLabel] => Array([$] => Add Example Sentences)[description] => Array([$] => Add example sentences to senses where they are missing.)[field] => Array([$] => ExampleSentence)[showFields] => Array([$] => ExampleSentence)[readOnly] => Array([$] => definition)[writingSystemsToMatch] => Array()[writingSystemsWhichAreRequired] => Array()[index] => 5)[5] => Array([taskName] => AddMissingInfo[visible] => false[label] => Array([$] => Base Forms)[longLabel] => Array([$] => Add Base Forms)[description] => Array([$] => Identify the "base form" word that this word is built from. In the printed dictionary, the derived or variant words can optionally be shown as subentries of their base forms.)[field] => Array([$] => BaseForm)[showFields] => Array([$] => BaseForm)[readOnly] => Array()[writingSystemsToMatch] => Array()[writingSystemsWhichAreRequired] => Array()[index] => 6)[6] => Array([taskName] => AdvancedHistory[visible] => false[index] => 7)[7] => Array([taskName] => NotesBrowser[visible] => false[index] => 8)[8] => Array([taskName] => GatherWordList[visible] => false[wordListFileName] => Array([$] => SILCAWL)[wordListWritingSystemId] => Array([$] => en)[index] => 9)[9] => Array([taskName] => GatherWordList[visible] => false[wordListFileName] => Array([$] => SILCAWL-MozambiqueAddendum)[wordListWritingSystemId] => Array([$] => en)[index] => 10)[10] => Array([taskName] => GatherWordsBySemanticDomains[visible] => true[semanticDomainsQuestionFileName] => Array([$] => Ddp4Questions-en.xml)[showMeaningField] => Array([$] => False)[index] => 11))))';
	private $FINAL_RESULT_UPDATED_XML = '<?xml version="1.0" encoding="utf-8"?><configuration version="8"><components><viewTemplate><fields><field index="1"><className>LexEntry</className><dataType>MultiText</dataType><displayName>Word</displayName><enabled>True</enabled><fieldName>EntryLexicalForm</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>False</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><visibility>Visible</visibility><writingSystems><id>qaa</id></writingSystems></field><field index="2"><className>LexEntry</className><dataType>MultiText</dataType><displayName>Citation Form</displayName><enabled>False</enabled><fieldName>citation</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>False</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><visibility>NormallyHidden</visibility><writingSystems><id>qaa</id></writingSystems></field><field index="3"><className>LexSense</className><dataType>MultiText</dataType><displayName>Definition (Meaning)</displayName><enabled>True</enabled><fieldName>definition</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>True</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><visibility>Visible</visibility><writingSystems><id>en</id></writingSystems></field><field index="4"><className>LexSense</className><dataType>MultiText</dataType><displayName>Gloss</displayName><enabled>False</enabled><fieldName>gloss</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>True</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><visibility>NormallyHidden</visibility><writingSystems><id>en</id></writingSystems></field><field index="5"><className>LexEntry</className><dataType>MultiText</dataType><displayName>Literal Meaning</displayName><enabled>False</enabled><fieldName>literal-meaning</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>True</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><visibility>NormallyHidden</visibility><writingSystems><id>en</id></writingSystems></field><field index="6"><className>PalasoDataObject</className><dataType>MultiText</dataType><displayName>Note</displayName><enabled>True</enabled><fieldName>note</fieldName><multiParagraph>True</multiParagraph><spellCheckingEnabled>True</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><visibility>NormallyHidden</visibility><writingSystems><id>en</id></writingSystems></field><field index="7"><className>LexSense</className><dataType>Picture</dataType><displayName>Picture</displayName><enabled>True</enabled><fieldName>Picture</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>False</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><visibility>NormallyHidden</visibility><writingSystems><id>en</id></writingSystems></field><field index="8"><className>LexSense</className><dataType>Option</dataType><displayName>PartOfSpeech</displayName><enabled>True</enabled><fieldName>POS</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>False</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><optionsListFile>PartsOfSpeech.xml</optionsListFile><visibility>Visible</visibility><writingSystems><id>en</id></writingSystems></field><field index="9"><className>LexExampleSentence</className><dataType>MultiText</dataType><displayName>Example Sentence</displayName><enabled>True</enabled><fieldName>ExampleSentence</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>True</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><visibility>Visible</visibility><writingSystems><id>qaa</id></writingSystems></field><field index="10"><className>LexExampleSentence</className><dataType>MultiText</dataType><displayName>Example Translation</displayName><enabled>False</enabled><fieldName>ExampleTranslation</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>True</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><visibility>Visible</visibility><writingSystems><id>en</id></writingSystems></field><field index="11"><className>LexSense</className><dataType>OptionCollection</dataType><displayName>Sem Dom</displayName><enabled>True</enabled><fieldName>semantic-domain-ddp4</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>False</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><optionsListFile>Ddp4.xml</optionsListFile><visibility>NormallyHidden</visibility><writingSystems><id>en</id></writingSystems></field><field index="12"><className>LexEntry</className><dataType>RelationToOneEntry</dataType><displayName>Base Form</displayName><enabled>False</enabled><fieldName>BaseForm</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>False</spellCheckingEnabled><multiplicity>ZeroOr1</multiplicity><visibility>NormallyHidden</visibility><writingSystems><id>qaa</id></writingSystems></field><field index="13"><className>LexEntry</className><dataType>RelationToOneEntry</dataType><displayName>Cross Reference</displayName><enabled>False</enabled><fieldName>confer</fieldName><multiParagraph>False</multiParagraph><spellCheckingEnabled>False</spellCheckingEnabled><multiplicity>ZeroOrMore</multiplicity><visibility>NormallyHidden</visibility><writingSystems><id>qaa</id></writingSystems></field></fields><id>Default View Template</id></viewTemplate></components><tasks><task index="1" taskName="Dashboard" visible="false"/><task index="2" taskName="Dictionary" visible="true"/><task index="3" taskName="AddMissingInfo" visible="false"><label>Meanings</label><longLabel>Add Meanings</longLabel><description>Add meanings (senses) to entries where they are missing.</description><field>definition</field><showFields>definition</showFields><readOnly>semantic-domain-ddp4</readOnly><writingSystemsToMatch/><writingSystemsWhichAreRequired/></task><task index="4" taskName="AddMissingInfo" visible="false"><label>Parts of Speech</label><longLabel>Add Parts of Speech</longLabel><description>Add parts of speech to senses where they are missing.</description><field>POS</field><showFields>POS</showFields><readOnly>definition, ExampleSentence</readOnly><writingSystemsToMatch/><writingSystemsWhichAreRequired/></task><task index="5" taskName="AddMissingInfo" visible="false"><label>Example Sentences</label><longLabel>Add Example Sentences</longLabel><description>Add example sentences to senses where they are missing.</description><field>ExampleSentence</field><showFields>ExampleSentence</showFields><readOnly>definition</readOnly><writingSystemsToMatch/><writingSystemsWhichAreRequired/></task><task index="6" taskName="AddMissingInfo" visible="false"><label>Base Forms</label><longLabel>Add Base Forms</longLabel><description>Identify the "base form" word that this word is built from. In the printed dictionary, the derived or variant words can optionally be shown as subentries of their base forms.</description><field>BaseForm</field><showFields>BaseForm</showFields><readOnly/><writingSystemsToMatch/><writingSystemsWhichAreRequired/></task><task index="7" taskName="AdvancedHistory" visible="false"/><task index="8" taskName="NotesBrowser" visible="false"/><task index="9" taskName="GatherWordList" visible="false"><wordListFileName>SILCAWL</wordListFileName><wordListWritingSystemId>en</wordListWritingSystemId></task><task index="10" taskName="GatherWordList" visible="false"><wordListFileName>SILCAWL-MozambiqueAddendum</wordListFileName><wordListWritingSystemId>en</wordListWritingSystemId></task><task index="11" taskName="GatherWordsBySemanticDomains" visible="true"><semanticDomainsQuestionFileName>Ddp4Questions-en.xml</semanticDomainsQuestionFileName><showMeaningField>False</showMeaningField></task></tasks><addins><addin id="SendReceiveAction" showInWeSay="True"/></addins></configuration>';
	private $NEW_JSON_SETTING = '{"tasks":{"task":[{"taskName":"Dashboard","visible":"false","index":"1"}]}}';
	
	function testTaskSettingXmlJsonMapper_XmlToJson() {
		$configFilePath = LexiconProjectEnvironment::locateConfigFilePath(TEST_PATH. "data/template/","user1");
		$xml_str = file_get_contents($configFilePath);
		$doc = new \DOMDocument;
		$doc->preserveWhiteSpace = FALSE;
		$doc->loadXML($xml_str);
		$componentsDoc = new \DomDocument;
		$componentsDoc->appendChild($componentsDoc->importNode($doc->getElementsByTagName("tasks")->item(0), true));
		$jsonText = print_r(TaskSettingXmlJsonMapper::encodeTaskXmlToJson($componentsDoc),true);
		$jsonText = str_replace("\n", "", $jsonText);
		$jsonText = str_replace("\r", "", $jsonText);
		$jsonText = str_replace("  ", "", $jsonText);
		$this->assertEqual($jsonText,$this->FINAL_RESULT_ARRAY);
	}

	function testTaskSettingXmlJsonMapper_XmlUpdateByJsonArray() {
		$configFilePath = LexiconProjectEnvironment::locateConfigFilePath(TEST_PATH. "data/template/","user1");
		$xml_str = file_get_contents($configFilePath);
		$doc = new \DOMDocument;
		$doc->preserveWhiteSpace = FALSE;
		$doc->loadXML($xml_str);
		TaskSettingXmlJsonMapper::updateTaskXmlFromJson(json_decode($this->NEW_JSON_SETTING),$doc);
		$xmlText = $doc->saveXML();
		// let DomDocument reformat it.
		$updateXmlDoc = new \DOMDocument;
		$updateXmlDoc->preserveWhiteSpace = FALSE;
		$updateXmlDoc->loadXML($this->FINAL_RESULT_UPDATED_XML);
		$updateXmlText = $updateXmlDoc->saveXML();
		$this->assertEqual($updateXmlText,$xmlText);
	}
}
?>