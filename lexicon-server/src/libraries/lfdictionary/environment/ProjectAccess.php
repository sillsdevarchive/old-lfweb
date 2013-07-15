<?php
namespace libraries\lfdictionary\environment;

class ProjectAccess {

	/**
	 * @var int
	 */
	public $projectId;
	
	/**
	 * @var int
	 */
	public $userId;
	
	/**
	 * @var string
	 */
	private $_role;
	
	/**
	 * @var ProjectPermission
	 */
	private $_permissions;
	
	/**
	* @var Boolean
	*/
	private $_reading = false;
	
	/**
	 * @param int $projectId
	 * @param int $userId
	 */
	public function __construct($projectId, $userId) {
		$this->projectId = $projectId;
		$this->userId = $userId;
	}

	/**
	 * Returns true if the user has $permission on this project.
	 * @param ProjectPermission $permission
	 * @return bool
	 */
	public function hasPermission($permission) {
		if (!$this->_permissions) {
			$role = $this->getRole();
			$this->_permissions = ProjectRole::get($role);
		}
		$result = $this->_permissions->has($permission);
		return $result;
	}

	/**
	 * Returns the permissions as an array of grants
	 * @return array
	 */
	public function permissionsAsArray() {
		if (!$this->_permissions) {
			$role = $this->getRole();
			$this->_permissions = ProjectRole::get($role);
		}
		return $this->_permissions->asArray();
	}
	
	/**
	 * Returns the role 
	 * @return string
	 */
	public function getRole() {
		if ($this->_role) {
			return $this->_role;
		}
		$this->read();
		return $this->_role;
	}
	
	/**
	 * Sets the role
	 * @param string $role
	 */
	public function setRole($role) {
		if ($this->_role != $role) {
			$this->_role = $role;
			$this->write();
		}
	}
	
	private function read() {
		$this->_reading = true;
		EnvironmentMapper::connect()->readProjectAccess($this);
		$this->_reading = false;
	}
	
	private function write() {
		if (!$this->_reading) {
			EnvironmentMapper::connect()->writeProjectAccess($this);
		}
	}
}

?>