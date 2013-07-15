<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

class TestOfListEntry extends UnitTestCase {

	function testEncode_JsonCorrect() {
		$multiText = \lfbase\dto\MultiText::create('en', 'meaning1');

		$listEntry = new \dto\ListEntry();
		$listEntry->setGuid("abcd");
		$listEntry->addEntry("fr", "entry1");
		$listEntry->addMeaning($multiText);

		$encoded = $listEntry->encode();

		$expected = '{"guid":"abcd","entry":{"fr":"entry1"},"meanings":[{"en":"meaning1"}]}';
		$json = json_encode($encoded);
		
		$this->assertEqual($expected, $json);
	}

	function testCreateFromParts_Correct() {
		$guid = 'abcd';
		$word = array('fr' => 'entry1');
		$definitions = array(array('definition' => array('en' => 'meaning1 en', 'th' => 'meaning1 th')));
		$listEntry = \dto\ListEntry::createFromParts($guid, $word, $definitions);

		//var_dump($listEntry);
		
		$encoded = $listEntry->encode();
		
		$result = json_encode($encoded);

		$expected = '{"guid":"abcd","entry":{"fr":"entry1"},"meanings":[{"en":"meaning1 en","th":"meaning1 th"}]}';
		
		$this->assertEqual($expected, $result);
	}

}

?>