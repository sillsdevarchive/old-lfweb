<?php
namespace commands;
use environment\LexiconProjectEnvironment;

use mapper\FieldSettingXmlJsonMapper;

require_once(dirname(__FILE__) . '/../Config.php');

class UpdateSettingUserFieldsSettingCommand
{
	/**
	 * @var array
	 */
	var $_result;

	/**
	 * @param string
	 *
	 var $_projectPath;

	 /**
	 * @param string
	 */
	var $_json;

	/**
	 * @param string
	 */
	var $_userNames;

	function __construct($projectPath, $userNames, $fields) {
		$this->_projectPath = $projectPath; // Path to the selected project
		$this->_json=$fields;
		$this->_userNames=$userNames;
	}

	function execute() {
		$this->processFile();
		return $this->_result;
	}

	function processFile() {

		$json = json_decode($this->_json);

		//apply too all in list
		foreach ($this->_userNames as &$userName) {
			$this-> persistTasks($userName,$json);
		}
		$this->_result = $json;
		return;
	}

	private function persistTasks($strName,$newSetting)
	{
		$targetFile="";
		$filePath = LexiconProjectEnvironment::userSettingsFilePath($this->_projectPath, $strName);
		$targetFile=$filePath;
		if(!file_exists($filePath)){
			$filePath = LexiconProjectEnvironment::projectDefaultSettingsFilePath($this->_projectPath);
			if(!file_exists($filePath)){
				throw new \Exception("Can not access default user profile! " .$filePath);
					
			}
		}
		$xml_str = file_get_contents($filePath);
		$doc = new \DOMDocument;
		$doc->preserveWhiteSpace = FALSE;
		$doc->loadXML($xml_str);
		FieldSettingXmlJsonMapper::updateFieldXmlFromJson($newSetting,$doc);
		$doc->save($targetFile);
	}


};

?>
