<?php
namespace libraries\lfdictionary\dto;

use libraries\lfdictionary\environment\ProjectRole;

use \libraries\lfdictionary\environment\ProjectPermission;

class ProjectAccessDTO {
	
	//here must match with client code : org.palaso.languageforge.client.base.common.ProjectPermissionType
	public static $_map = array (
		ProjectPermission::CAN_ADMIN            	=> 1,
		ProjectPermission::CAN_CREATE_ENTRY         => 2,
		ProjectPermission::CAN_EDIT_ENTRY         	=> 3,
		ProjectPermission::CAN_DELETE_ENTRY 		=> 4,
		ProjectPermission::CAN_EDIT_REVIEW_ALL 		=> 5,
		ProjectPermission::CAN_EDIT_REVIEW_OWN 		=> 6,
		ProjectPermission::CAN_CREATE_REVIEW 		=> 7,
		ProjectPermission::CAN_REPLY_REVIEW 		=> 8,
	);
	
	/**
	 * @var LFProjectAccess
	 */
	private $_projectAccess;

	/**
	 * @param LFProjectAccess $projectAccess
	 */
	public function __construct($projectAccess) {
		$this->_projectAccess = $projectAccess;
	}
	
	/**
	 * Encodes the object into a php array, suitable for use with json_encode
	 * @return mixed
	 */
	public function encode() {
// 		$grants = array_map(function($grant) { return ProjectAccessDTO::$_map[$grant]; }, $this->_projectAccess->permissionsAsArray());
		$grants = array(1, 2, 3, 4, 5, 6, 7, 8);
		return array(
			"grants" => $grants,
		    'activerole' => 'admin', //$this->_projectAccess->getRole(),
		    'availableroles' => 'admin', // TODO This can disappear CP 2013-08
		);

	}
	
}

?>