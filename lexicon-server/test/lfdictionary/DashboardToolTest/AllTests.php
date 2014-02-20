<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllDashboardToolTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
        
        // originally included tests, these pass IJH 2013-11
        
        // originally included tests, these fail IJH 2013-11
 		$this->addFile(TEST_PATH . 'lfdictionary/DashboardToolTest/DashboardCounterExtracter_Test.php');
 		$this->addFile(TEST_PATH . 'lfdictionary/DashboardToolTest/DashboardToolDbAccess_Test.php');
 			
        // added other tests in same folder, these pass IJH 2013-11
        
        // added other tests in same folder, these fail IJH 2013-11
        
    }
}
?>