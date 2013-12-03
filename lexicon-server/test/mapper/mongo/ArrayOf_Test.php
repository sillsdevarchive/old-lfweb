<?php

use models\mapper\MongoEncoder;
use models\mapper\MongoDecoder;
use models\mapper\ArrayOf;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

class TestMongoArrayOfModel {
	function __construct() {
		$this->values = new ArrayOf();
	}
	
	public $values;
}

class TestMongoArrayOfMapper extends UnitTestCase {

	function __construct() {
	}
	
	function testEncodeDecode_Same() {
		$model = new TestMongoArrayOfModel();
		$model->values[] = '1';
		$model->values[] = '2';
		$encoded = MongoEncoder::encode($model);
		$this->assertIsA($encoded['values'], 'array');
//  		var_dump($encoded);
		
		$otherModel = new TestMongoArrayOfModel();
		MongoDecoder::decode($otherModel, $encoded);
		$this->assertEqual($model->values, $otherModel->values);
		
// 		var_dump($iso8601);
		
	}
	
}

?>