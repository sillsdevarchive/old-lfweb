<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

require_once('LexAPITestEnvironment.php');

class TestOfLexAPIDeleteEntry extends LexAPITestCase {

	function test_deleteEntry_noThrow() {
		$api = new LexAPI($this->_e->ProjectNodeId, $this->_e->UserId);
		$guid = '1234';
		$sha = '5678';
		$result = $api->deleteEntry($guid, $sha);
	}

	
}

?>