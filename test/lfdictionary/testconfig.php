<?php

$rootPath = dirname(__FILE__) . '/';

define('TestMode', true);

define('TestPath', $rootPath);
define('SimpleTestPath', $rootPath . 'simpletest/');
define('SOURCE_PATH', $rootPath . '../src/');

//defining database connection variables as constants
define('DB_SERVER', 'localhost');
define('DB_USER', 'lfweb7');
define('DB_PASS', '123456');
define('DB_NAME', 'lfweb7');

define('LD_DB_SERVER', 'localhost');
define('LD_DB_USER', 'ldweb');
define('LD_DB_PASS', 'depotsky5');
define('LD_DB_NAME', 'redmine_default');

define('LF_BASE_PATH', $rootPath . '../src/');

require_once(SOURCE_PATH . 'Config.php');

?>