<?php

namespace models\lex;

use models\mapper\Id;

class Example {

	function __construct() {
		$this->example = new MultiText();
		$this->translation = new MultiText();
		$this->authorInfo = new AuthorInfo();
	}

	/**
	 * @var MultiText
	 */
	public $example;

	/**
	 * @var MultiText
	 */
	public $translation;

	/**
	 * @var AuthorInfo
	 */
	public $authorInfo;

	/**
	 * Create a new Example with the given example and translation.
	 * @param MultiText $example
	 * @param MultiText $translation
	 */
	static function create($example, $translation) {
		$result = new Example();
		$result->example = $example;
		$result->translation = $translation;
		return $result;
	}

}

?>