<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class TestOfDataConnector extends UnitTestCase {

	function testConnect_Defaults_SameReference() {
		$d1 = \lfbase\common\DataConnector::connect();
		$this->assertIsA($d1, '\lfbase\common\DataConnection');
		$d2 = \lfbase\common\DataConnector::connect();
		$this->assertReference($d1, $d2);
	}
	
}

?>