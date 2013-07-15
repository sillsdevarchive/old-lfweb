<?php 
use lfbase\environment\ProjectRole;
use lfbase\environment\ProjectPermission;
use lfbase\environment\ProjectAccess;
use lfbase\environment\EnvironmentMapper;

require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");
require_once(dirname(__FILE__) . '/../MockObject/AllMockObjects.php');

class TestOfProjectAccess extends UnitTestCase {

	function __construct() {
		EnvironmentMapper::connect(new ProjectAccessMockEnvironment());
	}
	
	function testHasPermission_Returns() {
		$p = new ProjectAccess(TestEnvironment::PROJECT_ID, TestEnvironment::USER_ID);
		$result = $p->hasPermission(ProjectPermission::CAN_ADMIN);
		$this->assertTrue($result);
	}
	
}

?>