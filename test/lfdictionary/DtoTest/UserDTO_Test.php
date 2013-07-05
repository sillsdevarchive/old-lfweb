<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");
require_once(dirname(__FILE__) . '/../MockObject/AllMockObjects.php');

class TestOfUserDTO extends UnitTestCase {
	function testUserDTOEncode_ReturnsCorrectJson() {
		$userDTO = new \lfbase\dto\UserDTO(new UserModelMockObject());
		$userDTO->setUserRole("admin");
		$result = json_encode($userDTO->encode());
		$this->assertEqual('{"id":2,"name":"name","role":"admin"}', $result);
	}
	
	function testUserListDTOEncode_ReturnsCorrectJson() {
		$userDTO = new \lfbase\dto\UserDTO(new UserModelMockObject());
		$userDTO->setUserRole("admin");
		$userListDTO = new \lfbase\dto\UserListDTO();
		$userListDTO->addListUser($userDTO);
		$result = json_encode($userListDTO->encode());
		
		$this->assertEqual('{"List":[{"id":2,"name":"name","role":"admin"}]}', $result);
	}
}

?>