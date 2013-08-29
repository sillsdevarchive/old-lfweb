<?php
namespace libraries\lfdictionary\environment;

class EnvironmentMapper {
	
	/**
	 * @var IEnvironmentMapper
	 */
	static private $_environment;
	
	/**
	 * @param string|IEnvironmentMapper $environment
	 * @return IEnvironmentMapper
	 * @throws \Exception
	 */
	static public function connect($environment = 'languageforge') {
		if (is_a($environment, '\libraries\lfdictionary\environment\IEnvironmentMapper')) {
			self::$_environment = $environment;	
			return self::$_environment;
		}
		if (self::$_environment) {
			return self::$_environment;
		}
		switch ($environment) {
			case 'languageforge':
				self::$_environment = new MongoDBEnvironmentMapper();
				break;
			default:
				throw new \Exception("Unsupported environment '$environment'");
		}
		return self::$_environment;
	}
	
}