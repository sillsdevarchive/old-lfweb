<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath .  'autorun.php');

class AllModelTests extends TestSuite {
    function __construct() {
        parent::__construct();
        $this->addFile(TestPath . 'DtoTest/Example_Test.php');
        $this->addFile(TestPath . 'DtoTest/Sense_Test.php');
        $this->addFile(TestPath . 'DtoTest/EntryDTO_Test.php');
        $this->addFile(TestPath . 'DtoTest/ListDTO_Test.php');	
		$this->addFile(TestPath . 'DtoTest/EntryListDTO_Test.php');
		
 		$this->addFile(TestPath . 'DtoTest/AutoListEntry_Test.php');
 		$this->addFile(TestPath . 'DtoTest/AutoListDTO_Test.php');
 		$this->addFile(TestPath . 'DtoTest/DashboardActivitiesDTO_Test.php');
 		$this->addFile(TestPath . 'DtoTest/DomainQuestionDTO_Test.php');
 		$this->addFile(TestPath . 'DtoTest/DomainTreeDTO_Test.php');
 		$this->addFile(TestPath . 'DtoTest/ListEntry_Test.php');
 		$this->addFile(TestPath . 'DtoTest/ProjectStateDTO_Test.php');
		
    }
}

?>