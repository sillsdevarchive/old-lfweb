<?php
namespace models;


use models\mapper\MongoMapper;

use models\mapper\MongoStore;
use models\mapper\ReferenceList;
use models\mapper\Id;

class ProjectListModel extends \models\mapper\MapperListModel
{
	public function __construct()
	{
		parent::__construct(
			ProjectModelMongoMapper::instance(),
			array(),
			array('projectname', 'language')
		);
	}
}

?>