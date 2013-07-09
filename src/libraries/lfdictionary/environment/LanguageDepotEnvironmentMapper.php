<?php
namespace libraries\lfdictionary\environment;

use libraries\lfdictionary\common\DataConnector;

class LanguageDepotEnvironmentMapper implements IEnvironmentMapper {

	/**
	 * @var DataConnection
	 */
	private $_connection;
	
	public function __construct() {
		$this->_connection = DataConnector::connect('languagedepot');
	}
	
	// methods not implemented
	public function readProjectAccess($projectAccess) { }
	public function writeProjectAccess($projectAccess) { }
	public function searchUser($name, $indexBegin, $indexEnd) { }
	public function removeUserFromProject($projectId, $userId) { }
	public function emailExists($email) { }
	
	
	/**
	 * 
	 * @param LFProjectModel $project
	 */
	public function readProject($project) {
		$id = $project->getId();
		$result = $this->_connection->execute("SELECT * FROM projects WHERE id = '$id'");
		$row =  $this->_connection->fetch_assoc($result);
		$arr = explode('-', $row['description'], 2); // language-type is stored in the description field
		$lang = $arr[0];
		$type = isset($arr[1]) ? $arr[1] : "";
		$project->set($row['name'], $lang, $row['identifier'], $type);
	}
	
	/**
	 * 
	 * @param LFProjectModel $project
	 */
	public function writeProject($project) {
		$this->_connection->execute(
				sprintf("UPDATE projects SET name='%s', identifier='%s', description='%s', updated_on=NOW() WHERE id=%d",
					$project->getTitle(), $project->getName(), 
					$project->getLanguageCode() . '-' . $project->getType(), $project->getId()
				)
		);
	}
	
	/**
	 * @param UserModel $user
	 */
	public function readUser($user) {
		$result = $this->_connection->execute(sprintf("SELECT * FROM users WHERE id = %d", $user->id()));
		$row =  $this->_connection->fetch_assoc($result);
		$user->setUserName($row['login']);
		$user->setFirstName($row['firstname']);
		$user->setLastName($row['lastname']);
		$user->setUserEmail($row['mail']);
	}
	
	/**
	 * Get an User by username
	 * @param string $username
	 * @return UserModel
	 */
	public function getUserByName($username) {
		$result = $this->_connection->execute(sprintf("SELECT id FROM users WHERE login = '%s'", $username));
		$row =  $this->_connection->fetch_assoc($result);
		if (count($row) > 0) {
			return new UserModel($row['id']);
		}
		return null;
	}
	
	/**
	 * Get a project by it's unique name
	 * @param string $name
	 * @return LFProjectModel|NULL if project doesn't exist
	 */
	public function getProjectByName($name) {
		$result = $this->_connection->execute(sprintf("SELECT id FROM projects WHERE identifier = '%s'", $name));
		$row =  $this->_connection->fetch_assoc($result);
		if (count($row) > 0) {
			return new LFProjectModel($row['id']);
		}
		return null;
	}
	
	
	/** Does the specified role ID exist in the roles table
	 * @param int $id
	 * @return bool
	 */
	public function roleExists($id) {
		$result = $this->_connection->execute(sprintf("SELECT id FROM roles WHERE id = %d", $id));
		$row =  $this->_connection->fetch_assoc($result);
		if (count($row) > 0) {
			return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * @param UserModel $user
	 */
	public function writeUser($id, $userName, $firstName, $lastName, $email) {
		$this->_connection->execute(
			sprintf("UPDATE users SET login='%s', firstname='%s', lastname='%s', mail='%s', updated_on=NOW() WHERE id=%d",
				$userName, $firstName, $lastName, $email, $id
			)
		);
	}
	
	
	/**
	* Add User to Project
	* @param int $projectId
	* @param int $userId
	* @param int $roleId
	* @return boolean
	*/
	public function addUserToProject($projectId, $userId, $roleId) {
		$result = $this->_connection->execute(sprintf("SELECT * FROM members WHERE user_id = %d AND project_Id = %d AND role_id = %d",
			$userId, $projectId, $roleId));
		$row =  $this->_connection->fetch_assoc($result);
		if (count($row) == 0) {
			$this->_connection->execute(
				sprintf("INSERT INTO members (user_id, project_id, role_Id, created_on, mail_notification) VALUES (%d, %d, %d, NOW(), 0)",
					$userId, $projectId, $roleId
					)
			);
		}
	}
	
	/**
	* @param String $userName
	*/
	public function userExists($userName) {
		$result = $this->_connection->execute(sprintf("SELECT * FROM users WHERE login = '%s'", $userName));
		$row =  $this->_connection->fetch_assoc($result);
		return (count($row) > 0);
	}
	
	
	
	
	/**
	* @param String $username
	* @param String $pwd
	* @param String $email
	*/
	public function createNewUser($username, $pwd, $email) {
		// we are compelled to keep the signature this way since the drupal environment mapper already implements this signature
		$this->_connection->execute(
				sprintf("INSERT INTO users (login, mail, hashed_password, created_on) VALUES ('%s', '%s', SHA1('%s'), NOW())",
						$username, $email, $pwd
				)
		);
		return $this->_connection->insert_id();
	}
	
	
	
	/*
	 * @param string $name A unique name for the project (project SLUG)
	 */
	public function createNewProject($name) {
		if (strlen($name) > 20 || strlen($name) == 0) {
			throw new \Exception("Redmine project 'identifer' must be between 1 and 20 characters");
		}
		$this->_connection->execute(
				sprintf("INSERT INTO projects (identifier, created_on) VALUES ('%s', NOW())",
						$name
				)
		);
		return $this->_connection->insert_id();
	}
	
	/*
	 * @param string $name A unique name for the project (project SLUG)
	 */
	public function projectExists($name) {
		$result = $this->_connection->execute(sprintf("SELECT * FROM projects WHERE identifier = '%s'", $name));
		$row =  $this->_connection->fetch_assoc($result);
		return (count($row) > 0);
	}
	
	
	public function setUserPassword($userId, $password) {
		$this->_connection->execute(
			sprintf("UPDATE users SET hashed_password=SHA1('%s'), updated_on=NOW() WHERE id=%d",
				$password, $userId
			)
		);
	}
}

?>
