<?php
namespace libraries\lfdictionary\environment;

/**
 */
class LFUserModel {

	/**
	 * @var bool
	 */
	private $_reading;
	
	/**
	 * @var int
	 */
	private $_userId;

	/**
	 * @var string
	 */
	private $_userName;
	
	/**
	* @var string
	*/
	private $_userEmail;
	
	/**
	* @var string
	*/
	private $_firstName;
	
	/**
	* @var string
	*/
	private $_lastName;
	
	/**
	 * @param int $userId
	 */
	public function __construct($userId) {
		$this->_userId = $userId;
		$this->_reading = false;
	}

	public function id() {
		return $this->_userId;
	}
	
	public function getUserName() {
		if ($this->_userName) {
			return $this->_userName;
		}
		$this->read();
		return $this->_userName;
	}
	
	public function getFirstName() {
		if ($this->_firstName) {
			return $this->_firstName;
		}
		$this->read();
		return $this->_firstName;
	}

	public function getLastName() {
		if ($this->_lastName) {
			return $this->_lastName;
		}
		$this->read();
		return $this->_lastName;
	}

	public function getUserEmail() {
		if ($this->_userEmail) {
			return $this->_userEmail;
		}
		$this->read();
		return $this->_userEmail;
	}
	
	public function setUserName($userName) {
		if ($this->_userName != $userName) {
			$this->_userName = $userName;
			$this->write();
		}
	}
	
	public function setUserEmail($email) {
		if ($this->_userEmail != $email) {
			$this->_userEmail = $email;
			$this->write();
		}
	}
	
	public function setFirstName($firstName) {
		if ($this->_firstName != $firstName) {
			$this->_firstName = $firstName;
			$this->write();
		}
	}
	
	public function setLastName($lastName) {
		if ($this->_lastName != $lastName) {
			$this->_lastName = $lastName;
			$this->write();
		}
	}
	
	public function setPassword($password) {
		EnvironmentMapper::connect()->setUserPassword($id, $password);
	}
	
	public function set($userName) {
		$this->_userName = $userName;
		$this->write();
	}
	
	public function read() {
		$this->_reading = true;
		EnvironmentMapper::connect()->readUser($this);
		$this->_reading = false;
	}
	
	private function write() {
		if (!$this->_reading) {
			EnvironmentMapper::connect()->writeUser($this->_userId, $this->_userName, $this->_firstName, $this->_lastName, $this->_userEmail);
		}
	}
	
	/**
	 * @param string $userName
	 * @return bool
	 * Returns true if the $userName already exists
	 */
	public static function nameExists($userName) {
		return EnvironmentMapper::connect()->userExists($userName);
	}
	
	/**
	* @param string $userName
	* @return bool
	* Returns UserDto if the $userName already exists, otherwise return FALSE
	*/
	public static function getUserByName($userName) {
		return EnvironmentMapper::connect()->getUserByName($userName);
	}
	
	/**
	 * @param string $email
	 * @return bool
	 * Returns true if the $email already exists
	 */
	public static function emailExists($email) {
		return EnvironmentMapper::connect()->emailExists($email);
	}
	
	/**
	 * 
	 * @param string $userName
	 * @param string $firstName
	 * @param string $lastName
	 * @param string $password
	 * @param string $email
	 * @throws Exception if the userName or email are empty
	 * @return null | \lfbase\environment\UserModel
	 */
	public static function addUser($userName, $firstName, $lastName, $password, $email) {
		if ($userName == "" || $email == "") {
			throw new \Exception("The userName and the email parameters are required and cannot be empty");
		}
		if (EnvironmentMapper::connect()->userExists($userName)) {
			return null;
		}

		$id = EnvironmentMapper::connect()->createNewUser($userName, $password, $email);
		$user = new UserModel($id);
		$user->read();
		$user->setFirstName($firstName);
		$user->setLastName($lastName);
		return $user;
	}
}

?>