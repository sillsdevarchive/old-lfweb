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
	
}

?>