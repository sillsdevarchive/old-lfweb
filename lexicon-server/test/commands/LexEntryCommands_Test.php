<?php

use models\ActivityModel;
use models\commands\LexEntryCommands;
use models\lex\LexEntryModel;
use models\mapper\JsonDecoder;
use models\mapper\Id;
use models\ProjectModel;
use models\rights\Roles;
use models\UserModel;

require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(TEST_PATH . 'common/MongoTestEnvironment.php');

class TestLexEntryCommands extends UnitTestCase {

	function testUpdateEntry_CreateEntryNoRights_ExceptionEntryNotCreated() {
		$e = new MongoTestEnvironment();
		$e->clean();

		// setup parameters: params, action, user and project
		$params = array(
			'id' => '',
		);
		$action = ActivityModel::ADD_ENTRY;
		$project = $e->createProject(LF_TESTPROJECT);
		$userId = $e->createUser('somename', 'Some Name', 'somename@example.com');
		$user = new UserModel($userId);
		$project->addUser($user->id->asString(), 'NoRight');
		$project->write();
		$user->addProject($project->id->asString());
		$user->write();
		
		// create entry, exception expected
		$e->inhibitErrorDisplay();
		$this->expectException();
		$entryId = LexEntryCommands::updateEntry($params, $action, $project, $userId);
		$e->restoreErrorDisplay();
	}
	
	function testUpdateEntry_CreateEntry_EntryCreated() {
		$e = new MongoTestEnvironment();
		$e->clean();
		
		// setup parameters: params, action, user and project
		$params = array(
			'id' => '',
			'lexeme' => array('en' => 'Some form'),
		);
		$action = ActivityModel::ADD_ENTRY;
		$project = $e->createProject(LF_TESTPROJECT);
		$userId = $e->createUser('somename', 'Some Name', 'somename@example.com');
		$user = new UserModel($userId);
		$project->addUser($user->id->asString(), Roles::USER);
		$project->write();
		$user->addProject($project->id->asString());
		$user->write();
		
		// create entry
		$entryId = LexEntryCommands::updateEntry($params, $action, $project, $userId);
		
		// read from disk
		$entry = new LexEntryModel($project, $entryId);
		
		// entry created
		$this->assertEqual('Some form', $entry->lexeme['en']);
	}
	
	function testDeleteUsers_NoThrow() {
		$e = new MongoTestEnvironment();
		$e->clean();
		
		$userId = $e->createUser('somename', 'Some Name', 'somename@example.com');
		
		LexEntryCommands::deleteUsers(array($userId));
	}
	
}

?>
