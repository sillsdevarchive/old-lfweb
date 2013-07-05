<?php
$rootPath = dirname(__FILE__) . '/';
define('SOURCE_PATH', $rootPath);

if (!defined('LF_BASE_PATH')) {
	define('LF_BASE_PATH', '/var/www/languageforge.org_dev7/web/lf/');
}

require_once(LF_BASE_PATH . 'Config.php');

define('LEXICON_WORD_PACK_FILE_NAME', 'SILCawl.lift');

// use for gather words from list.
define('LEXICON_WORD_LIST_SOURCE', '/var/lib/languageforge/lexicon/wordpacks/');

//point to root folder of dictionaries.
define('PROJECTS_HG_ROOT_FOLDER', '/var/lib/languageforge/work/');

define('DASHBOARD_TOOL', '/var/www/langforgedictionary/lexicon-dashboard-tool/index.php');


define('LANGUAGE_FORGE_DEFAULT_SETTINGS_LEX', 'WeSayConfig.Lex.Default');

// the folder too keep per user settings in a project 
define('LANGUAGE_FORGE_SETTINGS', '/LanguageForgeSettings/');

?>