<?php

$rootPath = dirname(__FILE__) . '/';
define('TestMode', true);

define('TestPath', $rootPath);
define('SimpleTestPath', $rootPath . 'simpletest/');

//defining database connection variables as constants
define('DB_SERVER', 'localhost');
define('DB_USER', 'lfweb7');
define('DB_PASS', '123456');
define('DB_NAME', 'lfweb7_test');

if (file_exists('/home/bob')) {
	// Build server
	define('LF_BASE_PATH', '/home/bob/agent/work/lfdrupal7/lfbase/lfbase-server/src/');
} else if (file_exists($rootPath . '../../../LFBase/lfbase-server/src')) {
	// Eclipse debugging in the dev environment
	define('LF_BASE_PATH', realpath($rootPath . '../../../LFBase/lfbase-server/src') . '/');
} else {
	throw new \Exception('Could not find a suitable LF_BASE_PATH');
}

error_log(sprintf("TestConfig: Using LF_BASE_PATH '%s'", LF_BASE_PATH));

require_once(TestPath . '../src/Config.php');

?>
