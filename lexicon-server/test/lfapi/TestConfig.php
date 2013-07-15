<?php

$rootPath = realpath(dirname(__FILE__) . '/../../') . '/';

//define('TestMode', true);

define('TEST_PATH', $rootPath . 'test/lfapi/');
define('TEST_LIB_PATH', $rootPath . 'test/lib/');
define('SIMPLETEST_PATH', $rootPath . 'test/lib/simpletest/');
define('SOURCE_PATH', $rootPath . 'src/');

// Fake some CodeIgniter path defines
define('APPPATH', $rootPath . 'src/');
define('BASEPATH', $rootPath . 'lib/CodeIgniter_2.1.3/system/');

require_once(APPPATH . 'helpers/loader_helper.php');

define('LF_DATABASE', 'languageforge_test');
define('LF_TESTPROJECT', 'Test Project');

?>