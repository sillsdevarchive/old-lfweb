<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath .  'autorun.php');

class AllTransliterationTests extends TestSuite {
    function __construct() {
        parent::__construct();
        $this->addFile(TestPath . 'TransliterationTest/TransliterationBasicFunctions_Test.php');
    }
}

?>