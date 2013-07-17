<?php
namespace libraries\lfdictionary\environment;

require_once(dirname(__FILE__) . '/../Config.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

class LanguageDepotImportEnvironment {	
	public $WorkRootPath;
	public $StateRootPath;
	public $ProjectPathName;
}

class LanguageDepotProjectDatabase {
	
	/**
	 * Language Depot project Id
	 * @var string
	 */
	private $_projectId;
	

	/**
	 * The handle to the LanguageDepot database connection. May be null if no connection is possible.
	 * @var Handle
	 */
	private $_dbLanguageDepot;
	
	public function __construct($projectId) {
		$this->_projectId = $projectId;
		// TODO We may not be on the same server, so try, fail quietly and ensure other functions do not assume connection exists. CP 2012-08
		$this->_dbLanguageDepot = new DataConnection(DB_SERVER, DB_USER, DB_PASS, LANG_DEPOT_DB_NAME);
		$this->_dbLanguageDepot->open();		
	}
	
	function __destruct() {
		$this->_dbLanguageDepot->close();
	}
	
	// TODO This needs work CP 2012-08
	public function makeReady($nid, $source, $destination) {
		$sql = "SELECT u.login, u.hashed_password, u.mail FROM projects p INNER JOIN members m ON p.id = m.project_id INNER JOIN users u ON u.id = m.user_id WHERE u.status = 1 AND p.identifier = '$this->_projectCode'";
		$query = $this->_dbLanguageDepot->execute($sql);
		$userId = 1; //Admin user Id for reference/dummy Id
		$UserModel = new UserModel($userId);
		while($result = mysql_fetch_object($query)) {
	
		if(!$UserModel->isUserMailId($result->mail)) {
		$newUser = array('name' => $result->login, 'pass' => $result->hashed_password, 'mail' => $result->mail);
		$userResult = $UserModel->addUser($newUser);
		$userName = "$result->login, $result->mail";
		$projResult = $UserModel->addUserToProject($nid, $userName);
		}
		}
	
		$LFProjectModel = new LFProjectModel($nid);
	
		$hgWrapper = new HgWrapper($destination);
		$hgWrapper->cloneRepository($source);
	}
	
	public function IsProjectManager($userMail) {
		$sql = "SELECT * FROM projects p INNER JOIN members m ON p.id = m.project_id INNER JOIN users u ON u.id = m.user_id WHERE u.status = 1 AND p.identifier = '$this->_projectCode' AND u.mail = '$userMail' AND m.role_id = 3";
		$query = $this->_dbLanguageDepot->execute($sql);
		$result = $this->_dbLanguageDepot->numrows($query) > 0;
		return $result;
	}

}

class LanguageDepotImporter {
	
	/**
	 * @var LanguageDepotImportEnvironment
	 */
	private $_environment;
		
	/**
	 * @param int $projectNodeId
	 * @param LanguageDepotImportEnvironment $environment
	 */
	public function __construct($projectNodeId, $environment = null) {
		$this->_environment = ($environment) ? $environment : self::createEnvironment($projectNodeId);
	}
	
	private static function createEnvironment($projectNodeId) {
		$result = new LanguageDepotImportEnvironment();
		$result->WorkRootPath = LexiconProjectEnvironment::languageforgeWorkRootPath();
		$result->StateRootPath = LexiconProjectEnvironment::languageforgeStateRootPath();
		$LFProjectModel = new \libraries\lfdictionary\environment\LFProjectModel($projectNodeId);
		$result->ProjectPathName = $LFProjectModel->getName();
		return $result;
	}
	
	private function destinationPath() {
		return $this->_environment->WorkRootPath . $this->_environment->ProjectPathName;
	}
	
	private function stateFilePath() {
		return $this->_environment->StateRootPath . $this->_environment->ProjectPathName;
	}
	
	/**
	 * Starts a clone using an AsyncRunner
	 * @param string $user Username credential
	 * @param string $password Password credential
	 * @param string $projectId Project ID on LanguageDepot
	 * @return AsyncRunner
	 */
	public function cloneRepository($user, $password, $projectId) {
		// TODO Add support for private repo? CP 2012-08
		$asyncRunner = $this->createAsyncRunner();
		if ($asyncRunner->isRunning()) {
			// The lock file exists, so we may be still running, or complete.
			if ($asyncRunner->isComplete()) {
				$asyncRunner->cleanUp();
			} else {
				return $asyncRunner;
			}
		}
		$url = "http://$user:$password@hg-public.languagedepot.org/$projectId";
		$hg = new \libraries\lfdictionary\common\HgWrapper($this->destinationPath());
		$hg->cloneRepository($url, $asyncRunner);
		return $asyncRunner;
	}
	
	/**
	 * Returns a progress percentage from the async file.
	 * @return int
	 * @throws \Exception
	 */
	public function progress() {
		// Analyze the output of the async file and return an appropriate progress indicator.
		$asyncRunner = $this->createAsyncRunner();
		if (!$asyncRunner->isRunning()) {
			throw new \Exception("Process '" . $this->stateFilePath() . "' not running");
		}
		if ($asyncRunner->isComplete()) {
			return 100;
		}
		$output = explode("\n", $asyncRunner->getOutput(true));
		$result = 0.0;
		foreach($output as $line) {
			$match = '';
			preg_match("/files.*\(([^%]+)%\)/", $line, $match);
			if (count($match) > 0) {
				$result = $match[1];
			}
		}
		return $result;
	}

	/**
	 * Returns an array of error strings.
	 * An empty array is returned if there are no errors.
	 * Will throw if not complete.
	 * @return string[] 
	 */
	public function error() {
		$asyncRunner = $this->createAsyncRunner();
		$output = $asyncRunner->getOutput();
		$errors = \libraries\lfdictionary\common\HgWrapper::errorMessageFilter($output);
		return $errors;
	}
	
	/**
	 * @return bool
	 */
	public function isComplete() {
		$asyncRunner = $this->createAsyncRunner();
		return $asyncRunner->isComplete();		
	}
	
	private function createAsyncRunner() {
		return new \libraries\lfdictionary\common\AsyncRunner($this->stateFilePath());
	}
	
	/**
	* @param LanguageDepotImporter $importer
	*/
	public function importContinue($projectState) {
		// Wait for a few seconds to give early failure notice.
		$increment = 250; // milliseconds
		$time = 1500;
		while ($time > 0) {
			$time -= $increment;
			if ($this->isComplete()) {
				$error = $this->error();
				if ($error) {
					$projectState->setState(\libraries\lfdictionary\environment\ProjectStates::Error, $error);
					break;
				} else {
					$projectState->setState(\libraries\lfdictionary\environment\ProjectStates::Ready);
					break;
				}
			}
			usleep($increment * 1000);
		}
	}
}

?>