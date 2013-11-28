<?php

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class AllEnvironmentTests extends TestSuite {

	function __construct() {
		parent::__construct();
		
        // originally included tests, these pass IJH 2013-11
        
        // originally included tests, these fail IJH 2013-11
// 		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/CommunityModel_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/ProjectAccess_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/ProjectModel_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/ProjectPermission_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/ProjectRole_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/UserModel_Test.php');
		
        // added other tests in same folder, these pass IJH 2013-11
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/LanguageDepotProjectDatabase_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/LexProjectFixer_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/LexProject_Test.php');
		
        // added other tests in same folder, these fail IJH 2013-11
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/Drupal7EnvironmentMapper_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/LanguageDepotImporter_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/LexProjectUserSettings_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/ProjectEnvironment_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/ProjectState_Test.php');
	}
}

?>