<?php
$rootPath = dirname(__FILE__) . '/../';

if (!defined('SOURCE_PATH')) {
	define('SOURCE_PATH', $rootPath);
}

if (!defined('LF_LIBRARY_PATH')) {
	define('LF_LIBRARY_PATH', SOURCE_PATH);
}

require_once(LF_LIBRARY_PATH . 'Config.php');



?>