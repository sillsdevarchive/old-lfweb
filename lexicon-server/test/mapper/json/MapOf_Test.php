<?php

use models\mapper\JsonEncoder;
use models\mapper\JsonDecoder;
use models\mapper\MapOf;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class TestJsonMapOfModel {
	function __construct() {
		$this->values = new MapOf();
	}
	
	public $values;
}

class TestJsonMapOfMapper extends UnitTestCase {

	function __construct() {
	}
	
	function testEncodeDecode_Same() {
		$model = new TestJsonMapOfModel();
		$model->values['key1'] = '1';
		$model->values['key2'] = '2';
		$encoded = JsonEncoder::encode($model);
		$this->assertIsA($encoded['values'], 'array');
		
		echo "<pre>";
//   		var_dump($encoded);
		echo "</pre>";
  				
		$otherModel = new TestJsonMapOfModel();
		JsonDecoder::decode($otherModel, $encoded);
		$this->assertEqual($model->values, $otherModel->values);
	}
	
}

?>
