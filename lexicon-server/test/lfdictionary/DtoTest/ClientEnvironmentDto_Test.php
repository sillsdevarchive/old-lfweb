<?php
use lfbase\environment\EnvironmentMapper;
use lfbase\environment\ProjectAccess;
use lfbase\environment\UserModel;
use lfbase\environment\ProjectModel;
use lfbase\environment\ProjectRole;
use lfbase\environment\ProjectPermission;
use lfbase\dto\ClientEnvironmentDto;

require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");
require_once(dirname(__FILE__) . '/../MockObject/AllMockObjects.php');


class TestOfClientEnvironment extends UnitTestCase {

	function __construct() {
		EnvironmentMapper::connect(new ProjectAccessMockEnvironment());
	}

	function testEncode_NoThrow() {
		$projectModel = new ProjectModel(TestEnvironment::PROJECT_ID);
		$projectAccess = new ProjectAccess(TestEnvironment::PROJECT_ID, TestEnvironment::USER_ID);
		$userModel = new UserModelMockObject(TestEnvironment::USER_ID,"name", "role");
		$c = new ClientEnvironmentDto($projectModel, $userModel, $projectAccess);
		$result = $c->encode();

		$projectDecoded = base64_decode($result['currentProject']);
		$userDecoded = base64_decode($result['currentUser']);
		$accessDecoded = base64_decode($result['access']);

		$this->assertEqual('{"id":1,"name":"name","title":"title","type":"dictionary","lang":"ln"}', $projectDecoded);
		$this->assertEqual('{"id":2,"name":"name","role":"admin"}', $userDecoded);
		$this->assertEqual('{"grants":[1],"activerole":"admin","availableroles":{"admin":"admin","user":"user"}}', $accessDecoded);
	}

}

?>