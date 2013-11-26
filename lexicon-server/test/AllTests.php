<?php
require_once(dirname(__FILE__) . '/TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class AllTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
 		$this->addFile(TEST_PATH . 'AllTests_NoApi.php');
		$this->addFile(TEST_PATH . 'api/AllTests.php');
    }
}
?>
