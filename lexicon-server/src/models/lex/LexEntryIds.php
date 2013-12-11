<?php
namespace models\lex;

use models\mapper\ArrayOf;

class LexEntryId {

	/**
	 * @var string
	 */
	public $id;
	
	/**
	 * @var string
	 */
	public $mercurialSha;
	
}

class LexEntryIds extends ArrayOf {

	public function __construct() {
		parent::__construct(
			function($data) {
				return new LexEntryId();
			}
		);
	}
	
}

?>
