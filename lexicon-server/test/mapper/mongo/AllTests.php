<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class AllMapperMongoTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
 		$this->addFile(TEST_PATH . 'mapper/mongo/Date_Test.php');
 		$this->addFile(TEST_PATH . 'mapper/mongo/ArrayOf_Test.php');
 		$this->addFile(TEST_PATH . 'mapper/mongo/MapOf_Test.php');
    }

}

?>
