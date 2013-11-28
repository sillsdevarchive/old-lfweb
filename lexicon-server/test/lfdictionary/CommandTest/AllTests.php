<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllCommandTests extends TestSuite {
	
	function __construct() {
		parent::__construct();
		
		// originally included tests, these pass IJH 2013-11 
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/GetDomainQuestionCommand_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/GetDomainTreeListCommand_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/GetSettingUserFieldsSettingCommand_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/GetSettingUserTasksSettingCommand_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/GetWordListFromWordPackCommand_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/UpdateSettingUserFieldsSettingCommand_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/UpdateSettingUserTasksSettingCommand_Test.php');
		
		// originally included tests, these fail IJH 2013-11
		//$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/GatherWordCommand_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/GetCommentsCommand_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/GetDashboardDataCommand_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/SaveCommentsCommand_Test.php');
		//$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/UpdateDashboardCommand_Test.php'); // Need a real hg repository. so disable it

		// added other tests in same folder, these pass IJH 2013-11
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/GetSettingInputSystemsCommand_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/GetWordCommand_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/UpdateSettingInputSystemsCommand_Test.php');
		
		// added other tests in same folder, these fail IJH 2013-11
		
	}
}

?>