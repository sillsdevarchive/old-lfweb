<?php
namespace libraries\lfdictionary\environment;

interface IEnvironmentMapper {
	
	/**
	 * 
	 * @param ProjectAccess $projectAccess
	 */
	public function readProjectAccess($projectAccess);
	
	/**
	 * 
	 * @param ProjectAccess $projectAccess
	 */
	public function writeProjectAccess($projectAccess);
	
	/**
	 * 
	 * @param LFProjectModel $project
	 */
	public function readProject($project);
	
	/**
	 * 
	 * @param LFProjectModel $project
	 */
	public function writeProject($project);
	
	/**
	 * @param UserModel $user
	 */
	public function readUser($user);
	
	/**
	* @param String $name
	* @param int $indexBegin
	* @param int $indexEnd
	*/
	public function searchUser($name, $indexBegin, $indexEnd);
	
	
	/**
	* Add User to Project along with community
	* @param int $projectId
	* @param int $userId
	* @return boolean
	*/
	//public function addUserToProject($projectId, $userId);
	
	/**
	* @param String $userName
	* @return bool
	*/
	public function userExists($userName);
	
	
	/**
	* @param String $email
	* @return bool
	*/
	public function emailExists($email);
	
	
	/**
	* @param String $username
	* @return UserModel|NULL
	*/
	public function getUserByName($username);
	
	
	/**
	 * 
	 * @param string $name
	 * @return LFProjectModel|NULL
	 */
	public function getProjectByName($name);
	
	/**
	* @param String $username
	* @param string $pwd
	* @param string $email
	* @return UserModel
	*/
	public function createNewUser($username, $pwd, $email);
	
	/**
	* @param int $projectId
	* @param int $userId
	*/
	public function removeUserFromProject($projectId, $userId);
	
	/**
	 * @param string $name A unique name for the project (project SLUG)
	 * @return LFProjectModel
	 */
	public function createNewProject($name);
	
	/**
	 * @param string $name A unique name for the project (project SLUG)
	 * @return bool
	 */
	public function projectExists($name);
	
	/**
	 * 
	 * @param string $userId
	 * @param string $password
	 */
	public function setUserPassword($userId, $password);
	
	/**
	 * 
	 * @param int $projectId
	 * @param int $userId
	 * @param int $roleId
	 * @throws Exception
	 */
	public function addUserToProject($projectId, $userId, $roleId);
	
	/**
	*
	* @param int $projectId
	*/
	public function listUsersInProject($projectId);
	
	/**
	*
	* @param int $projectId
	* @param int $hostRole
	*/
	public function getUsersInProjectByRole($projectId, $hostRole);
}

?>
