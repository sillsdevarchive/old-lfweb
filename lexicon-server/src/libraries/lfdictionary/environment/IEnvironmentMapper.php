<?php
namespace libraries\lfdictionary\environment;

interface IEnvironmentMapper {
	
	/**
	 * 
	 * @param LFProjectAccess $projectAccess
	 */
	public function readLFProjectAccess($projectAccess);
	
	/**
	 * 
	 * @param LFProjectAccess $projectAccess
	 */
	public function writeLFProjectAccess($projectAccess);
	
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
	*
	* @param int $projectId
	*/
	public function listUsersInProject($projectId);
	

}

?>
