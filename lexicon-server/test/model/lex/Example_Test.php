<?php

use models\lex\Example;
use models\lex\MultiText;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class TestExample extends UnitTestCase {

	function testCreate_ExampleAndTranslation_Correct() {
		$example = new MultiText();
		$example['en'] = 'example1';
		$translation = new MultiText();
		$translation['fr'] = 'translation1';
		$v = new Example();
		$v->example = $example;
		$v->translation = $translation;
		$this->assertEqual($v->example['en'], 'example1');
		$this->assertEqual($v->translation['fr'], 'translation1');
	}
		
}

?>
