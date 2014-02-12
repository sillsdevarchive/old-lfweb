'use strict';

function grammarCtrl($scope, userService, sessionService, lexEntryService, $window, $timeout) {
	console.log('Entering grammarCtrl controller function');
	var projectId = 'sampleProject';
	$scope.items = [];
	$scope.visibleEntries = [];
	$scope.config = {};
	$scope.pageData = {};
	$scope.pageData.currentEntry = {};

	$scope.getPageDto = function(callback) {
		lexEntryService.getPageDto(projectId, function(result) {
			console.log('getPageDto() returned:', result);
			$scope.items = result.data.entries;  // Items is a list of {id: 3, title: "foo", entry: (full entry)} objects
			// $scope.config = result.data.config; // Can't just do this because we need to modify our local copy
			$scope.config = JSON.parse(JSON.stringify(result.data.config)); // Fast deep copy, see http://stackoverflow.com/a/5344074/6524
			// We just want to see the definition and part of speech, but leave rest of config alone
			$scope.config.entry.definitions.senses.fields = ['definition', 'partOfSpeech'];
			// Definition should be read-only
			$scope.config.entry.definitions.senses.definitions.definition.readonly = true; // Not yet implemented, but soon
			(callback || angular.noop)();
		});
	};

	$scope.getEntryName = function(item) {
		return item.title;
	};

	// run this when the page loads
	$scope.getPageDto();
	
};

angular.module('meaning', ['jsonRpc', 'ui.bootstrap', 'lf.services', 'palaso.ui.dc.entry', 'palaso.ui.listview', 'ngAnimate']).
controller('grammarCtrl', ['$scope', 'userService', 'sessionService', 'lexEntryService', '$window', '$timeout', grammarCtrl])
;
