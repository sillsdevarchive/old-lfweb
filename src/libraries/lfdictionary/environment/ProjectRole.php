<?php
namespace libraries\lfdictionary\environment;

class ProjectRole {
	
	const ADMIN = 'admin';
	const USER = 'user';
	
	/**
	 * @var array(ProjectRole)
	 */
	private static $_roles = array();
	
	/**
	 *  all role current in use.
	* @var array
	*/
	private static $_availableRoles = array();
	
	/**
	 * @var ProjectPermission
	 */
	public $permission;
	
	/**
	 * @var int
	 */
	public $appRole;
	
	/**
	 * @var int
	 */
	public $hostRole;
	
	/**
	 * 
	 * @param ProjectPermisison $permission
	 * @param int $appRole
	 * @param int $hostRole
	 */
	public function __construct($permission, $appRole, $hostRole) {
		$this->permission = $permission;
		$this->appRole = $appRole;
		$this->hostRole = $hostRole;
	}
	
	public static function getAllRolesAsArray()
	{
		return self::$_availableRoles;
	}
	
	/**
	 * Returns the ProjectPermission for $role
	 * @param string $role
	 * @return ProjectPermission
	 * @throws \Exception
	 */
	public static function get($role) {
		if (!isset(self::$_roles[$role])) {
			throw new \Exception("Unknown role '$role'");
		}
		return self::$_roles[$role]->permission;
	}
	
	/**
	 * Adds $role having $permission
	 * @param string $role
	 * @param ProjectPermission $permission
	 */
	public static function add($role, $permission, $appRole, $hostRole) {
		self::$_roles[$role] = new ProjectRole($permission, $appRole, $hostRole);
		self::$_availableRoles[$role] = $role;
	}
	
	/**
	 * @param int $hostRole
	 * @return string The role
	 */
	public static function mapRoleFromHost($hostRole) {
		foreach (self::$_roles as $role => $projectRole) {
			if ($projectRole->hostRole == $hostRole) {
				return $role;
			}
		}
	}
	
	public static function mapRoleToHost($role) {
		if (!array_key_exists($role, self::$_roles)) {
			throw new \Exception("Unknown role '$role'");
		}
		return self::$_roles[$role]->hostRole;
	}	
}

?>