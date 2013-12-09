<?php
use models\rights\Realm;
use models\rights\Roles;

require_once 'secure_base.php';
class App extends Secure_base {
	public function view($app = 'main') {
		if (! file_exists ( "angular-app/$app" )) {
			show_404 ();
		} else {
			$data = array ();
			$data = $this->prepareData ( $app, $data );
			$this->_render_page ( "angular-app", $data );
		}
	}
	protected function prepareData($app, $data, $param1 = '', $param2 = '') {
		$data ['appName'] = $app;
		
		$sessionData = array ();
		$sessionData ['userId'] = ( string ) $this->session->userdata ( 'user_id' );
		$role = $this->_user->role;
		if (empty ( $role )) {
			$role = Roles::USER;
		}
		$sessionData ['userSiteRights'] = Roles::getRightsArray ( Realm::SITE, $role );
		$sessionData ['param1'] = $param1;
		$sessionData ['param2'] = $param2;
		$jsonSessionData = json_encode ( $sessionData );
		$data ['jsonSession'] = $jsonSessionData;
		
		$data ['jsCommonFiles'] = array ();
		self::addJavascriptFiles ( "angular-app/common/js", $data ['jsCommonFiles'] );
		self::addJavascriptFiles ( "angular-app/common/directive", $data ['jsCommonFiles'] );
		$data ['jsProjectFiles'] = array ();
		self::addJavascriptFiles ( "angular-app/$app", $data ['jsProjectFiles'] );
		
		$data ['title'] = "Language Forge";
		return $data;
	}
	protected static function ext($filename) {
		return pathinfo ( $filename, PATHINFO_EXTENSION );
	}
	protected static function addJavascriptFiles($dir, &$result) {
		if (($handle = opendir ( $dir ))) {
			while ( $file = readdir ( $handle ) ) {
				if (is_file ( $dir . '/' . $file )) {
					if (self::ext ( $file ) == 'js') {
						$result [] = $dir . '/' . $file;
					}
				} elseif ($file != '..' && $file != '.') {
					self::addJavascriptFiles ( $dir . '/' . $file, $result );
				}
			}
			closedir ( $handle );
		}
	}
}

?>