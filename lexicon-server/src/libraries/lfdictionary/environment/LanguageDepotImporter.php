<?php
namespace libraries\lfdictionary\environment;
require_once(dirname(__FILE__) . '/../Config.php');

use libraries\lfdictionary\common\AsyncRunner;
use libraries\lfdictionary\common\HgWrapper;
use libraries\lfdictionary\environment\LexProject;
use models\ProjectModel;

class LanguageDepotImporter {
	
	/**
	 * @var LexProject
	 */
	private $_lexProject;
		
	/**
	 * @param String $projectCode
	 * @param String $projectAdminUserId
	 * @param LexProject $lexProject
	 */
	public function __construct($projectCode, $projectAdminUserId, $lexProject = null) {
		$this->_lexProject = ($lexProject) ? $lexProject : self::createEnvironment($projectCode);
	}
	
	/*TODO
	 * 1. always pass projectCode and userid back to here
	 * 2. clone first.
	 * 3. tracking progress (by userID and projectCode)
	 * 4. when clone done create project entry in the DB
	 */
	private static function createEnvironment($projectCode) {
		$projectModel = new ProjectModel();
		$projectModel->projectname = $projectCode;
		$projectModel->projectCode = $projectCode;
		$projectModel->write();
		
		//TODO :I think will need do create new project in db at last step.
		$result = new LexProject($projectModel);
		//$result -> createNewProject();
		return $result;
	}
	
	/**
	 * Starts a clone using an AsyncRunner
	 * @param string $user Username credential
	 * @param string $password Password credential
	 * @param string $projectId Project ID on LanguageDepot
	 * @return AsyncRunner
	 */
	public function cloneRepository($user, $password, $projectCode) {
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
		$url = "http://$user:$password@hg-public.languagedepot.org/$projectCode";
		error_log("URL: " .  $url);
		error_log("DestinationPath: " .  $this->_lexProject->defaultWorkFolderPath(). $this->_lexProject->projectModel->projectname);
		$hg = new HgWrapper($this->_lexProject->defaultWorkFolderPath(). $this->_lexProject->projectModel->projectname);
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
			throw new \Exception("Process '" . $this->_lexProject->stateFolderPath() . $this->_lexProject->projectModel->projectname . "' not running");
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
		$errors = HgWrapper::errorMessageFilter($output);
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
		return new AsyncRunner($this->_lexProject->stateFolderPath() . $this->_lexProject->projectModel->projectname);
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
					$projectState->setState(\environment\ProjectStates::Error, $error);
					break;
				} else {
					$projectState->setState(\environment\ProjectStates::Ready);
					break;
				}
			}
			usleep($increment * 1000);
		}
	}
}

?>