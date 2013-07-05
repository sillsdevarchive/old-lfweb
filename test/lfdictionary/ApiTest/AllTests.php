<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath .  'autorun.php');

class AllApiTests extends TestSuite {
    function __construct() {
        parent::__construct();
        $this->addFile(TestPath . 'ApiTest/LFBaseAPI_Test.php');
        $this->addFile(TestPath . 'ApiTest/LanguageDepotAPI_Test.php');
	  }
}

?>