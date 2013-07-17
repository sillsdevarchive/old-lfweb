<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class TestOfExample extends UnitTestCase {

	function testEncode_ExampleAndTranslation_JsonCorrect() {
		$v = new \dto\Example();
		$v->setExample(\libraries\lfdictionary\dto\MultiText::create('en', 'example1'));
		$v->setTranslation(\libraries\lfdictionary\dto\MultiText::create('fr', 'translation1'));
		
		$result = json_encode($v->encode());
		
		$this->assertEqual('{"example":{"en":"example1"},"translation":{"fr":"translation1"}}', $result);
	}

	function testCreate_ExampleAndTranslation_Correct() {
		$v = \dto\Example::create(\libraries\lfdictionary\dto\MultiText::create('en', 'text1'), \libraries\lfdictionary\dto\MultiText::create('fr', 'text2'));
		$this->assertEqual(array('en' => 'text1'), $v->_example->getAll());
		$this->assertEqual(array('fr' => 'text2'), $v->_translation->getAll());
	}
	
	function testCreateFromArray_ExampleAndTranslation_Correct() {
		$src = \dto\Example::create(\libraries\lfdictionary\dto\MultiText::create('en', 'text1'), \libraries\lfdictionary\dto\MultiText::create('fr', 'text2'));
		$value = $src->encode();
		
		$v = \dto\Example::createFromArray($value);
		$this->assertEqual(array('en' => 'text1'), $v->_example->getAll());
		$this->assertEqual(array('fr' => 'text2'), $v->_translation->getAll());
	}
	
	
}

?>