<?php

namespace models\lex;

use models\mapper\MapOf;

class FormEntry {
	
	public function __construct($text = '') {
		$this->text = $text;
	}
	
	/**
	 * @var string
	 */
	public $text;
	
}

class MultiText extends MapOf {

	public function __construct() {
		parent::__construct(
			function($data) {
				return new FormEntry();
			}
		);
	}

	public function updateForm($language, $text) {
		$this->data[$language] = new FormEntry($text);
	}
	
	public function hasForm($language) {
		return key_exists($language, $this->data);
	}
	
	public function getForm($language) {
		return $this->data[$language]->text;
	}
	
	public function getAllLanguages() {
		return array_keys($this->data);
	}
	
	public function getAll() {
		return $this->data;
	}
	
	public static function create($language = '', $text = '') {
		$multitext = new MultiText();
		if ($language) {
			$multitext->updateForm($language, $text);
		}
		return $multitext;
	}
	
}

?>