						<div id="ViewSwitch" class="dic-view-switch">
							<aside id="sidebar-view-switch">
								<nav class="accordion">
									<ul id="view-switch" class="view-switch">
										<div style="text-align: center;">
											<div
												style="display: inline-block; width: 107px; float: left;">
												<li id="view_button"
													style="border-radius: 10px 0px 0px 10px;"><a
													class="view-switch-noarrow" href="#"><span
														class="ico-view-switch "> View </span> </a>
												</li>
											</div>
											<div
												style="display: inline-block; width: 107px float:     left;">
												<li id="edit_button" class="view-selected"
													style="border-radius: 0px 10px 10px 0px;"><a
													class="view-switch-noarrow" href="#"><span
														class="ico-edit-switch "> Edit </span> </a>
												</li>
											</div>
										</div>
									</ul>
								</nav>
							</aside>
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
							style="height: 710px; width: 100%; padding-top: 10px;"
							targetPage= "<?php echo $gwt_page; ?>">

							<?php if ($canShowEditor): ?>
								<?php echo $lexSettings; ?>
								<script type="text/javascript" src="/../js/gwt/lifteditor/lifteditor.nocache.js"></script>
							<?php endif; ?>
						</div>
