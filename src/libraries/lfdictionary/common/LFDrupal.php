<?php
namespace libraries\lfdictionary\common;

/**
 * Loads the Drupal API
 * @author Arivusudar
 */
class LFDrupal {
	
	private static $_drupalLoaded = false;
	
	/**
	 * Load the Drupal API using the bootstrap.inc file
	 */
	public static function loadDrupal() {
		if (!self::$_drupalLoaded) {
			try {
				// Check if the drupal environment exists at the expected location
				if (!file_exists(DrupalPath) || !file_exists(DrupalPath . '/includes/bootstrap.inc')) {
					return false;
				}
				chdir(DrupalPath);
				
				// $_SERVER variables are not set when tests are run from the CLI so fix that here.
				if (!isset($_SERVER['REMOTE_ADDR'])) {
					$_SERVER['REMOTE_ADDR'] = '192.168.1.1';
				}
				define('DRUPAL_ROOT', DrupalPath);
				require_once DrupalPath . '/includes/bootstrap.inc';
				
				//Load Drupal
				/* Notes: the following are options for loading Drupal:
				DRUPAL_BOOTSTRAP_DATABASE
				DRUPAL_BOOTSTRAP_SESSION
				DRUPAL_BOOTSTRAP_PATH
				DRUPAL_BOOTSTRAP_FULL - This results in 3 characters being output prior to the opening { of the json.
				*/
				
				// notes[XZ]: I need full loaded, becasue member settings need call to module's function
// 				drupal_bootstrap(DRUPAL_BOOTSTRAP_FULL);
				drupal_bootstrap(DRUPAL_BOOTSTRAP_SESSION); // See http://projects.palaso.org/issues/1067
				
				self::$_drupalLoaded=true;
				return true;
			} catch (Exception $e) {
				return false;
			}
		}
		return true;
	}

}

?>