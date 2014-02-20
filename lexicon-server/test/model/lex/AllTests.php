<?php
require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class AllModelLexTests extends TestSuite {
	
	function __construct() {
		parent::__construct();
		$this->addFile(TEST_PATH . 'model/lex/LexEntryModel_Test.php');
		$this->addFile(TEST_PATH . 'model/lex/Example_Test.php');
		$this->addFile(TEST_PATH . 'model/lex/MultiText_Test.php');
		$this->addFile(TEST_PATH . 'model/lex/Sense_Test.php');
	}

}

?>
