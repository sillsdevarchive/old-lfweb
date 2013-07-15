<?php
namespace libraries\lfdictionary\environment;

require_once(dirname(__FILE__) . '/../Config.php');
use libraries\lfdictionary\environment\ProjectAccess;
use libraries\lfdictionary\environment\ProjectRole;

/**
 * This class models a Project.
 *
 * Among other things it defines the Access Privileges for Logged in User for the Project.
 * Project Informations are retrieved based on the Project ID LanguageForge Dictionary API
 */
class LFProjectModel {
	/**
	 * @var int
	 */
	protected $_projectId;

	/**
	 * Project name used for this Project, e.g. th-thai_food-dictionary
	 *
	 * @var string
	 */
	protected $_projectName;

	/**
	 * Title for this project, e.g. "Thai food"
	 *
	 * @var string
	 */
	protected $_title;

	/**
	 * @var ProjectType
	 */
	protected $_type;

	/**
	 * @var string
	 */
	protected $_languageCode;

	/**
	* @var boolean
	*/
	protected $_reading;
	
	/**
	 * @param int $projectNodeId The application host numeric project id
	 */
	function __construct($projectNodeId) {
		if ($projectNodeId === null || $projectNodeId == "") {
			throw new \Exception("Invalid project node ID $projectNodeId");
		}
		$this->_projectId = $projectNodeId;
	}

	/**
	 * @return int
	 */
	public function getId() {
		return $this->_projectId;
	}

	/**
	 * Gets the project name
	 *
	 * Name format is <comm_code>-<project_title>-<project_type>
	 * e.g. "th-thai_food-dictionary"
	 *
	 * @return string
	 */
	function getName() {
		if ($this->_projectName) {
			return $this->_projectName;
		}
		$this->read();
		return $this->_projectName;
	}

	/**
	 * Gets the project title, e.g. "Telugu food"
	 */
	function getTitle() {
		if ($this->_title) {
			return $this->_title;
		}
		$this->read();
		return $this->_title;
	}

	/**
	 * List the projects
	 *
	 * @return string
	 */
	// Reviewed. I think this can stay as a static method CP 2012-11
	// 	function listProjects($from, $to) {

	// 		$result = db_query_range("SELECT node.nid as projectId, node.title AS project_name, term_data.name AS project_type, og.og_description AS language_code FROM node node LEFT JOIN term_node term_node ON node.vid = term_node.vid LEFT JOIN term_data term_data ON term_node.tid = term_data.tid LEFT JOIN og_ancestry og_ancestry ON node.nid = og_ancestry.nid LEFT JOIN og og ON og_ancestry.group_nid = og.nid  WHERE node.status = 1 AND node.type = 'project'", $from, $to);

	// 		while ($project = db_fetch_object($result)) {
	// 			$projectdto = new \libraries\lfdictionary\dto\ProjectDTO(); //ProjectDTO class
	// 			$projectdto->setProjectId($project->projectId);
	// 			$projectdto->setProjectName($project->project_name);
	// 			$projectdto->setProjectType($project->project_type);
	// 			$projectdto->setProjectLanguageCode($project->language_code);
	// 			$this->_projectListdto->addListProject($projectdto);
	// 		}

	// 		return $this->_projectListdto;
	// 	}

	/**
	 * Get project type
	 * @return ProjectType
	 */
	function getType(){
		if ($this->_type) {
			return $this->_type;
		}
		$this->read();
		return $this->_type;
	}

	/**
	 * Get project language code, e.g. "fr"
	 * @return string
	 */
	function getLanguageCode(){
		if ($this->_languageCode) {
			return $this->_languageCode;
		}
		$this->read();
		return $this->_languageCode;
	}

	/**
	 * Update project name
	 * @return bool value
	 */
	function setTitle($title) {
		if ($this->_title != $title) {
			$this->_title = $title;
			$this->write();
		}
		return true; // As opposed to an exception being thrown somewhere.
	}

	/**
	 * @return UserListDTO
	 * Lists all (paged?) users in Project
	 */
	public function listUsersInProjectWithRole() {
		$userlistdto = EnvironmentMapper::connect()->listUsersInProject($this->_projectId);
		$userlistdto = $this->attachProjectRoleToUserListDto($userlistdto);
		return $userlistdto;
	}


