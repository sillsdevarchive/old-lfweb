<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath .  'autorun.php');

class AllCommandTests extends TestSuite {
    function __construct() {
        parent::__construct();
		$this->addFile(TestPath . 'CommandTest/GatherWordCommand_Test.php');
		$this->addFile(TestPath . 'CommandTest/GetWordListFromWordPackCommand_Test.php');
		$this->addFile(TestPath . 'CommandTest/GetDomainTreeListCommand_Test.php');
		$this->addFile(TestPath . 'CommandTest/GetDomainQuestionCommand_Test.php');
		$this->addFile(TestPath . 'CommandTest/GetCommentsCommand_Test.php');
		$this->addFile(TestPath . 'CommandTest/SaveCommentsCommand_Test.php');
		$this->addFile(TestPath . 'CommandTest/GetDashboardDataCommand_Test.php');
		$this->addFile(TestPath . 'CommandTest/GetSettingUserFieldsSettingCommand_Test.php');
		$this->addFile(TestPath . 'CommandTest/GetSettingUserTasksSettingCommand_Test.php');
		$this->addFile(TestPath . 'CommandTest/UpdateSettingUserFieldsSettingCommand_Test.php');
		$this->addFile(TestPath . 'CommandTest/UpdateSettingUserTasksSettingCommand_Test.php');
		//$this->addFile(TestPath . 'CommandTest/UpdateDashboardCommand_Test.php'); // Need a real hg repository. so disable it
    }
}

?>