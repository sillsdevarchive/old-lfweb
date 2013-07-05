<?php
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__) . '/Config.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

use store\LexStoreMissingInfo;
use environment\ProjectState;
use dto\ProjectStateDTO;
use dto\ListDTO;
use store\LexStore;
use lfbase\common\AsyncRunner;
use lfbase\environment\ProjectAccess;
use lfbase\environment\ProjectPermission;
use lfbase\dto\ResultDTO;
use dashboardtool\HistoricalHgDataFetcher;
use lfbase\common\LoggerFactory;

/**
 * The main json-rpc Lexical API
 * Provides functions related to Lexicon management. Lexical Entries can be created, updated, deleted, and queried.
 * Provides functions for enhancing and building a Lexicon; RapidWords, and WordPacks for gathering words; MissingInfo for adding
 * additional info to Lexical Entries.
 */
class LFApi extends \LFBaseAPI
{
	
	/**
	 * @var LexProject
	 */
	private $_projectAccess;
	private $_lexProject;
	
	protected function initialize($projectNodeId, $userId) {
		
		LoggerFactory::getLogger()->logInfoMessage("Lexicon Project initialize...");
		$this->_userId = $userId;
		$this->_projectNodeId = $projectNodeId;
		$this->_projectModel = new \lfbase\environment\ProjectModel($projectNodeId);
		$this->_userModel = new \lfbase\environment\UserModel($userId);

		LoggerFactory::getLogger()->logInfoMessage(sprintf('LexAPI P=%s (%d) U=%s (%d)',
		$this->_projectModel->getName(),
		$projectNodeId,
		$this->_userModel->getUserName(),
		$userId
		));
		$this->_lexProject = new \environment\LexProject($this->_projectModel->getName());
		$this->_projectAccess = new \lfbase\environment\ProjectAccess($this->_projectNodeId,$this->_userId);
		$this->_projectPath = \environment\LexiconProjectEnvironment::projectPath($this->_projectModel);
	}

	/**
	 * Creates a new project.
	 * @return ProjectStateDTO
	 */
	function create() {
		$this->_lexProject->createNewProject($this->_projectModel->getLanguageCode());
		return $this->state();
	}

	/**
	 * @return bool
	 */
	private function isReadyOrThrow() {
		return $this->_lexProject->isReadyOrThrow();
	}

	/**
	 * Get word list
	 * @param int $start
	 * @param int $end
	 * @return \dto\ListDTO
	 */
	function getList($start, $end) {
		$this->isReadyOrThrow();

		$store = $this->getLexStore();
		$result = $store->readEntriesAsListDTO($start, $end - $start);
		return $result->encode();
	}

	/**
	 * Returns a list of suggestions that are similar to the $search term given.
	 * Up to $limit results are returned.
	 * @param string $field
	 * @param string $search
	 * @param int $indexFrom
	 * @param int $limit
	 * @return \dto\AutoListDTO
	 */
	function getWordsForAutoSuggest($field, $search, $indexFrom, $limit) {
		$this->isReadyOrThrow();

		$store = $this->getLexStore();
		$result = $store->readSuggestions($field, $search, $indexFrom, $limit);
		return $result->encode();
	}

	/**
	 * Get a single Lexical Entry
	 * @param unknown_type $guid
	 * @return dto\EntryDTO
	 */
	function getEntry($guid) {
		$this->isReadyOrThrow();

		$store = $this->getLexStore();
		$result = $store->readEntry($guid);

		return $result->encode();
	}

	/**
	 * Delete a Lexical Entry
	 * @param string $guid
	 * @param string $mercurialSHA
	 * @throws \lfbase\common\UserActionDeniedException
	 * @return ResultDTO
	 */
	function deleteEntry($guid, $mercurialSHA) {
		$this->isReadyOrThrow();

		//Error Validtion for User having access to Delete the project
		$projectModel = $this->_projectModel;
		if (!$this->_projectAccess->hasPermission(ProjectPermission::CAN_DELETE_ENTRY)) {
			throw new \lfbase\common\UserActionDeniedException('Access Denied For Delete');
		}
		$store = $this->getLexStore();
		$store->deleteEntry($guid, $mercurialSHA);
		$resultDTO = new ResultDTO(true);
		return $resultDTO->encode();
	}

