/**
 * 
 */


var Base64 = {
 
	// private property
	_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
 
	// public method for encoding
	encode : function (input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;
 
		input = Base64._utf8_encode(input);
 
		while (i < input.length) {
 
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
 
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
 
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
 
			output = output +
			this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
			this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);
 
		}
 
		return output;
	},
 
	// public method for decoding
	decode : function (input) {
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;
 
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
 
		while (i < input.length) {
 
			enc1 = this._keyStr.indexOf(input.charAt(i++));
			enc2 = this._keyStr.indexOf(input.charAt(i++));
			enc3 = this._keyStr.indexOf(input.charAt(i++));
			enc4 = this._keyStr.indexOf(input.charAt(i++));
 
			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;
 
			output = output + String.fromCharCode(chr1);
 
			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}
 
		}
 
		output = Base64._utf8_decode(output);
 
		return output;
 
	},
 
	// private method for UTF-8 encoding
	_utf8_encode : function (string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";
 
		for (var n = 0; n < string.length; n++) {
 
			var c = string.charCodeAt(n);
 
			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}
 
		}
 
		return utftext;
	},
 
	// private method for UTF-8 decoding
	_utf8_decode : function (utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;
 
		while ( i < utftext.length ) {
 
			c = utftext.charCodeAt(i);
 
			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			}
			else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			}
			else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
 
		}
 
		return string;
	}
 
}


var jsonWordList = [ {
	"word" : "apun",
	"meaning" : "apple",
	"Guid" : "1"
}, {
	"word" : "gluay",
	"meaning" : "banana",
	"Guid" : "2"
}, {
	"word" : "mamuang",
	"meaning" : "mango",
	"Guid" : "3"
} ];

var jsonLexicalEntry = {
	"Guid" : "1",
	"VCSVersion" : "abcd1234",
	"Entry" : {
		"en" : "apple"
	},
	"Entry_S" : {
		"Likes" : [ "cambell", "bob" ],
		"NoLikes" : [ "steve", "jim" ],
		"Score" : 80,
		"Comments" : [ {
			"a" : "cambell",
			"d" : "2011-04-23",
			"t" : "This is great!"
		} ]
	},
	"Senses" : [ {
		"Definition" : {
			"en" : "A round crisp fruit",
			"de" : "dedede"
		},
		"POS" : "Noun",
		"Examples" : [ {
			"Example" : {
				"th" : "abcd"
			},
			"Translation" : {
				"en" : "English example translation",
				"de" : "German translation"
			}
		}, {
			"Example" : {
				"th" : "dcba"
			},
			"Translation" : {
				"en" : "English example translation",
				"de" : "German translation"
			}
		} ]
	}, {
		"Definition" : {
			"en" : "Object of desire",
			"de" : "asdf"
		},
		"POS" : "Verb"
	} ]
};


var settingsPartOfSpeech = [ {
	"value" : "Select",
	"id" : "0"
}, {
	"value" : "Noun",
	"id" : "1"
}, {
	"value" : "Verb",
	"id" : "2"
}, {
	"value" : "Adjective",
	"id" : "3"
}, {
	"value" : "Adverb",
	"id" : "4"
} ];

var clientEnvironment = {
	"currentProject" : Base64.encode('{"id":284,"name":"ThaiDic","type":"test","lang":"th"}'),
	"currentUser" : Base64.encode('{"uid":61,"name":"newsampuser","mail":"zheng@hzne.com","userRole":3,"admin":true,"view":true,"edit":true,"del":true}')
};

var missingInfo = [ {
	"name" : "addMeanings",
	"value" : "DF"
}, {
	"name" : "addGrammatical",
	"value" : "PS"
}, {
	"name" : "addExample",
	"value" : "EX"
} ];

var fieldSettings = {
		"Word" : {
			"Label" : "Word",
			"Abbreviations" : [ "IPA", "th" ],
			"Languages" : [ "IPA", "th" ],
			"Visible" : true,
			"ReadonlyField" : false
		},
		"POS" : {
			"Label" : "Part Of Speech",
			"Abbreviations" : [ "en" ],
			"Languages" : [ "en" ],
			"Visible" : true,
			"ReadonlyField" : false
		},
		"Definition" : {
			"Label" : "Meaning",
			"Abbreviations" : [ "en" ],
			"Languages" : [ "en" ],
			"Visible" : true,
			"ReadonlyField" : false
		},
		"Example" : {
			"Label" : "Example",
			"Abbreviations" : [ "IPA", "th" ],
			"Languages" : [ "IPA", "th" ],
			"Visible" : true,
			"ReadonlyField" : false
		},
		"Translation" : {
			"Label" : "Translation",
			"Abbreviations" : [ "en" ],
			"Languages" : [ "en" ],
			"Visible" : true,
			"ReadonlyField" : false
		},
		"NewMeaning" : {
			"Label" : "New Meaning",
			"Abbreviations" : [ "en" ],
			"Languages" : [ "en" ],
			"Visible" : true,
			"ReadonlyField" : false
		},
		"NewExample" : {
			"Label" : "New Example",
			"Abbreviations" : [ "IPA", "th" ],
			"Languages" : [ "IPA", "th" ],
			"Visible" : true,
			"ReadonlyField" : false
		},
	};

