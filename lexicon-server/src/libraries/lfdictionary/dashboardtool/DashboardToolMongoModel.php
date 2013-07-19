<?php

namespace models;

use libraries\sf\MongoStore;
use libraries\sf\ReferenceList;


class DashboardToolMongoMapper extends \libraries\sf\MongoMapper
{
	public static function instance()
	{
		static $instance = null;
		if (null === $instance)
		{
			$instance = new DashboardToolMongoMapper(LF_DATABASE, 'lf_activity');
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

class DashboardToolMongoModel extends \libraries\sf\MapperModel
{
	public function __construct($id = NULL)
	{
		$this->users = new ReferenceList();
		parent::__construct(DashboardToolMongoMapper::instance(), $id);
	}
	
	public function readyByQuery($query)
	{
		$data = $this->findOneByQuery($query);
	}
		
	/**
	 * @var string
	 */
	public $id;

	/**
	* @var string
	*/
	public $project_id;
	
	/**
	* @var string
	*/
	public $filed_type;
	
	/**
	* @var int
	*/
	public $counter_value;
	/**
	* @var int
	*/
	public $hg_version;
	/**
	* @var string
	*/
	public $hg_hash;
	/**
	* @var int
	*/
	public $time_stamp;

	// What else needs to be in the model?
	
}

?>