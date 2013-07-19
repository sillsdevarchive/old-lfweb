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
			$instance = new ProjectAccessMongoMapper(LF_DATABASE, 'lf_access');
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

class ProjectAccessModel extends \libraries\sf\MapperModel
{
	public function __construct($id = NULL)
	{
		$this->users = new ReferenceList();
		parent::__construct(ProjectAccessMongoMapper::instance(), $id);
	}
	
	public function findOneByProjectIdAndUserID($projectId, $userId)
	{
		
		$data = $this->findOneByQuery(array("user_id" => new \MongoId($userId), "project_id" => new \MongoId($projectId)));

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
	// What else needs to be in the model?
	
}

?>