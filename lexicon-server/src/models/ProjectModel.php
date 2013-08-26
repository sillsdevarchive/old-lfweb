<?php

namespace models;

use models\rights\Roles;
use models\rights\ProjectRoleModel;
use models\mapper\MapOf;
use models\mapper\MongoMapper;
use models\mapper\MongoStore;
use models\mapper\ReferenceList;
use models\mapper\Id;

require_once(APPPATH . '/models/ProjectModel.php');

class ProjectModel extends \models\mapper\MapperModel
{
	public function __construct($id = '') {
		$this->id = new Id();
		$this->users = new MapOf(function($data) {
			return new ProjectRoleModel();
		});
		parent::__construct(ProjectModelMongoMapper::instance(), $id);
	}
	
	public function databaseName() {
		$name = strtolower($this->projectname);
		$name = str_replace(' ', '_', $name);

		return 'lf_' . $name;

	}

	/**
	 * Removes this project from the collection.
	 * User references to this project are also removed
	 */
	public function remove() {
		ProjectModelMongoMapper::instance()->drop($this->databaseName());
		ProjectModelMongoMapper::instance()->remove($this->id->asString());
	}
	
	/**
	 * Adds the $userId as a member of this project.
	 * @param string $userId
	 * @param string $role The role the user has in this project. Defaults to Roles::USER
	 * @see Roles;
	 */
	public function addUser($userId, $role = Roles::USER) {
		$mapper = ProjectModelMongoMapper::instance();
//		$ProjectModelMongoMapper::mongoID($userId)
		$model = new ProjectRoleModel();
		$model->role = $role;
		$this->users->data[$userId] = $model; 
	}
	
	/**
	 * Removes the $userId from this project.
	 * @param string $userId
	 */
	public function removeUser($userId) {
		unset($this->users->data[$userId]);
	}

	public function listUsers() {
		$userList = new UserList_ProjectModel($this->id->asString());
		$userList->read();
		return $userList;
	}
	
	/**
	 * Returns true if the given $userId has the $right in this project.
	 * @param string $userId
	 * @param int $right
	 * @return bool
	 */
	public function hasRight($userId, $right) {
		$role = $this->users->data[$userId]->role;
		$result = Roles::hasRight($role, $right);
		return $result;
	}
	
	/**
	 * Returns the rights array for the $userId role.
	 * @param string $userId
	 * @return array
	 */
	public function getRightsArray($userId) {
		$role = $this->users->data[$userId]->role;
		$result = Roles::getRightsArray($role);
		return $result;
	}
	
	/**
	 * @var Id
	 */
	public $id;
	
	/**
	 * @var string
	 */
	public $projectCode;
	
	/**
	 * @var string
	 */
	public $projectname;
	
	/**
	 * @var string
	 */
	public $language;
	
	/**
	 * @var MapOf<ProjectRoleModel>
	 */
	public $users;
	
	
	/**
	* @var string
	*/
	public $title;
	
	// What else needs to be in the model?
	
}


?>