<?php

use models\mapper\JsonEncoder;
use models\mapper\JsonDecoder;
use models\mapper\ArrayOf;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class TestJsonArrayOfModel {
	function __construct() {
		$this->values = new ArrayOf();
	}
	
	public $values;
}

class TestJsonArrayOfMapper extends UnitTestCase {

	function testEncodeDecode_Same() {
		$model = new TestJsonArrayOfModel();
		$model->values[] = '1';
		$model->values[] = '2';
		$encoded = JsonEncoder::encode($model);
		$this->assertIsA($encoded['values'], 'array');
//  		var_dump($encoded);
		
		$otherModel = new TestJsonArrayOfModel();
		JsonDecoder::decode($otherModel, $encoded);
		$this->assertEqual($model->values, $otherModel->values);
	}
	
}

?>
