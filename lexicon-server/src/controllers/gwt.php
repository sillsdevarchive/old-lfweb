<?php 

use libraries\lfdictionary\environment\LexClientEnvironment;

use models\rights\Operation;
use models\rights\Domain;
use models\rights\Realm;
use models\rights\Roles;


require_once 'base.php';

class gwt extends Base {
	
	public function view($page="project", $pid ='') {
		
		//some "favicon.ico" pass into here, find it out
		if (strpos($pid,'.') !== false || strlen($pid)!== 24) {
			error_log($pid. "is not a valid id");
	    	return;
		}
		$data = array();
		$data['gwt_page'] = $page;
		$data['project_id'] = $pid;
		$data['title'] = "Language Forge";
		$data['is_static_page'] = true;
		$data['canShowEditor'] = $pid != '' && $this->_isLoggedIn;
		if ($data['canShowEditor']) {
			require_once(APPPATH . '/libraries/lfdictionary/Config.php');
			$lexClientEnvironment = new LexClientEnvironment($pid, $this->_user);
			$data['lexSettings'] = $lexClientEnvironment->getSettings();
		}
		$this->_render_page("gwtpages/container", $data);
	}

}

?>