<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath .  'autorun.php');

class AllDashboardToolTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
 		$this->addFile(TestPath . 'DashboardToolTest/DashboardCounterExtracter_Test.php');
 		$this->addFile(TestPath . 'DashboardToolTest/DashboardToolDbAccess_Test.php');
    }
}
?>