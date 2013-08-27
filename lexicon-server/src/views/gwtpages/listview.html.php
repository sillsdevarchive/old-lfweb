<script type="text/javascript">
	(function($) {
		$(document).ready(function ()
		{
			$("body").on({
		    ajaxStart: function() { 
		        $(this).addClass("loading"); 
		    },
		    ajaxStop: function() { 
		        $(this).removeClass("loading"); 
		    }    
			});
			// RPC server address
			var endPointUrl = '/api/lf_dictionary?p=<?php echo $project_id ?>' ;
			$.jsonRPC.setup({
				endPoint: endPointUrl,
				namespace: ''
			});
			$.jsonRPC.request('getTitleLetterList', {
				params: new Array(),
				success: function(result) {
					// clear buttons list
					$('#titleletter-button-group').empty();
					var titleLetters = result['result']['tl'];
					var length = titleLetters.length;
					// fill the buttons.
						for (var i = 0; i < length; i++) {
						  element = titleLetters[i];
						  $("#titleletter-button-group").append('<li><button class="small button titleletter-button" value="' + element + '" id="titleletter-button-' + i +  '">' +element + '</button></li>');
						}
						// define the click event.
						$(':button').on('click', function ()
						{
							// styling stuff
							$(':button').removeClass('button-selected');
							if ($(this).attr('class').indexOf("titleletter-button") !== -1)
								{
									$(this).addClass('button-selected');
								}
								const selectedTitleLetter = $(this).attr('value');
							// call to server.	
								$.jsonRPC.request('getWordsByTitleLetter', {
										params: new Array(selectedTitleLetter),
										success: function(result) {
														$('#words-list').empty();
														//create title
														$('#words-list').append('<p align="center" class="lpTitlePara">' + selectedTitleLetter.toUpperCase() + ' - ' + selectedTitleLetter.toLowerCase() + '</p>');
														
														//build words list
														var wordsList = result['result']['entries'];
														var length = wordsList.length;

														// fill the words.
														for (var i = 0; i < length; i++) {
															  var wordEntry = wordsList[i];										
																$('#words-list').append(createWordBlock(wordEntry));
														}
										},
										error: function(result) {
											alert("error when try to get dicionary: " + JSON.stringify(result));
										}
									});
						});
						// select first one as default
						$("#titleletter-button-0").trigger("click");
				},
				error: function(result) {
					alert("error when trying to get title letters: " + JSON.stringify(result));
				}
			});
		});
	})(jQuery);
	
	function createWordBlock(entry)
	{

		var senseLenght = 0;
		if (entry.hasOwnProperty("senses")){
			 senseLenght = entry['senses'].length;
		}
		if (senseLenght > 1)
		{
			// we have mutiple senses
			for (var i = 0; i < senseLenght; i++) {								
						wordHtml = wordHtml + createBlockHtml(entry,senseLenght,i);
					}
		}else{
				//only one or none
				
			return createBlockHtml(entry,senseLenght,0);
		}
		return wordHtml;
	}
	
	function createBlockHtml(entry, senseLenght, senseIndex)
	{
			var wordHtml = "";
			if (senseIndex>0)
			{
				wordHtml = '<p class="lpLexEntryPara2">';
			}else
				{
					wordHtml = '<p class="lpLexEntryPara">';
					wordHtml = wordHtml + '<span id="e0" class="lpLexEntryName">' + entry['entry']['th'] +'</span>';
					wordHtml = wordHtml + '<span class="lpSpAfterEntryName">&nbsp;&nbsp;&nbsp;</span>';
				}
				
				if (senseLenght>1)
				{
					wordHtml = wordHtml + '<span class="lpSenseNumber">'+ (senseIndex + 1) +' &nbsp;•&nbsp;</span>';
				}
				
				if (entry.hasOwnProperty("senses") && entry['senses'].length>0){
				var sense = entry['senses'][senseIndex];
				if (sense.hasOwnProperty("POS") && !isEmpty(sense["POS"])){
					 wordHtml = wordHtml + '<span class="lpPartOfSpeech">' + sense["POS"] + '. </span>';
				}
				if (sense.hasOwnProperty("definition") && sense["definition"]){
					var keys = Object.keys(sense["definition"]); 
					wordHtml = wordHtml + '<span class="lpGlossEnglish">' + sense["definition"][keys[0]] + '. </span>';
				}
				if (sense.hasOwnProperty("SemDomValue") && !isEmpty(sense["SemDomValue"])){
					wordHtml = wordHtml + '<span class="lpCategory">' +sense["SemDomValue"] + '</span>';
					wordHtml = wordHtml + '<span class="lpPunctuation">.</span>';
				}
			}
			wordHtml = wordHtml + '</p>';
			return wordHtml;
	}
	
	function isEmpty(str) {
    return (!str || 0 === str.length);
	}
</script>
<div class="readonly-view-inner-box">
	<div>
		<ul id="titleletter-button-group" class="button-group">
		</ul>
	</div>


	<div  id="words-list" class = "readonly-view-word-view">

	</div>
</div>

<div class="view-loading-progress"><!-- Keep me at Bottom --></div>