<?php 
use lfbase\environment\ProjectPermission;

require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

class TestOfProjectPermission extends UnitTestCase {
	
	function testConstructor_MultipleArgs_Set() {
		$p = new ProjectPermission(ProjectPermission::CAN_ADMIN, ProjectPermission::CAN_EDIT_ENTRY);
		$this->assertTrue($p->has(ProjectPermission::CAN_ADMIN));
		$this->assertTrue($p->has(ProjectPermission::CAN_EDIT_ENTRY));
		$this->assertFalse($p->has(ProjectPermission::CAN_EDIT_REVIEW_OWN));
	}
	
}

?>
