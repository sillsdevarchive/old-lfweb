<?php 

use models\rights\Operation;
use models\rights\Domain;
use models\rights\Realm;
use models\rights\Roles;
use models\ProjectListModel;

require_once 'secure_base.php'; 
class gwtangular extends secure_base {
	
	public function view($page = 'main', $pid ='') {
		
		//some "favicon.ico" pass into here, find it out
		if (strpos($pid,'.') !== false || strlen($pid)!== 24) {
			error_log($pid. "is not a valid id");
	    	return;
		}

		if ( ! file_exists('views/gwtpages/project.html.php'))
		{
			show_404();
		} else {
			$this->_render_page("gwtpages/project", $data);
		}
	}

}

?>