<?php
namespace libraries\lfdictionary\LanguageDepot;

use libraries\lfdictionary\environment\EnvironmentMapper;
use libraries\lfdictionary\common\DataConnector;
use libraries\lfdictionary\environment\LFProjectModel;
use libraries\lfdictionary\environment\UserModel;
use libraries\lfdictionary\dto\LDAddProjectWithPasswordDTO;

error_reporting(E_ALL | E_STRICT);
ob_start("ob_gzhandler");
require_once(dirname(__FILE__) . '/Config.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

\libraries\lfdictionary\common\DataConnector::connect('languagedepot', 'redmine_default', 'redmine', 'redmine');
\libraries\lfdictionary\environment\EnvironmentMapper::connect('languagedepot');


/**
 */
class LanguageDepotAPI {

	function __construct() {
	}
	
	/**
	 * Add a new project with a project password (auto-creates an user)
	 * 
	 * Add New Project is a custom method which allows a client (namely FLEx) to create a new project, without having to create a new user
	 * first.  In addition, a project password is given allowing a client UI to not require the user to remember a username.
	 * The project password is intended to be shared among all members of the project team.
	 * 
	 * @param string $projectName
	 * @param string $email The project owner's email address
	 * @param string $password A project password
	 * @param string $type Type must one of either 'dictionary' or 'flex'
	 * @param string $language The ISO 639 3-letter language code of the project
	 * @throws Exception
	 * @return LDAddProjectWithPasswordDTO The DTO has a return code, a message, and the SLUG of the newly created project
	 * 			200 = success , 401 if the projectId already exists , 402 validation error , 500 server error
	 * 
	 */
	function addProjectWithPassword($projectName, $email, $password, $type, $language = 'qaa') {
		
		// validation checks
		if ($projectName == "") {
			$result = new LDAddProjectWithPasswordDTO("402");
			$result->message("The \$projectName parameter cannot be blank");
			return $result;
		}
		else if (strpos($email, '@') === FALSE) {
			$result = new LDAddProjectWithPasswordDTO("402");
			$result->message("The \$email parameter must be a valid email address");
			return $result;
		}
		else if (strlen($password) < 5) {
			$result = new LDAddProjectWithPasswordDTO("402");
			$result->message("The \$password parameter must be at least 5 characters");
			return $result;
		}
		else if ($type != 'flex' && $type != 'dictionary') {
			$result = new LDAddProjectWithPasswordDTO("402");
			$result->message("The \$type parameter must be either 'dictionary' or 'flex'");
			return $result;
		}
		else if (strlen($language) > 3 || strlen($language) < 2) {
			$result = new LDAddProjectWithPasswordDTO("402");
			$result->message("The \$language parameter must be 2 or 3 characters");
			return $result;
			
		}
		
		// make the SLUG by concatenating the ISO code and the project type		
		$id = $language . "-" . $type;
		
		// create project
		$newProjectResult = $this->addProject($projectName, $id, $type, $language);
		if ($newProjectResult == "401") {
			$result = new LDAddProjectWithPasswordDTO("401");
			$result->message("Cannot create project: the projectID $id already exists");
			return $result;
		}
		if ($newProjectResult == "500") {
			$result = new LDAddProjectWithPasswordDTO("500");
			$result->message("Server error creating new project with id '$id'");
			return $result;
		}
		
		// create user
		$newUserResult = $this->addUser($id, $id, $id, $password, $email);
		if ($newUserResult == "401") {
			$result = new LDAddProjectWithPasswordDTO("401");
			$result->message("Cannot create user: the user $id already exists");
			return $result;
		}
		if ($newUserResult == "500") {
			$result = new LDAddProjectWithPasswordDTO("500");
			$result->message("Server error creating new user with id '$id'");
			return $result;
		}
		
		// add user to project
		$roleid = "3";  // manager
		$userToProjectResult = $this->addUserToProject($id, $id, $roleid);
		if ($userToProjectResult == "402") {
			$result = new LDAddProjectWithPasswordDTO("402");
			$result->message("Cannot add user $id to project $id because role '$roleid' is invalid");
			return $result;
		}
		if ($userToProjectResult == "500") {
			$result = new LDAddProjectWithPasswordDTO("500");
			$result->message("Server error connecting user $id to project $id with role $id");
			return $result;
		}
		
		$result = new LDAddProjectWithPasswordDTO("200");
		$result->projectIdentifier($id);
		
		return $result;
	}
	
	/**
	 * Add a new LanguageDepot project
	 * 
	 * addProject adds a new languageDepot project (no user auth required)
	 * 
	 * @param string $projectName (max 30 characters)
	 * @param string $id The project ID or SLUG
	 * @param string $type One of either 'dictionary' or 'flex'
	 * @param string $language The ISO639 language code
	 * @param bool $isPublic Whether or not the project is marked public in Redmine
	 * @return $statusCode 200 on success, 401 if the projectID already exists, and 500 on server error
	 */
	function addProject($projectName, $id, $type, $language) {
		// TODO: validation checks
		try {
			$project = LFProjectModel::addProject($projectName, $id, $type, $language);
			if (!$project) {
				return "401"; // project already exists!
			}
			return "200";
		} catch (\Exception $e) {
			return "500";
		}
	}

	/**
	 * Add New User
	 * 
	 * @param string userName
	 * @param string firstName
	 * @param string lastName
	 * @param string password
	 * @param string email
	 * @return $statusCode 200 on success, 401 if the userName already exists, 500 on server error
	 */
	function addUser($userName, $firstName, $lastName, $password, $email) {
		try {
			$user = UserModel::addUser($userName, $firstName, $lastName, $password, $email);
			if (!$user) {
				return "401"; // user already exists!
			}
			return "200";
		} catch (\Exception $e) {
			return "500";
		}
	}
	
	/**
	 * Add User to Project
	 * 
	 * Add an existing user to an existing project with a specified role
	 * 
	 * @param string projectId (the redmine project SLUG)
	 * @param string userName
	 * @param string roleId (as specified in the redmine roles table.  e.g. 'contributer' is roleId = 4)
	 * @return $statusCode 200 on success, 402 if the role is invalid or the username or projectid don't exist, 500 on server error
	 */
	function addUserToProject($projectId, $userName, $roleId) {
		try {
			if (!\lfbase\environment\LFProjectModel::roleExists($roleId)) {
				return "402"; // invalid role ID
			}
			$LFProjectModel = \lfbase\environment\LFProjectModel::getProjectByName($projectId);
			if (!$LFProjectModel) {
				return "402"; // project doesn't exist
			}
			$result = $LFProjectModel->addMember($userName, $roleId);
			if (!$result) {
				return "402"; // invalid username
			}
			return "200";
		} catch (\Exception $e) {
			return "500";
		}
	}
}


// Main Function
function main() {

	\lfbase\common\ErrorHandler::register();

	$api = new LanguageDepotAPI();
	\lfbase\common\jsonRPCServer::handle($api);
}

if (!defined('TestMode')) {
	main();
}

?>