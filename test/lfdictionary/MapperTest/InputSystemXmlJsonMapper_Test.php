<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

use lfbase\mapper\InputSystemXmlJsonMapper;

class TestInputSystemXmlJsonMapper extends UnitTestCase {

	private $FINAL_RESULT_ARRAY = 'Array([ldml] => Array([identity] => Array([version] => Array([number] => )[generation] => Array([date] => 0001-01-01T00:00:00)[language] => Array([type] => en))[collations] => Array()[special] => Array([palaso:abbreviation] => Array([@xmlns] => Array([palaso] => urn://palaso.org/ldmlExtensions/v1)[value] => eng)[palaso:defaultFontFamily] => Array([@xmlns] => Array([palaso] => urn://palaso.org/ldmlExtensions/v1)[value] => Arial)[palaso:defaultFontSize] => Array([@xmlns] => Array([palaso] => urn://palaso.org/ldmlExtensions/v1)[value] => 12)[palaso:version] => Array([@xmlns] => Array([palaso] => urn://palaso.org/ldmlExtensions/v1)[value] => 2))))';
	private $FINAL_RESULT_NEW_XML = '';
	private $FINAL_SOURCE_JSON = '{"ldml":{"identity":{"version":{"number":""},"generation":{"date":"0001-01-01T00:00:00"},"language":{"type":"en"}},"collations":[],"special":{"palaso:abbreviation":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"eng"},"palaso:defaultFontFamily":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"Arial"},"palaso:defaultFontSize":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"12"},"palaso:version":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"2"}}}}';

	function testInputSystemXmlJsonMapper_XmlToJson() {
		$configFilePath = TestPath . "data/template/WritingSystems/en.ldml";
		$xml_str = file_get_contents($configFilePath);
		$doc = new \DOMDocument;
		$doc->preserveWhiteSpace = FALSE;
		$doc->loadXML($xml_str);
		$jsonText = print_r(InputSystemXmlJsonMapper::encodeInputSystemXmlToJson($doc),true);
		$jsonText = str_replace("\n", "", $jsonText);
		$jsonText = str_replace("\r", "", $jsonText);
		$jsonText = str_replace("  ", "", $jsonText);
		$this->assertEqual($jsonText,$this->FINAL_RESULT_ARRAY);
	}

	function testInputSystemXmlJsonMapper_CreateXmlFromJsonArray() {

		$newXmlDoc = new \DOMDocument;
		$newXmlDoc->preserveWhiteSpace = FALSE;
		$newXmlDoc->loadXML(InputSystemXmlJsonMapper::createInputSystemXmlFromJson(json_decode($this->FINAL_SOURCE_JSON)));
		$newXmlText = $newXmlDoc->saveXML();
		$configFilePath = TestPath . "data/template/WritingSystems/en.ldml";
		$xml_str = file_get_contents($configFilePath);
		$doc = new \DOMDocument;
		$doc->preserveWhiteSpace = FALSE;
		$doc->loadXML($xml_str);
		$XmlText = $doc->saveXML();
		$this->assertEqual($newXmlText,$XmlText);
	}
}
?>