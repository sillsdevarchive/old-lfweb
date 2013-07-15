<?php
require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');

require_once(TEST_LIB_PATH . 'jsonRPCClient.php');

class TestProjectAPI extends UnitTestCase {

	function __construct() {
	}
	
	function testProjectCRUD_CRUDOK() {
		$api = new jsonRPCClient("http://languageforge.local/api/lf", false);
		
		// List
		$result = $api->project_list();
		$projectCount = $result['count'];
		$this->assertTrue($projectCount >= 0);
		
		// Create
		$param = array(
			'id' => '',
			'projectname' =>'SomeProject',
			'language' => 'SomeLanguage'
		);
		$id = $api->project_update($param);
		$this->assertNotNull($id);
		$this->assertEqual(24, strlen($id));
		
		// Read
		$result = $api->project_read($id);
		$this->assertNotNull($result['id']);
		$this->assertEqual('SomeProject', $result['projectname']);
		$this->assertEqual('SomeLanguage', $result['language']);
		
		// Update
		$result['language'] = 'AnotherLanguage';
		$id = $api->project_update($result);
		$this->assertNotNull($id);
		$this->assertEqual($result['id'], $id);
		
		// Read back
		$result = $api->project_read($id);
		$this->assertNotNull($id);
		$this->assertEqual('AnotherLanguage', $result['language']);
		
		// List
		$result = $api->project_list();
		$this->assertEqual($projectCount + 1, $result['count']);
		
		// Delete
 		$result = $api->project_delete(array($id));
 		$this->assertTrue($result);
		
		// List to check delete
		$result = $api->project_list();
		$this->assertEqual($projectCount, $result['count']);
	}
		
}

?>