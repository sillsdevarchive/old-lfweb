<?php
require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class AllDtoTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
 		$this->addFile(TEST_PATH . 'dto/QuestionCommentDto_Test.php');
 		$this->addFile(TEST_PATH . 'dto/ActivityDto_Test.php');
		$this->addFile(TEST_PATH . 'dto/ProjectListDto_Test.php');
		$this->addFile(TEST_PATH . 'dto/ProjectPageDto_Test.php');
		$this->addFile(TEST_PATH . 'dto/ProjectSettingsDto_Test.php');
		$this->addFile(TEST_PATH . 'dto/QuestionListDto_Test.php');
    }

}

?>
