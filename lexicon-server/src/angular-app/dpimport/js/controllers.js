'use strict';

/* Controllers */

angular.module(
	'dpimport.controllers',
	[ 'lf.services', 'ui.bootstrap', 'dpimport.services','vcRecaptcha']
)
.controller('UserCtrl', ['$scope', 'depotImportService', '$location', function UserCtrl($scope, depotImportService, $location, vcRecaptchaService) {
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
				record.projectpassword = '';
				var stateChecker = function(){
			    	depotImportService.depotImportStates(record, function(result) {
			    		if (result.ok) {
			    			
			    			if (result.data.haserror==true)
			    				{
			    					$scope.success.state = false;
			    					$scope.success.message = "An error occurred in the import process: " + result.data.code;
			    					return;
			    				}
			    			
				            if (result.data.succeed==true)
			            	{ 	//import finished, so return code will be new project ID, and will redirect
			            		$scope.progressstep=100;
			            		window.location.href="/gwt/main/" + result.data.code;
			            	}else
			            	{
			            		setTimeout(stateChecker,1000);
			            		$scope.progressstep=parseInt(result.data.code);
			            	}
			    		} else {
			    			$scope.inprogerss=false;
							$scope.success.state = false;
							$scope.success.message = "An error occurred in the import process: ";
						}
			    	});
			    
			        };

			     setTimeout(stateChecker,1000);
			} else {
				$scope.inprogerss=false;
				$scope.success.state = false;
				$scope.success.message = "An error occurred in the import process: ";
			}
		});
		return true;
	};
}]);


