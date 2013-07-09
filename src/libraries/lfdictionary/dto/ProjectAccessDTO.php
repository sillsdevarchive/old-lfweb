<?php
namespace libraries\lfdictionary\dto;

use libraries\lfdictionary\environment\ProjectRole;

use \lfbase\environment\ProjectPermission;

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
	 * @var ProjectAccess
	 */
	private $_projectAccess;

	/**
	 * @param ProjectAccess $projectAccess
	 */
	public function __construct($projectAccess) {
		$this->_projectAccess = $projectAccess;
	}
	
	/**
	 * Encodes the object into a php array, suitable for use with json_encode
	 * @return mixed
	 */
	public function encode() {
		$grants = array_map(function($grant) { return ProjectAccessDTO::$_map[$grant]; }, $this->_projectAccess->permissionsAsArray());
		return array(
			"grants" => $grants,
		    'activerole' => $this->_projectAccess->getRole(),
		    'availableroles' => ProjectRole::getAllRolesAsArray()
		);

	}
	
}

?>