<?php

use libraries\api\ProjectCommands;
use libraries\api\UserCommands;
use libraries\palaso\CodeGuard;
use libraries\palaso\JsonRpcServer;
use models\mapper\Id;
use models\mapper\JsonEncoder;
use models\mapper\JsonDecoder;


require_once(APPPATH . 'models/UserModel.php');
require_once(APPPATH . 'models/ProjectModel.php');

class Lf
{
	
	public function __construct()
	{
		// TODO put in the LanguageForge style error handler for logging / jsonrpc return formatting etc. CP 2013-07
		//ini_set('display_errors', 0);
	}
	
	private function decode($model, $data) {
		$decoder = new JsonDecoder();
		$decoder->decode($model, $data);
	}
	
	private function encode($model) {
		$encoder = new JsonEncoder();
		return $encoder->encode($model);
	}
	
	//---------------------------------------------------------------
	// USER API
	//---------------------------------------------------------------
	
	/**
	 * Create/Update a User
	 * @param UserModel $json
	 * @return string Id of written object
	 */
	public function user_update($params) {
		$user = new \models\UserModel();
		$this->decode($user, $params);
		$result = $user->write();
		return $result;
	}

	/**
	 * Read a user from the given $id
	 * @param string $id
	 */
	public function user_read($id) {
		$user = new \models\UserModel($id);
		return $this->encode($user);
	}
	
	/**
	 * Delete multiple users
	 * @param array<string> $userIds
	 * @return int Total number of users deleted.
	 */
 	public function user_delete($userIds) {
 		return UserCommands::deleteUsers($userIds);
 	}

	// TODO Pretty sure this is going to want some paging params
	public function user_list() {
		$list = new \models\UserListModel();
		$list->read();
		return $list;
	}
	
	public function user_typeahead($term) {
		$list = new \models\UserTypeaheadModel($term);
		$list->read();
		return $list;
	}
	
	public function change_password($userId, $newPassword) {
		if (!is_string($userId) && !is_string($newPassword)) {
			throw new \Exception("Invalid args\n" . var_export($userId, true) . "\n" . var_export($newPassword, true));
		}
		$user = new \models\PasswordModel($userId);
		$user->changePassword($newPassword);
		$user->write();
	}
	
	
	//---------------------------------------------------------------
	// PROJECT API
	//---------------------------------------------------------------
	
	/**
	 * Create/Update a Project
	 * @param ProjectModel $json
	 * @return string Id of written object
	 */
	public function project_update($object) {
		$project = new \models\ProjectModel();
		$this->decode($project, $object);
		$result = $project->write();
		return $result;
	}

	/**
	 * Read a project from the given $id
	 * @param string $id
	 */
	public function project_read($id) {
		$project = new \models\ProjectModel($id);
		return $this->encode($project);
	}
	
	/**
	 * Delete multiple projects.
	 * @param array<string> $projectIds
	 * @return int Total number of projects deleted.
	 */
 	public function project_delete($projectIds) {
 		return ProjectCommands::deleteProjects($projectIds);
 	}

	// TODO Pretty sure this is going to want some paging params
	public function project_list() {
		$list = new \models\ProjectListModel();
		$list->read();
		return $list;
	}
	
	public function project_readUser($projectId, $userId) {
		throw new \Exception("project_readUser NYI");
	}
	
	public function project_updateUser($projectId, $object) {
		
		$projectModel = new \models\ProjectModel($projectId);
		$command = new \models\commands\ProjectUserCommands($projectModel);
		return $command->addUser($object);
	}
	
	public function project_deleteUsers($projectId, $userIds) {
		// This removes the user from the project.
		$projectModel = new \models\ProjectModel($projectId);
		foreach ($userIds as $userId) {
			$projectModel->removeUser($userId);
			$projectModel->write();
		}
	}
	
	public function project_listUsers($projectId) {
		$projectModel = new \models\ProjectModel($projectId);
		return $projectModel->listUsers();
	}
	
}