<?php

$rootPath = dirname(__FILE__) . '/';

define('TestMode', true);

define('TEST_PATH', $rootPath);
define('SIMPLETEST_PATH', $rootPath . 'simpletest/');
define('SOURCE_PATH', $rootPath . '../src/');

define('LF_BASE_PATH', $rootPath . '../src/');

require_once(SOURCE_PATH . 'Config.php');

?>