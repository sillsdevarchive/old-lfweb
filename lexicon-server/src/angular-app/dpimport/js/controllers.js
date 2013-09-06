'use strict';

/* Controllers */

angular.module(
	'dpimport.controllers',
	[ 'lf.services', 'ui.bootstrap', 'dpimport.services']
)
.controller('UserCtrl', ['$scope', 'userService', 'TimerService', function UserCtrl($scope, userService, TimerService) {
	$scope.progressstep=1;
	$scope.record = {};
	$scope.success = {
		'state':false,
		'message':''
	};
	$scope.usernameok = true;
	$scope.usernameexist = false;
	$scope.usernameloading = false;
	$scope.record.id = '';
	$scope.record.password = '';
	$scope.createUser = function(record) {
		record.captcha_challenge = record.captcha.challenge;
		record.captcha_response = record.captcha.response;
		userService.create(record, function(result) {
			if (result.ok) {
			
			} else {
				$scope.success.state = false;
				$scope.success.message = "An error occurred in the signup process: ";
			}
		});
		return true;
	};
 
	countController($scope);
        //TimerService.stop(timerName);
      
}]);

function countController($scope){
    $scope.countDown = 10;    
    var timer = setInterval(function(){
    	$scope.progressstep++;
        $scope.$apply();
        console.log($scope.progressstep);
    }, 1000);  
}
