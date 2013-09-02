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
						<?php include("editview.html.php")?>
						<?php endif; ?>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
