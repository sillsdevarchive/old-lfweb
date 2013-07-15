<?php 
use lfbase\environment\ProjectRole;
use lfbase\environment\ProjectPermission;

require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class TestOfProjectRole extends UnitTestCase {
	
	function testGet_NoRole_Throws() {
		$this->expectException('\Exception');
		$result = \lfbase\environment\ProjectRole::get('bogus');
	}
	
	function testAddGet_Returns() {
		ProjectRole::add('somerole', new ProjectPermission(ProjectPermission::CAN_ADMIN), 1, 1);
		$permissions = ProjectRole::get('somerole');
		$this->assertNotNull($permissions);
	}	
	
}

?>
