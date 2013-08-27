<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

require_once(TEST_PATH . 'CommandTest/LiftTestEnvironment.php');

class TestOfSaveCommentsCommand extends UnitTestCase {

	private $NEW_MESSAGE="This is an new saved message";
	private $NEW_MESSAGE_BY="SimpleWorld";

	function recursiveDelete($str) {
		if(is_file($str)) {
			return @unlink($str);
		} elseif(is_dir($str)) {
			$str = rtrim($str, DIRECTORY_SEPARATOR) . DIRECTORY_SEPARATOR;
			$objects = scandir($str);
			foreach ($objects as $object) {
				if ($object === "." || $object === "..") {
					continue;
				}
				self::recursiveDelete($str . $object);
			}
			reset($objects);
			@rmdir($str);
		}
	}

	/*
	 * test in this way: JsonSource -> Xml -> JsonRes
	* JsonSource == JsonRes
	*/
	function testUpdateSaveCommentsCommand() {
		$this->_path = sys_get_temp_dir() . '/CommentsTestingFolder';
		if (!file_exists($this->_path)) {
			mkdir($this->_path);
		}else {
			//delete all exist files
			$this->recursiveDelete($this->_path);
			mkdir($this->_path);
		}

		$sourceChorusNotesFilePath=  TEST_PATH. "data/Test.lift.ChorusNotes";
		$desChorusNotesFilePath= $this->_path  . "/Test.lift.ChorusNotes";
		//copy new file to test folder
		copy($sourceChorusNotesFilePath,$desChorusNotesFilePath);

		$now = new DateTime;
		$w3cDateString = $now->format(DateTime::W3C);
		$messageType=0;
		$messageStatus=0;
		$parentGuid= "dca37623-3f8a-45f3-8d53-5728205ad37c";
		$commentMessage=$this->NEW_MESSAGE;
		$userName=$this->NEW_MESSAGE_BY;

		$command = new \commands\GetCommentsCommand($desChorusNotesFilePath, "","question", 0,1,false);	
		$result=json_encode($command->execute()->encode());
		
		$this->assertFalse(strstr($result, $this->NEW_MESSAGE));
		$this->assertFalse(strstr($result, $this->NEW_MESSAGE_BY));
		$command = new \commands\SaveCommentsCommand($desChorusNotesFilePath, $messageStatus,$messageType, $parentGuid,$commentMessage,$w3cDateString,$userName,false);
		$command->execute();

		
		$command = new \commands\GetCommentsCommand($desChorusNotesFilePath, "","question", 0,1,false);
		$result=json_encode($command->execute()->encode());	

		$this->assertTrue(strstr($result, $this->NEW_MESSAGE));
		$this->assertTrue(strstr($result, $this->NEW_MESSAGE_BY));
		
		$this->recursiveDelete($this->_path);
	}
}

?>