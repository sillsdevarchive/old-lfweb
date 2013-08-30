<?php
namespace libraries\lfdictionary\environment;

use libraries\palaso\CodeGuard;

use libraries\lfdictionary\common\LoggerFactory;
use models\ProjectModel;

class LexProject
{
	
	/**
	 * @var ProjectModel
	 */
	public $projectModel;
	
	/**
	 * @var string
	 */
	public $projectPath;
	
	/**
	 * @var ProjectState
	 */
	public $projectState;

	/**
	 * @var string
	 */
	public $projectName;
	
	/**
	 * @var string
	 */
	private $_liftFilePath;
	
	/**
	 * @param string $projectPath
	 */
	public function __construct($projectModel, $projectBasePath = LANGUAGE_FORGE_WORK_PATH) {
		CodeGuard::checkTypeAndThrow($projectModel, 'models\ProjectModel');
		
		$this->projectModel = $projectModel;
		$this->projectName = $projectName;
		$projectBasePath = rtrim($projectBasePath, DIRECTORY_SEPARATOR) . DIRECTORY_SEPARATOR;
		$this->projectPath = rtrim($projectBasePath . $this->projectModel->projectCode, DIRECTORY_SEPARATOR) . DIRECTORY_SEPARATOR;
		$this->projectState = new ProjectState($this->projectName);
		// If not ready, check for existence and mark ready if we can. This copes with Legacy project created before ProjectState
		if ($this->projectState->getState() == '') {
			if (file_exists($this->projectPath)) {
				$this->projectState->setState(\libraries\lfdictionary\environment\ProjectStates::Ready);
			}
		}
	}
	
	/**
	 * Creates a new Lexicon project
	 * @param ProjectModel $projectModel
	 * @throws \Exception
	 */
	public function createNewProject($projectModel) {
		if (is_dir($this->projectPath)) {
			throw new \Exception(sprintf("Cannot create new project '%s' already exists", $this->projectPath));
		}
		$fixer = new LexProjectFixer($this->projectName, $projectModel);
		$fixer->fixProjectV01();
		
		$this->makeLanguageForgeSettingsFolderReady();
		$this->projectState->setState(\libraries\lfdictionary\environment\ProjectStates::Ready);
	}
	
	private function makeLanguageForgeSettingsFolderReady() {
		$languageForgeSettingsPath = $this->projectPath . LANGUAGE_FORGE_SETTINGS;
		if (!is_dir($languageForgeSettingsPath)) {
			if (!mkdir($languageForgeSettingsPath)){
				throw new \Exception(sprintf("Cannot create user setting folder '%s'", $languageForgeSettingsPath));
			}
		}
	}
	
	/**
	 * Returns the full path to the user (dictionary) settings file.
	 * @param string $projectPath
	 * @param string $userName
	 * @return string
	 * 		Example: /var/lib/languageforge/work/<some project>/LanguageForge/<username>.WeSayConfig
	 */
	static public function userSettingsFilePath($projectPath, $userName) {
		return $projectPath . LANGUAGE_FORGE_SETTINGS . $userName . LANGUAGE_FORGE_SETTINGS_EXTENSION;
	}
	
