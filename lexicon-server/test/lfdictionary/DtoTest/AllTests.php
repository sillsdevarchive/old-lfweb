<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllModelTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
        $this->addFile(TEST_PATH . 'lfdictionary/DtoTest/MultiText_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/UserDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ProjectDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/CommunityDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ConversationDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ClientEnvironmentDto_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ResultDTO_Test.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DtoTest/ProjectAccessDTO_Test.php');
    }
}

?>