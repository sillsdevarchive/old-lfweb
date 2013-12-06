angular.module('palaso.ui.dc.entry', ['palaso.ui.dc.sense', 'palaso.ui.dc.multitext'])
  // Palaso UI Dictionary Control: Entry
  .directive('dcEntry', [function() {
		return {
			restrict : 'E',
			templateUrl : '/angular-app/common/directive/dc-entry.html',
			scope : {
				config : "=",
				model : "=",
			},
			controller: ["$scope", "$window", function($scope, $window) {
				$scope.addSense = function() {
					$scope.model.senses.push({});
				};
				
				$scope.deleteSense = function(index) {
					if ($window.confirm("Are you sure you want to delete this sense?")) {
						$scope.model.senses.splice(index, 1);
					}
				};
			}],
			link : function(scope, element, attrs, controller) {
				if (!scope.model) {
					scope.model = {};
				}
				if (!scope.model.id) {
					scope.model.id = "";
				}
				if (!scope.model.senses) {
					scope.model.senses = [{}];
				}
			}
		};
  }])
  ;
