<?php
namespace libraries\lfdictionary\dashboardtool;

use models\mapper\MongoStore;
use models\mapper\ReferenceList;
class DashboardToolMongoListModel extends \models\mapper\MapperListModel
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