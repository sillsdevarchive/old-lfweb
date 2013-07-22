<?php
namespace models;

use libraries\sf\MongoStore;
use libraries\sf\ReferenceList;
class ProjectModelMongoMapper extends \libraries\sf\MongoMapper
{
	public static function instance()
	{
		static $instance = null;
		if (null === $instance)
		{
			$instance = new ProjectModelMongoMapper(LF_DATABASE, 'projects');
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
?>