<?php

use models\mapper\MongoEncoder;
use models\mapper\MongoDecoder;
use models\mapper\ArrayOf;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(TEST_PATH . 'common/MongoTestEnvironment.php');

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
	}
	
	function testOffsetSet_IndexWithString_ExceptionExpected() {
		$e = new MongoTestEnvironment();
		$model = new TestMongoArrayOfModel();
		$model->values[] = 'value1';
		
		$model->values[0] = 'update1';
		$e->inhibitErrorDisplay();
		$this->expectException();
		$model->values['someKey'] = 'update2';
		$e->restoreErrorDisplay();
	}
	
	function testOffsetGet_IndexWithString_ExceptionExpected() {
		$e = new MongoTestEnvironment();
		$model = new TestMongoArrayOfModel();
		$model->values[] = 'value1';
		
 		$result = $model->values[0];
		$e->inhibitErrorDisplay();
		$this->expectException();
 		$result = $model->values['someKey'];
		$e->restoreErrorDisplay();
	}
	
}

?>
