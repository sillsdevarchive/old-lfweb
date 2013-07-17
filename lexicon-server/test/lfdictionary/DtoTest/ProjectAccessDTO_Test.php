<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");
require_once(dirname(__FILE__) . '/../MockObject/AllMockObjects.php');
use lfbase\environment\ProjectRole;

use lfbase\environment\ProjectPermission;
use lfbase\environment\LFProjectAccess;
use lfbase\environment\EnvironmentMapper;

class TestOfProjectAccessDTO extends UnitTestCase {


	function __construct() {
		EnvironmentMapper::connect(new LFProjectAccessMockEnvironment());
	}

	function testProjectAccessDTOEncode_ReturnsCorrectJson() {
		$p = new LFProjectAccess(TestEnvironment::PROJECT_ID, TestEnvironment::USER_ID);
		$dto = new \libraries\lfdictionary\dto\ProjectAccessDTO($p);
		$result = json_encode($dto->encode());
		$this->assertEqual('{"grants":[1],"activerole":"admin","availableroles":{"admin":"admin","user":"user"}}', $result);
	}

}

?>