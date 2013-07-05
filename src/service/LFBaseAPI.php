<?php
error_reporting(E_ALL | E_STRICT);
ob_start("ob_gzhandler");
require_once(dirname(__FILE__) . '/Config.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

use lfbase\dto\UserDTO;
use lfbase\environment\ProjectAccess;
use lfbase\environment\EnvironmentMapper;
use lfbase\common\LoggerFactory;
// TODO This class is very low value. Shouldn't be needed.  All functions should be in LexAPI. CP 2012-09
// TODO This class really only represents the common API which seems to be only InputSystems related functions.
// A few InputSettings classes along the Store (Repository) pattern would work well. CP 2012-11
// Notes[ZX]: this class is shared between lexicon and LFRapidWord, not use for lexicon only.
/**
 * This class contains the main api functions that are managed by the json server
 * @author C
 */
abstract class LFBaseAPI {
	
	protected $_logger;
	
	var $_projectPath;

	/**
	* @var int
	*/
	protected $_userId;

	/**
	* @var int
	*/
	protected $_projectNodeId;
	
	/**
	* @var ProjectModel
	*/
	protected $_projectModel;

	
	/**
	* @var ProjectModel
	*/
	private $_userModel;
	
	abstract protected function initialize($projectNodeId, $userId);
	
	function __construct($projectNodeId, $userId) {
		$this->_logger = LoggerFactory::getLogger();
		$this->_logger->logInfoMessage("LFBaseAPI p:$projectNodeId u:$userId");
		$this->_userId = $userId;
		$this->_projectNodeId = $projectNodeId;
		
		$this->_projectModel = new \lfbase\environment\ProjectModel($projectNodeId);
		$this->initialize($projectNodeId, $userId);
	}

	// Reviewed This can stay here
	function getIANAData() {
		$JSONFile=LF_BASE_PATH . "lfbase/data/IANA.js";
		$result= file_get_contents($JSONFile);
		return json_decode($result);
	}

	// Reviewed This can stay here
	function getSettingInputSystems() {
		$command = new \lfbase\commands\GetSettingInputSystemsCommand($this->_projectPath);
		$result = $command->execute();
		return $result;
	}

	// Reviewed This is ok here CP
	function updateSettingInputSystems($inputSystems) {
		// don't use rawurldecode here, because it does not decode "+" -> " "
		$inputSystems = urldecode($inputSystems);
		$command = new \lfbase\commands\UpdateSettingInputSystemsCommand($this->_projectPath,$inputSystems);
		$command->execute();
		return $this->getSettingInputSystems();
	}

	// Reviewed Move to LanguageForgeAPI
	function updateProjectName($projectNodeId, $name) {
		$projectModel = new \lfbase\environment\ProjectModel($projectNodeId);
		if ($projectModel->setTitle(urldecode($name))){
			
			$getProjectDtO = new \lfbase\dto\ProjectDTO($projectModel);
			return $getProjectDtO->encode();
		}
		else {
				throw new Exception("Project name can not be updated.");
		}
	}

	protected function getUserNameById($userId) {
		$userModel = new \lfbase\environment\UserModel($userId);
		// use user name may not a good idea, Linux box is case sensitve,
		// so all user name will save in lowercase
		$strName = $userModel->getUserName();
		return mb_strtolower($strName, mb_detect_encoding($strName));
	}
	
	
	/**
	* Check User exists
	*
	* @return Boolean value
	*/
	function isUser($userName) {
		$userModel = new \lfbase\environment\UserModel($this->_userId);
		$result = $userModel->isUser($userName);
		return $result;
	}
	
	/**
	 * Add New User
	 */
	function addUser($newuser) {
		$userModel = new \lfbase\environment\UserModel($this->_userId);
		$result = $userModel->addUser($newuser);
		return $result->encode();
	}
	
	/**
	 * Search User
	 */
	function searchUser($search) {
		$userModel = new \lfbase\environment\UserModel($this->_userId);
		$result = $userModel->searchUser($search);
		return $result->encode();
	}
	
	/**
	 * Add User to Project
	 */
	function addUserToProject($projectId, $userName) {
		$userModel = new \lfbase\environment\UserModel($this->_userId);
		$result = $userModel->addUserToProject($projectId, $userName);
		return $result;
	}
	
	/**
	 * List User
	 */
	function listUsersInProject($projectId) {
		$projectModel = new \lfbase\environment\ProjectModel($projectId);
		$result = $projectModel->listUsersInProjectWithRole($projectId);
		return $result->encode();
	}
	
	/**
	 * Add new project
	 */
	function add($newProject) {
		$projectModel = new \lfbase\environment\ProjectModel();
		$result = $projectModel->add($newProject);
		if (!$result) {
			throw new Exception('Project already exists');
		}
	}
	
	/**
	 * List projects
	 */
	function listProjects($from, $to) {
		$projectModel = new \lfbase\environment\ProjectModel();
		$result = $projectModel->listProjects($from, $to);
	
		return $result->encode();
	}
	
	/**
	 * Search project
	 */
	function searchProject($string, $maxResultCount) {
		$projectModel = new \lfbase\environment\ProjectModel();
		$result = $projectModel->searchProject($string, $maxResultCount);
		return $result->encode();
	}
	
	/**
	 * Add new community
	 */
	function addCommunity($newCommunity) {
		$communityModel = new \lfbase\environment\CommunityModel($this->_userId);
		$result = $communityModel->addCommunity($newCommunity);
		if(!$result)
		throw new Exception('Community already exists');
	}
	
	/**
	 * List communities
	 */
	function listCommunities($from, $to) {
		$communityModel = new \lfbase\environment\CommunityModel($this->_userId);
		$result = $communityModel->listCommunities($from, $to);
		return $result->encode();
	}
	
	/**
	 * Search Community
	 */
	function searchCommunity($string, $maxResultCount) {
		$communityModel = new \lfbase\environment\CommunityModel($this->_userId);
		$result = $communityModel->searchCommunity($string, $maxResultCount);
		return $result->encode();
	}
	
	/**
	 * member search for Auto Suggest in Setting->Member tab
	 */
	function getMembersForAutoSuggest($search,$begin,$end) {
		$userModel = new \lfbase\environment\UserModel($this->_userId);
		$result = EnvironmentMapper::connect()->searchUser($search,$begin,$end);
		return $result->encode();
	}
	
	/**
	 * Add User to Project (this will return a new user list in JSON)
	 */
	function addUserToProjectForLex($projectId, $userId) {
		$userModel = new \lfbase\environment\UserModel($this->_userId);
		$projectModel = new \lfbase\environment\ProjectModel($projectId);
		if ($projectModel->isUserInProject($userId))
		{
			throw new lfbase\common\UserActionDeniedException("User already a member of project, it may added by other user. please refresh to see changes");
		}
		$result = EnvironmentMapper::connect()->addUserToProject($projectId, $userId);
		if ($result) {
			$result = $projectModel->listUsersInProjectWithRole($projectId);
		}
		else {
			$result= new \lfbase\dto\UserListDTO();
		}
		return $result->encode();
	}
	
	/**
	 * Add User to Project (this will return a new user list in JSON)
	 */
	function removeUserFromProjectForLex($projectId, $userId) {
		$projectId=(int)$projectId;
		$userId=(int)$userId;
		$projectModel = new \lfbase\environment\ProjectModel($projectId);
		if (!$projectModel->isUserInProject($userId))
		{
			throw new lfbase\common\UserActionDeniedException("User not a member of project, it may removed by other user. please refresh to see changes");
		}
		$userModel = new \lfbase\environment\UserModel($this->_userId);
		$result = EnvironmentMapper::connect()->removeUserFromProject($projectId, $userId);
		// always reload new list
		$result = $projectModel->listUsersInProjectWithRole();
		return $result->encode();
	}
	
	/**
	 * change a user's access role
	 */
	function updateUserRoleGrant($projectId, $userDtoString) {
		$projectId=(int)$projectId;
		$projectModel = new \lfbase\environment\ProjectModel($projectId);
		$userJson = json_decode(urldecode($userDtoString));
		$userId=$userJson->id;
		if ($projectModel->isUserInProject($userId)) {
			$userDTO = new UserDTO(new \lfbase\environment\UserModel($userId));
			$projectAccess = new ProjectAccess($projectId, $userId);
			$userDTO->setUserRole($projectAccess->getRole());
			return $userDTO->encode();
		}
		else {
			throw new lfbase\common\UserActionDeniedException("User is not a member of project.");
		}
	}
	
	/**
	 * create a new user and add it into project
	 */
	function rapidUserMemberCreation($projectId, $userName) {
		$projectModel = new \lfbase\environment\ProjectModel($projectId);
		$userListDto = $projectModel->getProjectAdmins();
		if (count($userListDto->_user)<=0) {
			throw new lfbase\common\UserActionDeniedException("Selected project doesn't have a active admin, so new user can not be created by admin.");
		}
		$users = $userListDto->getUsers(); 
		$userDto = $users[0]; // use first one if there have mutil-admins
		$userModel= new \lfbase\environment\UserModel($userDto->getUserId());
		if ($projectModel->isUserInProjectByName($userName)) {
			throw new lfbase\common\UserActionDeniedException("User name already exist!");
		}
		
		$newUserId =  EnvironmentMapper::connect()->createNewUser($userName, $userName, $userModel->getUserEmail());
		if (!$newUserId) {
			throw new lfbase\common\UserActionDeniedException("Error saving user account.");
		}
		return $this->addUserToProjectForLex($projectId,$newUserId);
	}
	
	/**
	 * invite user by send a email
	 */
	function inviteByEmail($gid, $recEmail, $pmessage) {
		
		$node = node_load($gid);
		$variables = array(
		    '@group' => $node->title,
		    '@description' => $node->og_description,
		    '@site' => variable_get('site_name', 'drupal'),
		    '!group_url' => url("og/subscribe/$node->nid", array('absolute' => TRUE)),
		    '@body' => $pmessage,
		);
	
		global $user;
		$from = $user->mail;
		$result = drupal_mail('og', 'invite_user', $recEmail, $GLOBALS['language'], $variables, $from);
	
		if (!$result['result']) {
			throw new \lfbase\common\UserActionDeniedException("Unable to send e-mail. Please contact the site administrator if the problem persists.");
		}
		else {
			return array(
							"text" => "Invite Sent."
			);
		}
	}
	
}

?>
