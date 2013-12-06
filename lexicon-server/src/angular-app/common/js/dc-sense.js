angular.module('palaso.ui.dc.sense', ['palaso.ui.dc.multitext', 'palaso.ui.dc.optionlist', 'palaso.ui.dc.example'])
  // Palaso UI Dictionary Control: Sense
  .directive('dcSense', [function() {
		return {
			restrict : 'E',
			templateUrl : '/angular-app/common/directive/dc-sense.html',
			scope : {
				config : "=",
				model : "=",
			},
			controller: ['$scope', function($scope) {
				$scope.makeValidModel = function() {
					if (!$scope.model) {
						$scope.model = {};
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
