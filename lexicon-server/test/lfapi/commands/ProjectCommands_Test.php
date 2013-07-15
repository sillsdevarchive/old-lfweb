<?php
use libraries\api\ProjectCommands;

require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

require_once(TEST_PATH . 'common/MongoTestEnvironment.php');

class TestProjectCommands extends UnitTestCase {

	function __construct()
	{
	}
	
	function testDeleteProjects_NoThrow() {
		$e = new MongoTestEnvironment();
		$e->clean();
		
		$project = $e->createProject(LF_TESTPROJECT);
		
		ProjectCommands::deleteProjects(array($project->id));
	}
	
}

?>