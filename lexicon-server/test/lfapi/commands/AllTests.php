<?php
require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class AllCommandsTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
 		$this->addFile(TEST_PATH . 'commands/ProjectUserCommands_Test.php');
 		$this->addFile(TEST_PATH . 'commands/LinkCommands_Test.php');
 		$this->addFile(TEST_PATH . 'commands/UserCommands_Test.php');
 		$this->addFile(TEST_PATH . 'commands/ProjectCommands_Test.php');
    }

}

?>
