<?php
namespace models;

use libraries\palaso\CodeGuard;

class ProjectModelFixer
{
	/**
	 * @var ProjectModel
	 */
	private $_projectModel;
	

	/**
	 * @param ProjectModel $projectModel
	 */
	public function __construct($projectModel) {
		CodeGuard::checkTypeAndThrow($projectModel, 'models\ProjectModel');
		$this->_projectModel = $projectModel;
	}
}

?>