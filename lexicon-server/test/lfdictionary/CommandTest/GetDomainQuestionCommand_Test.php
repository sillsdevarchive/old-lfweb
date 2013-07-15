<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class TestOfGetDomainQuestionCommand extends UnitTestCase {

	private $FINAL_RESULT= '{"guid":"79ebb5ce-f0fd-4fb5-9f22-1fa4965a555b","description":"Use this domain for general words referring to bodies of water.","questions":["(1) What general words refer to a body of water?","(2) What words describe something belonging to the water?"],"exampleWords":["body of water, hydrosphere","marine, oceanic, riverine"],"exampleSentences":["",""]}';

	function testListCommand() {
		$command = new \commands\GetDomainQuestionCommand(TestPath. "data/domain-test-th.xml", "en","79ebb5ce-f0fd-4fb5-9f22-1fa4965a555b");
		$result = json_encode($command->execute()->encode());
		$this->assertEqual($this->FINAL_RESULT, $result);
	}
}

?>