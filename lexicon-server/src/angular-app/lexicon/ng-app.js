'use strict';

// Declare app level module which depends on filters, and services
angular.module('lexicon', 
		[
		 'ngRoute',
		 'dbe',
		])
	.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when(
				'/view',
				{
					templateUrl: '/angular-app/lexicon/views/not-implemented.html',
					// controller: 'dbeCtrl'
				}
			);
		$routeProvider.when(
				'/dashboard',
				{
					templateUrl: '/angular-app/lexicon/views/not-implemented.html',
					// controller: 'dashboardCtrl'
				}
			);
		$routeProvider.when(
				'/gather-words',
				{
					templateUrl: '/angular-app/lexicon/views/not-implemented.html',
				}
			);
		$routeProvider.when(
				'/dbe',
				{
					templateUrl: '/angular-app/lexicon/views/dbe.html',
					controller: 'dbeCtrl'
				}
			);
		$routeProvider.when(
				'/settings',
				{
					templateUrl: '/angular-app/lexicon/views/not-implemented.html',
				}
			);
		$routeProvider.otherwise({redirectTo: '/dashboard'});
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
