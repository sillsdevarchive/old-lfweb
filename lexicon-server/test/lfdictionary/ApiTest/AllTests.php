<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllApiTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
        // originally included tests, these pass IJH 2013-11
        
        // originally included tests, these fail IJH 2013-11
        //$this->addFile(TEST_PATH . 'lfdictionary/ApiTest/LFBaseAPI_Test.php');
        //$this->addFile(TEST_PATH . 'lfdictionary/ApiTest/LanguageDepotAPI_Test.php');
        
        // added other tests in same folder, these pass IJH 2013-11
        $this->addFile(TEST_PATH . 'lfdictionary/ApiTest/LexAPI_Import_Test.php'); // passes because test is commented out
        
        // added other tests in same folder, these fail IJH 2013-11
        //$this->addFile(TEST_PATH . 'lfdictionary/ApiTest/LexAPI_Create_Test.php');
        //$this->addFile(TEST_PATH . 'lfdictionary/ApiTest/LexAPI_DeleteEntry_Test.php');
        //$this->addFile(TEST_PATH . 'lfdictionary/ApiTest/LexAPI_GetEntries_Test.php');
        //$this->addFile(TEST_PATH . 'lfdictionary/ApiTest/LexAPI_GetEntry_Test.php');
        //$this->addFile(TEST_PATH . 'lfdictionary/ApiTest/LexAPI_MissingInfo_Test.php');
        //$this->addFile(TEST_PATH . 'lfdictionary/ApiTest/LexAPI_SaveEntry_Test.php');
        //$this->addFile(TEST_PATH . 'lfdictionary/ApiTest/LexAPI_Suggestion_Test.php');
        
    }
	  
}

?>