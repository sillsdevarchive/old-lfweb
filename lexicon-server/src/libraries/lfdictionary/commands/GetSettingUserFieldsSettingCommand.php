<?php
namespace libraries\lfdictionary\commands;
use \libraries\lfdictionary\mapper\FieldSettingXmlJsonMapper;

require_once(dirname(__FILE__) . '/../Config.php');

use \libraries\lfdictionary\environment\LexiconProjectEnvironment;
class GetSettingUserFieldsSettingCommand {

	/**
	 * @var JSON
	 */
	var $_result;

	/**
	 * @param string $projectPath
	 */
	var $_projectPath;

	/**
	 * @param string
	 */
	var $_userName;

	function __construct($projectPath, $userName) {
		$this->_projectPath = $projectPath; // Path to the selected project
		$this->_userName = $userName;
	}

	function execute() {
		$this->processFile();
		return $this->_result;
	}

	function processFile() {
		$configFilePath = LexiconProjectEnvironment::locateConfigFilePath($this->_projectPath, $this->_userName);
		$xml_str = file_get_contents($configFilePath);
		$doc = new \DOMDocument;
		$doc->preserveWhiteSpace = FALSE;
		$doc->loadXML($xml_str);
		$componentsDoc = new \DomDocument;
		$componentsDoc->appendChild($componentsDoc->importNode($doc->getElementsByTagName("fields")->item(0), true));
		$this->_result = FieldSettingXmlJsonMapper::encodeFieldXmlToJson($componentsDoc);
	}


};

?>
