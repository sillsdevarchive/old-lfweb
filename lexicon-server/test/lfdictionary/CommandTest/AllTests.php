<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllCommandTests extends TestSuite {
    function __construct() {
        parent::__construct();

		$this->addFile(TEST_PATH . 'CommandTest/GetSettingInputSystemsCommand_Test.php');
		$this->addFile(TEST_PATH . 'CommandTest/UpdateSettingInputSystemsCommand_Test.php');
    }
}

?>