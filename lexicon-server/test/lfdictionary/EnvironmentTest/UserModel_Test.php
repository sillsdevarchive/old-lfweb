<?php
use lfbase\environment\UserModel;
use lfbase\environment\EnvironmentMapper;
use lfbase\environment\ProjectRole;
use lfbase\environment\ProjectPermission;

require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");
require_once(dirname(__FILE__) . '/../MockObject/AllMockObjects.php');

class TestUserModel extends UnitTestCase {

	function __construct() {
		EnvironmentMapper::connect(new LFProjectAccessMockEnvironment());
	}
	
	function testGetUserName_Reads() {
		$p = new UserModel(TestEnvironment::USER_ID);
		$this->assertEqual('username', $p->getUserName());
	}
	
}

?>