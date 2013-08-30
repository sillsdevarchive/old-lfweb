<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllModelTests extends TestSuite {
    function __construct() {
        parent::__construct();
        $this->addFile(TEST_PATH . 'DtoTest/Example_Test.php');
        $this->addFile(TEST_PATH . 'DtoTest/Sense_Test.php');
        $this->addFile(TEST_PATH . 'DtoTest/EntryDTO_Test.php');
        $this->addFile(TEST_PATH . 'DtoTest/ListDTO_Test.php');	
		$this->addFile(TEST_PATH . 'DtoTest/EntryListDTO_Test.php');
		
 		$this->addFile(TEST_PATH . 'DtoTest/AutoListEntry_Test.php');
 		$this->addFile(TEST_PATH . 'DtoTest/AutoListDTO_Test.php');
 		$this->addFile(TEST_PATH . 'DtoTest/DashboardActivitiesDTO_Test.php');
 		$this->addFile(TEST_PATH . 'DtoTest/DomainQuestionDTO_Test.php');
 		$this->addFile(TEST_PATH . 'DtoTest/DomainTreeDTO_Test.php');
 		$this->addFile(TEST_PATH . 'DtoTest/ListEntry_Test.php');
 		$this->addFile(TEST_PATH . 'DtoTest/ProjectStateDTO_Test.php');
		
    }
}

?>