<?php

namespace models\commands;

use models\ProjectModel;
use models\UserModel;
use models\rights\Roles;


use libraries\palaso\CodeGuard;
use models\mapper\JsonEncoder;
use models\mapper\JsonDecoder;

class ProjectCommands
{
	/**
	 * Create/Update a Project for RPC call
	 * @param json array $jsonModel
	 * @return string Id of written object
	 */
	public static function createProject($jsonModel, $userId) {
		$project = new \models\ProjectModel();
		$id = $jsonModel['id'];
		$isNewProject = ($id == '');
		if (!$isNewProject) {
			$project->read($id);
		}
		JsonDecoder::decode($project, $jsonModel);
		return createOrUpdateProject($project, $userId, $isNewProject);
	}
	
	/**
	 * Create/Update a Project for internal
	 * @param ProjectModel $project
	 * @return string Id of written object
	 */
	public static function createOrUpdateProject($project, $userId, $isNewProject) {
		CodeGuard::checkTypeAndThrow($project, 'models\ProjectModel');
		if ($isNewProject) {
			$user = new \models\UserModel($userId);
			$user->read($userId);
				
			$project->addUser($userId, Roles::PROJECT_ADMIN);
			$project->projectCode = ProjectModel::makeProjectCode($project->languageCode, $project->projectname, ProjectModel::PROJECT_LIFT);
		}
	
		$newProjectId = $project->write();
		error_log("New Project ID: " . $newProjectId);
		$user->addProject($newProjectId);
		$user->write();
	
		if ($isNewProject) {
			//ActivityCommands::addProject($project); // TODO: Determine if any other params are needed. RM 2013-08
		}
		return $newProjectId;
	}
	
	/**
	 * @param array $projectIds
	 * @return int Total number of projects removed.
	 */
	public static function deleteProjects($projectIds) {
		CodeGuard::checkTypeAndThrow($projectIds, 'array');
		$count = 0;
		foreach ($projectIds as $projectId) {
			CodeGuard::checkTypeAndThrow($projectId, 'string');
			$project = new \models\ProjectModel($projectId);
			$project->remove();
			$count++;
		}
		return $count;
	}

	public static function renameProject($projectId, $oldName, $newName) {
		// TODO: Write this. (Move renaming logic over from sf->project_update). RM 2013-08
	}
	
}

?>
