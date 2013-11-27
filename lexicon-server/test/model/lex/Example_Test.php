<?php

use \models\lex\Example;
use \models\lex\MultiText;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class TestOfExample extends UnitTestCase {

	function testEncode_ExampleAndTranslation_JsonCorrect() {
		$v = new Example();
		$v->setExample(MultiText::create('en', 'example1'));
		$v->setTranslation(MultiText::create('fr', 'translation1'));
		
		$result = json_encode($v->encode());
		
		$this->assertEqual('{"example":{"en":"example1"},"translation":{"fr":"translation1"}}', $result);
	}

	function testCreate_ExampleAndTranslation_Correct() {
		$v = Example::create(MultiText::create('en', 'text1'), MultiText::create('fr', 'text2'));
		$this->assertEqual(array('en' => 'text1'), $v->_example->getAll());
		$this->assertEqual(array('fr' => 'text2'), $v->_translation->getAll());
	}
	
	function testCreateFromArray_ExampleAndTranslation_Correct() {
		$src = Example::create(MultiText::create('en', 'text1'), MultiText::create('fr', 'text2'));
		$value = $src->encode();
		
		$v = Example::createFromArray($value);
		$this->assertEqual(array('en' => 'text1'), $v->_example->getAll());
		$this->assertEqual(array('fr' => 'text2'), $v->_translation->getAll());
	}
	
}

?>