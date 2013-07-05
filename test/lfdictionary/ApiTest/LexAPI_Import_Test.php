<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class TestOfLexAPI extends UnitTestCase {

	/* This test is timing sensitive, and only useful for helping with debugging CP 2012-09
	function test_import_works() {
		$api = new LexAPI(293, 61);
		$result = $api->import('LanguageDepot', 'testpal-dictionary', 'test', 'tset23');
		print_r($result);
		if ($result['result'] == 'Importing') {
			$this->assertTrue(true, 'Importing OK');
			$i = 0;
			while ($i < 5) {
				sleep(1);
				$result = $api->state();
				print_r($result);
				$i++;
			}
		}
// 		$this->assertNotNull($result);
	}
	*/
}

?>