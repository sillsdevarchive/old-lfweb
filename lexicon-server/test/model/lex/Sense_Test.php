<?php

use \models\lex\Example;
use \models\lex\MultiText;
use \models\lex\Sense;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class TestOfSense extends UnitTestCase {
/*
	function testEncode_DefinitionPOSAndExample_JsonCorrect() {
		$sense = new Sense();
		$sense->definition = MultiText::create('en', 'definition1');
		$sense->partOfSpeech = 'Noun';
		$sense->semanticDomainName = 'semantic-domain-ddp4';
		$sense->semanticDomainValue = '2.1 Body';
		$example = Example::create(
			MultiText::create('en', 'example1'), 
			MultiText::create('fr', 'translation1')
		);
		$sense->examples->append($example);
		
		$result = json_encode($sense);
		
		$this->assertEqual('{"definition":{"en":"definition1"},"POS":"Noun","examples":[{"example":{"en":"example1"},"translation":{"fr":"translation1"}}],"SemDomValue":"2.1 Body","SemDomName":"semantic-domain-ddp4"}', $result);
	}
*/
}

?>
