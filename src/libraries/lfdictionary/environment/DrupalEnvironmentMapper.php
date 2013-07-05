<?php
namespace lfbase\environment;

use lfbase\dto\UserDTO;
use lfbase\dto\UserListDTO;
use lfbase\common\DataConnection;
use lfbase\common\DataConnector;
use lfbase\environment\ProjectRole;

class DrupalEnvironmentMapper implements IEnvironmentMapper {

	/**
	 * @var DataConnection
	 */
	private $_connection;

	public function __construct() {
		$this->_connection = DataConnector::connect();
	}

	/**
	 * @param ProjectAccess $projectAccess
	 * @see lfbase\environment.IEnvironment::readProjectAccess()
	 */
	public function readProjectAccess($projectAccess) {
		$sql = sprintf(
			"SELECT nid,uid,lf_role FROM lf_access WHERE nid=%d AND uid=%d AND is_active!=0",
		$projectAccess->projectId, $projectAccess->userId
		);
		$result = $this->_connection->execute($sql);
		if ($row = $this->_connection->fetchrow($result)) {
			$projectAccess->setRole(ProjectRole::mapRoleFromHost($row['lf_role']));
		} else {
			DrupalEnvironmentFixer::fixProjectAccess($this, $projectAccess);
		}
	}

	/**
	 * @param ProjectAccess $projectAccess
	 * @see lfbase\environment.IEnvironment::writeProjectAccess()
	 */
	public function writeProjectAccess($projectAccess) {
		$hostRole = ProjectRole::mapRoleToHost($projectAccess->getRole());
		$sql = sprintf(
			"INSERT lf_access SET lf_role='%s',nid=%d,uid=%d ON DUPLICATE KEY UPDATE lf_role='%s'",
		$hostRole, $projectAccess->projectId, $projectAccess->userId, $hostRole
		);
		$this->_connection->execute($sql);
	}

	/**
	 *
	 * @param ProjectModel $project
	 */
	public function readProject($project) {
		$sql = sprintf("SELECT n.title, og.og_description, ctp.field_procode_value " .
			"FROM node AS n INNER JOIN og_ancestry AS oa ON n.nid=oa.nid " .
			"INNER JOIN og ON og.nid=oa.group_nid " .
			"INNER JOIN content_type_project AS ctp ON n.nid=ctp.nid " .
			"WHERE n.nid=%d",
		$project->getId()
		);

		$result = $this->_connection->execute($sql);
		$row = $this->_connection->fetchrow($result);

		$typeTokens = explode("-", $row['field_procode_value']);
		$type = $typeTokens[count($typeTokens) - 1];

		$project->set($row['title'], $row['og_description'], $row['field_procode_value'], $type);
	}

	/**
	 *
	 * @param ProjectModel $project
	 */
	public function writeProject($project) {
		$sql = sprintf("UPDATE node SET title='%s' WHERE nid=%d AND type='project'", $project->getTitle(), $project->getId());
		$result = $this->_connection->execute($sql);
	}

	/**
	 * @param UserModel $user
	 */
	public function readUser($user) {
		$sql = sprintf("SELECT name, mail FROM users WHERE uid=%d", $user->id());
		$result = $this->_connection->execute($sql);
		$row = $this->_connection->fetchrow($result);

		$user->set($row['name']);
		$user->setUserEmail($row['mail']);
	}

	/**
	 * @param String $name
	 * @param int $indexBegin
	 * @param int $indexEnd
	 */
	public function searchUser($name, $indexBegin, $indexEnd) {
		$userlistdto = new  \lfbase\dto\UserListDTO();
		if ($name) {
			$sql = sprintf("SELECT u.uid, u.name FROM {users} u INNER JOIN {users_roles} ur ON u.uid = ur.uid WHERE LOWER(u.name) LIKE LOWER('%s') OR LOWER(u.name) LIKE LOWER('%s') OR LOWER(u.name) LIKE LOWER('%s')","%".$name."%", $name."%", "%".$name);
			$result = db_query_range($sql, $indexBegin, $indexEnd);
			while ($user = db_fetch_object($result)) {
				$userModel= new UserModel($user->uid);
				$userdto = new \lfbase\dto\UserDTO($userModel);;
				$userlistdto->addListUser($userdto);
			}
		}
		return $userlistdto;
	}


