'use strict';

/* Controllers */

angular.module(
	'entryblock.controllers',
	[ 'entryblock.services', 'ui.bootstrap']
)
.controller('EntryBlockCtrl', ['$scope', 'entryBlockService', function UserCtrl($scope, entryBlockService) {

	$scope.record = {};

	$scope.projectid = window.session.param1;
	$scope.entryid = window.session.param2;
	console.log($scope.projectid);
	console.log($scope.entryid);
	$scope.getEntry = function(projectId, entryId) {
		entryBlockService.getEntryById(projectId, entryId, function(result) {
			if (result.ok) {
				if (result.data.succeed==true)
					{

					}else
					{

					}
			} else {

			}
		});
		return true;
	};
	
	$scope.getEntry($scope.projectid,$scope.entryid);
}])
;
