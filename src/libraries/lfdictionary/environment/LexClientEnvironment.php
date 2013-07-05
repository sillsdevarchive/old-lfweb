<?php
namespace environment;

use \environment\LexProjectUserSettings;
use lfbase\common\LoggerFactory;
class LexClientEnvironment
{
	
	/**
	 * @var ProjectModel
	 */
	public $projectModel;
	
	/**
	 * @var UserModel
	 */
	public $userModel;
	
	/**
	 * @var ProjectAccess
	 */
	public $projectAccess;
	
	/**
	 * @var LexProject
	 */
	private $_lexProject;
	
	/**
	 * @var int
	 */
	private $_userId;
	
	/**
	 * @param int $projectNodeId
	 * @param int $userId
	 * @throws \Exception
	 */
	public function __construct($projectNodeId, $userId) {
		
		$this->_userId = $userId;
		$this->projectModel = new \lfbase\environment\ProjectModel($projectNodeId);
		$this->userModel = new \lfbase\environment\UserModel($userId);
		$this->projectAccess = new \lfbase\environment\ProjectAccess($projectNodeId, $userId);
		$this->_lexProject = new \environment\LexProject($this->projectModel->getName());
		
		LoggerFactory::getLogger()->logInfoMessage(sprintf('LexClientEnvironment P=%s (%d) U=%s (%d)',
			$this->projectModel->getName(),
			$projectNodeId,
			$this->userModel->getUserName(),
			$userId
		));
		
	}
	
	/**
	 * Returns the javascript settings variables for use by the Lexical Client application
	 * @return string
	 */
	public function getSettings() {
		$this->isReady();
		
		$projectModel = $this->projectModel;
		$userModel =  new \lfbase\environment\UserModel($this->_userId);
		$clientEnvironmentDto = new \lfbase\dto\ClientEnvironmentDto($projectModel, $userModel, $this->projectAccess);
		$lexProjectUserSettings = new LexProjectUserSettings($projectModel, $userModel);
		$partOfSpeechSettingsModel = new \environment\PartOfSpeechSettingsModel($projectModel);
		
		$settingsString = '<script type="text/javascript" language="javascript">' . "\n" .
			'var settingsPartOfSpeech = ' . json_encode($partOfSpeechSettingsModel->encode()) . ";\n" .
			'var clientEnvironment = ' . json_encode($clientEnvironmentDto->encode()) . ";\n" .
			'var taskSettings = ' . json_encode($lexProjectUserSettings->encodeTasks()) . ";\n" .
			'var fieldSettings = ' . json_encode($lexProjectUserSettings->encodeFields(LexProjectUserSettings::FOR_BASE)) . ";\n" .
			'var fieldSettingsForAddMeaning = ' . json_encode($lexProjectUserSettings->encodeFields(LexProjectUserSettings::FOR_ADD_MEANING_MODEL)) . ";\n" .
			'var fieldSettingsForAddPOS = ' . json_encode($lexProjectUserSettings->encodeFields(LexProjectUserSettings::FOR_ADD_POS_MODEL)) . ";\n" .
			'var fieldSettingsForAddExample = ' . json_encode($lexProjectUserSettings->encodeFields(LexProjectUserSettings::FOR_ADD_EXAMPLE_MODEL)) . ";\n" .
			'var fieldSettingsForGatherWordFromList = ' . json_encode($lexProjectUserSettings->encodeFields(LexProjectUserSettings::FOR_GATHER_WORD_FROM_WORD_LIST)) . ";\n" .
			'var fieldSettingsForGatherWordFromSemanticDomain = ' . json_encode($lexProjectUserSettings->encodeFields(LexProjectUserSettings::FOR_GATHER_WORD_FROM_SEMANTIC_DOMAIN)) . ";\n" .
			'</script>';

		return $settingsString;
	}

	private function isReady() {
		if (!$this->_lexProject->isReady()) {
			LoggerFactory::getLogger()->logInfoMessage(sprintf("Project %s not ready, creating new project...", $this->projectModel->getName()));
			$this->_lexProject->createNewProject($this->projectModel->getLanguageCode());
		}
	}
	
}

?>