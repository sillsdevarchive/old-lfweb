						<div id="ViewSwitch" class="dic-view-switch">
		
							<script type="text/javascript">
	function runToggle(iDuration ,domDiv) {
            $(domDiv).fadeToggle(iDuration,"linear",function() {
          });
	};

		(function($) {
			$(document).ready(function(){
				//default show edit view
				$('#ViewerContent').hide();
				$('#GWTContent').show();
				$('#view_button').attr('class', '');
				$('#edit_button').attr('class', 'view-selected');
				
				$('#view-switch li').live('click', function(e) {
					e.preventDefault;
					if ($(this).attr('id')== 'view_button')
					{
						// check does it already show before show it.
						if ($('#GWTContent').is(":visible")){
							$('#GWTContent').hide();
							//$('#ViewerContent').show();
							runToggle(100, $('#ViewerContent'));
							$('#view_button').attr('class', 'view-selected');
							$('#edit_button').attr('class', '');   
						}
					}else
						{
							if ($('#ViewerContent').is(":visible")){
								$('#ViewerContent').hide();
								//$('#GWTContent').show();
								runToggle(100, $('#GWTContent'));
								$('#view_button').attr('class', '');
								$('#edit_button').attr('class', 'view-selected');
							}
						}
					});

				});
			})(jQuery);
		</script>
						</div>
						<div id="ViewerContent" class="readonly-view-view-with-edit">
							<?php include ("listview.html.php"); ?>
						</div>
						<div id="GWTContent"
							style="height:730px;  width: 100%;"
							targetPage= "<?php echo $gwt_page; ?>">

							<?php if ($canShowEditor): ?>
								<?php echo $lexSettings; ?>
								<script type="text/javascript" src="/../js/gwt/lifteditor/lifteditor.nocache.js"></script>
							<?php endif; ?>
						</div>
