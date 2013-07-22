<?php
namespace models;

use libraries\sf\MongoStore;
use libraries\sf\ReferenceList;
class ProjectList_UserModel extends \libraries\sf\MapperListModel
{

	public function __construct($userId)
	{
		parent::__construct(
		ProjectModelMongoMapper::instance(),
		array('users' => array('$in' => array(new \MongoId($userId)))),
		array('projectname')
		);
	}

}


?>