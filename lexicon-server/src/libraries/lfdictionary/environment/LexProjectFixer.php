<?php
namespace libraries\lfdictionary\environment;

use libraries\lfdictionary\common\LoggerFactory;
class LexProjectFixer
{
	/**
	 * @var LexProject
	 */
	private $_lexProject;
	
	
	function __construct($lexProject) {
		
		$this->_lexProject = $lexProject;
	}
	
	function checkProjectFolderExists() {
		$projectPath = $this->_lexProject->projectPath;
		return file_exists($projectPath); 		
	}
	
	function createProjectFolder() {
		$projectPath = $this->_lexProject->projectPath;
		mkdir($projectPath);
	}
	
	function checkLiftFileExists() {
		if (!$this->checkProjectFolderExists()) {
			return false;
		}
		$projectPath = $this->_lexProject->projectPath;
		
		// TODO Might like to try a lift file matching <projectName>.lift in the first instance. CP 2012-11
		
		// Try any lift file
		$filePath = glob($projectPath . '*.lift');
		$c = count($filePath);
		if ($c == 0) {
			LoggerFactory::getLogger()->logInfoMessage(sprintf("no lift file find in '%s'",$this->projectPath));
			return false;
		}
		if ($c > 1) {
			LoggerFactory::getLogger()->logDebugMessage(sprintf("%d lift files found in '%s' using '%s'", $c, $this->projectPath, $this->_liftFilePath));
		}
		return true;
	}
	
	function checkTemplatesExist() {
		$templatePath = LexProject::templatePath();
		if (!file_exists($templatePath)) {
			return false;
		}
		$templateFilePath = $templatePath . 'default.lift';
		if (!file_exists($templateFilePath)) {
			return false;
		}
		$templateFilePath = $templatePath . 'WritingSystems/qaa.ldml';
		if (!file_exists($templateFilePath)) {
			return false;
		}
		$templateFilePath = $templatePath . 'WritingSystems/en.ldml';
		if (!file_exists($templateFilePath)) {
			return false;
		}
		$templateFilePath = $templatePath . 'WritingSystems/idchangelog.xml';
		if (!file_exists($templateFilePath)) {
			return false;
		}
		return true;
	}
	
	function createLiftFile() {
		if (!$this->checkProjectFolderExists()) {
			$this->createProjectFolder();
		}
		if ($this->checkLiftFileExists()) {
			throw new \Exception(sprintf("Lift file '%s' already exists in '%s'",$this->_lexProject->projectName . ".lift", $this->_lexProject->projectPath));
		}
		
		// TODO working on the below :-)
		$templatePath = LexProject::templatePath();
		$projectPath = $this->_lexProject->projectPath;
		
		// Copy from default file/folder
		$this->fileCopy($templatePath, $projectPath);
		
		// Rename default lift in to project name lift file
		$liftFileName = $this->_lexProject->projectName . ".lift";
		rename($projectPath . 'default.lift', $projectPath . $liftFileName);
		
		// Rename default WeSay config in to project wysay config
		$configFilePath = LexProject::projectDefaultSettingsFilePath($projectPath);
		// 		rename($projectPath . "default.WeSayConfig", $configFilePath);
		
		// Rename default ldml to project *.ldml
		$ldmlFileName = $languageCode.".ldml";
		rename($projectPath . "WritingSystems/qaa.ldml", $projectPath . "WritingSystems/$ldmlFileName");
		
		// Language Code Format Changing into WeSay Config File
		$file = $configFilePath;
		$this->findReplace($file, $languageCode);
		
		// Language Code Format Changing into Langcode.idml File
		$file = $projectPath . "WritingSystems/$ldmlFileName";
		$this->findReplace($file, $languageCode);
		
		// Language Code Format Changing into idChangelog File
		$file =  $projectPath . "WritingSystems/idchangelog.xml";
		$this->findReplace($file, $languageCode);
		
		
		
		
	}
	
	function checkSettingsFileExists() {
		if (!$this->checkLiftFileExists()) {
			return false;
		}
	}
	
	function createSettingsFiles() {
		
	}
	
	function checkHasHg() {
		
	}
	
	function createHg() {
		
	}
	
	private function fileCopy($source, $target ) {
		if (is_dir($source)) {
			if (!is_dir($target)) {
				mkdir($target);
			}
			$d = dir($source);
			while (FALSE !== ($entry = $d->read())) {
				if ($entry == '.' || $entry == '..') {
					continue;
				}
				$entryFilePath = $source . '/' . $entry;
				if (is_dir($entryFilePath)) {
					$this->fileCopy($entryFilePath, $target . '/' . $entry);
					continue;
				}
				copy($entryFilePath, $target . '/' . $entry);
			}
			$d->close();
		} else {
			copy($source, $target);
		}
	}
	
	private function findReplace($filePath, $languageCode) {
		$content = file_get_contents($filePath);
		$newContent = str_replace("qaa", $languageCode, $content);
		file_put_contents($filePath, $newContent);
	}
	
	
}