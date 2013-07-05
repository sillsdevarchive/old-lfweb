<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath .  'autorun.php');

class AllCommandTests extends TestSuite {
    function __construct() {
        parent::__construct();

		$this->addFile(TestPath . 'CommandTest/GetSettingInputSystemsCommand_Test.php');
		$this->addFile(TestPath . 'CommandTest/UpdateSettingInputSystemsCommand_Test.php');
    }
}

?>