	/**
	 * Add User to Project along with community
	 * @param int $projectId
	 * @param int $userId
	 * @return boolean
	 */
	public function addUserToProject($projectId, $userId) {
		$communitId = $this->getCommunityIdByProjectId($projectId);
		if (!$this->userIsCommunityMember($communitId,$userId)) {
			// not in communit yet, join first
			$this->userJoinCommunity($communitId, $userId);
		}
		$this->userJoinProject($projectId, $userId);
		$this->defaultUserProjectPermission($projectId, $userId);
		$projectModel = new \lfbase\environment\ProjectModel($projectId);
		if ($projectModel->isUserInProject($userId)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @param int $projectId
	 * @param int $userId
	 */
	public function removeUserFromProject($projectId, $userId)
	{
		$sql = sprintf("DELETE FROM lf_access WHERE nid = %d AND uid = %d", $projectId, $userId);
		$result = $this->_connection->execute($sql);
	}


	/**
	 * @param String $userName
	 */
	public function userExists($userName)
	{
		$sql = sprintf("SELECT COUNT(*) FROM users WHERE uid=%d", $username);
		$result = $this->_connection->execute($sql);
		$row = $this->_connection->fetchrow($result);
		if (array_key_exists('count', $row) && $row['count']>0)
		{
			return true;
		}
		return false;
	}

	/**
	 * @param String $email
	 */
	public function emailExists($email)
	{
		$sql = sprintf("SELECT COUNT(*) FROM users WHERE mail='%s'", $email);
		$result = $this->_connection->execute($sql);
		$row = $this->_connection->fetchrow($result);
		if (array_key_exists('count', $row) && $row['count']>0)
		{
			return true;
		}
		return false;
	}

	/**
	 * @param String $username
	 */
	public function getUserByName($username)
	{
		$username = trim($username);
		$sql = sprintf("SELECT name, mail, uid FROM users WHERE name='%s'", $username);
		$result = $this->_connection->execute($sql);
		$row = $this->_connection->fetchrow($result);
		if (count($row)>0){
			$user = new UserModel($row['uid']);
			$user->set($row['name']);
			$user->setUserEmail($row['mail']);
			return $user;
		}
		return FALSE;
	}
	
	/**
	*
	* @param string $name
	* @return ProjectModel|NULL
	*/
	public function getProjectByName($name) {
	
		$sql = sprintf("SELECT n.title, n.nid, og.og_description, ctp.field_procode_value " .
			"FROM node AS n INNER JOIN og_ancestry AS oa ON n.nid=oa.nid " .
			"INNER JOIN og ON og.nid=oa.group_nid " .
			"INNER JOIN content_type_project AS ctp ON n.nid=ctp.nid " .
			"WHERE n.nid=%s",
		$name
		);
	
		$result = $this->_connection->execute($sql);
		$row = $this->_connection->fetchrow($result);
	
		$typeTokens = explode("-", $row['field_procode_value']);
		$type = $typeTokens[count($typeTokens) - 1];
		if (array_key_exists('nid', $row))
		{
			$project = new ProjectModel($row['nid']);
			// set(title, language, name (slug), type);
			$project->set($row['title'], $row['og_description'], $row['field_procode_value'], $type);
			return $project;
		}
		return null;
	}

	public function createNewProject($name) {
		// TODO: implement this
		return false;
	}

	public function projectExists($name) {
		$sql = sprintf("SELECT n.title, og.og_description, ctp.field_procode_value " .
			"FROM node AS n INNER JOIN og_ancestry AS oa ON n.nid=oa.nid " .
			"INNER JOIN og ON og.nid=oa.group_nid " .
			"INNER JOIN content_type_project AS ctp ON n.nid=ctp.nid " .
			"WHERE n.title=%s",$name);

		$result = $this->_connection->execute($sql);
		
		$row = $this->_connection->fetchrow($result);
		if (array_key_exists('title', $row) && $row['title']>0)
		{
			return true;
		}
		return false;
	}

	/**
	 * // do something similar to user.module
	 * @param String $username
	 * @param String $pwd
	 * @param String $email
	 */
	public function createNewUser($username, $pwd, $email)
	{
		$user_fields = $this->user_fields();
		$array = array('name' => $username, 'pass' => $pwd, 'init' => $email, 'roles' => array(3), 'status'=> 1);

		// Allow 'created' to be set by the caller.
		if (!isset($array['created'])) {
			$array['created'] = time();
		}

		// Note: we wait to save the data column to prevent module-handled
		// fields from being saved there. We cannot invoke hook_user('insert') here
		// because we don't have a fully initialized user object yet.
		foreach ($array as $key => $value) {
			switch ($key) {
				case 'pass':
					$fields[] = $key;
					$values[] = md5($value);
					$s[] = "'%s'";
					break;
				case 'mode':       case 'sort':     case 'timezone':
				case 'threshold':  case 'created':  case 'access':
				case 'login':      case 'status':
					$fields[] = $key;
					$values[] = $value;
					$s[] = "%d";
					break;
				default:
					if (substr($key, 0, 4) !== 'auth' && in_array($key, $user_fields)) {
					$fields[] = $key;
					$values[] = $value;
					$s[] = "'%s'";
				}
				break;
			}
		}
		$success = db_query('INSERT INTO {users} ('. implode(', ', $fields) .') VALUES ('. implode(', ', $s) .')', $values);
		if (!$success) {
			// On a failed INSERT some other existing user's uid may be returned.
			// We must abort to avoid overwriting their account.
			return FALSE;
		}

		// Build the initial user object.
		$array['uid'] = db_last_insert_id('users', 'uid');

		// Build and save the serialized data field now.
		$data = array();
		foreach ($array as $key => $value) {
			if ((substr($key, 0, 4) !== 'auth') && ($key != 'roles') && (!in_array($key, $user_fields)) && ($value !== NULL)) {
				$data[$key] = $value;
			}
		}
		db_query("UPDATE {users} SET data = '%s' WHERE uid = %d", serialize($data), $array['uid']);

		// Save user roles (delete just to be safe).
		if (isset($array['roles']) && is_array($array['roles'])) {
			db_query('DELETE FROM {users_roles} WHERE uid = %d', $array['uid']);
			foreach (array_keys($array['roles']) as $rid) {
				if (!in_array($rid, array(DRUPAL_ANONYMOUS_RID, DRUPAL_AUTHENTICATED_RID))) {
					db_query('INSERT INTO {users_roles} (uid, rid) VALUES (%d, %d)', $array['uid'], $rid);
				}
			}
		}
		return $array['uid'];
	}

	/**
	 * @param int $projectId
	 */
	public function listUsersInProject($projectId)
	{
		$userlistdto = new  UserListDTO();
		if ($projectId) {
			$result = db_query("SELECT u.uid, ur.rid, u.name, u.mail FROM {lf_access} opu INNER JOIN {users} u ON opu.uid = u.uid INNER JOIN {users_roles} ur ON u.uid = ur.uid WHERE u.status = 1 AND opu.nid = $this->_projectId");
			while ($user = db_fetch_object($result)) {
				$userModel= new UserModel($user->uid);
				$userdto = new UserDTO($userModel);;
				$userlistdto->addListUser($userdto);
			}
		}
		return $userlistdto;
	}


	private function user_fields() {
		static $fields;
		if (!$fields) {
			$result = db_query('SELECT * FROM {users} WHERE uid = 1');
			if ($field = db_fetch_array($result)) {
				$fields = array_keys($field);
			}
			else {
				// Make sure we return the default fields at least.
				$fields = array('uid', 'name', 'pass', 'mail', 'picture', 'mode', 'sort', 'threshold', 'theme', 'signature', 'signature_format', 'created', 'access', 'login', 'status', 'timezone', 'language', 'init', 'data');
			}
		}
		return $fields;
	}

	/**
	 * created for to get the community nid as per given project id
	 * @param int $nid
	 * @return int
	 */
	private function getCommunityIdByProjectId($nid) {

		$sql = sprintf("SELECT oa.group_nid FROM og_ancestry oa WHERE oa.nid=%d", $nid);
		$result = $this->_connection->execute($sql);
		while ($row = $this->_connection->fetchrow($result)) {
			$community_nid = $row['group_nid'];
		}
		return $community_nid;
	}

	/**
	 * created for to check that does the user exist and active in the community.
	 * @param int $communityId
	 * @param int $userId
	 * @return boolean
	 */
	private function userIsCommunityMember($communityId,$userId) {
		$sql = sprintf("SELECT og.nid FROM og_uid og WHERE og.nid=%d and og.uid=%d and og.is_active=1", $communityId, $userId);
		$result = $this->_connection->execute($sql);
		while ($row = $this->_connection->fetchrow($result)) {
			return true;
		}
		return false;
	}

	/**
	 * created for to let a user join or reactive in the community.
	 * @param int $communityId
	 * @param int $userId
	 */
	private function userJoinCommunity($communityId,$userId) {
		if ($userId > 0) {
			$sql = sprintf("SELECT COUNT(*) FROM og_uid WHERE nid = %d AND uid = %d", $communityId, $userId);
			$result = $this->_connection->execute($sql);
			$row = $this->_connection->fetchrow($result);
			$cnt= 0 ;
			if (array_key_exists('count', $row))
			{
				$cnt =  $row['count'];
			}
			$time = time();
			if ($cnt == 0) {
				//not exist, create a now record
				$sql = sprintf("INSERT og_uid SET nid=%d,og_role=%d,is_active=%d,is_admin=%d,uid=%d,created=%d, changed=%d  ON DUPLICATE KEY UPDATE is_active=1",
				$communityId,0,1,0,$userId,$time,$time);
				$this->_connection->execute($sql);
			}
			else {
				//exists, reactive it.
				$sql = sprintf("UPDATE og_uid SET is_active = 1, changed=%d WHERE nid=%d AND uid=%d",$time, $communityId, $userId);
				$result = $this->_connection->execute($sql);
			}
		}
	}

	/**
	 * created for to let a user join or reactive in the project.
	 * @param int $projectId
	 * @param int $userId
	 */
	private function userJoinProject($projectId,$userId) {
		if ($userId > 0) {
			$sql = sprintf("SELECT COUNT(*) FROM og_project_uid WHERE nid = %d AND uid = %d", $projectId, $userId);
			$result = $this->_connection->execute($sql);
			$row = $this->_connection->fetchrow($result);
			$cnt= 0 ;
			if (array_key_exists('count', $row))
			{
				$cnt =  $row['count'];
			}
			$time = time();
			if ($cnt == 0) {
				//not exist, create a now record
				$sql = sprintf("INSERT og_project_uid SET nid=%d,og_role=%d,is_active=%d,is_admin=%d,uid=%d,created=%d,changed=%d  ON DUPLICATE KEY UPDATE is_active=1",
				$projectId,0,1,0,$userId,$time,$time);
				$this->_connection->execute($sql);
			}
			else {
				//exists, reactive it.
				$sql = sprintf("UPDATE og_project_uid SET is_active = 1, changed=%d WHERE nid=%d AND uid=%d", $time, $projectId, $userId);
				$result = $this->_connection->execute($sql);
			}
		}
	}

	/**
	 * created or reactive user access role.
	 * @param int $projectId
	 * @param int $userId
	 */
	private function defaultUserProjectPermission($projectId,$userId) {
		if ($userId > 0) {
			$sql = sprintf("SELECT COUNT(*) FROM lf_access WHERE nid = %d AND uid = %d", $projectId, $userId);
			$result = $this->_connection->execute($sql);
			$row = $this->_connection->fetchrow($result);
			$cnt= 0 ;
			if (array_key_exists('count', $row))
			{
				$cnt =  $row['count'];
			}
			$time = time();
			if ($cnt == 0) {
				//not exist, create a new record with USER role
				$hostRole = ProjectRole::mapRoleToHost(ProjectRole::USER);
				$sql = sprintf("INSERT lf_access SET nid=%d,lf_role=%d,is_active=%d,uid=%d,created=%d, modified=%d  ON DUPLICATE KEY UPDATE is_active=1",
				$projectId, $hostRole, 1, $userId,$time,$time);
				$this->_connection->execute($sql);
			}
			else {
				//exists, reactive it.
				$sql = sprintf("UPDATE lf_access SET is_active = 2, modified=%d WHERE nid=%d AND uid=%d",$time, $projectId, $userId);
				$result = $this->_connection->execute($sql);
			}
		}
	}
	
	/**
	*
	* @param int $projectId
	* @param int $hostRole
	*/
	public function getUsersInProjectByRole($projectId, $hostRole) {
		$userlistdto = new \lfbase\dto\UserListDTO();
		$sql = sprintf("SELECT u.uid, u.name, u.mail FROM users u INNER JOIN lf_access op ON u.uid = op.uid WHERE op.is_active=1 AND op.lf_role=%d AND op.nid=%d", $hostRole, $projectId);
		$result = db_query($sql);
		while ($user = db_fetch_object($result)) {
			$userModel= new UserModel($user->uid);
			$userdto = new \lfbase\dto\UserDTO($userModel);;
			$userlistdto->addListUser($userdto);
		}
		return $userlistdto;
	}
}

?>