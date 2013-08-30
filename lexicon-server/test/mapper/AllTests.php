<?php
require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class AllMapperTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
 		$this->addFile(TEST_PATH . 'mapper/json/AllTests.php');
 		$this->addFile(TEST_PATH . 'mapper/mongo/AllTests.php');
    }

}

?>
