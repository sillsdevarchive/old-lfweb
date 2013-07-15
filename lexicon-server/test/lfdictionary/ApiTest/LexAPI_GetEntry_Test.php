<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

require_once('LexAPITestEnvironment.php');

class TestOfLexAPIGetEntry extends LexAPITestCase {

	function test_getEntry_noThrow() {
		$api = new LexAPI($this->_e->ProjectNodeId, $this->_e->UserId);
		$result = $api->getEntry('1234');
// 		print_r($result);
	}

}

?>