<?php
require_once(dirname(__FILE__) . '/../testconfig.php');
require_once(SIMPLETEST_PATH . 'autorun.php');
require_once(LF_BASE_PATH . "/lfbase/Loader.php");

class TestOfDashboardActivitiesDTO extends UnitTestCase {

	function testDashboardActivitiesDTO_Encode_JsonCorrect() {
		$dto= new \dto\DashboardActivitiesDTO();
		
		$dto->setActivityDate('9999-9-9');
		$dto->setDefinitionActivities(12);
		$dto->setEntryActivities(22);
		$dto->setExampleActivities(32);
		$dto->setPartOfSpeechActivities(42);
		$dto->setStatsExamplesCount(10);
		$dto->setStatsMeaningsCount(20);
		$dto->setStatsPOSCount(30);
		$dto->setStatsWordCount(40);		
		
		$result = json_encode($dto->encode());
		$this->assertEqual($result, '{"entryActivities":22,"exampleActivities":32,"partOfSpeechActivities":42,"definitionActivities":12,"activityDate":"9999-9-9","statsWordCount":40,"statsPos":30,"statsMeanings":20,"statsExamples":10}');
	}

}

?>