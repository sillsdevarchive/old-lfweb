<?php

namespace models\lex;

use models\mapper\Id;
use models\mapper\ArrayOf;

class Sense {

	function __construct() {
		$this->id = new Id();
		$this->definition = MultiText::create();
		$this->partOfSpeech = '';
		$this->examples = new ArrayOf(ArrayOf::OBJECT, function($data) {
			return new Example();
		});
		$this->authorInfo = new AuthorInfoModel();
	}

	/**
	 *
	 * @var String
	 */
	public $id;
	
	/**
	 * @var MultiText
	 */
	public $definition;

	/**
	 * @var string
	 */
	public $partOfSpeech;

	/**
	 * @var string
	 */
	public $semanticDomainName;

	/**
	 * @var string
	 */
	public $semanticDomainValue;

	/**
	 * @var ArrayOf ArrayOf<Example>
	 */
	public $examples;

	/**
	 *
	 * @var AuthorInfoModel
	 */
	public $authorInfo;

}

?>
