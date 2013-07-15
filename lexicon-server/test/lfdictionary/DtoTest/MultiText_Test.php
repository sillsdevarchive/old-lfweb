<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

class TestOfMultiText extends UnitTestCase {

	function testEncode_MultiTextSingleEntry_JsonDictionary() {
		$v = new \lfbase\dto\MultiText();
		$v->addForm('en', 'text');
		
		$result = json_encode($v->encode());
		
		$this->assertEqual('{"en":"text"}', $result);
	}

	function testEncode_MultiTextMultiEntry_JsonDictionary() {
		$v = new \lfbase\dto\MultiText();
		$v->addForm('en', 'text1');
		$v->addForm('fr', 'text2');
		
		$result = json_encode($v->encode());
		
		$this->assertEqual('{"en":"text1","fr":"text2"}', $result);
	}
	
	function testCreate_HasCorrectForm() {
		$v = \lfbase\dto\MultiText::create('en', 'text1');
		$this->assertEqual('text1', $v->getForm('en'));
	}
	
	function testCreateFromArray_TwoForms_Correct() {
		$value = array('en' => 'text1', 'fr' => 'text2');
		$v = \lfbase\dto\MultiText::createFromArray($value);
		$this->assertEqual(array('en' => 'text1', 'fr' => 'text2'), $v->getAll());
	}

}

?>