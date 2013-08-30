<?php
require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class AllModelTests extends TestSuite {
	
    function __construct() {
        parent::__construct();
 		$this->addFile(TEST_PATH . 'model/UserModel_Test.php');
 		$this->addFile(TEST_PATH . 'model/ProjectModel_Test.php');
 		$this->addFile(TEST_PATH . 'model/MultipleModel_Test.php');
 		$this->addFile(TEST_PATH . 'model/PasswordModel_Test.php');
 		//$this->addFile(TEST_PATH . 'model/QuestionModel_Test.php');
 		//$this->addFile(TEST_PATH . 'model/AnswerModel_Test.php');
 		//$this->addFile(TEST_PATH . 'model/CommentModel_Test.php');
 		$this->addFile(TEST_PATH . 'model/Roles_Test.php');
    }

}

?>
