<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllAPITests extends TestSuite {
    function __construct() {
        parent::__construct();
		$this->addFile(TEST_PATH . 'APITest/LexAPI_GetEntries_Test.php');
		$this->addFile(TEST_PATH . 'APITest/LexAPI_GetEntry_Test.php');
		// TODO The two below commented out by CP 2012-11. Put back when lfbase ProjectUser completed. CP 2012-11
// 		$this->addFile(TEST_PATH . 'APITest/LexAPI_SaveEntry_Test.php');
// 		$this->addFile(TEST_PATH . 'APITest/LexAPI_DeleteEntry_Test.php');
		$this->addFile(TEST_PATH . 'APITest/LexAPI_Suggestion_Test.php');
		$this->addFile(TEST_PATH . 'APITest/LexAPI_MissingInfo_Test.php');
// 		$this->addFile(TEST_PATH . 'APITest/LexAPI_Import_Test.php'); // This test is long running, so manually run when needed. CP
// 		$this->addFile(TEST_PATH . 'APITest/LexAPI_Create_Test.php'); // This test / feature not currently ready (at least as an API). CP
	}
}

?>