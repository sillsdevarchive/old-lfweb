<?php

namespace models\lex;

use models\mapper\Id;
use models\mapper\ArrayOf;

class TextEntry
{
	/**
	 * @var string
	 */
	public $language;
	
	/**
	 * @var string
	 */
	public $text;
	
}

class MultiText {

	public function __construct() {
		$this->entries = new ArrayOf(
			ArrayOf::OBJECT, 
			function($data) {
				return new PickItem();
			}
		);
		$this->_multitext = array();
	}

	/**
	 * Array of language => text key value pairs
	 * @var ArrayOf ArrayOf<TextEntry>
	 */
	public $entries;
	
	/**
	 * Array of language => text key value pairs
	 * @var array
	 */
	private $_multitext;
	
	public function addForm($language, $text) {
		$this->_multitext[$language] = $text;
	}
	
	public function hasForm($language) {
		return key_exists($language, $this->_multitext);
	}
	
	public function getForm($language) {
		return $this->_multitext[$language];
	}
	
	public function getAllLanguages() {
		return array_keys($this->_multitext);
	}
	
	public function getAll() {
		return $this->_multitext;
	}
	
	public static function create($language = '', $text = '') {
		$multitext = new MultiText();
		if ($language) {
			$multitext->addForm($language, $text);
		}
		return $multitext;
	}
	
}

?>