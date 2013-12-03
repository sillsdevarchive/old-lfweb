'use strict';

function dbeCtrl($scope, userService, sessionService) {
	
	// see http://alistapart.com/article/expanding-text-areas-made-elegant
	// for an idea on expanding text areas
	
	$scope.definition = {
		'label': 'word',
		'writingsystems': {
			'en': 'English',
			'th': 'Thai'
		},
		'width': 20
	};
	
	
 
	
	/*
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
	
	
	$scope.config = {
		'entry': {
			'type': 'config',
			'fields': ['lexeme', 'senses'],
			'definitions': {
				'lexeme': {
					'type': 'multitext',
					'label': 'Word',
					'writingsystems': {
						'th': 'Thai',
					},
					'width': 20
				},
				'senses': {
					'type': 'config',
					'fields': ['definition', 'partOfSpeech', 'semanticDomainValue', 'examples'],
					'definitions': {
						'definition': {
							'type': 'multitext',
							'label': 'Meaning',
							'writingsystems': {
								'my': 'Burmese',
								'en': 'English'
							},
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
							'type': 'config',
							'fields': ['example', 'translation'],
							'definitions': {
								'example': {
									'type': 'multitext',
									'label': 'example',
									'writingsystems': {
										'th': 'Thai'
									},
									'width': 20
								},
								'translation': {
									'type': 'multitext',
									'label': 'translation',
									'writingsystems': {
										'en': 'English',
									},
									'width': 20
								}
							}
						}
					}
				}
			}
		}
	};

	
	$scope.emptyRecord = {
		'entry': {
			'word': {},
			'senses': []
		}
	};
	
}


angular.module('dbe', ['jsonRpc', 'ui.bootstrap', 'lf.services', 'palaso.ui.dc.entry']).
controller('dbeCtrl', ['$scope', 'userService', 'sessionService', dbeCtrl])
;
