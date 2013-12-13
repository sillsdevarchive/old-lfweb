'use strict';

function dbeCtrl($scope, userService, sessionService, $window) {
	
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
	
	$scope.entry = {};
	
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
	
	$scope.entries = [];
	
	$scope.editEntry = function(id) {
		$scope.entry = $scope.entries[$scope.getEntryIndexById(id)];
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
	}
	
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
	
	$scope.config = {
		'writingsystems': {
			'type': 'map',
			'map': {
				'th': 'Thai',
				'en': 'English',
				'my': 'Burmese'
			}
		},
		'entry': {
			'type': 'fields',
			'fields': ['lexeme', 'senses'],
			'definitions': {
				'lexeme': {
					'type': 'multitext',
					'label': 'Word',
					'writingsystems': ['th'],
					'width': 20
				},
				'senses': {
					'type': 'fields',
					'fields': ['definition', 'partOfSpeech', 'semanticDomainValue', 'examples'],
					'definitions': {
						'definition': {
							'type': 'multitext',
							'label': 'Meaning',
							'writingsystems': ['my', 'en'],
							'width': 20
						},
						'partOfSpeech': {
							'type': 'optionlist',
							'label': 'Part of Speech',
							'values': {
								'noun': 'Noun',
								'verb': 'Verb',
								'adjective': 'Adjective'
							},
							'width': 20
						},
						'semanticDomainValue': {
							'type': 'optionlist',
							'label': 'Semantic Domain',
							'values': {
								'2.1': '2.1 Body',
								'2.2': '2.2 Head and Shoulders',
								'2.3': '2.3 Feet'
							},
							'width': 20
						},
						'examples': {
							'type': 'fields',
							'fields': ['example', 'translation'],
							'definitions': {
								'example': {
									'type': 'multitext',
									'label': 'example',
									'writingsystems': ['th'],
									'width': 20
								},
								'translation': {
									'type': 'multitext',
									'label': 'translation',
									'writingsystems': ['en'],
									'width': 20
								}
							}
						}
					}
				}
			}
		}
	};

	
}


angular.module('dbe', ['jsonRpc', 'ui.bootstrap', 'lf.services', 'palaso.ui.dc.entry', 'ngAnimate']).
controller('dbeCtrl', ['$scope', 'userService', 'sessionService', '$window', dbeCtrl])
;
