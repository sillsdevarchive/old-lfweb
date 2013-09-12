'use strict';

/* Controllers */

angular.module(
	'dpimport.controllers',
	[ 'lf.services', 'ui.bootstrap', 'dpimport.services']
)
.controller('UserCtrl', ['$scope', 'depotImportService', '$location', function UserCtrl($scope, depotImportService, $location) {
	$scope.progressstep=0;
	$scope.showprogressbar = false;
	$scope.record = {};
	$scope.success = {
		'state':false,
		'message':''
	};
	$scope.usernameok = true;
	$scope.usernameexist = false;
	$scope.usernameloading = false;
	$scope.record.projectcode = '';
	$scope.record.projectusername = '';
	$scope.record.projectpassword = '';
	
	$scope.importProject = function(record) {
		depotImportService.depotImport(record, function(result) {
			if (result.ok) {
				$scope.showprogressbar = true;
				$scope.progressstep=0;
				$scope.inprogerss=true;
			    var timer = setInterval(function(){
			    	
			    	depotImportService.depotImportStates(record, function(result) {
			    		if (result.ok) {
				            if (result.data.succeed==true || result.data.code==100)
			            	{ //import finished, so return code will be new project ID, and will redirect
			            		clearInterval(timer);
			            		$scope.progressstep=100;
			            		//window.location.href="/gwt/main/" + result.data.code;
			            	}else
			            		{
			            		$scope.progressstep=parseInt(result.data.code);
			            		}
			    		} else {
			    			clearInterval(timer);
			    			$scope.inprogerss=false;
							$scope.success.state = false;
							$scope.success.message = "An error occurred in the import process: ";
						}
			    	});
			    
			        }, 2000);
			} else {
				$scope.inprogerss=false;
				$scope.success.state = false;
				$scope.success.message = "An error occurred in the import process: ";
			}
		});
		return true;
	};
}]);


