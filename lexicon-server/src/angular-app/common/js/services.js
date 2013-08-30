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
				COMMENTS:  function() { return 160;}
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
	;
