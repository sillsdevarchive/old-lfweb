<?php
require_once(dirname(__FILE__) . '/TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class AllTestsNoApi extends TestSuite {
	
    function __construct() {
        parent::__construct();
 		$this->addFile(TEST_PATH . 'model/AllTests.php');
 		$this->addFile(TEST_PATH . 'mapper/AllTests.php');
 		//$this->addFile(TEST_PATH . 'commands/AllTests.php');
 		//$this->addFile(TEST_PATH . 'dto/AllTests.php');
    }
}
?>
