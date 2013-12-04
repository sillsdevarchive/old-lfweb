<?php

use models\lex\LexEntryModel;
use models\lex\LexEntryListModel;
use models\lex\Example;
use models\lex\MultiText;
use models\lex\Sense;
use models\mapper\MongoStore;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(TEST_PATH . 'common/MongoTestEnvironment.php');
require_once(SourcePath . "models/lex/LexEntryModel.php");

class TestLexEntryModel extends UnitTestCase {
	
	function testLexEntryModel_CRUD_Works() {
		$e = new MongoTestEnvironment();
		$e->clean();
		$project = $e->createProject(LF_TESTPROJECT);
		
		// List
		$list = new LexEntryListModel($project);
		$list->read();
		$this->assertEqual(0, $list->count);

		// Create
		$entry = new LexEntryModel($project);
		$lexeme1 = new MultiText();
		$lexeme1['fr'] = 'Some form';
		$entry->lexeme = $lexeme1;
		$sense = new Sense();
		$definition = new MultiText();
		$definition['en'] = 'Some definition';
		$sense->definition = $definition;
		$sense->semanticDomainName = 'semantic-domain-ddp4';
		$sense->semanticDomainValue = '2.1 Body';
		$example = new MultiText();
		$example['en'] = 'example1';
		$translation = new MultiText();
		$translation['fr'] = 'translation1';
		$lexExample = new Example();
		$lexExample->example = $example;
		$lexExample->translation = $translation;
		$sense->examples->append($lexExample);
		$entry->senses->append($sense);
		$id = $entry->write();
		$this->assertNotNull($id);
		$this->assertIsA($id, 'string');
		$this->assertEqual($id, $entry->id->asString());

		echo "<pre>";
//  		var_dump($entry);
		echo "</pre>";
		
		// Read back
		$otherEntry = new LexEntryModel($project, $id);
		$this->assertEqual($id, $otherEntry->id->asString());
		$this->assertEqual($lexeme1['fr'], $otherEntry->lexeme['fr']);
		$this->assertEqual($sense->definition['en'], $otherEntry->senses[0]->definition['en']);
		$this->assertEqual($lexExample->translation['fr'], $otherEntry->senses[0]->examples[0]->translation['fr']);
		
		// Update
		$lexeme2 = new MultiText();
		$lexeme2['fr'] = 'Other form';
		$otherEntry->lexeme = $lexeme2;
		$otherEntry->write();
	
		// Read back
		$otherEntry = new LexEntryModel($project, $id);
		$this->assertEqual($lexeme2['fr'], $otherEntry->lexeme['fr']);
	
		// List
		$list->read();
		$this->assertEqual(1, $list->count);
	
		// Delete
		LexEntryModel::remove($project, $id);
	
		// List
		$list->read();
		$this->assertEqual(0, $list->count);
	}

	function testUpdateThenRemove_NewProject_CreatesThenRemovesProjectDatabase() {
		$e = new MongoTestEnvironment();
		$e->clean();
	
		$project = $e->createProject(LF_TESTPROJECT);
		$databaseName = $project->databaseName();
	
		$project->remove();
		$this->assertFalse(MongoStore::hasDB($databaseName));
			
		$entry = new LexEntryModel($project);
		$lexeme = new MultiText();
		$lexeme['fr'] = 'Some form';
		$entry->lexeme = $lexeme;
		$entry->write();
	
		$this->assertTrue(MongoStore::hasDB($databaseName));
	
		$project->remove();
	
		$this->assertFalse(MongoStore::hasDB($databaseName));
	}
	
}

?>
