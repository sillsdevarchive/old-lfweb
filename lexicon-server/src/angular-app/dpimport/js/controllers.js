'use strict';

/* Controllers */

angular.module(
	'dpimport.controllers',
	[ 'lf.services', 'ui.bootstrap', 'dpimport.services','vcRecaptcha', 'palaso.ui.typeahead', 'ui.bootstrap']
)
.controller('UserCtrl', ['$scope', 'depotImportService', '$location', 'languageService', 'vcRecaptchaService',  function UserCtrl($scope, depotImportService, $location, languageService, vcRecaptchaService) {
	$scope.progressstep=0;
	$scope.showprogressbar = false;
	$scope.record = {};
	$scope.success = {
		'state':false,
		'message':''
	};
	$scope.usernameok = true;
	$scope.usernameexist = false;
	$scope.usernameloading = false;
	$scope.record.projectname = '';
	$scope.record.projectlanguagecode = '';
	$scope.record.projectcode = '';
	$scope.record.projectusername = '';
	$scope.record.projectpassword = '';
	
	$scope.importProject = function(record) {
		$scope.success = {
				'state':false,
				'message':''
			};
		record.captcha_challenge = record.captcha.challenge;
		record.captcha_response = record.captcha.response;
		$scope.record.projectlanguagecode = $scope.language.subtag;
		depotImportService.depotImport(record, function(result) {
			if (result.ok) {
				
				if (result.data.succeed==true) {
					$scope.showprogressbar = true;
					$scope.progressstep=0;
					$scope.inprogerss=true;
					record.projectpassword = '';
					var stateChecker = function(){
				    	depotImportService.depotImportStates(record, function(result) {
				    		if (result.ok) {
				    			
				    			if (result.data.haserror==true)
				    				{
				    					$scope.success.state = false;
				    					$scope.success.message = "An error occurred in the import process: " + result.data.code;
				    					return;
				    				}
				    			
					            if (result.data.succeed==true)
				            	{ 	//import finished, so return code will be new project ID, and will redirect
				            		$scope.progressstep=100;
				            		window.location.href="/gwt/main/" + result.data.code;
				            	}else
				            	{
				            		setTimeout(stateChecker,1000);
				            		$scope.progressstep=parseInt(result.data.code);
				            	}
				    		} else {
				    			$scope.inprogerss=false;
								$scope.success.state = false;
								$scope.success.message = "An error occurred in the import process: ";
							}
				    	});
				    
				        };

				     setTimeout(stateChecker,1000);
				} else {
					//$scope.inprogerss=false;
					result.data.haserror =true;
					$scope.success.state = false;
					$scope.success.message = result.data.code;
					alert(result.data.code);
					vcRecaptchaService.reload();
					return;
				}
				
			} else {
				$scope.inprogerss=false;
				$scope.success.state = false;
				$scope.success.message = "An error occurred in the import process: ";
			}
		});
		return true;
	};
	// ----------------------------------------------------------
	// Typeahead for project selection
	// ----------------------------------------------------------
	$scope.languages = [];
	$scope.language = {};
	$scope.typeahead = {};
	$scope.typeahead.langName = '';

	$scope.queryLanguages = function(searchTerm) {
		console.log('Searching for languages matching', searchTerm);
		if (searchTerm.length < 3) {
			return;
		}
		languageService.typeahead(searchTerm, function(result) {
			console.log("languageService.typeahead(", searchTerm, ") returned:");
			console.log(result);
			if (result.ok) {
				$scope.languages = result.data.entries;
				console.log("$scope.languages is now:", $scope.languages);
				//$scope.updateSomethingInTheForm(); // TODO: Figure out what, if anything, needs to be updated when the list comes back. 2013-08 RM
			}
		});
	};

	$scope.selectLanguage = function(item) {
		console.log('selectLanguage called with args:');
		console.log(arguments);
		$scope.language = item;
		$scope.typeahead.langName = item.description[0];
	};

	$scope.languageDescription = function(language) {
		// Format a language description for display
		// Language with just one name (most common case): English
		// Language with two names: Dutch (Flemish)
		// Language with 3+ names: Romanian (Moldavian, Moldovan)
		var desc = language.description;
		var first = desc[0];
		var rest = desc.slice(1).join(', ');
		if (rest) {
			return first + " (" + rest + ")";
		} else {
			return first;
		}
	};

	$scope.deprecationWarning = function(language) {
		if (language.deprecated) {
			return " (Deprecated)";
		} else {
			return "";
		}
	};
}]);


