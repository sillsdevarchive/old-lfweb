<?php 

use models\rights\Operation;
use models\rights\Domain;
use models\rights\Realm;
use models\rights\Roles;


require_once 'secure_base.php';

class gwt extends base {
	
	public function view($page = 'main', $pid ='') {
		
		$data = array();
		$data['gwt_page'] = $page;
		$data['project_id'] = $pid;
		$data['title'] = "Language Forge";
		$data['is_static_page'] = true;
		$data['logged_in'] = $this->_isLoggedIn;
		if ($this->_isLoggedIn) {
			$isAdmin = Roles::hasRight(Realm::SITE, $this->_user->role, Domain::USERS + Operation::CREATE);
			$data['is_admin'] = $isAdmin;
			$data['user_name'] = $data['user_name'] = $this->_user->username;
			$data['user_id'] = $this->_user->id;
			$data['small_gravatar_url'] = $this->ion_auth->get_gravatar("30");
			$data['small_avatar_url'] = $this->_user->avatar_ref;
			$projects = $this->_user->listProjects();
			$data['projects_count'] = $projects->count;
			$data['projects'] = $projects->entries;
		}
		
		$projectList = new models\ProjectListModel();
		$projectList->read();
		$data['all_projects_count'] = $projectList->count;
		$data['all_projects'] = $projectList->entries;
		
		
		if ( ! file_exists('views/gwtpages/project.html.php'))
		{
			show_404();
		} else {
			$this->_render_page("gwtpages/project", $data);
		}
	}
}

?>