<?php
namespace libraries\lfdictionary\commands;
use mapper\TaskSettingXmlJsonMapper;

require_once(dirname(__FILE__) . '/../Config.php');

use \environment\LexiconProjectEnvironment;
use \environment\LexProjectUserSettings;

class GetSettingUserTasksSettingCommand
{

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
		$componentsDoc->appendChild($componentsDoc->importNode($doc->getElementsByTagName("tasks")->item(0), true));
		$this->_result = TaskSettingXmlJsonMapper::encodeTaskXmlToJson($componentsDoc);
	}
};

?>
