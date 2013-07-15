<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class TestOfResultDTO extends UnitTestCase {
	
	function testResultDTOEncode_ReturnsCorrectJson() {
		$dtoTrue = new \lfbase\dto\ResultDTO(true);
		$resultTrue = json_encode($dtoTrue->encode());
		$this->assertEqual('{"succeed":true}', $resultTrue);
		
		$dtoFalse = new \lfbase\dto\ResultDTO(false);
		$resultFalse = json_encode($dtoFalse->encode());
		$this->assertEqual('{"succeed":false}', $resultFalse);
	}

}

?>