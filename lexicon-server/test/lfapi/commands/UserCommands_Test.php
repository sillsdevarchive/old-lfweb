<?php
use libraries\api\UserCommands;

require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

require_once(TEST_PATH . 'common/MongoTestEnvironment.php');

class TestUserCommands extends UnitTestCase {

	function __construct()
	{
	}
	
	function testDeleteUsers_NoThrow() {
		$e = new MongoTestEnvironment();
		$e->clean();
		
		$userId = $e->createUser('somename', 'Some Name', 'somename@example.com');
		
		UserCommands::deleteUsers(array($userId));
	}
	
}

?>