	/**
	 * Create / Update a single Lexical Entry
	 * @param EntryDTO $entry
	 * @param string $action
	 * @throws \lfbase\common\UserActionDeniedException
	 * @return ResultDTO
	 */
	function saveEntry($entry, $action) {
		$this->isReadyOrThrow();
		// Check that user is a member of the project.
		if (!$this->_projectModel->isUserInProject($this->_userId)) {
			throw new \lfbase\common\UserActionDeniedException('User must have joined the community in order to create/update projects');
		}
		// Check that user has edit privileges on the project
		$userModel = $this->_userModel;
		if (!$this->_projectAccess->hasPermission(ProjectPermission::CAN_EDIT_ENTRY)) {
			throw new \lfbase\common\UserActionDeniedException('Access Denied For Update');
		}
		// Save Entry
		$rawEntry = json_decode($entry, true);
		$entryDto = dto\EntryDTO::createFromArray($rawEntry);
		$store = $this->getLexStore();
		$store->writeEntry($entryDto, $action);

		$resultDTO = new ResultDTO(true);
		return $resultDTO->encode();
	}

	/**
	 * Returns a ListDTO of entries that do not have data for $language in the given $field.
	 * @param MissingInfoType $field
	 * @see LexStoreMissingInfo
	 * @return dto\ListDTO
	 */
	function getMissingInfo($field) {
		$this->isReadyOrThrow();

		$store = $this->getLexStore();
		$result = $store->readMissingInfo($field);

		return $result->encode();
	}

	// Gather words from Text box
	function getGatherWords($words,$filename) {
		$this->isReadyOrThrow();

		$projectModel = $this->_projectModel;
		$languageCode = $projectModel->getLanguageCode();

		// get all from lift file.
		$existWordsList=$this->getList(1,PHP_INT_MAX);
		if ( \lfbase\common\TextFormatHelper::startsWith($filename,"GWTU-")) {
			// it is from uploaded file.
			$uploadedFolder = PHP_UPLOAD_PATH . session_id();
			$uploadedBinFile=$uploadedFolder . "/" . $filename . ".bin";
			$uploadedInfoFile=$uploadedFolder . "/" . $filename . ".info";
			// read everything from uploaded file
			$fileHandler = fopen($uploadedBinFile, 'r');
			if (!$fileHandler)
			{
				throw new \lfbase\common\UserActionDeniedException('File upload failed.');
			}
			if (filesize($uploadedBinFile)==0)
			{
				return 0;
			}
			$fileData=fread($fileHandler,filesize($uploadedBinFile));
			fclose($fileHandler);
			unlink($uploadedBinFile);
			unlink($uploadedInfoFile);
			rmdir($uploadedFolder);
			// format conversion
			$words=\lfbase\common\TextFormatHelper::convertToUTF8String($fileData);
		}
		$existWords= array();
		$wordEntries=$existWordsList['entries'];
		for($i = 0; $i <= $existWordsList['count']; $i++) {
			if (array_key_exists($i, $wordEntries)) {
				if (array_key_exists('entry', $wordEntries[$i])) {
					if (array_key_exists($languageCode, $wordEntries[$i]['entry'])) {
						$existWords[] = $wordEntries[$i]['entry'][$languageCode];
					}
				}
			}
		}

		$command = new \commands\GatherWordCommand($this->_lexProject->getLiftFilePath(),$languageCode,$existWords,$words);
		return $command->execute();
	}

	function getListForGatherWord() {
		$this->isReadyOrThrow();

		$wordPackFile= LEXICON_WORD_LIST_SOURCE . LEXICON_WORD_PACK_FILE_NAME;

		$store = $this->getLexStore();
		// read all exist words from DB
		$existWordListDto = $store->readEntriesAsListDTO(0, PHP_INT_MAX);

		$command = new \commands\GetWordListFromWordPackCommand($existWordListDto, $wordPackFile);
		$result = $command->execute();
		return $result->encode();
	}

	function getEntryForGatherWord($guid) {
		$this->isReadyOrThrow();

		$wordPackFile= LEXICON_WORD_LIST_SOURCE . LEXICON_WORD_PACK_FILE_NAME;
		$command = new \commands\GetWordCommand($wordPackFile, $guid);
		$result = $command->execute();
		return $result->encode();
	}

	function getDomainTreeList() {
		$this->isReadyOrThrow();

		$command = new \commands\GetDomainTreeListCommand(\environment\LexProject::locateSemanticDomainFilePath('en'), 'en');
		$result = $command->execute();
		return $result->encode();
	}

	function getDomainQuestion($guid) {
		$this->isReadyOrThrow();

		$command = new \commands\GetDomainQuestionCommand(\environment\LexProject::locateSemanticDomainFilePath('en'), 'en', $guid);
		$result = $command->execute();
		return $result->encode();
	}

	function getComments($messageStatus,$messageType, $startIndex,$limits, $isRecentChanges) {
		$this->isReadyOrThrow();

		$chorusNotesFilePath= $this->_lexProject->getLiftFilePath() . ".ChorusNotes";
		$command = new \commands\GetCommentsCommand($chorusNotesFilePath, $messageStatus,$messageType, $startIndex,$limits,$isRecentChanges);
		$result = $command->execute();
		return $result->encode();
	}

