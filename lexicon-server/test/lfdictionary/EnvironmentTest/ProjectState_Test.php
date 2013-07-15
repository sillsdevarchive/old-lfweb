<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

require_once(SOURCE_PATH . 'environment/ProjectState.php');

class ProjectStateTestEnvironment {
	
	const PROJECT_NAME = 'qaa-test-dictionary';
	
}

class TestOfProjectState extends UnitTestCase {

	function testReadFirst_NoThrow() {
		$subject = new \environment\ProjectState(ProjectStateTestEnvironment::PROJECT_NAME);
		@unlink($subject->filePath());
		$state = $subject->getState();
	}
	
	/* This can't actually be unit tested, but was useful to run through in the debugger. CP.
	function testReadTwice_DoesntReadFileSecondTime() {
		$subject = new \environment\ProjectState(ProjectStateTestEnvironment::PROJECT_NAME);
		@unlink($subject->filePath());
		$state = $subject->getState();
		$state = $subject->getState();
	}
	*/
	
	function testFilePath_ReturnCorrect() {
		$subject = new \environment\ProjectState(ProjectStateTestEnvironment::PROJECT_NAME);
		$filePath = $subject->filePath();
		$this->assertEqual('/var/lib/languageforge/state/qaa-test-dictionary.state', $filePath);
	}
	
	function testSetState_FileExists() {
		$subject = new \environment\ProjectState(ProjectStateTestEnvironment::PROJECT_NAME);
		$subject->setState(\environment\ProjectStates::Importing);
		$result = $subject->getState();
		$this->assertEqual(\environment\ProjectStates::Importing, $result);
		$this->assertTrue(file_exists($subject->filePath()));
	}
	
	function testSetGetStateByReadFile_ReturnCorrect() {
		// Write the state file
		$subject = new \environment\ProjectState(ProjectStateTestEnvironment::PROJECT_NAME);
		$subject->setState(\environment\ProjectStates::Importing);
		// Use a new instance to read it back
		$subject = new \environment\ProjectState(ProjectStateTestEnvironment::PROJECT_NAME);
		$result = $subject->getState();
		$this->assertEqual(\environment\ProjectStates::Importing, $result);
	}
	
	function testSetGetMessage_ReturnCorrect() {
		// Write the state file
		$subject = new \environment\ProjectState(ProjectStateTestEnvironment::PROJECT_NAME);
		$subject->setState(\environment\ProjectStates::Importing, 'Some message');
		// Use a new instance to read it back
		$subject = new \environment\ProjectState(ProjectStateTestEnvironment::PROJECT_NAME);
		$result = $subject->getMessage();
		$this->assertEqual('Some message', $result);
	}
	
	function testSetMessage_WithArray_Implodes() {
		// Write the state file
		$subject = new \environment\ProjectState(ProjectStateTestEnvironment::PROJECT_NAME);
		$testMesesage = array('Line 1', 'Line 2');
		$subject->setState(\environment\ProjectStates::Importing, $testMesesage);
		// Use a new instance to read it back
		$subject = new \environment\ProjectState(ProjectStateTestEnvironment::PROJECT_NAME);
		$result = $subject->getMessage();
		$this->assertEqual('Line 1; Line 2', $result);
	}

}

?>