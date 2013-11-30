<?php

namespace models\lex;

use models\mapper\Id;
use models\mapper\MapOf;

class FormEntry {
	
	/**
	 * @var string
	 */
	public $text;
	
}

class MultiText {

	public function __construct() {
		$this->entries = new MapOf(
			function($data) {
				return new FormEntry();
			}
		);
	}

	/**
	 * Map of language => text key value pairs
	 * @var MapOf MapOf<TextEntry>
	 */
	public $entries;
	
	public function updateForm($language, $text) {
		$this->entries->data[$language] = $text;
	}
	
	public function hasForm($language) {
		return key_exists($language, $this->entries->data);
	}
	
	public function getForm($language) {
		return $this->entries->data[$language];
	}
	
	public function getAllLanguages() {
		return array_keys($this->entries->data);
	}
	
	public function getAll() {
		return $this->entries->data;
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