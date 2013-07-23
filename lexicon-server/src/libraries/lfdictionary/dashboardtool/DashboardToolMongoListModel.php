<?php
namespace libraries\lfdictionary\dashboardtool;

use libraries\sf\MongoStore;
use libraries\sf\ReferenceList;
class DashboardToolMongoListModel extends \libraries\sf\MapperListModel
{
	public function __construct()
	{
		parent::__construct(
		DashboardToolMongoMapper::instance(),
		array()
		);
	}
}
?>