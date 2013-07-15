<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

require_once(dirname(__FILE__) . '/../MockObject/AllMockObjects.php');

class TestOfProjectDTO extends UnitTestCase {

	function testEncode_ListAddProject_JsonCorrect() {
		$project = new \lfbase\dto\ProjectDTO(new ProjectModelMockObject());
			
		$result = json_encode($project->encode());
		$this->assertEqual('{"id":1,"name":"name","title":"title","type":"dictionary","lang":"fr"}', $result);
		
		$ProjectListDTO = new \lfbase\dto\ProjectListDTO();
		$ProjectListDTO->addListProject($project);
		$result = json_encode($ProjectListDTO->encode());
		
		$this->assertEqual('{"List":[{"id":1,"name":"name","title":"title","type":"dictionary","lang":"fr"}]}', $result);
	}
}

?>