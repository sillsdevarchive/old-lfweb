<?php

use models\mapper\MongoEncoder;
use models\mapper\MongoDecoder;
use models\mapper\MapOf;

require_once(dirname(__FILE__) . '/../../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');
require_once(TEST_PATH . 'common/MongoTestEnvironment.php');

class TestMongoMapOfModel {
	function __construct() {
		$this->values = new MapOf();
	}
	
	public $values;
}

class TestMongoMapOfMapper extends UnitTestCase {

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
	
	function testOffsetSetAndGet_Create_Read() {
		$model = new TestMongoMapOfModel();
		$model->values['key1'] = 'value1';
		$this->assertEqual($model->values['key1'], 'value1');
	}
	
	function testOffsetSet_IndexWithInt_ExceptionExpected() {
		$e = new MongoTestEnvironment();
		$model = new TestMongoMapOfModel();
		$model->values['key1'] = 'value1';
		
		$e->inhibitErrorDisplay();
		$this->expectException();
 		$model->values[0] = 'update1';
		$e->restoreErrorDisplay();
	}
	
	function testOffsetSet_IndexWithNull_ExceptionExpected() {
		$e = new MongoTestEnvironment();
		$model = new TestMongoMapOfModel();
		$model->values['key1'] = 'value1';
		
		$e->inhibitErrorDisplay();
		$this->expectException();
		$model->values[] = 'value2';
		$e->restoreErrorDisplay();
	}
	
	function testOffsetGet_IndexWithInt_ExceptionExpected() {
		$e = new MongoTestEnvironment();
		$model = new TestMongoMapOfModel();
		$model->values['key1'] = 'value1';
		
		$e->inhibitErrorDisplay();
		$this->expectException();
 		$result = $model->values[0];
		$e->restoreErrorDisplay();
	}
	
}

?>
