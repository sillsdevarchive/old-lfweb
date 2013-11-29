'use strict';

// Declare app level module which depends on filters, and services
angular.module('lfprojects', 
		[
		 'ngRoute',
		 'lfprojects.projects',
		 'lfprojects.project',
		 'lfprojects.filters',
		 'lfprojects.services'
		])
	.config(['$routeProvider', function($routeProvider) {
	    $routeProvider.when(
    		'/projects', 
    		{
    			templateUrl: '/angular-app/projects/partials/projects.html', 
    			controller: 'ProjectsCtrl'
    		}
	    );
	    $routeProvider.when(
    		'/project/:projectId', 
    		{
    			templateUrl: '/angular-app/projects/partials/project.html', 
    			controller: 'ProjectCtrl'
    		}
    	);
	    $routeProvider.when(
	    		'/project/:projectId/settings', 
	    		{
	    			templateUrl: '/angular-app/projects/partials/project-settings.html', 
	    			controller: 'ProjectSettingsCtrl'
	    		}
	    	);
	    $routeProvider.otherwise({redirectTo: 'projects'});
	}])
	.controller('MainCtrl', ['$scope', '$route', '$routeParams', '$location', function($scope, $route, $routeParams, $location) {
		$scope.route = $route;
		$scope.location = $location;
		$scope.routeParams = $routeParams;
	}])
	.controller('BreadcrumbCtrl', ['$scope', '$rootScope', 'breadcrumbService', function($scope, $rootScope, breadcrumbService) {
		$scope.idmap = breadcrumbService.idmap;
		$rootScope.$on('$routeChangeSuccess', function(event, current) {
			$scope.breadcrumbs = breadcrumbService.read();
		});
		$scope.$watch('idmap', function(oldVal, newVal, scope) {
			$scope.breadcrumbs = breadcrumbService.read();
		}, true);
	}])
	;
