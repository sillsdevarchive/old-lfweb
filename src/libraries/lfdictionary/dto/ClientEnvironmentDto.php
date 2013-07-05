<?php
namespace lfbase\dto;

use lfbase\environment\UserModel;
class ClientEnvironmentDto {
	/**
	 * @var ProjectModel
	 */
	private $_projectModel;

	/**
	 * @var UserModel
	 */
	private $_userModel;
	
	/**
	 * @var ProjectAccess
	 */
	private $_projectAccess;
	
	/**
	 * @param ProjectModel $projectModel
	 * @param UserModel $userModel
	 * @param ProjectAccess $projectAccess
	 */
	function __construct($projectModel, $userModel, $projectAccess) {
		$this->_projectModel = $projectModel;
		$this->_userModel = $userModel;
		$this->_projectAccess = $projectAccess;
	}

	function encode() {
		// TODO Don't think we really need projectDTO and userDTO, we can just use projectAccessDTO maybe CP 2012-11
		$projectDTO = new ProjectDTO($this->_projectModel);
		$project = base64_encode(json_encode($projectDTO->encode()));
		
		$userDTO = new UserDTO($this->_userModel);
		$userDTO->setUserRole($this->_projectAccess->getRole());
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