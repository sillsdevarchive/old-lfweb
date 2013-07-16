<?php 
use lfbase\environment\ProjectRole;
use lfbase\environment\ProjectPermission;
use lfbase\environment\LFProjectAccess;
use lfbase\environment\EnvironmentMapper;

require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");
require_once(dirname(__FILE__) . '/../MockObject/AllMockObjects.php');

class TestOfLFProjectAccess extends UnitTestCase {

	function __construct() {
		EnvironmentMapper::connect(new LFProjectAccessMockEnvironment());
	}
	
	function testHasPermission_Returns() {
		$p = new LFProjectAccess(TestEnvironment::PROJECT_ID, TestEnvironment::USER_ID);
		$result = $p->hasPermission(ProjectPermission::CAN_ADMIN);
		$this->assertTrue($result);
	}
	
}

?>