<?php

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SIMPLETEST_PATH .  'autorun.php');

class AllTransliterationTests extends TestSuite {
    function __construct() {
        parent::__construct();

        // originally included tests, these pass IJH 2013-11
        $this->addFile(TEST_PATH . 'lfdictionary/TransliterationTest/TransliterationBasicFunctions_Test.php');
        
        // originally included tests, these fail IJH 2013-11
        
        // added other tests in same folder, these pass IJH 2013-11
        
        // added other tests in same folder, these fail IJH 2013-11
        
    }
}

?>