<?php

use models\ProjectModel;

class MockProjectModel {

	public function databaseName() {
		$name = ProjectModel::makeProjectSlug(LF_TEST_LANGUAGE, LF_TESTPROJECT, ProjectModel::PROJECT_LIFT);
		return 'lf_' . $name;
	}
}


?>