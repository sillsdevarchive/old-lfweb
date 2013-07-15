<?php
require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

require_once(TEST_LIB_PATH . 'jsonRPCClient.php');
require_once(TEST_PATH . 'common/MongoTestEnvironment.php');

class UserAPITestEnvironment
{
	/**
	 * @var jsonRPCClient
	 */
	private $_api;
	
	/**
	 * @var array
	 */
	private $_idAdded = array();
	
	function __construct() {
		$this->_api = new jsonRPCClient("http://languageforge.local/api/lf", false);
		$e = new MongoTestEnvironment();
		$e->clean();
	}
	
	/**
	 * @param string $name
	 * @param string $username
	 * @param string $email
	 */
	function addUser($name = 'Some User', $username = 'someuser', $email = 'someuser@example.com') {
		$param = array(
			'id' => '',
			'name' => $name,
			'username' => $username,
			'email' => $email
		);
		$id = $this->_api->user_update($param);
		$this->_idAdded[] = $id;
	}
	
	function dispose() {
		$this->_api->user_delete($this->_idAdded);
	}
}

class TestUserAPI extends UnitTestCase {

	function __construct() {
	}
	
	function testUserCRUD_CRUDOK() {
		$api = new jsonRPCClient("http://languageforge.local/api/lf", false);
		
		// List
		$result = $api->user_list();
		$userCount = $result['count'];
		$this->assertTrue($userCount >= 0);
		
		// Create
		$param = array(
			'id' => '',
			'username' =>'SomeUser',
			'name' => 'Some User',
			'email' => 'user@example.com'
		);
		$id = $api->user_update($param);
		$this->assertNotNull($id);
		$this->assertEqual(24, strlen($id));
		
		// Read
		$result = $api->user_read($id);
		$this->assertNotNull($result['id']);
		$this->assertEqual('SomeUser', $result['username']);
		$this->assertEqual('user@example.com', $result['email']);
		
		// Update
		$result['email'] = 'other@example.com';
		$id = $api->user_update($result);
		$this->assertNotNull($id);
		$this->assertEqual($result['id'], $id);
		
		// Read back
		$result = $api->user_read($id);
		$this->assertNotNull($id);
		$this->assertEqual('other@example.com', $result['email']);
		
		// List
		$result = $api->user_list();
		$this->assertEqual($userCount + 1, $result['count']);
		
		// Delete
 		$result = $api->user_delete(array($id));
 		$this->assertEqual(1, $result);
 		
 		// List to check delete
 		$result = $api->user_list();
 		$this->assertEqual($userCount, $result['count']);
 			
	}
/*
	function testUserTypeahead_Ok() {
		$e = new UserAPITestEnvironment();
		$e->addUser('Some User');
		
		$api = new jsonRPCClient("http://languageforge.local/api/lf", false);
		$result = $api->user_typeahead('ome');
		
		$this->assertTrue($result['count'] > 0);
		
		$e->dispose();
	}
*/
}

?>