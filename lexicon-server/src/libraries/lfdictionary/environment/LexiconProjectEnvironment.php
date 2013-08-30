<?php
namespace libraries\lfdictionary\environment;

require_once(dirname(__FILE__) . '/../Config.php');

use libraries\lfdictionary\common\LoggerFactory;
use \models\ProjectModel;

/**
 * @see LexProject
 */
class LexiconProjectEnvironment {

	/**
	 * @var String
	 */
	private $_projectName;

	/**
	 * @var string
	 */
	private $_projectPath;

	/**
	 * @var string
	 */
	private $_workingPath;

	/**
	 *
	 * @param string $projectName
	 * @param string $workingPath
	 */
	public function __construct($projectName, $workingPath = LANGUAGE_FORGE_WORK_PATH) {
		
		// TODO May want to change $workingPath to $environment and a class to facilitate better testing. CP 2012-08
		if (!is_string($projectName)) {
			throw new \Exception('Type error: ProjectName not a string');
		}

		$this->_projectName = $projectName;
		$this->_workingPath = $workingPath;

		$this->_projectPath = $workingPath . $this->_projectName;
	}

	// 	private function checkAndUpdateUserDefSettings() {
	// 		$this->makeLanguageForgeSettingsFolderReady();

	// 		if ($this->_LFProjectModel->getType() == "Dictionary") {
	// 			$masterFile=LF_LIBRARY_PATH . "lfbase/data/" . LANGUAGE_FORGE_DEFAULT_SETTINGS_LEX;
	// 		} elseif ($this->_LFProjectModel->getType()=="Rapid Word Collection"){
	// 			$masterFile=LF_LIBRARY_PATH . "lfbase/data/" . LANGUAGE_FORGE_DEFAULT_SETTINGS_RWC;
	// 		} else {
	// 			throw new \libraries\lfdictionary\common\UserActionDeniedException("Unknown project type: ". $this->_LFProjectModel->getType());
	// 		}
	// 		$projectFile=$this->_projectPath. LANGUAGE_FORGE_SETTINGS . LANGUAGE_FORGE_DEFAULT_SETTINGS;

	// 		if (!file_exists($masterFile)) {
	// 			throw new \libraries\lfdictionary\common\UserActionDeniedException("Master default user setting file '" . $masterFile . "' missing");
	// 		}

	// 		if (!file_exists($projectFile)) {
	// 			copy($masterFile,$projectFile);
	// 			return;
	// 		}

	// 		$masterFileCrc = strtoupper(dechex(crc32(file_get_contents($masterFile))));
	// 		$projectFileCrc = strtoupper(dechex(crc32(file_get_contents($projectFile))));

	// 		if ($masterFileCrc!=$projectFileCrc) {
	// 			// files not the same
	// 			copy($masterFile,$projectFile);
	// 		} else {
	// 			// files the same, do nothing
	// 			return;
	// 		}
	// 	}

	private function cloneMasterAndWait($sourcePath) {
		$hgWrapper = new \libraries\lfdictionary\common\HgWrapper($this->_projectPath);
		$hgWrapper->cloneRepository($sourcePath);

		// Wait for the clone to complete by checking for a known file to be present. In this case the *.lift file.
		$searchFilePattern = $this->_projectPath . "/*.lift";
		$completed = false;
		$counter = 0;
		// TODO / Review If this initial clone is taking a really long time we may need to introduce a server api response that says
		// 'please wait'. Then client can poll until project is ready then carry on.
		while (!$completed && $counter < 60) {
			$filePaths = glob($searchFilePattern);
			if (!$filePaths) {
				sleep(5);
				$counter += 5;
			} else {
				$completed = true;
			}
		}
		if (!$completed) {
			LoggerFactory::getLogger()->logInfoMessage("The clone did not complete in time. No lift data file found in path '" . $this->_projectPath . "'");
		}
	}

	/**
	 * Returns the path to the project working folder.
	 * 		e.g. /var/lib/languageforge/work/someProjectName
	 * @param ProjectModel $projectModel
	 * @return string
	 */
	static public function projectPath($projectModel) {
		return LANGUAGEFORGE_VAR_PATH . 'work/' . $projectModel->projectCode;
	}

	/**
	 * Returns the path to the template folder
	 * @return string
	 * 		Example: /var/lib/languageforge/lexicon/template/
	 */
	static public function templatePath() {
		return LANGUAGEFORGE_VAR_PATH . "lexicon/template/";
	}

	/**
	 * Returns the full path to the project default settings file.
	 * @param string $projectPath
	 * @return string
	 * 		Example: /var/lib/languageforge/work/<some project>/LanguageForge/default.WeSayConfig
	 */
	static public function projectDefaultSettingsFilePath($projectPath) {
		return LexiconProjectEnvironment::projectSettingsFolderPath($projectPath) . LANGUAGE_FORGE_DEFAULT_SETTINGS;
	}

	static public function projectSettingsFolderPath($projectPath) {
		return $projectPath . LANGUAGE_FORGE_SETTINGS ;
	}

	static public function languageforgeWorkRootPath() {
		return LANGUAGEFORGE_VAR_PATH . 'work/';
	}


	function getPath() {
		return LANGUAGE_FORGE_WORK_PATH . $this->_projectName;
	}
	
	static public function getTemplateDefaultSettingsPath() {
		return self::templatePath() . LANGUAGE_FORGE_DEFAULT_SETTINGS;
	}

	
	/**
	 * Returns the full path to the user (dictionary) settings file.
	 * @param string $projectPath
	 * @param string $userName
	 * @return string
	 * 		Example: /var/lib/languageforge/work/<some project>/LanguageForge/<username>.WeSayConfig
	 */
	static public function userSettingsFilePath($projectPath, $userName) {
		return LexiconProjectEnvironment::projectSettingsFolderPath($projectPath) . $userName . LANGUAGE_FORGE_SETTINGS_EXTENSION;
	}




}
?>