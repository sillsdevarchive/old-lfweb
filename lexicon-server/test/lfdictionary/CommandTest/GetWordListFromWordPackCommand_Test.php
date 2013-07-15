<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

require_once(TestPath . 'CommandTest/LiftTestEnvironment.php');

class TestOfGetWordPackListCommand extends UnitTestCase {

	function testWordPackListCommand_TwoEntries() {
		$listDto = new \dto\ListDTO();

		$multiText1 = new \lfbase\dto\MultiText();
		$multiText1->addForm("en", "meaning1");
		$listEntry1 = new \dto\ListEntry();
		$listEntry1->setGuid("guid0");
		$listEntry1->addEntry("fr", "entry1");
		$listEntry1->addMeaning($multiText1);

		$multiText2 = new \lfbase\dto\MultiText();
		$multiText2->addForm("en", "meaning1");
		$listEntry2 = new \dto\ListEntry();
		$listEntry2->setGuid("guid1");
		$listEntry2->addEntry("fr", "entry1");
		$listEntry2->addMeaning($multiText1);

		$listDto->addListEntry($listEntry1);
		$listDto->addListEntry($listEntry2);
		
		$sorceLiftFile = new LiftTestEnvironment();
		$sorceLiftFile->createLiftWith(4, 1, 0, 0, 0, 0, 0);

		$command = new \commands\GetWordListFromWordPackCommand($listDto, $sorceLiftFile->getLiftFilePath());
		$result = $command->execute();

		$this->assertEqual(2, count($result->_entries));
		$this->assertEqual(2, $result->entryCount);
	}
}

?>