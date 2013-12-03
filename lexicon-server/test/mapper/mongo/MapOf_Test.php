<?php

use models\mapper\MongoEncoder;
use models\mapper\MongoDecoder;
use models\mapper\MapOf;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class TestMongoMapOfModel {
	function __construct() {
		$this->values = new MapOf();
	}
	
	public $values;
}

class TestMongoMapOfMapper extends UnitTestCase {

	function __construct() {
	}
	
	function testEncodeDecode_Same() {
		$model = new TestMongoMapOfModel();
		$model->values['key1'] = '1';
		$model->values['key2'] = '2';
		$encoded = MongoEncoder::encode($model);
		$this->assertIsA($encoded['values'], 'array');
		
		echo "<pre>";
//   		var_dump($encoded);
		echo "</pre>";
				
		$otherModel = new TestMongoMapOfModel();
		MongoDecoder::decode($otherModel, $encoded);
		$this->assertEqual($model->values, $otherModel->values);
	}
	
}

?>
