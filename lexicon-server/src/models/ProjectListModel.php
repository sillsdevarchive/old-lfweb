<?php
namespace models;

use libraries\sf\MongoStore;
use libraries\sf\ReferenceList;
class ProjectListModel extends \libraries\sf\MapperListModel
{
	public function __construct()
	{
		parent::__construct(
		ProjectModelMongoMapper::instance(),
		array(),
		array('projectname', 'language', 'title')
		);
	}
}
?>