<?php

$rootPath = realpath(dirname(__FILE__) . '/../') . '/';

define('TestMode', true);

define('TEST_PATH', $rootPath . 'test/');
define('TestLibPath', $rootPath . 'test/lib/');
define('SimpleTestPath', $rootPath . 'test/lib/simpletest/');
define('SourcePath', $rootPath . 'src/');
define('DicTestPath', $rootPath . 'test/lfdictionary/');

define('LF_DATABASE', 'languageforge_test');
define('LF_TESTPROJECT', 'Test Project');
define('LF_TESTPROJECT2', 'Test Project2');

// Fake some CodeIgniter path defines
define('APPPATH', $rootPath . 'src/');
define('BASEPATH', $rootPath . 'lib/CodeIgniter_2.1.3/system/');
define('SIMPLETEST_PATH', $rootPath . 'test/lib/simpletest/');
define('LF_BASE_PATH', $rootPath . 'lib/CodeIgniter_2.1.3/system/core/');

require_once(APPPATH . 'helpers/loader_helper.php');
require_once(APPPATH . 'vendor/autoload.php');
require_once(APPPATH . 'libraries/lfdictionary/Config.php');
require_once(APPPATH . 'config/lf_config.php');

?>