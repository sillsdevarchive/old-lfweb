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
	
	$scope.entry = undefined;
	
	$scope.entryTitle = function() {
		if ($scope.entry && $scope.entry.lexeme) {
			var lexemeWritingSystem = $scope.config.entry.definitions.lexeme.writingsystems[0];
			return $scope.entry.lexeme[lexemeWritingSystem];
		}
		return "";
	};
	
	$scope.entries = [];
	
	$scope.editEntry = function(index) {
		$scope.entry = $scope.entries[index];
	};
	
	$scope.addEntry = function() {
		var lexemeWritingSystem = $scope.config.entry.definitions.lexeme.writingsystems[0];
		var lexeme = {};
		lexeme[lexemeWritingSystem] = '[blank]';
		$scope.entries.push({'id':"", 'lexeme': lexeme});
	};
	
	$scope.deleteEntry = function(index) {
		if ($window.confirm("Are you sure you want to delete entry #" + (index+1) + " ?")) {
			$scope.entries.splice(index, 1);
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


angular.module('dbe', ['jsonRpc', 'ui.bootstrap', 'lf.services', 'palaso.ui.dc.entry']).
controller('dbeCtrl', ['$scope', 'userService', 'sessionService', '$window', dbeCtrl])
;
