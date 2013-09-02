<?php

namespace models\commands;

use models\ProjectModel;

use libraries\palaso\CodeGuard;
use models\mapper\JsonEncoder;
use models\mapper\JsonDecoder;

class ProjectCommands
{
	/**
	 * Create/Update a Project
	 * @param ProjectModel $json
	 * @return string Id of written object
	 */
	public static function createProject($jsonModel) {
		$project = new \models\ProjectModel();
		$id = $jsonModel['id'];
		$isNewProject = ($id == '');
		if (!$isNewProject) {
			$project->read($id);
		}
		JsonDecoder::decode($project, $jsonModel);

		if ($isNewProject) {
			$project->projectCode = ProjectModel::makeProjectCode($project->languageCode, $project->projectname, ProjectModel::PROJECT_LIFT);
		}

		$result = $project->write();
		if ($isNewProject) {
			//ActivityCommands::addProject($project); // TODO: Determine if any other params are needed. RM 2013-08
		}
		return $result;
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
