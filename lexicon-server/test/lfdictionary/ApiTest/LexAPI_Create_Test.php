<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

require_once('LexAPITestEnvironment.php');

// TODO Create (as an api at least) is not complete at this time CP 2012-10
// TODO Review: This file is currently excluded from all tests.
class TestOfLexAPICreate extends LexAPITestCase {

	function test_create_noThrow() {
		$api = new LexAPI($this->_e->ProjectNodeId, $this->_e->UserId);
		$result = $api->create();
		// 		print_r($result);
	}
	
}

?>