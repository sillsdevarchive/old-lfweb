<?php
require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

class AllTests extends TestSuite {
    function __construct() {
        parent::__construct();
        
        // originally included tests, these pass IJH 2013-11
        $this->addFile(TEST_PATH . 'lfdictionary/TransliterationTest/AllTests.php');
        
        // originally included tests, these fail IJH 2013-11
        $this->addFile(TEST_PATH . 'lfdictionary/DtoTest/AllTests.php');
		$this->addFile(TEST_PATH . 'lfdictionary/CommandTest/AllTests.php');
		$this->addFile(TEST_PATH . 'lfdictionary/MapperTest/AllTests.php');
		$this->addFile(TEST_PATH . 'lfdictionary/store/AllTests.php');
		$this->addFile(TEST_PATH . 'lfdictionary/EnvironmentTest/AllTests.php');
		//$this->addFile(TEST_PATH . 'lfdictionary/APITest/AllTests.php');
		$this->addFile(TEST_PATH . 'lfdictionary/DashboardToolTest/AllTests.php');
        
        // added other tests in same folder, these pass IJH 2013-11
        
        // added other tests in same folder, these fail IJH 2013-11
		//$this->addFile(TEST_PATH . 'lfdictionary/APIOverJSONRPCTest/LexAPI_GetWordList_Test.php');
		//$this->addFile(TEST_PATH . 'lfdictionary/APIOverJSONRPCTest/LexAPI_Import_Test.php');
		//$this->addFile(TEST_PATH . 'lfdictionary/common/DataConnection_Test.php');
		//$this->addFile(TEST_PATH . 'lfdictionary/common/DataConnector_Test.php');
		
    }
}
?>