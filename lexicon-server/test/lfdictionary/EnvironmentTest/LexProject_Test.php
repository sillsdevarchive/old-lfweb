<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

use \libraries\lfdictionary\common\HgWrapper;

class LexProjectTestEnvironment {
	
	/**
	 * @var string
	 */
	public $projectWorkPath;
	
	/**
	 * @var string
	 */
	public $projectName;
	
	/**
	 * @var HgWrapper
	 */
	private $_hg;
	
	const PROJECT_NAME = 'LexProject_Test';
	
	function __construct($projectName = self::PROJECT_NAME, $projectWorkPath = null, $doInit = true) {
		$this->projectName = $projectName;
		if ($projectWorkPath == null) {
			$this->projectWorkPath = self::normalizePath(sys_get_temp_dir());
			self::recursiveDelete($this->getProjectPath());
		}
		if ($doInit) {
			if (!file_exists($this->getProjectPath())) {
				mkdir($this->getProjectPath());
			}
			$this->_hg = new \libraries\lfdictionary\common\HgWrapper($this->getProjectPath());
			$this->_hg->init();
		}
// 		echo "construct";
	}
	
	function __destruct() {
 		self::recursiveDelete($this->getProjectPath());
// 		echo "destruct";
	}

	public function getProjectPath() {
		return self::normalizePath($this->projectWorkPath . $this->projectName);
	}
	
	static private function recursiveDelete($str) {
		if(is_file($str)) {
			return @unlink($str);
		} elseif(is_dir($str)) {
			$str = self::normalizePath($str);
			$objects = scandir($str);
			foreach ($objects as $object) {
				if ($object === "." || $object === "..") {
					continue;
				}
				self::recursiveDelete($str . $object);
			}
			reset($objects);
			@rmdir($str);
		}
	}
	
	static private function normalizePath($path) {
		$path = rtrim($path, DIRECTORY_SEPARATOR) . DIRECTORY_SEPARATOR;
		return $path;
	}
	
	public function addFile($fileName, $contents) {
		$filePath = $this->getProjectPath() . $fileName;
		file_put_contents($filePath, $contents);
		$this->_hg->addFile($filePath);
		$this->_hg->commit("File added");
	}
	
}

class TestOfLexProject extends UnitTestCase {

	function testConstructor_SetsAndNormalizeProjectPath() {
		$project = new \environment\LexProject('SomeProject', '/tmp');
		$this->assertEqual('/tmp/SomeProject/', $project->projectPath);
	}
	
	function testGetCurrentHash_SomeRepo_ReturnsHash() {
		$e = new LexProjectTestEnvironment();
		$e->addFile("File1.txt", "Contents");
		
		$project = new \environment\LexProject($e->projectName, $e->projectWorkPath);
		$result = $project->getCurrentHash();
		$this->assertEqual(12, strlen($result));
	}
	
	function testGetLiftFilePath_NoLiftFile_Throws() {
		$e = new LexProjectTestEnvironment();
		$project = new \environment\LexProject($e->projectName, $e->projectWorkPath);
		$this->expectException('Exception');
		$result = $project->getLiftFilePath();
	}
	
	function testGetLiftFilePath_LiftFile_ReturnsFilePath() {
		$expected = 'Test.lift';
		$e = new LexProjectTestEnvironment();
		$e->addFile($expected, '<lift />');
		$project = new \environment\LexProject($e->projectName, $e->projectWorkPath);
		$result = $project->getLiftFilePath();
		$this->assertEqual($e->getProjectPath() . $expected, $result);
	}
	
	function testCreateNewProject_HasRequiredFiles() {
		$e = new LexProjectTestEnvironment(LexProjectTestEnvironment::PROJECT_NAME, null, false);
		$project = new \environment\LexProject($e->projectName, $e->projectWorkPath);
		$project->createNewProject('de');
		
		$this->assertFileExists($e->getProjectPath() . $e->projectName . '.lift');
	}
	
	private function assertFileExists($filePath) {
		$this->assertTrue(file_exists($filePath), sprintf("Expected file not found '%s'", $filePath));
	}
	
}

?>