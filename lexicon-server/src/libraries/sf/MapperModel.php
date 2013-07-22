<?php

namespace libraries\sf;

class MapperModel /*extends CI_Model*/
{
	protected $_mapper;

	protected function __construct($mapper, $id = NULL)
	{
		$this->_mapper = $mapper;
		if (!empty($id))
		{
			$this->_mapper->read($this, $id);
		}
	}
	
	public function findOneByQuery($query, $fields = array())
	{
		return $this->_mapper->findOneByQuery($this, $query, $fields = array());
	}
	
	/**
	 * Reads the model from the mongo collection
	 * @see MongoMapper::read()
	 */
	function read()
	{
		return $this->_mapper->read($this, $this->id);
	}
	
	/**
	 * Writes the model to the mongo collection
	 * @return string The unique id of the object written
	 * @see MongoMapper::write()
	 */
	function write()
	{
		$this->id = $this->_mapper->write($this); 
		return $this->id;
	}
}

?>