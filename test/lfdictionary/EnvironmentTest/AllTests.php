<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class AllEnvironmentTests extends TestSuite {

	function __construct() {
		parent::__construct();
		$this->addFile(TestPath . 'EnvironmentTest/ProjectAccess_Test.php');
		$this->addFile(TestPath . 'EnvironmentTest/ProjectPermission_Test.php');
		$this->addFile(TestPath . 'EnvironmentTest/ProjectRole_Test.php');
		$this->addFile(TestPath . 'EnvironmentTest/ProjectModel_Test.php');
		$this->addFile(TestPath . 'EnvironmentTest/UserModel_Test.php');
		
// 		$this->addFile(TestPath . 'EnvironmentTest/CommunityModel_Test.php');
// 		$this->addFile(TestPath . 'EnvironmentTest/ProjectRepository_Test.php');
	}
}

?>