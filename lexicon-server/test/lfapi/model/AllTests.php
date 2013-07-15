<?php
require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class AllModelTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
 		$this->addFile(TEST_PATH . 'model/UserModel_Test.php');
 		$this->addFile(TEST_PATH . 'model/ProjectModel_Test.php');
 		$this->addFile(TEST_PATH . 'model/MultipleModel_Test.php');
    }

}

?>
