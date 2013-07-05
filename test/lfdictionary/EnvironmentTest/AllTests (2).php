<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath .  'autorun.php');

class AllEnvironmentTests extends TestSuite {
    function __construct() {
        parent::__construct();        
		//$this->addFile(TestPath . 'EnvironmentTest/ProjectModel_Test.php'); // TODO This test should come back here
		//$this->addFile(TestPath . 'EnvironmentTest/LexProjectUserSettings_Test.php'); // TODO This test should come back here
		//$this->addFile(TestPath . 'EnvironmentTest/LanguageDepotImporter_Test.php'); // This test suite can be long running > 10 seconds
		$this->addFile(TestPath . 'EnvironmentTest/ProjectState_Test.php'); 
		$this->addFile(TestPath . 'EnvironmentTest/LexProject_Test.php'); 
		// 		$this->addFile(TestPath . 'EnvironmentTest/LexiconProjectEnvironment_Test.php');
    }
}

?>