	/**
	 * @return boolean value
	 * This method is for to find whether the User is member of this Project
	 */
	public function isUserInProject($userId) {
		$result = db_query("SELECT u.uid FROM {lf_access} opu INNER JOIN {users} u ON opu.uid = u.uid INNER JOIN {users_roles} ur ON u.uid = ur.uid WHERE u.status = 1 AND opu.nid = $this->_projectId AND u.uid = $userId");
		return ($result != FALSE)? true : false;
	}
	
	/**
	* @return boolean value
	* This method is for to find whether the User is member of this Project
	*/
	public function isUserInProjectByName($userName) {
		$result = EnvironmentMapper::connect()->getUserByName($userName);
		return ($result != FALSE)? true : false;
	}


	/**
	* @return UserListDTO
	* This method is use for get all admin role users of a project.
	*/
	public function getProjectAdmins() {
		$userlistdto = new \libraries\lfdictionary\dto\UserListDTO();
		if ($this->_projectId) {
			$hostRole = ProjectRole::mapRoleToHost(ProjectRole::ADMIN);
			$userlistdto = EnvironmentMapper::connect()->getUsersInProjectByRole($this->_projectId, $hostRole);
			$userlistdto = $this->attachProjectRoleToUserListDto($userlistdto);
		}
		return $userlistdto;
	}


	/**
	 * use for user list attach user role name into UserDto
	 * @param UserListDTO $userlistdto
	 */
	private function attachProjectRoleToUserListDto($userlistdto)
	{
		foreach ($userlistdto->getUsers() as $value) {
			$projectAccess = new ProjectAccess($this->_projectId, $value->getUserId());
			$value->setUserRole($projectAccess->getRole());
		}
		return $userlistdto;
	}



	/**
	 * Used by a mapper to set properties
	 * @param string $title
	 * @param string $language
	 * @param string $name
	 * @param string $type
	 */
	public function set($title, $language, $name, $type) {
		$this->_title = $title;
		$this->_languageCode = $language;
		$this->_projectName = $name;
		$this->_type = $type;
	}

	private function read() {
		$this->_reading = true;
		EnvironmentMapper::connect()->readProject($this);
		$this->_reading = false;
	}

	private function write() {
		if (!$this->_reading) {
			EnvironmentMapper::connect()->writeProject($this);
		}
	}
	
	/**
	 * AddProject static function for creating a new project
	 * @param string $title
	 * @param string $name
	 * @param string $type
	 * @param string $language
	 * @return LFProjectModel
	 * @throws \Exception
	 */
	static function addProject($title, $name, $type, $language) {
		if(EnvironmentMapper::connect()->projectExists($name)) {
			return null;
		}
		if ($name == "") {
			throw \Exception("'name' parameter of addProject cannot be empty");
		}
		
		$id = EnvironmentMapper::connect()->createNewProject($name);
		$proj = new LFProjectModel($id);
		$proj->set($title, $language, $name, $type);
		$proj->write();
		return $proj;
	}
	
	/**
	 * Gets a project by the unique name of the project (also known as the project SLUG or identifier)
	 * 
	 * @param string $name
	 * @return LFProjectModel | null if the project name does not exist
	 */
	static function getProjectByName($name) {
		return EnvironmentMapper::connect()->getProjectByName($name);
	}
	
	/**
	 * 
	 * @param int $id
	 * @return booll
	 */
	// TODO move this to a different class (maybe ProjectAccess)
	static function roleExists($id) {
		return EnvironmentMapper::connect()->roleExists($id);
	}
	
	
	/**
	 * 
	 * @param string $userName
	 * @param int $roleId
	 * @return boolean
	 */
	public function addMember($userName, $roleId) {
		if(!EnvironmentMapper::connect()->userExists($userName)) {
			return false;
		}
		$user = UserModel::getUserByName($userName);
		EnvironmentMapper::connect()->addUserToProject($this->_projectId, $user->id(), $roleId);
		return true;
		
	}
}

?>