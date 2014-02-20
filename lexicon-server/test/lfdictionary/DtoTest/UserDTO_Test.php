<?php

use \libraries\lfdictionary\dto\UserDTO;
use \models\UserModel;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(LF_BASE_PATH . "Loader.php");
require_once(dirname(__FILE__) . '/../MockObject/AllMockObjects.php');

// TODO Low value test.  Unlikely to be a useful DTO.  The default encoder for UserModel is likely fine. CP 2013-11
class TestOfUserDTO extends UnitTestCase {
	
	function testUserDTOEncode_ReturnsCorrectJson() {
		$userModel = new UserModel();
		$userDTO = new UserDTO($userModel);
// 		$userDTO->setUserRole("admin");
		$result = json_encode($userDTO->encode());
		$this->assertEqual('{"id":2,"name":"name","role":"admin"}', $result);
	}
	
	function testUserListDTOEncode_ReturnsCorrectJson() {
		$userModel = new UserModel();
		$userDTO = new UserDTO($userModel);
// 		$userDTO->setUserRole("admin");
		$userListDTO = new \libraries\lfdictionary\dto\UserListDTO();
		$userListDTO->addListUser($userDTO);
		$result = json_encode($userListDTO->encode());
		
		$this->assertEqual('{"List":[{"id":2,"name":"name","role":"admin"}]}', $result);
	}
}

?>