<?php
namespace models;

use models\mapper\MongoStore;
use models\mapper\ReferenceList;

class ProjectModelMongoMapper extends \models\mapper\MongoMapper
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