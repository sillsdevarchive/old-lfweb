<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class TestOfAutoListDTO extends UnitTestCase {

	function testAutoListDTO_Encode_JsonCorrect() {
		$dtoList = new \dto\AutoListDTO();

		$dtoA = new \dto\AutoListEntry("guid1", "word1");
		$dtoList->addListEntry($dtoA);

		$dtoB = new \dto\AutoListEntry("guid2", "word2");
		$dtoList->addListEntry($dtoB);

		$result = json_encode($dtoList->encode());
		$this->assertEqual(2,$dtoList->entryCount);
		$this->assertEqual($result, '{"TotalSize":2,"Options":[{"Value":"guid1","DisplayName":"word1"},{"Value":"guid2","DisplayName":"word2"}]}');
	}

}

?>