'use strict';

/* Controllers */

angular.module(
	'dpimport.controllers',
	[ 'lf.services', 'ui.bootstrap', 'dpimport.services']
)
.controller('UserCtrl', ['$scope', 'depotImportService', function UserCtrl($scope, depotImportService) {
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
			    var timer = setInterval(function(){
			    	
			    	depotImportService.depotImportStates(record, function(result) {
			    		if (result.ok) {
				            if (result.data.succeed==true)
			            	{ //import finished
			            		clearInterval(timer);
			            		$scope.progressstep=parseInt(result.data.code);
						        //$scope.$apply();
			            	}else
			            		{
			            		$scope.progressstep=parseInt(result.data.code);
						        //$scope.$apply();
			            		}
			    		} else {
			    			clearInterval(timer);
							$scope.success.state = false;
							$scope.success.message = "An error occurred in the import process: ";
						}
			    	});
			    
			        }, 2000);
			} else {
				$scope.success.state = false;
				$scope.success.message = "An error occurred in the import process: ";
			}
		});
		return true;
	};
}]);


