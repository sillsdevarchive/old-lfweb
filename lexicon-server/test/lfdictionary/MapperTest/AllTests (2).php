<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath .  'autorun.php');

class AllMapperTests extends TestSuite {
    function __construct() {
        parent::__construct();
        $this->addFile(TestPath . 'MapperTest/LiftUpdater_Test.php');
        $this->addFile(TestPath . 'MapperTest/FieldSettingXmlJsonMapper_Test.php');
        $this->addFile(TestPath . 'MapperTest/TaskSettingXmlJsonMapper_Test.php');
    }
}

?>