<?php

namespace models;

use libraries\sf\MongoStore;
use libraries\sf\ReferenceList;

require_once(APPPATH . '/models/ProjectModel.php');

class ProjectAccessMongoMapper extends \libraries\sf\MongoMapper
{
	public static function instance()
	{
		static $instance = null;
		if (null === $instance)
		{
			$instance = new ProjectAccessMongoMapper(SF_DATABASE, 'projects');
		}
		return $instance;
	}
	
	public function drop($databaseName) {
		if (MongoStore::hasDB($databaseName)) {
			$db = MongoStore::connect($databaseName);
			$db->drop();
		}
	}
}

class ProjectAccess extends \libraries\sf\MapperModel
{
	public function __construct($id = NULL)
	{
		$this->users = new ReferenceList();
		parent::__construct(ProjectAccessMongoMapper::instance(), $id);
	}
	
	public function databaseName() {
		$name = strtolower($this->projectname);
		$name = str_replace(' ', '_', $name);
		return 'sf_' . $name;
	}

	/**
	 * Removes this project from the collection.
	 * User references to this project are also removed
	 */
	public function remove()
	{
		ProjectAccessMongoMapper::instance()->drop($this->databaseName());
		ProjectAccessMongoMapper::instance()->remove($this->id);
	}
	
	
	/**
	 * Adds the $userId as a member of this project.
	 * You do NOT need to call write() as this method calls it for you
	 * @param string $userId
	 */
	public function addUser($userId) {
		$this->users->_addRef($userId);

	}
	
	
	/**
	 * Removes the $userId from this project.
	 * You do NOT need to call write() as this method calls it for you
	 * @param string $userId
	 */
	public function removeUser($userId) {
		//$userModel = new UserModel($userId);
		$this->users->_removeRef($userId);
		//$userModel->projects->_removeRef($this->id);
	}

	public function listUsers() {
		$userList = new UserList_ProjectAccess($this->id);
		$userList->read();
		return $userList;
	}
	
	/**
	 * @var string
	 */
	public $id;
	
	/**
	 * @var string
	 */
	public $user_id;

	/**
	* @var string
	*/
	public $project_id;
	
	/**
	* @var int
	*/
	public $lf_role;
	
	/**
	* @var int
	*/
	public $is_active;
	
	/**
	* @var int
	*/
	public $created;
	
	/**
	* @var int
	*/
	public $modified;
	// What else needs to be in the model?
	
}

class ProjectListModel extends \libraries\sf\MapperListModel
{
	public function __construct()
	{
		parent::__construct(
			ProjectAccessMongoMapper::instance(),
			array(),
			array('projectname', 'language', 'title')
		);
	}
}

class ProjectList_UserModel extends \libraries\sf\MapperListModel
{

	public function __construct($userId)
	{
		parent::__construct(
				ProjectAccessMongoMapper::instance(),
				array('users' => array('$in' => array(new \MongoId($userId)))),
				array('projectname')
		);
	}

}


?>