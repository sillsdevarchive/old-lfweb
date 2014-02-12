'use strict';

// Services
// ScriptureForge common services
angular.module('lf.services', ['jsonRpc'])
	.service('userService', ['jsonRpc', function(jsonRpc) {
		jsonRpc.connect('/api/lf'); // Note this doesn't actually 'connect', it simply sets the connection url.
		this.read = function(id, callback) {
			jsonRpc.call('user_read', [id], callback);
		};
		this.update = function(model, callback) {
			jsonRpc.call('user_update', [model], callback);
		};
		this.create = function(model, callback) {
			jsonRpc.call('user_create', [model], callback);
		};
		this.remove = function(userIds, callback) {
			jsonRpc.call('user_delete', [userIds], callback);
		};
		this.changePassword = function(id, password, callback) {
			jsonRpc.call('change_password', [id, password], callback);
		};
		this.list = function(callback) {
			// TODO Paging CP 2013-07
			jsonRpc.call('user_list', [], callback);
		};
		this.typeahead = function(term, callback) {
			jsonRpc.call('user_typeahead', [term], callback);
		};
		this.changePassword = function(userId, newPassword, callback) {
			jsonRpc.call('change_password', [userId, newPassword], callback);
		};
		this.usernameexists = function(userName, callback) {
			jsonRpc.call('username_exists', [userName], callback);
		};
	}])
	.service('languageService', ['jsonRpc', function(jsonRpc) {
		jsonRpc.connect('/api/lf'); // Note this doesn't actually 'connect', it simply sets the connection url.
		this.typeahead = function(term, callback) {
			jsonRpc.call('language_typeahead', [term], callback);
		};
	}])
	.service('projectService', ['jsonRpc', function(jsonRpc) {
		jsonRpc.connect('/api/lf'); // Note this doesn't actually 'connect', it simply sets the connection url.
		this.read = function(projectId, callback) {
			jsonRpc.call('project_read', [projectId], callback);
		};
		this.update = function(model, callback) {
			jsonRpc.call('project_update', [model], callback);
		};
		this.remove = function(projectIds, callback) {
			jsonRpc.call('project_delete', [projectIds], callback);
		};
		this.list = function(callback) {
			jsonRpc.call('project_list', [], callback);
		};
		this.readUser = function(projectId, userId, callback) {
			jsonRpc.call('project_readUser', [projectId, userId], callback);
		};
		this.updateUser = function(projectId, model, callback) {
			jsonRpc.call('project_updateUser', [projectId, model], callback);
		};
		this.removeUsers = function(projectId, users, callback) {
			jsonRpc.call('project_deleteUsers', [projectId, users], callback);
		};
		this.listUsers = function(projectId, callback) {
			// TODO Paging CP 2013-07
			jsonRpc.call('project_listUsers', [projectId], callback);
		};
	}])
	.service('textService', ['jsonRpc', function(jsonRpc) {
		jsonRpc.connect('/api/lf'); // Note this doesn't actually 'connect', it simply sets the connection url.
		this.read = function(projectId, textId, callback) {
			jsonRpc.call('text_read', [projectId, textId], callback);
		};
		this.update = function(projectId, model, callback) {
			jsonRpc.call('text_update', [projectId, model], callback);
		};
		this.remove = function(projectId, textIds, callback) {
			jsonRpc.call('text_delete', [projectId, textIds], callback);
		};
		this.list = function(projectId, callback) {
			jsonRpc.call('text_list_dto', [projectId], callback);
		};
		this.settings_dto = function(projectId, textId, callback) {
			jsonRpc.call('text_settings_dto', [projectId, textId], callback);
		};
	}])
	.service('questionsService', ['jsonRpc', function(jsonRpc) {
		jsonRpc.connect('/api/lf'); // Note this doesn't actually 'connect', it simply sets the connection url.
		this.read = function(projectId, questionId, callback) {
			jsonRpc.call('question_read', [projectId, questionId], callback);
		};
		this.update = function(projectId, model, callback) {
			jsonRpc.call('question_update', [projectId, model], callback);
		};
		this.remove = function(projectId, questionIds, callback) {
			jsonRpc.call('question_delete', [projectId, questionIds], callback);
		};
		this.list = function(projectId, textId, callback) {
			jsonRpc.call('question_list_dto', [projectId, textId], callback);
		};
	}])
	.service('questionService', ['jsonRpc', function(jsonRpc) {
		jsonRpc.connect('/api/lf');
		this.read = function(projectId, entryId, questionId, callback) {
			jsonRpc.call('question_comment_dto', [projectId, entryId, questionId], callback);
		};
		this.update = function(projectId, model, callback) {
			jsonRpc.call('question_update', [projectId, model], callback);
		};
		this.update_answer = function(projectId, questionId, model, callback) {
			jsonRpc.call('question_update_answer', [projectId, questionId, model], callback);
		};
		this.remove_answer = function(projectId, questionId, answerId, callback) {
			jsonRpc.call('question_remove_answer', [projectId, questionId, answerId], callback);
		};
		this.update_comment = function(projectId, questionId, answerId, model, callback) {
			jsonRpc.call('question_update_comment', [projectId, questionId, answerId, model], callback);
		};
		this.remove_comment = function(projectId, questionId, answerId, commentId, callback) {
			jsonRpc.call('question_remove_comment', [projectId, questionId, answerId, commentId], callback);
		};
		this.answer_voteUp = function(projectId, questionId, answerId, callback) {
			jsonRpc.call('answer_vote_up', [projectId, questionId, answerId], callback);
		};
		this.answer_voteDown = function(projectId, questionId, answerId, callback) {
			jsonRpc.call('answer_vote_down', [projectId, questionId, answerId], callback);
		};
	}])
	.service('questionTemplateService', ['jsonRpc', function(jsonRpc) {
		jsonRpc.connect('/api/lf');
		this.read = function(questionTemplateId, callback) {
			jsonRpc.call('questionTemplate_read', [questionTemplateId], callback);
		};
		this.update = function(questionTemplate, callback) {
			jsonRpc.call('questionTemplate_update', [questionTemplate], callback);
		};
		this.remove = function(questionTemplateIds, callback) {
			jsonRpc.call('questionTemplate_delete', [questionTemplateIds], callback);
		};
		this.list = function(callback) {
			jsonRpc.call('questionTemplate_list', [], callback);
		};
	}])
	.service('activityService', ['jsonRpc', function(jsonRpc) {
		jsonRpc.connect('/api/lf');
		this.list_activity = function(offset, count, callback) {
			jsonRpc.call('activity_list_dto', [offset, count], callback);
		};
	}])
	.service('sessionService', function() {
		this.currentUserId = function() {
			return window.session.userId;
		};
		
		this.realm = {
			SITE: function() { return window.session.userSiteRights; }
		};
		this.domain = {
				ANY:       function() { return 100;},
				USERS:     function() { return 110;},
				PROJECTS:  function() { return 120;},
				TEXTS:     function() { return 130;},
				QUESTIONS: function() { return 140;},
				ANSWERS:   function() { return 150;},
				COMMENTS:  function() { return 160;},
				TEMPLATES: function() { return 170;}
		};
		this.operation = {
				CREATE:       function() { return 1;},
				EDIT_OWN:     function() { return 2;},
				EDIT_OTHER:   function() { return 3;},
				DELETE_OWN:   function() { return 4;},
				DELETE_OTHER: function() { return 5;},
				LOCK:         function() { return 6;}
		};
		
		this.hasRight = function(rights, domain, operation) {
			var right = domain() + operation();
			return rights.indexOf(right) != -1;
		};
		
		this.session = function() {
			return window.session;
		};
	})
	.service('linkService', function() {
		this.href = function(url, text) {
			return '<a href="' + url + '">' + text + '</a>';
		};
		
		this.project = function(projectId) {
			return '/gwt/project/' + projectId;
			
		};
		
		this.projectSettings = function(projectId) {
			return '/app/projects#/project/' + projectId + '/settings';
			
		};
		this.text = function(projectId, textId) {
			return this.project(projectId) + "/" + textId;
		};
		
		this.question = function(projectId, textId, questionId) {
			return this.text(projectId, textId) + "/" + questionId;
		};
		
		this.user = function(userId) {
			return '/app/userprofile/' + userId;
		};
	})	
	.service('linkServiceGwt', function() {
		this.project = function(projectId) {
			return '/gwtangular/sfchecks#/project/' + projectId;
			
		};
		this.projectSettings = function(projectId) {
			return '/gwtangular/projects#/project/' + projectId + '/settings';
			
		};
		this.text = function(projectId, textId) {
			return this.project(projectId) + "/" + textId;
		};
		this.question = function(projectId, textId, questionId) {
			return this.text(projectId, textId) + "/" + questionId;
		};	
	})
	.service('lexEntryService', function() {
		var config = {
			'writingsystems': {
				'type': 'map',
				'map': {
					'th': 'Thai',
					'en': 'English',
					'thipa': 'Thai IPA'
				}
			},
			'entry': {
				'type': 'fields',
				'fieldNames': ['lexeme', 'senses'],
				'fields': {
					'lexeme': {
						'type': 'multitext',
						'label': 'Word',
						'writingsystems': ['thipa'],
						'width': 20
					},
					'senses': {
						'type': 'fields',
						'fieldNames': ['definition', 'partOfSpeech', 'semanticDomainValue', 'examples'],
						'fields': {
							'definition': {
								'type': 'multitext',
								'label': 'Meaning',
								'writingsystems': ['th', 'en'],
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
								'fieldNames': ['example', 'translation'],
								'fields': {
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
		this.projectSettings = function() {
			return config;
		};

		var sampleData = [
				{
					"lexeme": {"thipa": "khâaw kài thɔ̂ɔt"},
					"senses": [{
						"meaning": {
							"th": "ข้าวไก่ทอด",
							"en": "pieces of fried chicken served over rice, usually with a sweet and spicy sauce on the side",
						}
					}],
				},

				{
					"lexeme": {"thipa": "krapâw mǔu"},
					"senses": [{
						"definition": {
							"th": "กระเพาหมู",
							"en": "stir fried basil and hot peppers with ground pork over rice",
						}
					}],
				},

				{
					"lexeme": {"thipa": "phàt siiʔ ǐw mǔu"},
					"senses": [{
						"definition": {
					"th": "ผัดชีอิ้วหมู",
					"en": "Noodles fried in soy sauce with pork",
					}}],
				},

				{
					"lexeme": {"thipa": "kài phàt métmàmùaŋ"},
					"senses": [{
						"definition": {
							"th": "ไก่ผัดเม็ดมะม่วง",
							"en": "Stir fried chicken with cashews",
						}
					}],
				},

				{
					"lexeme": {"thipa": "cèt khǔnsʉ̀k phàt phrìk phǎw"},
					"senses": [{
						"definition": {
							"th": "เจ็ดขุนศึกผัดผริกเผา",
							"en": "seven kinds of meat fried and seared with peppers",
						}
					}],
				},

				{
					"lexeme": {"thipa": "phàt prîaw wǎan kài"},
					"senses": [{
						"definition": {
							"th": "ผัดเปรี้ยวหวานหมู",
							"en": "Sweet and sour chicken",
						}
					}],
				},

				{
					"lexeme": {"thipa": "phàt thai kûŋ"},
					"senses": [{
						"definition": {
							"th": "ผักไทกุ้ง",
							"en": "Fried noodles mixed or wrapped with egg and bamboo shoots topped with shrimp",
						}
					}],
				},

				{
					"lexeme": {"thipa": "khâaw khài ciaw mǔu yɔ̂ɔ"},
					"senses": [{
						"definition": {
							"th": "ข้าวไข่เจียหมูยอ",
							"en": "fried omelette with pork over rice",
						}
					}],
				},

				{
					"lexeme": {"thipa": "khâaw phàt mǔu"},
					"senses": [{
						"definition": {
							"th": "ข้าวผัดหมู",
							"en": "Fried rice with minced pork",
						}
					}],
				},

				{
					"lexeme": {"thipa": "nɔ̀máay fàràŋ phàt kûŋ"},
					"senses": [{
						"definition": {
							"th": "หน่อไม้ฝรั่งผัดกุ้ง",
							"en": "Sauteed asparagus with shrimp over rice",
						}
					}],
				},

				{
					"lexeme": {"thipa": "kài sòt kràthiam"},
					"senses": [{
						"definition": {
							"th": "ไก่สกกระเกียม",
							"en": "stir fried garlic chicken over rice",
						}
					}],
				},

			];

		var serverEntries = [];
		var dirtyEntries = [];
		var lastLocalId = 0;
		var lastServerId = 0;
		var localIdMap = {};
		
		// for debugging
		this.serverEntries = function() {
			return serverEntries;
		};
		
		// for debugging
		this.dirtyEntries = function() {
			return dirtyEntries;
		};
		
		function serverIter(func) {
			for (var i=0; i<serverEntries.length; i++) {
				if (func(i, serverEntries[i])) {
					break;
				}
			}
		}
		
		// TODO: replace instances of this function with the full for loop
		// While this is a handy shortcut for developers, it makes the resulting code less readable
		// and perhaps less understandable.  Probably best to just code the full for loop everywhere
		// even though it is a few more keystrokes
		function dirtyIter(func) {
			for (var i=0; i<dirtyEntries.length; i++) {
				if (func(i, dirtyEntries[i])) {
					break;
				}
			}
		}
		
		function getNewServerId() {
			lastServerId++;
			return "server " + lastServerId;
		}
		
		function getNewLocalId() {
			lastLocalId++;
			return "local " + lastLocalId;
		}

		this.canSave = function() {
			return dirtyEntries.length > 0;
		};
		
		this.saveNow = function(projectId, callback) {
			// save each entry in the dirty list
			dirtyIter(function(i, dirtyEntry) {
				// do update or add on server (server figures it out)
				var updated = false;
				serverIter(function(j, serverEntry) {
					if (serverEntry.id == dirtyEntry.id) {
						serverEntries[j] = dirtyEntry;
						updated = true;
						return true;
					}
				});
				if (!updated) {
					var newServerId = getNewServerId(); 
					localIdMap[dirtyEntry.id] = newServerId;
					dirtyEntry.id = newServerId;
					serverEntries.unshift(dirtyEntry);
				}
			});
			dirtyEntries = [];
			(callback || angular.noop)({data:''});
		};

		this.read = function(projectId, id, callback) {
			var result = {};
			dirtyIter(function(i,e) {
				if (e.id == id) {
					result = e;
					return true;
				}
			});
			if (!result.hasOwnProperty('id')) {
				if (id.indexOf("local") != -1) {
					// this is a local id, get the corresponding server id
					id = localIdMap[id];
				}
				// read from server
				serverIter(function(i,e) {
					if (e.id == id) {
						result = e;
						return true;
					}
				});
			}
			(callback || angular.noop)({data: result});
		};
		
		this.update = function(projectId, entry, callback) {
			if (entry.hasOwnProperty('id') && entry.id != '') {
				var foundInDirty = false;
				dirtyIter(function(i,e) {
					if (e.id == entry.id) {
						dirtyEntries[i] = entry;
						foundInDirty = true;
						return true;
					}
				});
				if (!foundInDirty) {
					dirtyEntries.unshift(entry);
				}
			} else {
				entry.id = getNewLocalId();
				dirtyEntries.unshift(entry);
			}
			(callback || angular.noop)({data:entry});
		};
		
		this.remove = function(projectId, id, callback) {
			dirtyIter(function(i,e) {
				if (e.id == id) {
					dirtyEntries.splice(i, 1);
					return true;
				}
			});
			// remove from server
			serverIter(function(i,e){
				if (e.id == id) {
					serverEntries.splice(i, 1);
					return true;
				}
			}); 
			(callback || angular.noop)({data: {}});
			return;
		};
		
		this.getPageDto = function(projectId, callback) {
			var list = [];
			var ws = config.entry.fields.lexeme.writingsystems[0];
			serverIter(function(i,e) {
				var title = e.lexeme[ws];
				if (!title) {
					title = '[new word]';
				}
				list.push({id: e.id, title: title, entry: e});
			});
			(callback || angular.noop)({data: { entries: list, config: config }});
		};
		
		// --- BEGIN TEST CODE ---
		// Set up sample data when service first created
		// (This will be removed once a real server is available)
		for (var _idx = 0; _idx < sampleData.length; _idx++) {
			var entry = sampleData[_idx];
			this.update('sampleProject', entry);
		};
		this.saveNow('sampleProject');
		// --- END TEST CODE ---
	})
	;
