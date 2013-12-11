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
use models\lex\LexEntryIds;
use models\lex\LexEntryId;
use models\mapper\JsonEncoder;

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
		$id = LexEntryCommands::updateEntry($params, $action, $project, $userId);
		$e->restoreErrorDisplay();
	}
	
	function testUpdateEntryAndDeleteEntries_CRUD_Works() {
		$e = new MongoTestEnvironment();
		$e->clean();
		
		// setup parameters: params, action, user and project
		$params = array(
			'id' => '',
			'lexeme' => array('en' => 'Some form'),
		);
		$action = ActivityModel::ADD_ENTRY;
		$project = $e->createProject(LF_TESTPROJECT, 'en');
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
		
		// Create entry
		$id = LexEntryCommands::updateEntry($params, $action, $project, $userId);
		
		// Read from disk, entry created, list
		$entry = new LexEntryModel($project, $id);
		$this->assertEqual('Some form', $entry->lexeme['en']);
		$entryList->read();
		$this->assertEqual(1, $entryList->count);
		$activityList->read();
		$this->assertEqual(1, $activityList->count);
		
		// Update parameters: params and action
		$params['id'] = $id;
		$params['lexeme'] = array('fr' => 'Other form');
		$action = ActivityModel::UPDATE_ENTRY;
		
		// Update entry
		$id = LexEntryCommands::updateEntry($params, $action, $project, $userId);
		
		// Read from disk, entry updated
		$entry->read($id);
		$this->assertEqual($params['id'], $id);
		$this->assertEqual('Some form', $entry->lexeme['en']);
		$this->assertEqual('Other form', $entry->lexeme['fr']);
		
		// List
		$entryList->read();
		$this->assertEqual(1, $entryList->count);
		$activityList->read();
		$this->assertEqual(2, $activityList->count);
		
		// Delete entry
		$entryIds = new LexEntryIds();
		$entryId = new LexEntryId();
		$entryId->id = $id;
		$entryId->mercurialSha = '';
		$entryIds->append($entryId);
		$jsonIds = JsonEncoder::encode($entryIds);
		echo "<pre>";
		var_dump($entryIds);
		var_dump($jsonIds);
		$jsonIds = array(
			array(
				'id' => $id,
				'mercurialSha' => ''
			)
		);
		var_dump($jsonIds);
		echo "</pre>";
		
		$count = LexEntryCommands::deleteEntries($project, $userId, $jsonIds);
		
		// List
		$this->assertEqual(1, $count);
		$entryList->read();
		$this->assertEqual(0, $entryList->count);
		$activityList->read();
		$this->assertEqual(3, $activityList->count);
	}
	
}

?>