	/**
	 * Returns the full path to the project default settings file.
	 * @param string $projectPath
	 * @return string
	 * 		Example: /var/lib/languageforge/work/<some project>/LanguageForge/default.WeSayConfig
	 */
	static public function projectDefaultSettingsFilePath($projectPath) {
		return $projectPath . LANGUAGE_FORGE_SETTINGS . LANGUAGE_FORGE_DEFAULT_SETTINGS;
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
	 * Returns the path to the resources folder.
	 * The resources folder contains resources such as the SemanticDomain files
	 * @return string
	 * 		Example: /var/lib/languageforge/lexicon/resources/
	 */
	static public function resourcePath() {
		return LANGUAGEFORGE_VAR_PATH . "lexicon/resources/";
	}
	
	/**
	 * Locates and returns the full path to the *.WeSayConfig file to use for this user / project.
	 * A .WeSayConfig file is created if needs be.
	 * @return string
	 * @throws \Exception
	 */
	static public function locateConfigFilePath($projectPath, $userName) {
		// 1) See if we can find a user specific settings file
		$filePath = LexiconProjectEnvironment::userSettingsFilePath($projectPath, $userName);
		if (!file_exists($filePath)) {
					LoggerFactory::getLogger()->logDebugMessage(sprintf("Project settings file '%s' not found for user '%s' loading defaults.",
			 					$filePath,
		 					$userName
			 			));
			// 2) If not, look for a project wide settings file under LanguageForgeSettings
			$filePath = LexiconProjectEnvironment::projectDefaultSettingsFilePath($projectPath);
			if (!file_exists($filePath)) {
				// Check and create the LanguageForgeSettings folder if needs be.
				if (!file_exists($projectPath . LANGUAGE_FORGE_SETTINGS)) {
					mkdir($projectPath . LANGUAGE_FORGE_SETTINGS);
				}
				// 3) If not, see if we can copy a *.WeSayConfig file from the root folder of the project
				$filesFound = glob($projectPath . '*' . LANGUAGE_FORGE_SETTINGS_EXTENSION);
				if (count($filesFound) > 0) {
					$source = $filesFound[0];
				} else {
					// 4) Failing everything we get a default config file from the template folder
					// TODO This will of course need to be fixed for the vernacular language CP 2010-10
					$source = LexiconProjectEnvironment::getTemplateDefaultSettingsPath();
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
	
	/**
	 * Locates and returns the full path to the *.WeSayConfig file to use for this user / project.
	 * A .WeSayConfig file is created if needs be.
	 * @return string
	 * @throws \Exception
	 */
	static public function locateSemanticDomainFilePath($languageCode) {
		$filePath = self::resourcePath() . 'SemanticDomains/Ddp4-' . $languageCode . '.xml';
		if (!file_exists($filePath)) {
			$filePathEnglish = self::resourcePath() . 'SemanticDomains/Ddp4-en.xml';
			if (!file_exists($filePathEnglish)) {
				throw new \Exception(sprintf("Semantic Domains: Resource file not found '%s'", $filePathEnglish));
			}
			LoggerFactory::getLogger()->logInfoMessage(sprintf("Semantic Domains: Resource not found '%s' using english", $filePath));
			$filePath = $filePathEnglish;
		}
		return $filePath;
	}
	
	public function getCurrentHash() {
		$hg = new \libraries\lfdictionary\common\HgWrapper($this->projectPath);
		try {
			$currentHash = $hg->getCurrentHash();
		} catch (\Exception $exception) {
			$currentHash = 'unknown';
			LoggerFactory::getLogger()->logInfoMessage(sprintf("WARNING: getCurrentHash failed for '%s'", $this->projectName));
		}
		return $currentHash;
	}

	public function getChorusNotesFilePath() {
		$liftFilePath = $this->getLiftFilePath();
		return $liftFilePath . '.ChorusNotes';
	}
	
	protected function locateLiftFilePath() {
		if ($this->_liftFilePath) {
			return $this->_liftFilePath;
		}
		
		$filePaths = glob($this->projectPath . '*.lift');

		$c = count($filePaths);
		if ($c == 0) {
			return null;
		}
		
		//try a lift file almost matching <projectName>.lift in the first instance
		$prePercent = 0;
		$bestMatchName = "";
		foreach ($filePaths as $filePath) {			
			similar_text(basename($filePath, ".lift"), $this->projectName, $percent);
			if ($prePercent <= $percent)
			{
				$prePercent = $percent;
				$bestMatchName = $filePath;
			}
		}
		if ($bestMatchName==="")
		{
			$this->_liftFilePath = $filePaths[0];
		}else
		{
			$this->_liftFilePath = $bestMatchName;
		}
		if ($c > 1) {
			LoggerFactory::getLogger()->logInfoMessage(sprintf("%d lift files found in '%s' using '%s'", $c, $this->projectPath, $this->_liftFilePath));
		}

		return $this->_liftFilePath;
	}
	
	public function getLiftFilePath() {
		$this->isReadyOrThrow();
		$liftFilePath = $this->locateLiftFilePath();
		if ($liftFilePath == null) {
			$fixer = new LexProjectFixer($lexProject, $projectModel);
			$fixer->fixProjectVLatest();
			return $fixer->getLiftFilePath();
		}
		return $liftFilePath;
	}
	
	/**
	 * @return bool
	 */
	public function isReady() {
		$state =  $this->projectState->getState();
		if ($state != \libraries\lfdictionary\environment\ProjectStates::Ready) {
			return false;
		}
		return true;
	}
	
	/**
	 * @return bool
	 */
	public function isReadyOrThrow() {
		$result = $this->isReady();
		if (!$result) {
			throw new \Exception(sprintf(
					"The project '%s' is not yet ready for use.",
					$this->projectName
			));
		}
		return $result;
	}
}


?>
