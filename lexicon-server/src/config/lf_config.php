<?php

// CodeIgniter style config for 3rd party CodeIgniter plugins used by LanguageForge

$config['db'] = 'languageforge';
// Should be either 'dev' or 'prod', in lowercase with single-quotes
$config['lfenv'] = 'dev';

// General ScriptureForge Configuration

if (!defined('LF_DATABASE')) {
	define('LF_DATABASE', $config['db']);
}

if (!defined('LF_USE_MINIFIED_JS')) {
	if ('dev' === $config['lfenv']) {
		define('LF_USE_MINIFIED_JS', false);
	} else {
		define('LF_USE_MINIFIED_JS', true);
	}
}

define('LF_DEFAULT_EMAIL',      'no-reply@languageforge.org');
define('LF_DEFAULT_EMAIL_NAME', 'LanguageForge');

?>
