<?php

use models\lex\Sense;
use models\lex\Example;
use models\lex\MultiText;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class TestSense extends UnitTestCase {

	function testCreate_Sense_Correct() {
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
		
		$this->assertEqual($sense->definition['en'], 'Some definition');
		$this->assertEqual($sense->examples[0]->example['en'], 'example1');
		$this->assertEqual($sense->examples[0]->translation['fr'], 'translation1');

		echo "<pre>";
//   		var_dump($sense);
		echo "</pre>";
	}
		
}

?>
