<?php

use models\ActivityModel;
use models\ActivityListModel;
use models\commands\LexEntryCommands;
use models\lex\LexEntryModel;
use models\lex\LexEntryListModel;
use models\lex\MultiText;
use models\mapper\JsonDecoder;
use models\mapper\Id;
use models\ProjectModel;
use models\rights\Roles;
use models\UserModel;

require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(TEST_PATH . 'common/MongoTestEnvironment.php');
require_once(SourcePath . "models/lex/LexEntryModel.php");

class TestLexEntryCommands extends UnitTestCase {

	function testUpdateEntry_CreateEntryNoRights_Exception() {
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
		$project->addUser($user->id->asString(), 'NoRights');
		$project->write();
		$user->addProject($project->id->asString());
		$user->write();
		
		// create entry, exception expected
		$e->inhibitErrorDisplay();
		$this->expectException();
		$entryId = LexEntryCommands::updateEntry($params, $action, $project, $userId);
		$e->restoreErrorDisplay();
	}
	
	function testUpdateEntry_CreateAndUpdateEntry_EntryCreatedAndUpdated() {
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
		$project->addUser($userId, Roles::USER);
		$project->write();
		$user->addProject($project->id->asString());
		$user->write();
		
		// List
		$entryList = new LexEntryListModel($project);
		$entryList->read();
		$this->assertEqual(0, $entryList->count);
		$activityList = new ActivityListModel($project);
		$activityList->read();
		$this->assertEqual(0, $activityList->count);
		
		// create entry
		$entryId = LexEntryCommands::updateEntry($params, $action, $project, $userId);
		
		// read from disk, entry created, list
		$entry = new LexEntryModel($project, $entryId);
		$this->assertEqual('Some form', $entry->lexeme['en']);
		$entryList->read();
		$this->assertEqual(1, $entryList->count);
		$activityList->read();
		$this->assertEqual(1, $activityList->count);
		
		// update parameters: params and action
		$params['id'] = $entryId;
		$params['lexeme'] = array('fr' => 'Other form');
		$action = ActivityModel::UPDATE_ENTRY;
		
		// update entry
		$entryId = LexEntryCommands::updateEntry($params, $action, $project, $userId);
		
		// read from disk, entry updated, list
		$entry->read($entryId);
		$this->assertEqual($params['id'], $entryId);
		$this->assertEqual('Some form', $entry->lexeme['en']);
		$this->assertEqual('Other form', $entry->lexeme['fr']);
		$entryList->read();
		$this->assertEqual(1, $entryList->count);
		$activityList->read();
		$this->assertEqual(2, $activityList->count);
	}
	
	function testDeleteEntries_DeleteEntry_DeletedNoThrow() {
		$e = new MongoTestEnvironment();
		$e->clean();
		
		// create entry and list
		$project = $e->createProject(LF_TESTPROJECT);
		$entry = new LexEntryModel($project);
		$lexeme = new MultiText();
		$lexeme['en'] = 'Some form';
		$entry->lexeme = $lexeme;
		$entryId = $entry->write();
		$entryList = new LexEntryListModel($project);
		$entryList->read();
		$this->assertEqual(1, $entryList->count);
		
		// delete entry
		$count = LexEntryCommands::deleteEntries($project, array($entryId));
		
		// check entry is deleted
		$this->assertEqual(1, $count);
		$entryList->read();
		$this->assertEqual(0, $entryList->count);
	}
	
}

?>
