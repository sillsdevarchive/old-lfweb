<?php

use \models\lex\Example;
use \models\lex\MultiText;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class TestExample extends UnitTestCase {

	function testCreate_ExampleAndTranslation_Correct() {
		$v = Example::create(MultiText::create('en', 'example1'), MultiText::create('fr', 'translation1'));
		$this->assertEqual('example1', $v->example->getForm('en'));
		$this->assertEqual('translation1', $v->translation->getForm('fr'));
	}
/*	
	function testEncode_ExampleAndTranslation_JsonCorrect() {
		$v = new Example();
		$v->example = MultiText::create('en', 'example1');
		$v->translation = MultiText::create('fr', 'translation1');
		
		$result = json_encode($v);
		
		$this->assertEqual('{"id":"","example":{"en":"example1"},"translation":{"fr":"translation1"},"metadata":{"crid":"","crname":"","modid":"","modname":"","crdate":0,"moddate":0}}', $result);
	}
*/
}

?>
