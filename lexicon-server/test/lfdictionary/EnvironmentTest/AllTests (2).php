<?php
require_once(dirname(__FILE__) . '/../../testconfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllEnvironmentTests extends TestSuite {
    function __construct() {
        parent::__construct();        
		//$this->addFile(TEST_PATH . 'EnvironmentTest/ProjectModel_Test.php'); // TODO This test should come back here
		//$this->addFile(TEST_PATH . 'EnvironmentTest/LexProjectUserSettings_Test.php'); // TODO This test should come back here
		//$this->addFile(TEST_PATH . 'EnvironmentTest/LanguageDepotImporter_Test.php'); // This test suite can be long running > 10 seconds
		$this->addFile(TEST_PATH . 'EnvironmentTest/ProjectState_Test.php'); 
		$this->addFile(TEST_PATH . 'EnvironmentTest/LexProject_Test.php'); 
		// 		$this->addFile(TEST_PATH . 'EnvironmentTest/LexiconProjectEnvironment_Test.php');
    }
}

?>