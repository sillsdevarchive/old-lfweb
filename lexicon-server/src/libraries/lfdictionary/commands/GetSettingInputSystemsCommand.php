<?php
namespace libraries\lfdictionary\commands;
use libraries\lfdictionary\mapper\InputSystemXmlJsonMapper;


class GetSettingInputSystemsCommand
{

	/**
	 * @var array
	 */
	var $_result;

	/**
	 * @param string $projectPath
	 */
	var $_projectPath;

	function __construct($projectPath) {
		$this->_projectPath = $projectPath; // Path to the selected project
	}

	function execute() {
		$this->processFile();
		return $this->_result;
	}

	function processFile() {
		$ldmls = array();
		$writingSystemsPath = $this->_projectPath. WRITING_SYSTEMS_DIR;
		if ((file_exists($writingSystemsPath) && is_dir($writingSystemsPath)))
		{
			$filesPath = glob($writingSystemsPath ."*.ldml");
			foreach ($filesPath as &$filePath) {
				$xml_str = file_get_contents($filePath);
				$doc = new \DOMDocument;
				$doc->preserveWhiteSpace = FALSE;
				$doc->loadXML($xml_str);
				$ldmls[] = InputSystemXmlJsonMapper::encodeInputSystemXmlToJson($doc);
			}
		} else {
			throw new \Exception(sprintf("Cannot access writing systems in '%s'", $writingSystemsPath));
		}
		$this->_result = array(
			"list" => $ldmls
		);
	}

};

?>
