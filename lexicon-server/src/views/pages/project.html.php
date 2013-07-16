<div id="main">
	<div class="main-holder">
		<div class="clear-block">
			<div class="tabs"></div>
			<div class="region region-content">
				<div class="block block-system" id="block-system-main">
					<div class="content">

					<?php if (!$logged_in): ?>
						<!--  user not loged in, show only view-->
						<div id="ViewerContent" class="readonly-view-view-only">
							
						<?php include ("listview.html.php"); ?>
						</div>
						
						

<?php else: ?>
<div id="ViewSwitch" class="dic-view-switch">
	<aside id="sidebar-view-switch">
		<nav class="accordion">
			<ul id="view-switch">
				<div style="text-align:center;">
					<div style=" display:inline-block; width:107px; float: left;">
						<li id="view_button" style=" border-radius: 10px 0px 0px 10px;"> <a class="view-switch-noarrow" href="#"><span class="ico-view-switch "> View </span></a> </li>
					</div>
					<div style=" display:inline-block; width:107px float: left;">
						<li id="edit_button" class ="view-selected" style=" border-radius: 0px 10px 10px 0px;"> <a class="view-switch-noarrow" href="#"><span class="ico-edit-switch "> Edit </span></a> </li>
					</div>
				</div>
			</ul>
		</nav>
	</aside>
	<script type="text/javascript">
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
						$('#GWTContent').hide();
						$('#ViewerContent').show();
						$('#view_button').attr('class', 'view-selected');
						$('#edit_button').attr('class', '');
					}else
						{
							$('#ViewerContent').hide();
							$('#GWTContent').show();
							$('#view_button').attr('class', '');
							$('#edit_button').attr('class', 'view-selected');
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
		style="height: 710px; width: 100%; padding-top: 10px;">

		<?php
		//	error_reporting(E_ALL | E_STRICT);
		require_once(APPPATH . '/libraries/lfdictionary/Config.php');
		require_once(APPPATH . '/helpers/loader_helper.php');

		//	$errorHandler = new \libraries\lfdictionary\common\ErrorHandler();
		if ($project_id!=null && $project_id!="" && $user_id!=null && $user_id!="") {
			$lexClientEnvironment = new libraries\lfdictionary\environment\LexClientEnvironment($project_id, $user_id);
			echo $lexClientEnvironment->getSettings();
			?>
			<script type="text/javascript" language="javascript"
			src="/../js/gwt/lifteditor/lifteditor.nocache.js"></script>
			<?php
		}
		?>
	</div>

	<?php endif; ?>
	</div>
				</div>
			</div>
		</div>
	</div>
</div>
