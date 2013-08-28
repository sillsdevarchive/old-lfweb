'use strict';

/* Controllers */

angular.module(
	'signup.controllers',
	[ 'lf.services', 'ui.bootstrap' ]
)
.controller('UserCtrl', ['$scope', 'userService', function UserCtrl($scope, userService) {

	$scope.record = {};
	$scope.success = {
		'state':false,
		'message':''
	};
	$scope.record.id = '';
	$scope.record.password = '';
	$scope.createUser = function(record) {
		userService.create(record, function(result) {
			if (result.ok) {
				$scope.success.state = true;
				$scope.success.message = "";
			} else {
				$scope.success.state = false;
				$scope.success.message = "An error occurred in the signup process";
			}
		});
		return true;
	};
}])
;
