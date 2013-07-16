<?php
namespace libraries\lfdictionary\dto;

class Sense {
	
	/**
	 * @var MultiText
	 */
	var $_definition;
	
	/**
	 * @var string
	 */
	var $_partOfSpeech;
	
	/**
	* @var string
	*/
	var $_semanticDomainName;
	
	/**
	* @var string
	*/
	var $_semanticDomainValue;
	
	/**
	 * @var array<Example>
	 */
	var $_examples;
	
	function __construct() {
		$this->_examples = array();
		$this->_definition = \lfbase\dto\MultiText::create();
		$this->_partOfSpeech = '';
	}
	
	/**
	 * @return MultiText
	 */
	function getDefinition() {
		return $this->_definition;
	}
	
	/**
	 * @param MultiText $multitext
	 */
	function setDefinition($multiText) {
		$this->_definition = $multiText;
	}
	
	/**
	 * @return string
	 */
	function getPartOfSpeech() {
		return $this->_partOfSpeech;
	}
	
	/**
	 * @param string $partOfSpeech
	 */
	function setPartOfSpeech($partOfSpeech) {
		$this->_partOfSpeech = $partOfSpeech;
	}
	
	/**
	* @return string
	*/
	function getSemanticDomainName() {
		return $this->_semanticDomainName;
	}
	
	/**
	 * @param string $semanticDomainName
	 */
	function setSemanticDomainName($semanticDomainName) {
		$this->_semanticDomainName = $semanticDomainName;
	}
	
	/**
	* @return string
	*/
	function getSemanticDomainValue() {
		return $this->_semanticDomainValue;
	}
	
	/**
	 * @param string $semanticDomainValue
	 */
	function setSemanticDomainValue($semanticDomainValue) {
		$this->_semanticDomainValue = $semanticDomainValue;
	}
	
	
	/**
	 * @param Example $example
	 */
	function addExample($example) {
		$this->_examples[] = $example;
	}
	
	/**
	 * @return int
	 */
	function exampleCount() {
		return count($this->_examples);
	}
	
	/**
	 * @param int $index
	 * @return Example
	 */
	function getExample($index) {
		return $this->_examples[$index];
	}
	
	function encode() {
		$examples = array();	
		
			foreach ($this->_examples as $example) {
				$examples[] = $example->encode();
			}		
		
		$definition = $this->_definition->encode();
		
			return array(
				"definition" => $definition,
				"POS" => $this->_partOfSpeech,
				"examples" => $examples,
				"SemDomValue"  => $this->_semanticDomainValue,
				"SemDomName"  => $this->_semanticDomainName
				);		
	}
	
	function decode($value) {
	
		$this->_definition = \lfbase\dto\MultiText::createFromArray($value['definition']);
		$this->_partOfSpeech = $value['POS'];
		$this->_semanticDomainValue= $value['SemDomValue'];
		$this->_semanticDomainName= $value['SemDomName'];
		foreach ($value['examples'] as $exampleValue) {
			$example = Example::createFromArray($exampleValue);
			$this->addExample($example);
		}
	}
	
	/**
	 * @return Sense
	 */
	static function create() {
		return new Sense(); // Kind of pointless, but it fits the pattern we are using for other model classes. CP 2011-06
	}
	
	/**
	 * @return Sense
	 */
	static function createFromArray($value) {
		$result = new Sense();
		$result->decode($value);
		return $result;
	}
	
}

?>