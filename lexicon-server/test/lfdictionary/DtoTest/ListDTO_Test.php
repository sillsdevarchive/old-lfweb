<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class TestOfListDTO extends UnitTestCase {

	function testListDTO_Encode_EntryAndSense_JsonCorrect() {
		$entry = new \dto\ListDTO();
		
		$multiText = new \lfbase\dto\MultiText();
		$multiText->addForm("en", "meaning1");
		
		$listEntry = new \dto\ListEntry();
		$listEntry->setGuid("abcd");
		$listEntry->addEntry("fr", "entry1");
		$listEntry->addMeaning($multiText);
				
		$entry->addListEntry($listEntry);
		
		$result = json_encode($entry->encode());
		
		$this->assertEqual('{"count":0,"beginindex":0,"endindex":0,"entries":[{"guid":"abcd","entry":{"fr":"entry1"},"meanings":[{"en":"meaning1"}]}]}', $result);
	}

}

?>