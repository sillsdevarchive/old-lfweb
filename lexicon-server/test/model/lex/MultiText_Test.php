<?php

use \models\lex\MultiText;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class TestMultiText extends UnitTestCase {

	function testCRUandGetAll_HasCorrectForm() {
		// Create
		$v = MultiText::create('en', 'text1');
		
		// Read
		$this->assertEqual('text1', $v->getForm('en'));
		
		// Update
		$v->updateForm('en', 'text2');
		$this->assertEqual('text2', $v->getForm('en'));
		
		// Get All
		$v->updateForm('fr', 'text3');
		$this->assertEqual($v->count(), 2);
		$this->assertEqual($v->data, $v->getAll());

		// Get All Languages
		$this->assertEqual(array('en', 'fr'), $v->getAllLanguages());
	}
/*	
	function testEncode_MultiTextSingleEntry_JsonDictionary() {
		$v = new MultiText();
		$v->updateForm('en', 'text');
		
		$result = json_encode($v);
		
		$this->assertEqual('{"en":"text"}', $result);
	}

	function testEncode_MultiTextMultiEntry_JsonDictionary() {
		$v = new MultiText();
		$v->updateForm('en', 'text1');
		$v->updateForm('fr', 'text2');
		
		$result = json_encode($v);
		
		$this->assertEqual('{"en":"text1","fr":"text2"}', $result);
	}
*/	
}

?>
