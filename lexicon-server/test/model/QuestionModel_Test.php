<?php

use models\AnswerModel;

use models\QuestionListModel;

use models\mapper\MongoStore;
use models\ProjectModel;
use models\QuestionModel;

require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

require_once(TEST_PATH . 'common/MongoTestEnvironment.php');

require_once(SourcePath . "models/ProjectModel.php");
require_once(SourcePath . "models/QuestionModel.php");


class TestQuestionModel extends UnitTestCase {

	function __construct() {
		$e = new MongoTestEnvironment();
		$e->clean();
	}

	function testCRUD_Works() {
		$e = new MongoTestEnvironment();
		$entryRef = MongoTestEnvironment::mockId();
		$projectModel = new MockProjectModel();
		
		// List
		$list = new QuestionListModel($projectModel, $entryRef);
		$list->read();
		$this->assertEqual(0, $list->count);
		
		// Create
		$question = new QuestionModel($projectModel);
		$question->title = "SomeQuestion";
		$question->entryRef->id = $entryRef;
		$id = $question->write();
		$this->assertNotNull($id);
		$this->assertIsA($id, 'string');
		$this->assertEqual($id, $question->id->asString());
		
		// Read back
		$otherQuestion = new QuestionModel($projectModel, $id);
		$this->assertEqual($id, $otherQuestion->id->asString());
		$this->assertEqual('SomeQuestion', $otherQuestion->title);
		$this->assertEqual($entryRef, $otherQuestion->entryRef->id);
		
		// Update
		$otherQuestion->title = 'OtherQuestion';
		$otherQuestion->write();

		// Read back
		$otherQuestion = new QuestionModel($projectModel, $id);
		$this->assertEqual('OtherQuestion', $otherQuestion->title);
		
		// List
		$list->read();
		$this->assertEqual(1, $list->count);

		// Delete
		QuestionModel::remove($projectModel->databaseName(), $id);
		
		// List
		$list->read();
		$this->assertEqual(0, $list->count);
		
	}

	function testEntryReference_NullRefValidRef_AllowsNullRef() {
		$projectModel = new MockProjectModel();
		$mockTextRef = (string)new \MongoId();
		
		// Test create with null entryRef
		$question = new QuestionModel($projectModel);
		$id = $question->write();
		
		$otherQuestion = new QuestionModel($projectModel, $id);
		$this->assertEqual('', $otherQuestion->entryRef->id);
		
		// Test update with entryRef
		$question->entryRef->id = $mockTextRef;
		$question->write();
		
		$otherQuestion = new QuestionModel($projectModel, $id);
		$this->assertEqual($mockTextRef, $otherQuestion->entryRef->id);
		
	}

}

?>
