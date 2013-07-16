<?php
namespace libraries\lfdictionary\dto;

/**
 * This class contains Project DTO
 */
class ProjectDTO {
	
	/**
	 * @var LFProjectModel
	 */
	private $_LFProjectModel;
	
	/**
	 * @param LFProjectModel $LFProjectModel
	 */
	public function __construct($LFProjectModel) {
		$this->_LFProjectModel = $LFProjectModel;
	}
	
	/**
	 * Encodes the object into a php array, suitable for use with json_encode
	 * @return mixed
	 */
	function encode() {
		return array(
			'id' => $this->_LFProjectModel->getId(),
			'name' => $this->_LFProjectModel->getName(),
			'title' => $this->_LFProjectModel->getTitle(),
			'type' => $this->_LFProjectModel->getType(),
			'lang' => $this->_LFProjectModel->getLanguageCode()
		);
		
	}
}

?>