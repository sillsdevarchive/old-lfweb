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
				
				$scope.makeValidModel = function() {
					if (!$scope.model) {
						$scope.model = {};
					}
					if (!$scope.model.id) {
						$scope.model.id = "";
					}
					if (!$scope.model.senses) {
						$scope.model.senses = [{}];
					}
				};
				

				
				$scope.deleteSense = function(index) {
					if ($window.confirm("Are you sure you want to delete sense #" + (index+1) + " ?")) {
						$scope.model.senses.splice(index, 1);
					}
				};
			}],
			link : function(scope, element, attrs, controller) {
				scope.$watch('model', function() {
					scope.makeValidModel();
				});
			}
		};
  }])
  ;
