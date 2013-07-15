<?php
require_once(dirname(__FILE__) . '/testconfig.php');
require_once(SimpleTestPath . 'autorun.php');

class AllTests extends TestSuite {
    function __construct() {
        parent::__construct();
		$this->addFile(TestPath . 'DtoTest/AllTests.php');
		$this->addFile(TestPath . 'CommandTest/AllTests.php');
		$this->addFile(TestPath . 'MapperTest/AllTests.php');
		$this->addFile(TestPath . 'store/AllTests.php');
		$this->addFile(TestPath . 'EnvironmentTest/AllTests.php');
		$this->addFile(TestPath . 'APITest/AllTests.php');
		$this->addFile(TestPath . 'DashboardToolTest/AllTests.php');
		$this->addFile(TestPath . 'TransliterationTest/AllTests.php');
    }
}
?>