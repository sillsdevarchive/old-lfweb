<?php

namespace commands;
require_once(dirname(__FILE__) . '/../Config.php');


class GatherWordCommand
{
	/**
	 * @var String
	 */
	var $gatherWords;

	/**
	 * @var array
	 */
	var $exitWordsArr;

	/**
	 * @var array
	 */
	var $newWordsArr;

	/**
	 * @var bool
	 */
	var $_result;

	/**
	 * @var String
	 */
	var $lang;
	
	/**
	* @var Int
	*/
	var $_addedCount;

	/**
	 * @param string $filePath
	 * @param mixed $dtoEncoded
	 */
	function __construct($filePath, $language, $exitWordsArr, $gatherwords) {
		$this->_filePath = $filePath;
		$this->gatherWords = $gatherwords;
		$this->exitWordsArr = $exitWordsArr;
		$this->lang = $language;

	}

	function execute() {
		$this->_addedCount=0;
		$this->processFile();
		return $this->_addedCount;
	}

	function processFile() {
		$this->gatherWords=urldecode($this->gatherWords);
		// remove exist
		$this->newWordsArr=array_diff(\lfbase\common\WordsParser::parsingToArray($this->gatherWords) ,$this->exitWordsArr);

		if (count($this->newWordsArr)>0){

			$now = \mapper\LiftUpdater::now();
			$filePath = \mapper\LiftUpdater::updateFilePath($this->_filePath, $now);
			$rootXml = new \SimpleXMLElement('<lift />');
			// loop words array to add text
			foreach ($this->newWordsArr as $results) {
				if ($wordEntry=trim($results)!=""){
					$entryXml = $rootXml->addChild('entry');
					$entryXml['dateCreated'] = $entryXml['dateModified'] = gmdate("Y-m-d\TH:i:s\Z");
					$entryXml['guid'] = \lfbase\common\UUIDGenerate::uuid_generate_php();
					$entryXml['id'] = $results . "_" . $entryXml['guid'];

					$ChildUnitXml=$entryXml->addChild('lexical-unit');
					$ChildForm=$ChildUnitXml->addChild('form');
					$ChildForm->addAttribute('lang', $this->lang);
					$ChildForm->addChild('text',$results);
					$this->_addedCount+=1;
				}
			}
			$this->_result = $rootXml->saveXML($filePath) ;
		}
	}
};

?>