<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllModelTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
		
		// originally included tests, these pass IJH 2013-11 
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/CommunityDTO_Test.php');

		// originally included tests, these fail IJH 2013-11
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ClientEnvironmentDto_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ConversationDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ProjectDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ResultDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/UserDTO_Test.php');
		
    	// added other tests in same folder, these pass IJH 2013-11
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/AutoListDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/AutoListEntry_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/DashboardActivitiesDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/DomainQuestionDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/DomainTreeDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ListDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ListEntry_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ProjectStateDTO_Test.php');
		
        // added other tests in same folder, these fail IJH 2013-11
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/EntryListDTO_Test.php');
    }
}

?>