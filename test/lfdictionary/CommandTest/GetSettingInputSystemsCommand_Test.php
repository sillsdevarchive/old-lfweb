<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class TestOfGetSettingInputSystemsCommand extends UnitTestCase {

	private $FINAL_RESULT='{"list":[{"ldml":{"identity":{"version":{"number":""},"generation":{"date":"0001-01-01T00:00:00"},"language":{"type":"en"}},"collations":[],"special":{"palaso:abbreviation":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"eng"},"palaso:defaultFontFamily":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"Arial"},"palaso:defaultFontSize":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"12"},"palaso:version":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"2"}}}},{"ldml":{"identity":{"version":{"number":""},"generation":{"date":"0001-01-01T00:00:00"},"language":{"type":"qaa"}},"collations":[],"special":{"palaso:abbreviation":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"qaa"},"palaso:defaultFontFamily":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"Arial"},"palaso:defaultFontSize":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"12"},"palaso:languageName":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"Abau"},"palaso:version":{"@xmlns":{"palaso":"urn:\\/\\/palaso.org\\/ldmlExtensions\\/v1"},"value":"2"}}}}]}';
	
	function testGetSettingInputSystemsCommand_TwoEntries() {
		$command = new \lfbase\commands\GetSettingInputSystemsCommand(TestPath . "data/template");
		$result = json_encode($command->execute());
		$this->assertEqual($this->FINAL_RESULT, $result);
	}
}

?>