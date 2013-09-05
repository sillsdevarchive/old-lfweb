<?php
class LexProjectMockObject {

	/**
	 * @var StoreLiftTestEnvironment
	 */
	private $_liftEnvironment;

	/**
	 * @param StoreLiftTestEnvironment $liftEnvironment
	 */
	function __construct($liftEnvironment = null) {
		$this->_liftEnvironment = $liftEnvironment;
	}

	function getCurrentHash() {
		if ($this->_liftEnvironment) {
			return $this->_liftEnvironment->getCurrentHash();
		}
		return ''; // Will match the mongo default so no update will occur
	}

	function getLiftFilePath() {
		return $this->_liftEnvironment->getLiftFilePath();
	}
	
	function writingSystemsFolderPath() {
		return "test\lfdictionary\data\template\WritingSystems";
	}
}
?>