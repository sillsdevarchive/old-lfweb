<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath .  'autorun.php');

class AllStoreTests extends TestSuite {
    function __construct() {
        parent::__construct();
        $this->addFile(TestPath . 'store/LexStoreMongo_Tests.php');
        $this->addFile(TestPath . 'store/LexStore_Tests.php');
        $this->addFile(TestPath . 'store/LiftScanner_Tests.php');
        $this->addFile(TestPath . 'store/LiftMongoImporter_Tests.php');
    }
}

?>