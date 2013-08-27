<?php
namespace models;

use models\mapper\MongoMapper;

use models\mapper\MongoStore;
use models\mapper\ReferenceList;
use models\mapper\Id;

class ProjectList_UserModel extends \models\mapper\MapperListModel
{

	public function __construct($userId)
	{
		parent::__construct(
				ProjectModelMongoMapper::instance(),
				array('users.' . $userId => array('$exists' => true)),
				array('projectname')
		);
	}

}



?>