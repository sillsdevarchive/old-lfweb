<?php

namespace libraries\sf;

class MongoMapper extends MapperBase
{
	/**
	 * @var MongoDB
	 */
	protected $_db;

	/**
	 * @var MongoCollection
	 */
	protected $_collection;
	
	/**
	 * @var string
	 */
	private $_idKey;

	/**
	 * @param string $database
	 * @param string $collection
	 * @param string $idKey defaults to id
	 */
	protected function __construct($database, $collection, $idKey = 'id')
	{
		$this->_db = MongoStore::connect($database);
		$this->_collection = $this->_db->$collection;
		$this->_idKey = $idKey;
	}
	
	/**
	 * Private clone to prevent copies of the singleton.
	 */
	private function __clone()
	{
	}

	/**
	 * Returns the name of the database.
	 * @return string
	 */
	public function databaseName() {
		return (string)$this->_db;
	}
	
	public function readList($model, $query, $fields = array(), $sortFields = array(), $limit = 0)
	{
		$cursor = $this->_collection->find($query, $fields);
		
		if (count($sortFields)>0)
		{
			$cursor = $cursor->sort($sortFields);
		}
		
		if ($limit>0)
		{
			$cursor = $cursor->limit($limit);
		}
		
		$model->entries = array();
		foreach ($cursor as $item) {
			$id = strval($item['_id']);
			$item[$this->_idKey] = $id;
			unset($item['_id']);
			$model->entries[] = $item;
		}
	}
	

	public function findOneByQuery($model, $query, $fields = array())
	{
		$data = $this->_collection->findOne($query, $fields);
		if ($data === NULL)
		{
			return;
		}
		try {
			$this->decode($model, $data);
		} catch (\Exception $ex) {
			throw new \Exception("Exception thrown while reading", $ex->getCode(), $ex);
		}
	}
	
	/**
	 * @param Object $model
	 * @param string $id
	 */
	public function read($model, $id)
	{
		if (!is_string($id) || empty($id)) {
			$type = get_class($id);
			throw new \Exception("Bad id '$id' ($type)");
		}		
		$data = $this->_collection->findOne(array("_id" => new \MongoId($id)));
		if ($data === NULL)
		{
			throw new \Exception("Could not find id '$id'");
		}
		try {
			$this->decode($model, $data);
		} catch (\Exception $ex) {
			throw new \Exception("Exception thrown while reading '$id'", $ex->getCode(), $ex);
		}
	}
	
	public function write($model)
	{
		$data = $this->encode($model);
		return $this->update($this->_collection, $data, $model->id);
	}

	/**
	 * Sets the public properties of $model to values from $values[propertyName]
	 * @param object $model
	 * @param array $data
	 */
	public function decode($model, $data)
	{
		$properties = get_object_vars($model);
		$idKey = $this->_idKey;
		// Map the Mongo _id to the property $idKey
		if (array_key_exists($idKey, $properties))
		{
			$model->$idKey = (string)$data['_id']; // MongoId
			unset($properties[$idKey]);
		}
		foreach ($properties as $key => $value)
		{
			if (!array_key_exists($key, $data))
			{
				// oops // TODO Add to list, throw at end CP 2013-06
				continue;
			}
			if (is_a($value, 'libraries\sf\ReferenceList')) {
				$this->decodeReferenceList($model->$key, $data[$key]);
			} else {
				$model->$key = $data[$key];
			}
		}
	}
	
	/**
	 * Decodes the mongo array into the ReferenceList $model
	 * @param ReferenceList $model
	 * @param array $data
	 * @throws \Exception
	 */
	public function decodeReferenceList($model, $data) {
		$model->refs = array();
		if (array_key_exists('refs', $data)) {
			// This is bogus data who put that here.
			throw new \Exception(
				"Bad refs structure 'refs'"
			);
		}
		$refsArray = $data;
		foreach ($refsArray as $objectId) {
			if (!is_a($objectId, 'MongoId')) {
				throw new \Exception(
					"Invalid type '" . gettype($objectId) . "' in ref collection '$key'"
				);
			}
			array_push( $model->refs, (string)$objectId );
		}
	}

	/**
	 * Sets key/values in the array from the public properties of $model
	 * @param object $model
	 * @return array
	 */
	public function encode($model)
	{
		$data = array();
		$properties = get_object_vars($model);
		$idKey = $this->_idKey;
		// We don't want the 'idKey' in the data so remove that from the properties
		if (array_key_exists($idKey, $properties))
		{
			unset($properties[$idKey]);
		}
		foreach ($properties as $key => $value)
		{
			if (is_a($value, 'libraries\sf\ReferenceList')) {
				$data[$key] = $this->encodeReferenceList($model->$key);
			} else {
				if ($key == 'projects' || $key == 'users') {
					throw new \Exception("Possible bad write of '$key'\n" . var_export($model, true));
				}
				$data[$key] = $value;
			}
		}
		return $data;
	}
	
	public function encodeReferenceList($model) {
		$result = array_map(
			function($id) {
				return new \MongoId($id);
			},
			$model->refs
		);
		return $result;
	}

	public function remove($id)
	{
		if (!is_string($id) || empty($id)) {
			throw new \Exception("Bad id '$id'");
		}
// 		assert(is_string($id) && !empty($id));
		$result = $this->_collection->remove(
			array('_id' => new \MongoId($id)),
			array('safe' => true)
		);
		return $result;
	}
	
	/**
	 *
	 * @param MongoCollection $collection
	 * @param array $data
	 * @param MongoId $id
	 * @return MongoId
	 */
	protected function update($collection, $data, $id)
	{
		if (!$id) {
			$id = NULL;
		}
		assert($id === NULL || is_string($id));
		$result = $collection->update(
				array('_id' => new \MongoId($id)),
				array('$set' => $data),
				array('upsert' => true, 'multiple' => false, 'safe' => true)
		);
		return isset($result['upserted']) ? $result['upserted'].$id : $id;
	}

}

?>