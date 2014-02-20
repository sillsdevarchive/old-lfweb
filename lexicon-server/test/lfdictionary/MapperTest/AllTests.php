<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
// require_once(LF_BASE_PATH . "Loader.php");

class AllMapperTests extends TestSuite {
    function __construct() {
        parent::__construct();
        
        // originally included tests, these pass IJH 2013-11
        
        // originally included tests, these fail IJH 2013-11
        $this->addFile(TEST_PATH . 'lfdictionary/MapperTest/InputSystemXmlJsonMapper_Test.php');
        
        // added other tests in same folder, these pass IJH 2013-11
        $this->addFile(TEST_PATH . 'lfdictionary/MapperTest/LiftUpdater_Test.php');
        
        // added other tests in same folder, these fail IJH 2013-11
        $this->addFile(TEST_PATH . 'lfdictionary/MapperTest/FieldSettingXmlJsonMapper_Test.php');
        $this->addFile(TEST_PATH . 'lfdictionary/MapperTest/TaskSettingXmlJsonMapper_Test.php');
        
    }
}

?>