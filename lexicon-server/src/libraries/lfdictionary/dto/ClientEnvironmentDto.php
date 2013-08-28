<?php
namespace libraries\lfdictionary\dto;

use models\UserModel;
class ClientEnvironmentDto {
	/**
	 * @var LFProjectModel
	 */
	private $_LFProjectModel;

	/**
	 * @var UserModel
	 */
	private $_userModel;
	
	/**
	 * @var LFProjectAccess
	 */
	private $_projectAccess;
	
	/**
	 * @param LFProjectModel $LFProjectModel
	 * @param UserModel $userModel
	 * @param LFProjectAccess $projectAccess
	 */
	function __construct($LFProjectModel, $userModel, $projectAccess) {
		$this->_LFProjectModel = $LFProjectModel;
		$this->_userModel = $userModel;
		$this->_projectAccess = $projectAccess;
	}

	function encode() {
		// TODO Don't think we really need projectDTO and userDTO, we can just use projectAccessDTO maybe CP 2012-11
		$projectDTO = new ProjectDTO($this->_LFProjectModel);
		$project = base64_encode(json_encode($projectDTO->encode()));
		
		$userDTO = new UserDTO($this->_userModel);
		$user = base64_encode(json_encode($userDTO->encode()));
		$projectAccessDTO = new ProjectAccessDTO($this->_projectAccess);
		$projectAccess = base64_encode(json_encode($projectAccessDTO->encode()));
		
		return array(
			'currentProject' => $project,
			'currentUser' => $user,
			'access' => $projectAccess
		);
		
	}
}

?>