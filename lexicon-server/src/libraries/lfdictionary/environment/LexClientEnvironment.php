<?php
namespace libraries\lfdictionary\environment;

use libraries\lfdictionary\environment\LexProjectUserSettings;
use libraries\lfdictionary\common\LoggerFactory;
class LexClientEnvironment
{
	
	/**
	 * @var LFProjectModel
	 */
	public $LFProjectModel;
	
	/**
	 * @var LFUserModel
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
	 * @var String
	 */
	private $_userId;
	
	/**
	 * @param int $projectNodeId
	 * @param int $userId
	 * @throws \Exception
	 */
	public function __construct($projectNodeId, $userId) {
		
		$this->_userId = $userId;
		$this->LFProjectModel = new \libraries\lfdictionary\environment\LFProjectModel($projectNodeId);
		$this->userModel = new \libraries\lfdictionary\environment\LFUserModel($userId);
		$this->projectAccess = new \libraries\lfdictionary\environment\ProjectAccess($projectNodeId, $userId);
		$this->_lexProject = new \libraries\lfdictionary\environment\LexProject($this->LFProjectModel->getName());
		
		LoggerFactory::getLogger()->logInfoMessage(sprintf('LexClientEnvironment P=%s (%s) U=%s (%s)',
			$this->LFProjectModel->getName(),
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
		
		$LFProjectModel = $this->LFProjectModel;
		$userModel =  new \libraries\lfdictionary\environment\LFUserModel($this->_userId);
		$clientEnvironmentDto = new \libraries\lfdictionary\dto\ClientEnvironmentDto($LFProjectModel, $userModel, $this->projectAccess);
		$lexProjectUserSettings = new LexProjectUserSettings($LFProjectModel, $userModel);
		$partOfSpeechSettingsModel = new \libraries\lfdictionary\environment\PartOfSpeechSettingsModel($LFProjectModel);
		
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
			LoggerFactory::getLogger()->logInfoMessage(sprintf("Project %s not ready, creating new project...", $this->LFProjectModel->getName()));
			$this->_lexProject->createNewProject($this->LFProjectModel->getLanguageCode());
		}
	}
	
}

?>