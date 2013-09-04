'use strict';

/* Controllers */

angular.module(
	'signup.controllers',
	[ 'lf.services', 'ui.bootstrap','vcRecaptcha' ]
)
.controller('UserCtrl', ['$scope', 'userService', function UserCtrl($scope, userService, vcRecaptchaService) {

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
				if (result.data.succeed==true)
					{
					$scope.success.state = true;
					$scope.success.message = "";
					}else
					{
						$scope.success.state = false;
						$scope.success.message = result.data.code;
						alert(result.data.code);
						vcRecaptchaService.reload();
					}
			} else {
				$scope.success.state = false;
				$scope.success.message = "An error occurred in the signup process: ";
				vcRecaptchaService.reload();
			}
		});
		return true;
	};
}])
;
