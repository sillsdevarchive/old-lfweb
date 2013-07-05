<?php

$rootPath = dirname(__FILE__) . '/';

if (!defined('SOURCE_PATH')) {
	define('SOURCE_PATH', $rootPath);
}

if(!defined('TestMode')) {
	//defining database connection variables as constants
	define('DB_SERVER', 'localhost');
	define('DB_USER', 'lfweb7');
	define('DB_PASS', '123456');
	define('DB_NAME', 'lfweb7');
	define('IS_DEV_MODE', 0);	
	define('SHOW_ERROR_DETAIL_CLIENT', 1);
}

//Working directory for Drupal root
define('DrupalPath', '/var/www/languageforge.org_dev7/web/');

if (!defined('LF_BASE_PATH')) {
	define('LF_BASE_PATH', DrupalPath . 'lf/');
}

define('LANGUAGEFORGE_VAR_PATH', '/var/lib/languageforge/');

define('LANGUAGEFORGE_LOG_PATH', '/tmp/');

//Language Depot Database name
define('LANG_DEPOT_DB_NAME', 'languagedepot');

// the per use setting file extenstion.
define('LANGUAGE_FORGE_SETTINGS_EXTENSION', '.WeSayConfig');

// the default setting file for user
// Do not use a file name with extension as LANGUAGE_FORGE_SETTINGS_EXTENSION for default setting
// Update to All function will delete all with extension as LANGUAGE_FORGE_SETTINGS_EXTENSION!!
define('LANGUAGE_FORGE_DEFAULT_SETTINGS_RWC', 'WeSayConfig.Rwc.Default'); // TODO Move this to the LFRapidWords project CP 2012-09
define('LANGUAGE_FORGE_DEFAULT_SETTINGS', 'default.WeSayConfig');	// TODO name need to be changed, if a user named "default" will make problem!

// the folder to keep input systems setting in a project // TODO Move to lexicon-server CP 2012-08
define('WRITING_SYSTEMS_DIR', '/WritingSystems/');

define('VCS_MASTER_PATH', '/var/vcs/languageforge/');
define('LANGUAGE_FORGE_WORK_PATH', '/var/lib/languageforge/work/'); // TODO This doesn't need to be configurable. Should be in a static method somewhere CP 2012-10

// use for jsupload.php, you need set executable premission for it to work
define('PHP_UPLOAD_PATH', '/tmp/php_upload/');

// max file size per post (byte) for jsupload, it you also need to change APC and PHP settings!
define('PHP_UPLOAD_MAX_FILE_SIZE', 20 * 1024 * 1024);

?>