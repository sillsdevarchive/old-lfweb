<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_LIBRARY_PATH . "/lfbase/Loader.php");

class TestOfConversationDTO extends UnitTestCase {

	function testConversationDTOEncode_ReturnsCorrectJson() {
		$rootDto = new \lfbase\dto\ConversationDTO();
		$rootDto->setClass("TestClassRoot");
		$rootDto->setReference("TestRef");
		$rootDto->setGuid("00000000-1111-2222-3333-444444444444");
		$rootDto->setAuthor("tester");
		$rootDto->setDate(9000);
		$rootDto->setComment("This is a test question");
		$rootDto->setStatus("");
		
		$childDto = new \lfbase\dto\ConversationDTO();
		$childDto->setClass("TestClass");
		$childDto->setReference("");
		$childDto->setGuid("88888888-1111-2222-3333-444444444444");
		$childDto->setAuthor("bug");
		$childDto->setDate(8000);
		$childDto->setComment("This is a test comment");
		$childDto->setStatus("Close");
		
		$rootDto->add($childDto);
		$childDto->setParent($rootDto);
		$result = json_encode($rootDto->encode());
		$this->assertEqual("{\"conclass\":\"TestClassRoot\",\"guid\":\"00000000-1111-2222-3333-444444444444\",\"ref\":\"TestRef\",\"author\":\"tester\",\"date\":9000,\"comment\":\"This is a test question\",\"status\":\"\",\"children\":[{\"conclass\":\"TestClass\",\"guid\":\"88888888-1111-2222-3333-444444444444\",\"ref\":\"\",\"author\":\"bug\",\"date\":8000,\"comment\":\"This is a test comment\",\"status\":\"Close\",\"children\":[]}]}", $result);
		$this->assertEqual($rootDto->_guid, $childDto->getParent()->_guid);
	}

	function testConversationListDTOEncode_ReturnsCorrectJson() {

		$conversationListDTO = new \lfbase\dto\ConversationListDTO();
		
		
		$rootDto = new \lfbase\dto\ConversationDTO();
		$rootDto->setClass("TestClassRoot");
		$rootDto->setReference("TestRef");
		$rootDto->setGuid("00000000-1111-2222-3333-444444444444");
		$rootDto->setAuthor("tester");
		$rootDto->setDate(9000);
		$rootDto->setComment("This is a test question");
		$rootDto->setStatus("");
		
		$childDto = new \lfbase\dto\ConversationDTO();
		$childDto->setClass("TestClass");
		$childDto->setReference("");
		$childDto->setGuid("88888888-1111-2222-3333-444444444444");
		$childDto->setAuthor("bug");
		$childDto->setDate(8000);
		$childDto->setComment("This is a test comment");
		$childDto->setStatus("Close");
		
		$rootDto->add($childDto);
		$childDto->setParent($rootDto);
		$conversationListDTO->addConversation($rootDto);
		$result = json_encode($conversationListDTO->encode());
		$this->assertEqual("{\"entries\":[{\"conclass\":\"TestClassRoot\",\"guid\":\"00000000-1111-2222-3333-444444444444\",\"ref\":\"TestRef\",\"author\":\"tester\",\"date\":9000,\"comment\":\"This is a test question\",\"status\":\"\",\"children\":[{\"conclass\":\"TestClass\",\"guid\":\"88888888-1111-2222-3333-444444444444\",\"ref\":\"\",\"author\":\"bug\",\"date\":8000,\"comment\":\"This is a test comment\",\"status\":\"Close\",\"children\":[]}]}]}", $result);
	}
}

?>