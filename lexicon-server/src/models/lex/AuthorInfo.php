<?php

namespace models\lex;

/**
 * This class contains author information for the lex entry element and it sub-elements
 */
class AuthorInfo {

	public function __construct() {
		$this->createdbyId = "";
		$this->createdby = "";
		$this->createdDate = 0;
		$this->modifiedById = "";
		$this->modifiedBy = "";
		$this->modifiedDate = 0;
	}
	
	/**
	 * user's Id as string
	 * @var String
	 */
	public $createdbyId;
	
	/**
	 * user's name as string
	 * @var String
	 */
	public $createdby;
	
	/**
	 *	datetime as timestamp
	 * @var int
	 */
	public $createdDate;
	
	/**
	 * user's Id as string
	 * @var String
	 */
	public $modifiedById;
	
	/**
	 * user's name as string
	 * @var String
	 */
	public $modifiedBy;
	
	/**
	 * datetime as timestamp
	 * @var int
	 */
	public $modifiedDate;
	
}

?>