<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath .  'autorun.php');

class AllModelTests extends TestSuite {
    function __construct() {
        parent::__construct();
        $this->addFile(TestPath . 'DtoTest/MultiText_Test.php');
		$this->addFile(TestPath . 'DtoTest/UserDTO_Test.php');
		$this->addFile(TestPath . 'DtoTest/ProjectDTO_Test.php');
		$this->addFile(TestPath . 'DtoTest/CommunityDTO_Test.php');
		$this->addFile(TestPath . 'DtoTest/ConversationDTO_Test.php');
		$this->addFile(TestPath . 'DtoTest/ClientEnvironmentDto_Test.php');
		$this->addFile(TestPath . 'DtoTest/ResultDTO_Test.php');
		$this->addFile(TestPath . 'DtoTest/ProjectAccessDTO_Test.php');
    }
}

?>