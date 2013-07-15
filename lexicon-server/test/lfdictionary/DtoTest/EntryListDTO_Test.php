<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class TestOfEntryListDTO extends UnitTestCase {

	function testEntryListDTO_Encode_EntryAndSense_JsonCorrect() {
		$entry = new \dto\EntryListDTO();
		
		$entry1 = \dto\EntryDTO::create("guid0");
		$entry1->setEntry(\lfbase\dto\MultiText::create('fr', 'form1'));
		
		$sense1 = new \dto\Sense();
		$sense1->setDefinition(\lfbase\dto\MultiText::create('en', 'definition1'));
		$sense1->setSemanticDomainName('semantic-domain-ddp4');
		$sense1->setSemanticDomainValue('2.1 Body');
		$sense1->addExample(\dto\Example::create(
			\lfbase\dto\MultiText::create('en', 'example1'),
			\lfbase\dto\MultiText::create('fr', 'translation1')
		));
		$entry1->addSense($sense1);
		
		$entry2 = \dto\EntryDTO::create("guid1");
		$entry2->setEntry(\lfbase\dto\MultiText::create('th', 'form2'));
		
		$sense2 = new \dto\Sense();
		$sense2->setDefinition(\lfbase\dto\MultiText::create('en', 'definition2'));
		$sense2->setSemanticDomainName('semantic-domain-ddp4');
		$sense2->setSemanticDomainValue('2.1 Body');
		$sense2->addExample(\dto\Example::create(
		\lfbase\dto\MultiText::create('fr', 'example2'),
		\lfbase\dto\MultiText::create('th', 'translation2')
		));
		$entry2->addSense($sense2);
		
		$entry->addEntry($entry1);
		$entry->addEntry($entry2);
		$entry->entryCount=2;
		
		$result = json_encode($entry->encode());
		$this->assertEqual('{"count":2,"entries":[{"guid":"guid0","mercurialSHA":null,"entry":{"fr":"form1"},"senses":[{"definition":{"en":"definition1"},"POS":"","examples":[{"example":{"en":"example1"},"translation":{"fr":"translation1"}}],"SemDomValue":"2.1 Body","SemDomName":"semantic-domain-ddp4"}]},{"guid":"guid1","mercurialSHA":null,"entry":{"th":"form2"},"senses":[{"definition":{"en":"definition2"},"POS":"","examples":[{"example":{"fr":"example2"},"translation":{"th":"translation2"}}],"SemDomValue":"2.1 Body","SemDomName":"semantic-domain-ddp4"}]}]}', $result);
	}

}

?>