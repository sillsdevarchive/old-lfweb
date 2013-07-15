<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

require_once('LexAPITestEnvironment.php');

class TestOfLexAPIGetEntries extends LexAPITestCase {

	function test_getEntries_noThrow() {
		$api = new LexAPI($this->_e->ProjectNodeId, $this->_e->UserId);
		$result = $api->getList(0, 50);
// 		print_r($result);
	}

	
}

?>