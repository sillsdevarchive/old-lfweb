<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllCommandTests extends TestSuite {
    function __construct() {
        parent::__construct();
		$this->addFile(TEST_PATH . 'CommandTest/GatherWordCommand_Test.php');
		$this->addFile(TEST_PATH . 'CommandTest/GetWordListFromWordPackCommand_Test.php');
		$this->addFile(TEST_PATH . 'CommandTest/GetDomainTreeListCommand_Test.php');
		$this->addFile(TEST_PATH . 'CommandTest/GetDomainQuestionCommand_Test.php');
		$this->addFile(TEST_PATH . 'CommandTest/GetCommentsCommand_Test.php');
		$this->addFile(TEST_PATH . 'CommandTest/SaveCommentsCommand_Test.php');
		$this->addFile(TEST_PATH . 'CommandTest/GetDashboardDataCommand_Test.php');
		$this->addFile(TEST_PATH . 'CommandTest/GetSettingUserFieldsSettingCommand_Test.php');
		$this->addFile(TEST_PATH . 'CommandTest/GetSettingUserTasksSettingCommand_Test.php');
		$this->addFile(TEST_PATH . 'CommandTest/UpdateSettingUserFieldsSettingCommand_Test.php');
		$this->addFile(TEST_PATH . 'CommandTest/UpdateSettingUserTasksSettingCommand_Test.php');
		//$this->addFile(TEST_PATH . 'CommandTest/UpdateDashboardCommand_Test.php'); // Need a real hg repository. so disable it
    }
}

?>