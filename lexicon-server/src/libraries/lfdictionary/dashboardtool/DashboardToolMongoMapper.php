<?php

namespace libraries\lfdictionary\dashboardtool;
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

?>