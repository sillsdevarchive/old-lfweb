'use strict';

function dbeCtrl($scope, userService, sessionService, lexService, $window) {
	
	// see http://alistapart.com/article/expanding-text-areas-made-elegant
	// for an idea on expanding text areas
	
	
 
	
	/* this is what an entry looks like
	$scope.entry = {
		'id': '1234',
		'lexeme': { 'en': '', 'th': '' },
		'senses': [
		    {
				'meaning': { 'en': '', 'th': '' },
		    }
		]
	};
	*/
	var projectId = 'blah';
	$scope.currentEntry = {};
	$scope.entries = [];
	$scope.config = {};
	
	$scope.entryIsDirty = function() {
		
	};
	
	$scope.editEntry = function(id) {

		lexService.read(id, function(result) {
			$scope.currentEntry = result.data;
		});
	};
	
	
	$scope.entryTitle = function(entry) {
		entry = entry || $scope.entry;
		var title = "[new word]";
		if (entry.lexeme) {
			var lexemeWritingSystem = $scope.config.entry.definitions.lexeme.writingsystems[0];
			if (entry.lexeme[lexemeWritingSystem]) {
				title = entry.lexeme[lexemeWritingSystem];
			}
		}
		return title;
	};
	
	
	$scope.getNewId = function() {
		var newId = 0;
		for (var i=0; i<$scope.entries.length; i++) {
			var e = $scope.entries[i];
			if (e.id >= newId) {
				newId = e.id + 1;
			}
		}
		return newId;
	};
	
	$scope.getEntryIndexById = function(id) {
		var index = undefined;
		for (var i=0; i<$scope.entries.length; i++) {
			var e = $scope.entries[i];
			if (e.id == id) {
				index = i;
				break;
			}
		}
		return index;
	};
	
	$scope.getEntryById = function(id) {
		var entry = undefined;
		for (var i=0; i<$scope.entries.length; i++) {
			var e = $scope.entries[i];
			if (e.id == id) {
				entry = e;
				break;
			}
		}
		return entry;
	};
	
	$scope.entryLoaded = function() {
		return $scope.entry.hasOwnProperty('id');
	};
	
	$scope.addEntry = function() {
		var newId = $scope.getNewId();
		$scope.entries.unshift({'id': newId});
		$scope.editEntry(newId);
	};
	
	$scope.deleteEntry = function(entry) {
		if ($window.confirm("Are you sure you want to delete " + $scope.entryTitle(entry) + " (id " + entry.id + ") ?")) {
			$scope.entries.splice($scope.getEntryIndexById(entry.id), 1);
			$scope.entry = {};
		}
	};

	// run this when the page loads
	lexService.getPageDto(projectId, function(result) {
		$scope.entries = result.data.entries;
		$scope.config = result.data.config;
	});
}


angular.module('dbe', ['jsonRpc', 'ui.bootstrap', 'lf.services', 'palaso.ui.dc.entry', 'ngAnimate']).
controller('dbeCtrl', ['$scope', 'userService', 'sessionService', 'lexEntryService', '$window', dbeCtrl])
;
