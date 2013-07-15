<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

class TestOfDataConnection extends UnitTestCase {

	function testConstructor_BogusConnection_Throws() {
		$this->expectException('\Exception');
		$d = new \lfbase\common\DataConnection('bogus', 'bogus', 'bogus');
	}
	
	function testConstructor_lfweb_NoThrow() {
		$d = new \lfbase\common\DataConnection(DB_NAME, DB_USER, DB_PASS);
		$this->assertIsA($d->mysqli, 'mysqli');
	}
	
}

?>