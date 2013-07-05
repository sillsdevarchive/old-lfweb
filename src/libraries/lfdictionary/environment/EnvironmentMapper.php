<?php
namespace lfbase\environment;

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
	static public function connect($environment = 'drupal7') {
		if (is_a($environment, '\lfbase\environment\IEnvironmentMapper')) {
			self::$_environment = $environment;	
			return self::$_environment;
		}
		if (self::$_environment) {
			return self::$_environment;
		}
		switch ($environment) {
			case 'drupal':
			case 'drupal7':
				self::$_environment = new Drupal7EnvironmentMapper();
				break;
			case 'drupal6':
				self::$_environment = new DrupalEnvironmentMapper();
				break;
			case 'languagedepot':
				self::$_environment = new LanguageDepotEnvironmentMapper();
				break;
			default:
				throw new \Exception("Unsupported environment '$environment'");
		}
		return self::$_environment;
	}
	
}