angular.module('dpimport.services', [ 'jsonRpc' ])
 .service('depotImportService', [ 'jsonRpc', function(jsonRpc) {
	jsonRpc.connect('/api/Lf_dictionary'); // Note this doesn't actually 'connect', it
								// simply sets the connection url.
	this.depotImport = function(model, callback) {
		jsonRpc.call('depot_begin_import', [ model ], callback);
	};
	
	this.depotImportStates = function(model, callback) {
		jsonRpc.call('depot_check_import_states', [model], callback);
	};
}]);
