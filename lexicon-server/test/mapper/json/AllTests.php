<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class AllMapperJsonTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
 		$this->addFile(TEST_PATH . 'mapper/json/Date_Test.php');
 		$this->addFile(TEST_PATH . 'mapper/json/ArrayOf_Test.php');
 		$this->addFile(TEST_PATH . 'mapper/json/MapOf_Test.php');
    }

}

?>
