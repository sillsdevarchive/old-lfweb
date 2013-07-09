<?php
namespace libraries\lfdictionary\dto;

/**
 * This class contains RPC API call simple result
 */
class ResultDTO {
	
	/**
	 * @var boolean
	 */
	private $_succeed;
	
	/**
	 * @param Boolean $succeed
	 */
	public function __construct($succeed) {
		$this->_succeed = $succeed;
	}
	
	/**
	 * Encodes the object into a php array, suitable for use with json_encode
	 * @return mixed
	 */
	function encode() {
		return array(
			'succeed' => $this->_succeed,
		);
		
	}
}

?>