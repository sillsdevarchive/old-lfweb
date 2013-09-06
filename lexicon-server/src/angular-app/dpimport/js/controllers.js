'use strict';

/* Controllers */

angular.module(
	'dpimport.controllers',
	[ 'lf.services', 'ui.bootstrap', 'timer']
)
.controller('UserCtrl', ['$scope', 'userService', function UserCtrl($scope, userService) {

	$scope.progressstep=70;
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
}])
;
