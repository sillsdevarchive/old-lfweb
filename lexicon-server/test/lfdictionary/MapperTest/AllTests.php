<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

class AllMapperTests extends TestSuite {
    function __construct() {
        parent::__construct();
        $this->addFile(TestPath . 'MapperTest/InputSystemXmlJsonMapper_Test.php');
    }
}

?>