<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

require_once('LexAPITestEnvironment.php');

class TestOfLexAPIMissingInfo extends LexAPITestCase {

	function test_getMissingInfo_noThrow() {
		$json = <<<EOD
{"id":3,"method":"getMissingInfo","params":["1"]}
EOD;
		$request = json_decode($json);
		$params = $request->params;
		$field = $params[0];
//  		print_r($field);
		$api = new LexAPI($this->_e->ProjectNodeId, $this->_e->UserId);
		$result = $api->getMissingInfo($field);
//  		print_r($result);
	}

	
}

?>