var fieldSettingsForAddMeaning = {
	"Word" : {
		"Label" : "Word",
		"Abbreviations" : [ "th" ],
		"Languages" : [ "th" ],
		"Visible" : true,
		"ReadonlyField" : true
	},
	"POS" : {
		"Label" : "Part Of Speech",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"Definition" : {
		"Label" : "Meaning",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"Example" : {
		"Label" : "Example",
		"Abbreviations" : [ "IPA", "th" ],
		"Languages" : [ "IPA", "th" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"NewMeaning" : {
		"Label" : "New Meaning",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : true,
		"ReadonlyField" : false
	},
	"NewExample" : {
		"Label" : "New Example",
		"Abbreviations" : [ "IPA", "th" ],
		"Languages" : [ "IPA", "th" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"Translation" : {
		"Label" : "Translation",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
};
var fieldSettingsForAddPOS = {
	"Word" : {
		"Label" : "Word",
		"Abbreviations" : [ "th" ],
		"Languages" : [ "th" ],
		"Visible" : true,
		"ReadonlyField" : true
	},
	"POS" : {
		"Label" : "Part Of Speech",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : true,
		"ReadonlyField" : false
	},
	"Definition" : {
		"Label" : "Meaning",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : true,
		"ReadonlyField" : true
	},
	"Example" : {
		"Label" : "Example",
		"Abbreviations" : [ "IPA", "th" ],
		"Languages" : [ "IPA", "th" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"NewMeaning" : {
		"Label" : "New Meaning",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"NewExample" : {
		"Label" : "New Example",
		"Abbreviations" : [ "IPA", "th" ],
		"Languages" : [ "IPA", "th" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"Translation" : {
		"Label" : "Translation",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
};
var fieldSettingsForAddExample = {
	"Word" : {
		"Label" : "Word",
		"Abbreviations" : [ "th" ],
		"Languages" : [ "th" ],
		"Visible" : true,
		"ReadonlyField" : true
	},
	"POS" : {
		"Label" : "Part Of Speech",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : true,
		"ReadonlyField" : true
	},
	"Definition" : {
		"Label" : "Meaning",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : true,
		"ReadonlyField" : true
	},
	"NewMeaning" : {
		"Label" : "New Meaning",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"Example" : {
		"Label" : "Example",
		"Abbreviations" : [ "th", "en" ],
		"Languages" : [ "th", "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"NewExample" : {
		"Label" : "New Example",
		"Abbreviations" : [ "th", "en" ],
		"Languages" : [ "th", "en" ],
		"Visible" : true,
		"ReadonlyField" : false
	},
	"Translation" : {
		"Label" : "Translation",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
};

var fieldSettingsForGatherWordFromList = {
	"Word" : {
		"Label" : "In my language",
		"Abbreviations" : [ "th", "IPA" ],
		"Languages" : [ "th", "IPA" ],
		"Visible" : true,
		"ReadonlyField" : false
	},
	"POS" : {
		"Label" : "Part Of Speech",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"Definition" : {
		"Label" : "Meaning",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"NewMeaning" : {
		"Label" : "New Meaning",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"Example" : {
		"Label" : "Example",
		"Abbreviations" : [ "th", "en" ],
		"Languages" : [ "th", "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"NewExample" : {
		"Label" : "New Example",
		"Abbreviations" : [ "th", "en" ],
		"Languages" : [ "th", "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
	"Translation" : {
		"Label" : "Translation",
		"Abbreviations" : [ "en" ],
		"Languages" : [ "en" ],
		"Visible" : false,
		"ReadonlyField" : false
	},
};

var fieldSettingsForGatherWordFromSemanticDomain = {
		"Word" : {
			"Label" : "Word",
			"Abbreviations" : [ "th"],
			"Languages" : [ "th"],
			"Visible" : true,
			"ReadonlyField" : false
		},
		"POS" : {
			"Label" : "Part Of Speech",
			"Abbreviations" : [ "en" ],
			"Languages" : [ "en" ],
			"Visible" : true,
			"ReadonlyField" : false
		},
		"Definition" : {
			"Label" : "Meaning",
			"Abbreviations" : [ "en" ],
			"Languages" : [ "en" ],
			"Visible" : true,
			"ReadonlyField" : false
		},
		"NewMeaning" : {
			"Label" : "New Meaning",
			"Abbreviations" : [ "en" ],
			"Languages" : [ "en" ],
			"Visible" : false,
			"ReadonlyField" : false
		},
		"Example" : {
			"Label" : "Example",
			"Abbreviations" : [ "th", "en" ],
			"Languages" : [ "th", "en" ],
			"Visible" : true,
			"ReadonlyField" : false
		},
		"NewExample" : {
			"Label" : "New Example",
			"Abbreviations" : [ "th", "en" ],
			"Languages" : [ "th", "en" ],
			"Visible" : false,
			"ReadonlyField" : false
		},
		"Translation" : {
			"Label" : "Translation",
			"Abbreviations" : [ "en" ],
			"Languages" : [ "en" ],
			"Visible" : true,
			"ReadonlyField" : false
		},
	};

var taskSettings = {
	"tasks" : {
		"task" : [
				{
					"taskName" : "Dashboard",
					"visible" : "true",
					"taskspecifieddata" : {
						"$" : "{\"activitytimerange\":{\"$\":30},\"targetwordcount\":{\"$\":100}}"
					}
				},
				{
					"taskName" : "Review",
					"visible" : "true"
				},
				{
					"taskName" : "Dictionary",
					"visible" : "true"
				},
				{
					"label" : {
						"$" : "Meanings"
					},
					"longLabel" : {
						"$" : "Add Meanings"
					},
					"description" : {
						"$" : "Add meanings (senses) to entries where they are missing."
					},
					"field" : {
						"$" : "definition"
					},
					"showFields" : {
						"$" : "definition"
					},
					"readOnly" : {
						"$" : "semantic-domain-ddp4"
					},
					"writingSystemsToMatch" : [

					],
					"writingSystemsWhichAreRequired" : [

					],
					"taskName" : "AddMissingInfo",
					"visible" : "true"
				},
				{
					"label" : {
						"$" : "Parts of Speech"
					},
					"longLabel" : {
						"$" : "Add Parts of Speech"
					},
					"description" : {
						"$" : "Add parts of speech to senses where they are missing."
					},
					"field" : {
						"$" : "POS"
					},
					"showFields" : {
						"$" : "POS"
					},
					"readOnly" : {
						"$" : "definition, ExampleSentence"
					},
					"writingSystemsToMatch" : [

					],
					"writingSystemsWhichAreRequired" : [

					],
					"taskName" : "AddMissingInfo",
					"visible" : "true"
				},
				{
					"label" : {
						"$" : "Example Sentences"
					},
					"longLabel" : {
						"$" : "Add Example Sentences"
					},
					"description" : {
						"$" : "Add example sentences to senses where they are missing."
					},
					"field" : {
						"$" : "ExampleSentence"
					},
					"showFields" : {
						"$" : "ExampleSentence"
					},
					"readOnly" : {
						"$" : "definition"
					},
					"writingSystemsToMatch" : [

					],
					"writingSystemsWhichAreRequired" : [

					],
					"taskName" : "AddMissingInfo",
					"visible" : "true"
				},
				{
					"label" : {
						"$" : "Base Forms"
					},
					"longLabel" : {
						"$" : "Add Base Forms"
					},
					"description" : {
						"$" : "Identify the \"base form\" word that this word is built from. In the printed dictionary, the derived or variant words can optionally be shown as subentries of their base forms."
					},
					"field" : {
						"$" : "BaseForm"
					},
					"showFields" : {
						"$" : "BaseForm"
					},
					"readOnly" : [

					],
					"writingSystemsToMatch" : [

					],
					"writingSystemsWhichAreRequired" : [

					],
					"taskName" : "AddMissingInfo",
					"visible" : "false"
				}, {
					"taskName" : "AdvancedHistory",
					"visible" : "false"
				}, {
					"taskName" : "NotesBrowser",
					"visible" : "false"
				}, {
					"longLabel" : {
						"$" : "From texts"
					},
					"taskName" : "GatherWordList",
					"visible" : "true"
				}, {
					"wordListFileName" : {
						"$" : "SILCAWL"
					},
					"wordListWritingSystemId" : {
						"$" : "en"
					},
					"taskName" : "GatherWordList",
					"visible" : "true"
				}, {
					"wordListFileName" : {
						"$" : "SILCAWL-MozambiqueAddendum"
					},
					"wordListWritingSystemId" : {
						"$" : "en"
					},
					"taskName" : "GatherWordList",
					"visible" : "false"
				}, {
					"semanticDomainsQuestionFileName" : {
						"$" : "LocalizedLists-th.xml"
					},
					"showMeaningField" : {
						"$" : "true"
					},
					"showGlossField" : {
						"$" : "true"
					},
					"showPOSField" : {
						"$" : "true"
					},
					"showExampleSentenceField" : {
						"$" : "true"
					},
					"showExampleTranslationField" : {
						"$" : "true"
					},
					"taskName" : "GatherWordsBySemanticDomains",
					"visible" : "true"
				}, {
					"longLabel" : {
						"$" : "Settings"
					},
					"taskName" : "ConfigureSettings",
					"visible" : "true"
				} ]
	}
};