	function saveNewComment($messageStatus,$parentGuid, $commentMessage,$isRootMessage) {
		$this->isReadyOrThrow();

		$chorusNotesFilePath = $this->_lexProject->getLiftFilePath() . ".ChorusNotes";
		$now = new DateTime;
		$w3cDateString = $now->format(DateTime::W3C);
		$userModel = $this->_userModel;
		$messageType=0;
		$command = new \commands\SaveCommentsCommand($chorusNotesFilePath, $messageStatus,$messageType, $parentGuid,$commentMessage,$w3cDateString,$userModel->getUserName(),$isRootMessage);
		$result = $command->execute();
		return $result->encode();
	}

	function getDashboardData($actRange) {
		$this->isReadyOrThrow();

		$command = new \commands\GetDashboardDataCommand($this->_projectNodeId, $this->_lexProject->getLiftFilePath(),$actRange);
		$result = $command->execute();

		return $result->encode();
	}

	function getDashboardUpdateRunning() {
		$command = new \commands\UpdateDashboardCommand($this->_projectNodeId, $this->_projectModel, $this->_lexProject);
		return $command->execute();
	}


	function getUserFieldsSetting($userId) {
		$userModel = new \lfbase\environment\UserModel($userId);
		// use user name may not a good idea, Linux box is case sensitve,
		// so all user name will save in lowercase
		$strName = $userModel->getUserName();
		$strName = mb_strtolower($strName, mb_detect_encoding($strName));
		$command = new \commands\GetSettingUserFieldsSettingCommand($this->_projectPath,$strName);
		$result = $command->execute();
		return $result;
	}

	function getUserTasksSetting($userId) {
		$userModel = new \lfbase\environment\UserModel($userId);
		// use user name may not a good idea, Linux box is case sensitve,
		// so all user name will save in lowercase
		$strName = $userModel->getUserName();
		$strName = mb_strtolower($strName, mb_detect_encoding($strName));
		$command = new \commands\GetSettingUserTasksSettingCommand ($this->_projectPath,$strName);
		$result = $command->execute();
		return $result;
	}


	function getUserSettings($userId) {
		$resultTask=$this->getUserTasksSetting($userId);
		$resultFields=$this->getUserFieldsSetting($userId);
		$result =  array(
	"tasks" => $resultTask["tasks"],
	"fields" => $resultFields["fields"]
		);
		return $result;
	}


	function updateSettingTasks($userIds, $tasks) {
		// don't use rawurldecode here, because it does not decode "+" -> " "
		$tasks = urldecode($tasks);
		$userIds = urldecode($userIds);
		$userNames = array();
		if ( !(stristr( $userIds, '|') === FALSE)) {
			$userIdArray = explode('|', $userIds);

			//apply too all
			foreach ($userIdArray as &$userId) {
				if (is_numeric ($userId)) {
					$userNames[] = $this->getUserNameById($userId);
				}
			}
		}
		else {
			//apply to special user
			$userNames[]  = $this->getUserNameById($userIds);
		}
		$command = new \commands\UpdateSettingUserTasksSettingCommand($this->_projectPath,$userNames,$tasks);
		$result = $command->execute();
		return $result;
	}

	function updateSettingFields($userIds, $fields) {
		// don't use rawurldecode here, because it does not decode "+" -> " "
		$fields = urldecode($fields);
		$userIds = urldecode($userIds);
		$userNames = array();
		if ( !(stristr( $userIds, '|') === FALSE)) {
			$userIdArray = explode('|', $userIds);

			//apply too all
			foreach ($userIdArray as &$userId) {
				if (is_numeric ($userId)) {
					$userNames[] = $this->getUserNameById($userId);
				}
			}
		}
		else {
			// apply to special user
			$userNames[]  = $this->getUserNameById($userIds);
		}
		$command = new \commands\UpdateSettingUserFieldsSettingCommand($this->_projectPath,$userNames,$fields);
		$result = $command->execute();
		return $result;
	}

