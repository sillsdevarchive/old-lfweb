<?php
use models\rights\Operation;
use models\rights\Domain;
use models\rights\Realm;
use models\rights\Roles;
use models\ProjectListModel;

require_once 'app.php';
class gwtangular extends App {
	
	public function view($app = 'main', $mode = '', $pid = '') {
		
		// some "favicon.ico" pass into here, find it out
		if (strpos ( $pid, '.' ) !== false || strlen ( $pid ) !== 24) {
			error_log ( $pid . " is not a valid id" );
			return;
		}
		
		$data = array ();
		$data ['project_id'] = $pid;
		$data ['title'] = "Language Forge";
		$data ['is_static_page'] = true;
		$data ['logged_in'] = $this->_isLoggedIn;
		if ($this->_isLoggedIn) {
			$isAdmin = Roles::hasRight ( Realm::SITE, $this->_user->role, Domain::USERS + Operation::CREATE );
			$data ['is_admin'] = $isAdmin;
			$data ['user_name'] = $data ['user_name'] = $this->_user->username;
			$data ['user_id'] = $this->_user->id;
			$data ['small_gravatar_url'] = $this->ion_auth->get_gravatar ( "30" );
			$data ['small_avatar_url'] = $this->_user->avatar_ref;
		}
		// Angular App view
		$data = $this->prepareData($app,$data);
		$view="angular-app";
		if (file_exists(APPPATH . "/views/" . $view . ".html.php")) {
			$view = $view . ".html.php";
		}
		$data["page"] = $view;
		if (! file_exists ( 'views/gwtpages/gwtangularcontainer.html.php' )) {
			show_404 ();
		} else {
			return $this->load->view ('gwtpages/gwtangularcontainer.html.php', $data, false );
		}
	}

}

?>