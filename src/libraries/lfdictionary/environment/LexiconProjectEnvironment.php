<?php
namespace libraries\lfdictionary\environment;
require_once(dirname(__FILE__) . '/../Config.php');
use libraries\lfdictionary\common\LoggerFactory;

/**
 * @see LexProject
 */
class LexiconProjectEnvironment {

	/**
	 * @var LFProjectModel
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
	// 			$masterFile=LF_BASE_PATH . "lfbase/data/" . LANGUAGE_FORGE_DEFAULT_SETTINGS_LEX;
	// 		} elseif ($this->_LFProjectModel->getType()=="Rapid Word Collection"){
	// 			$masterFile=LF_BASE_PATH . "lfbase/data/" . LANGUAGE_FORGE_DEFAULT_SETTINGS_RWC;
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
	 * @param LFProjectModel $LFProjectModel
	 * @return string
	 */
	static public function projectPath($LFProjectModel) {
		return LANGUAGEFORGE_VAR_PATH . 'work/' . $LFProjectModel->getName();
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

	static public function languageforgeStateRootPath() {
		return LANGUAGEFORGE_VAR_PATH . 'state/';
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



	/**
	 * Locates and returns the full path to the *.WeSayConfig file to use for this user / project.
	 * A .WeSayConfig file is created if needs be.
	 * @return string
	 * @throws \Exception
	 */
	static public function locateConfigFilePath($projectPath, $userName) {
		// 1) See if we can find a user specific settings file
		$filePath = self::userSettingsFilePath($projectPath, $userName);
		if (!file_exists($filePath)) {
			LoggerFactory::getLogger()->logDebugMessage(sprintf("Project settings file '%s' not found for user '%s' loading defaults.",
			 					$filePath,
			 					$userName
			 			));
			// 2) If not, look for a project wide settings file under LanguageForgeSettings
			$filePath = self::projectDefaultSettingsFilePath($projectPath);
			if (!file_exists($filePath)) {
				// Check and create the LanguageForgeSettings folder if needs be.
				if (!file_exists(LexiconProjectEnvironment::projectSettingsFolderPath($projectPath))) {
					mkdir(LexiconProjectEnvironment::projectSettingsFolderPath($projectPath));
				}
				// 3) If not, see if we can copy a *.WeSayConfig file from the root folder of the project
				$filesFound = glob($projectPath . '*' . LANGUAGE_FORGE_SETTINGS_EXTENSION);
				if (count($filesFound) > 0) {
					$source = $filesFound[0];
				} else {
					// 4) Failing everything we get a default config file from the template folder
					// TODO This will of course need to be fixed for the vernacular language CP 2010-10
					$source =self::projectDefaultSettingsFilePath(self::templatePath());
					if (!file_exists($source)) {
						throw new \Exception(sprintf(
									"Cannot access default user profile from file '%s'",
						$source
						));
					}
				}
				copy($source, $filePath);
			}
		}
		return $filePath;
	}
}
?>