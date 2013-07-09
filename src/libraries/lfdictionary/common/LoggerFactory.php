<?php
namespace libraries\lfdictionary\common;


class LoggerFactory
{
	static function getLogger()
	{
		$fileLogger = new Logger\FileLogger();
		$stack = new Stack(array(
				'loggers' => array($fileLogger)
		));
		return $stack;
	}
}
?>