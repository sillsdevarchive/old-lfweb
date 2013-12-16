'use strict';

function dbeCtrl($scope, userService, sessionService, lexService, $window) {
	
	// see http://alistapart.com/article/expanding-text-areas-made-elegant
	// for an idea on expanding text areas
	
	
 
	
	/* this is what an entry looks like
	$scope.currentEntry = {
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
	$scope.pristineEntry = {};
	$scope.entries = [];
	$scope.config = {};
	
	// for debugging
	$scope.serverEntries = lexService.serverEntries;
	$scope.dirtyEntries = lexService.dirtyEntries;
	
	$scope.currentEntryIsDirty = function() {
		return !angular.equals($scope.currentEntry, $scope.pristineEntry);
	};
	
	$scope.updateListWithEntry = function(entry) {
		var isNew = true;
		for (var i=0; i<$scope.entries.length; i++) {
			var e = $scope.entries[i];
			if (e.id == entry.id) {
				$scope.entries[i].title = $scope.entryTitle(entry);
				isNew = false;
				break;
			}
		}
		if (isNew) {
			$scope.entries.unshift({id:entry.id, title:$scope.entryTitle(entry)});
		}
		
		
	};
	
	$scope.setCurrentEntry = function(entry) {
		$scope.currentEntry = entry;
		$scope.pristineEntry = entry;
	};
	
	$scope.editEntry = function(id) {
		if ($scope.entryLoaded()) {
			// first, save current entry
			lexService.update(projectId, $scope.currentEntry, function(result) {
				$scope.updateListWithEntry(result.data);
			});
		}

		if (arguments.length == 0) {
			// create new entry
			$scope.setCurrentEntry({'id':''});
		} else {
			// load existing entry
			lexService.read(id, function(result) {
				$scope.setCurrentEntry(result.data);
			});
		}
	};

	$scope.newEntry = function() {
		$scope.editEntry();
	};
	
	$scope.entryTitle = function(entry) {
		entry = entry || $scope.currentEntry;
		var title = "[new word]";
		if (entry.lexeme && $scope.config && $scope.config.entry) {
			var lexemeWritingSystem = $scope.config.entry.definitions.lexeme.writingsystems[0];
			if (entry.lexeme[lexemeWritingSystem]) {
				title = entry.lexeme[lexemeWritingSystem];
			}
		}
		return title;
	};

	$scope.entryLoaded = function() {
		return $scope.currentEntry.hasOwnProperty('id');
	};
	
	$scope.deleteEntry = function(entry) {
		if ($window.confirm("Are you sure you want to delete '" + $scope.entryTitle(entry) + "'?")) {
			$scope.entries.splice($scope.getEntryIndexById(entry.id), 1);
			lexService.remove(projectId, entry.id, function(){});
			$scope.setCurrentEntry({});
		}
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
	
	// run this when the page loads
	lexService.getPageDto(projectId, function(result) {
		$scope.entries = result.data.entries;
		$scope.config = result.data.config;
	});
}


angular.module('dbe', ['jsonRpc', 'ui.bootstrap', 'lf.services', 'palaso.ui.dc.entry', 'ngAnimate']).
controller('dbeCtrl', ['$scope', 'userService', 'sessionService', 'lexEntryService', '$window', dbeCtrl])
;
