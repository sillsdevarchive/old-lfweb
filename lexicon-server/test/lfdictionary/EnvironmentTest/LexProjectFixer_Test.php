<?php
use environment\LexProject;

require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

use \lfbase\common\HgWrapper;
use \environment;

class LexProjectFixerTestEnvironment {
	
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
	
	const PROJECT_NAME = 'LexProjectFixer_Test';
	
	function __construct($doInit = false, $projectName = self::PROJECT_NAME, $projectWorkPath = null) {
		$this->projectName = $projectName;
		if ($projectWorkPath == null) {
			$this->projectWorkPath = self::normalizePath(sys_get_temp_dir());
			self::recursiveDelete($this->getProjectPath());
		}
		if ($doInit) {
			$this->init();
		}
// 		echo "construct";
	}
	
	function __destruct() {
 		self::recursiveDelete($this->getProjectPath());
// 		echo "destruct";
	}

	public function init() {
		if (!file_exists($this->getProjectPath())) {
			mkdir($this->getProjectPath());
		}
	}
	
	public function lexProject() {
		return new LexProject($this->projectName, $this->projectWorkPath);
	}
	
	public function getProjectPath() {
		return self::normalizePath($this->projectWorkPath . $this->projectName);
	}
	
	public function getFilePath($fileName) {
		return $this->getProjectPath() . $fileName;
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
	}
	
}

class TestOfLexProjectFixer extends UnitTestCase {

	public function testCheckProjectFolderExists_NoFolder_False() {
		$e = new LexProjectFixerTestEnvironment();
		$f = new \environment\LexProjectFixer($e->lexProject());
		$result = $f->checkProjectFolderExists();
		$this->assertTrue($result === false, "Expected false got $result");
	}
	
	public function testCheckProjectFolderExists_Folder_True() {
		$e = new LexProjectFixerTestEnvironment();
		$e->init();		$e->init(); // Creates the folder
		
		$f = new \environment\LexProjectFixer($e->lexProject());
		$result = $f->checkProjectFolderExists();
		$this->assertTrue($result);
	}
	
	public function testCreateProjectFolder_NoFolder_CreatesFolder() {
		$e = new LexProjectFixerTestEnvironment();
		$f = new \environment\LexProjectFixer($e->lexProject());
		// Pre-condition check
		$pre = file_exists($e->getProjectPath());
		$this->assertFileNotExist($e->getProjectPath());
		$f->createProjectFolder();
		$result = file_exists($e->getProjectPath());
		$this->assertFileExists($e->getProjectPath());
	}
	
	public function testCreateProjectFolder_Folder_CreatesFolder() {
		$e = new LexProjectFixerTestEnvironment();
		$e->init(); // Creates the folder
		$f = new \environment\LexProjectFixer($e->lexProject());
		// Pre-condition check
		$pre = file_exists($e->getProjectPath());
		$this->assertFileExists($e->getProjectPath());
		$this->expectError(); // Currently we expect a php error, and do not throw our own exception.
		$f->createProjectFolder();
	}
	
	public function testCheckLiftFile_NoFile_False() {
		$e = new LexProjectFixerTestEnvironment(true);
		$f = new \environment\LexProjectFixer($e->lexProject());
		$result = $f->checkLiftFileExists();
		$this->assertTrue($result === false, "Expected false got $result");
	}
	
	public function testCheckLiftFile_File_True() {
		$e = new LexProjectFixerTestEnvironment(true);
		$f = new \environment\LexProjectFixer($e->lexProject());
		$e->addFile($e->projectName . ".lift", '<lift />');
		$result = $f->checkLiftFileExists();
		$this->assertTrue($result === true, "Expected true got $result");
	}
	
	public function testCheckTemplatesExist_Templates_True() {
		$e = new LexProjectFixerTestEnvironment(true);
		$f = new \environment\LexProjectFixer($e->lexProject());
		$result = $f->checkTemplatesExist();
		$this->assertTrue($result === true, "Expected true got $result");
	}
	
	public function testCreateLiftFile_NoFile_CreatesFile() {
		$e = new LexProjectFixerTestEnvironment(true);
		$f = new \environment\LexProjectFixer($e->lexProject());
		$filePath = $e->getFilePath($e->projectName . ".lift");
		$this->assertFileNotExist($filePath);
		$f->createLiftFile();
		$this->assertFileExists($filePath);
	}
	
	
	private function assertFileExists($filePath) {
		$this->assertTrue(file_exists($filePath), sprintf("Expected file not found '%s'", $filePath));
	}

	private function assertFileNotExist($filePath) {
		$this->assertFalse(file_exists($filePath), sprintf("Unexpected file found '%s'", $filePath));
	}
	
}

?>