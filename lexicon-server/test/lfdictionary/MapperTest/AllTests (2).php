<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllMapperTests extends TestSuite {
    function __construct() {
        parent::__construct();
        $this->addFile(TEST_PATH . 'MapperTest/LiftUpdater_Test.php');
        $this->addFile(TEST_PATH . 'MapperTest/FieldSettingXmlJsonMapper_Test.php');
        $this->addFile(TEST_PATH . 'MapperTest/TaskSettingXmlJsonMapper_Test.php');
    }
}

?>