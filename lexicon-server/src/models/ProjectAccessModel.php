<?php

namespace models;

use models\mapper\MongoMapper;

use models\mapper\Id;
use models\mapper\ReferenceList;

require_once(APPPATH . '/models/ProjectModel.php');

class ProjectAccessMongoMapper extends \models\mapper\MongoMapper
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

class ProjectAccessModel extends \models\mapper\MapperModel
{
	public function __construct($id = NULL)
	{
		$this->id = new Id();
		$this->users = new ReferenceList();
		parent::__construct(ProjectAccessMongoMapper::instance(), $id);
	}
	
	public function findOneByProjectIdAndUserID($projectId, $userId)
	{
		$this->findOneByQuery(array("user_id" => new \MongoId($userId), "project_id" => new \MongoId($projectId)));
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