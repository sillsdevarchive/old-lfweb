<?php

use models\lex\MultiText;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class TestMultiText extends UnitTestCase {

	function testCRUD_Works() {
		// Create
		$v = new MultiText();
		$v['en'] = 'text1';
		
		// Read
		$this->assertEqual($v['en'], 'text1');
		
		// Update
		$v['en'] = 'text2';
		$this->assertEqual($v['en'], 'text2');
		
		// Delete
		$v['fr'] = 'text3';
		$this->assertEqual($v->count(), 2);
		unset($v['en']);
		$this->assertEqual($v->count(), 1);
		$this->assertEqual($v['fr'], 'text3');
	}

	function testOffsetExists_Read_HasCorrectForm() {
		$v = new MultiText();
		$v['en'] = 'text1';
		$this->assertTrue($v->offsetExists('en'));
		$this->assertFalse($v->offsetExists('fr'));
		$v['fr'] = 'text2';
		$this->assertTrue($v->offsetExists('fr'));
	}

	function testGetAllLanguages_Read_HasCorrectForm() {
		$v = new MultiText();
		$v['en'] = 'text1';
		$v['fr'] = 'text2';
		$this->assertEqual($v->getAllLanguages(), array('en', 'fr'));

		echo "<pre>";
// 		var_dump($v);
		echo "</pre>";
	}

}

?>
