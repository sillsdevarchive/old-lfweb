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
