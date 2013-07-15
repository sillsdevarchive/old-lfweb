<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

//require_once(SOURCE_PATH . 'environment/CommunityModel.php');
require_once(TestPath . 'EnvironmentTest/DrupalTestEnvironment.php');
DrupalTestEnvironment::setDrupalTestDataConnection();
\lfbase\common\LFDrupal::loadDrupal();

class TestOfCommunityModule extends UnitTestCase {
	
	function __destruct() {
		DrupalTestEnvironment::revertBackTestDataConnection();
	}
	
	function testsearchCommunity() {
		
		$db = new DrupalTestEnvironment();
		$db->import();
		
		$projectId = 87;		
		$communityModel = new \lfbase\environment\CommunityModel($projectId);		
		
		//SearchUser
		$string = 'tel';
		$result = $communityModel->searchCommunity($string, 10);
		$output = json_encode($result->encode());
		
		$this->assertEqual('{"List":[{"CommunityId":"86","CommunityName":"Telugu"}]}', $output);
		
		$db->dispose();
	}
	
	function testaddProject() {
		
		$db = new DrupalTestEnvironment();
		$db->import();
		
		$projectId = 87;
		$communityModel = new \lfbase\environment\CommunityModel($projectId);		
		
		$db->setDrupalConnection(); //Drupal Database connection
		
		//Add Community
		$newCommunity = array('title'=>"malayalam", 'uid'=>'3', 'code'=>'ma');		
		$result = $communityModel->addCommunity($newCommunity);
		$this->assertTrue($result);
		
		$result = $communityModel->listCommunities(0, 2);
		$output = json_encode($result->encode());
		$this->assertEqual('{"List":[{"CommunityId":"86","CommunityName":"Telugu"},{"CommunityId":"90","CommunityName":"Thai"}]}', $output);
		
		$db->closeDrupalConnection(); // set back to original
		
		$db->dispose();
	}
}

?>