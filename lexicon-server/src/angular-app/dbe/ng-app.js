'use strict';

function dbeCtrl($scope, userService, sessionService) {
	
	$scope.updatePassword = function() {
		if ($scope.vars.password == $scope.vars.confirm_password) {
			userService.changePassword(sessionService.currentUserId(), $scope.vars.password, function(result) {
				if (result.ok) {
					$scope.notify.message = "Password Updated successfully";
				} else {
					$scope.notify.error = "Error updating password";
				}
			});
		}
	};
}

angular.module('dbe', ['jsonRpc', 'ui.bootstrap', 'lf.services', 'palaso.ui.multitext']).
controller('dbeCtrl', ['$scope', 'userService', 'sessionService', dbeCtrl])
;