	/**
	 * get the exemplarCharacters index of language of current project.
	 */
	function getTitleLetterList()
	{
		if ($this->_projectNodeId === null || $this->_projectNodeId <= 0) {
			throw new \Exception("Invalid project node ID $projectNodeId");
		}

		// get project language : FieldSettings.fromWindow().value("Word").getAbbreviations().get(0);
		
		//looking for ldml which has <exemplarCharacters type="index">
		//example: 'zh_Hans_CN' -NO-> 'zh_Hans' -NO-> 'zh' ->FOUND!
		//TODO ZX 2013-4, how to get Dictionary language.
		$languageCode = "th-TH";
		$fileName = preg_replace('/-+/', '_', $languageCode);
		while(true)
		{
			$fileFullPath = LF_BASE_PATH . "lfbase/data/ldml-core-common-main/" . $fileName.".xml";
			if (file_exists($fileFullPath))
			{
				$xml_str = file_get_contents($fileFullPath);
				$doc = new \DOMDocument;
				$doc->preserveWhiteSpace = FALSE;
				$doc->loadXML($xml_str);
				$xpath = new \DOMXPath($doc);
				$entries = $xpath->query('//ldml/characters/exemplarCharacters[@type="index"]');
				if ($entries->length==1)
				{
					$exemplarValues =  $entries->item(0)->nodeValue;
					$exemplarValues = str_replace('[', '', $exemplarValues);
					$exemplarValues = str_replace(']', '', $exemplarValues);
					$exemplars=explode(" ", $exemplarValues);
					$exemplarsArray = array();
					foreach ($exemplars as $value) {
						$exemplarsArray[]=$value;
					}
					return  array("tl" => $exemplarsArray);
				}
			}
			if (strpos($fileName, '_') !== FALSE)
			{
				//remove some sub-categroy info from file name
				$fileNameParts = explode("_", $fileName);
				$fileName = "";
				array_pop($fileNameParts); // remove last part
				foreach ($fileNameParts as $value) {
					if (strlen($fileName)>1)
					{
						$fileName =  $fileName . '_';
					}
					$fileName =  $fileName . $value;
				}
			}
			else
			{
				// no more.
				return  array("tl" => array());
			}
		}

		return  array("tl" => array());
	}

    /**
	 * get words
	 */
	function getWordsByTitleLetter($letter)
	{
		$this->isReadyOrThrow();
		$store = $this->getLexStore();
		
		//TODO ZX 2013-4, how to get Dictionary language.
		$result = $store->searchEntriesAsWordList("th",trim($letter), null, null);
		return $result->encode();
	}
	

	/**
	 * @param string $type 'LanguageDepot'
	 * @param string $soruceURI
	 * @param string $sourceCredentials
	 * @return ProjectStateDTO
	 */
	function import($type, $soruceURI, $user, $password) {
		// For now we're assuming type is LanguageDepot

		$currentState = $this->_lexProject->projectState->getState();
		switch ($currentState) {
			case \environment\ProjectStates::Error:
			case '':
				// Have another go at importing
				break;
			default:
				return $this->state();
		}

		$this->_lexProject->projectState->setState(\environment\ProjectStates::Importing, "Importing from LanguageDepot");

		$importer = new \environment\LanguageDepotImporter($this->_projectNodeId);
		$importer->cloneRepository($user, $password, $soruceURI);
		$importer->importContinue($this->_lexProject->projectState);
		return $this->state();
	}


	/**
	 * @return ProjectStateDTO
	 */
	function state() {
		$currentState = $this->_lexProject->projectState->getState();
		$progress = 0;
		switch ($currentState) {
			case \environment\ProjectStates::Importing:
				$importer = new \environment\LanguageDepotImporter($this->_projectNodeId);
				$importer->importContinue($this->_lexProject->projectState);
				$progress = $importer->progress();
				break;
		}
		$state = $this->_lexProject->projectState->getState();
		$message = $this->_lexProject->projectState->getMessage();
		$dto = new \dto\ProjectStateDTO($state, $message);
		$dto->Progress = $progress;
		return $dto->encode();
	}

	/**
	 * @var LexStore
	 */
	private $_lexStore;
	private function getLexStore() {
		if (!isset($this->_lexStore)) {
			$this->_lexStore = new \store\LexStoreController(\store\LexStoreType::STORE_MONGO, $this->_lexProject->projectName, $this->_lexProject);
		}
		return $this->_lexStore;
	}

}


//Main Function
function main() {

	\lfbase\common\LFDrupal::loadDrupal();
	//error handler must register after drupal loaded!, otherwise will be replace by drupal's handler
	\lfbase\common\ErrorHandler::register();
	global $user;
	$userId = isset($user) ? $user->uid : null;
	$projectId = isset($_SESSION['projectid']) ? $_SESSION['projectid'] : null;

	if ($userId == null && isset($_GET['u'])) {
		$userId = $_GET['u'];
	}
	if ($projectId == null && isset($_GET['p'])) {
		$projectId = $_GET['p'];
	}

	$api = new LexAPI($projectId, $userId);
	\lfbase\common\jsonRPCServer::handle($api);
}

if (!defined('TestMode')) {
	main();
}

?>
