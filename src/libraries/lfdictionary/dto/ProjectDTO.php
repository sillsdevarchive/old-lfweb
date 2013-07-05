<?php
namespace lfbase\dto;

/**
 * This class contains Project DTO
 */
class ProjectDTO {
	
	/**
	 * @var ProjectModel
	 */
	private $_projectModel;
	
	/**
	 * @param ProjectModel $projectModel
	 */
	public function __construct($projectModel) {
		$this->_projectModel = $projectModel;
	}
	
	/**
	 * Encodes the object into a php array, suitable for use with json_encode
	 * @return mixed
	 */
	function encode() {
		return array(
			'id' => (int)$this->_projectModel->getId(),
			'name' => $this->_projectModel->getName(),
			'title' => $this->_projectModel->getTitle(),
			'type' => $this->_projectModel->getType(),
			'lang' => $this->_projectModel->getLanguageCode()
		);
		
	}
}

?>