'use strict';

/* Controllers */

angular.module(
	'signup.controllers',
	[ 'sf.services', 'ui.bootstrap' ]
)
.controller('UserCtrl', ['$scope', 'userService', function UserCtrl($scope, userService) {

	$scope.vars = {};

	$scope.createUser = function(record) {
		userService.create(record, function(result) {
			if (result.ok) {
				console.log("user signup success");
			} else {
				console.log("error condition");
			}
		});
		return true;
	};


	$scope.changePassword = function(record) {
//		console.log("changePassword() called with ", record);
		userService.changePassword(record.id, record.password, function(result) {
//			console.log("Password successfully changed.");
		});
	};
	
}])
;
