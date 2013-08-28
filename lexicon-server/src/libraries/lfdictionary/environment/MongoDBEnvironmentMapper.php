<?php
namespace libraries\lfdictionary\environment;
use models\ProjectAccessModel;
use models\ProjectModel;
use models\UserModel;
use models\ProjectList_UserModel;
use libraries\lfdictionary\dto\UserDTO;

use libraries\lfdictionary\dto\UserListDTO;
use libraries\lfdictionary\common\DataConnection;
use libraries\lfdictionary\common\DataConnector;
use libraries\lfdictionary\environment\ProjectRole;
use libraries\lfdictionary\common\LoggerFactory;
require_once(APPPATH . '/models/ProjectModel.php');
require_once(APPPATH . '/models/UserModel.php');
class MongoDBEnvironmentMapper  extends \models\mapper\MongoMapper implements IEnvironmentMapper {

	public function __construct() {
		LoggerFactory::getLogger()->logInfoMessage("Uses MongoDBEnvironmentMapper");
	}

	/**
	 * @param LFProjectAccess $projectAccess
	 * @see libraries\lfdictionary\environment.IEnvironment::readLFProjectAccess()
	 */
	public function readLFProjectAccess($projectAccess) {
		$projectAccessModel = new ProjectAccessModel("");
		$projectAccessModel->findOneByProjectIdAndUserID($projectAccess->projectId, $projectAccess->userId);
		
		if ($projectAccessModel->id!=null) {
			$projectAccess->setRole(ProjectRole::mapRoleFromHost($projectAccessModel->lf_role));
		} else {
			MongoDBEnvironmentFixer::fixLFProjectAccess($this, $projectAccess);
		}
	}
	
	/**
	 * @param LFProjectAccess $projectAccess
	 * @see libraries\lfdictionary\environment.IEnvironment::writeLFProjectAccess()
	 */
	public function writeLFProjectAccess($projectAccess) {
		$hostRole = ProjectRole::mapRoleToHost($projectAccess->getRole());
		
		$projectAccessModel =  new ProjectAccessModel();
		$projectAccessModel->findOneByProjectIdAndUserID($projectAccess->projectId, $projectAccess->userId);
		$projectAccessModel->is_active = 1;
		$projectAccessModel->lf_role = $hostRole;
		$projectAccessModel->project_id = $projectAccess->projectId;
		$projectAccessModel->user_id = $projectAccess->userId;
		$projectAccessModel->write();
	}
	
	/**
	 *
	 * @param LFProjectModel $project
	 */
	public function readProject($project) {
		
		$projectModel = new ProjectModel($project->getId());
		//$projectModel->read($project->getId());
		
 		$typeTokens = explode("-", $projectModel->projectname);
 		$type = $typeTokens[count($typeTokens) - 1];
 		// set(title, language, name (slug), type);
 		$project->set($projectModel->title, $projectModel->language, $projectModel->projectname, $type);
	}
	
	/**
	 *
	 * @param LFProjectModel $project
	 */
	public function writeProject($project) {
		$projectModel = new ProjectModel($project->getId());
		$projectModel->title=$project->getTitle();
		$projectModel->language =$project->getLanguageCode();
		$projectModel->projectname=$project->getName();
		$projectModel->write();
	}
	
	/**
	 * @param UserModel $user
	 */
	public function readUser($user) {
		$userModel = new UserModel($user->id());
		$user->set($userModel->name);
		$user->setUserEmail($userModel->email);
	}
	
	/**
	* @param int $projectId
	*/
	public function listUsersInProject($projectId)
	{
		$userlistdto = new  UserListDTO();
		if ($projectId) {
// 			$projectModel = new ProjectModel($projectId);
// 			$projectModel->listUsers()->read();
// 			foreach ($projectModel->listUsers()->entries() as $user) {
// 				$userModel= new LFUserModel($user->id);
// 				$userdto = new UserDTO($userModel);
// 				$userlistdto->addListUser($userdto);
// 			}
			
			//TODO XZ listUsers always return 0, an I can not add new user into Project 
			$userModel= new UserModel("51e604b1d4a66e7d19358eca");
			$userdto = new UserDTO($userModel);
			$userlistdto->addListUser($userdto);
			//Test code end
		}
		return $userlistdto;
	}
	
	

}

?>