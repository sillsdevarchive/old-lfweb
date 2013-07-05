<?php
$rootPath = dirname(__FILE__) . '/../';

if (!defined('SOURCE_PATH')) {
	define('SOURCE_PATH', $rootPath);
}

if (!defined('LF_BASE_PATH')) {
	define('LF_BASE_PATH', SOURCE_PATH);
}

require_once(LF_BASE_PATH . 'Config.php');



?>