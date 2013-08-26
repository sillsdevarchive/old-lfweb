		<div id="header" class="png_bg">
			
			<div class="sfcontainer">
				<div class="lf-logo">
					<img src="/images/lf_logo.png" alt="Language Forge" width="96" height="117" class="png_bg" />
				</div>
				<div id="header-nav" class="left">
					<ul class="sf-menu">
						<li><a href="/">Home</a></li>
						<li><a href="#">Explore</a>
							<ul>
								<li><a href="#">Jamaica Project 1</a></li>
								<!--
								<li><a href="#">Sub Menu Item 2</a>
									<ul>
										<li><a href="#">Another Sub Menu Item 1</a></li>
										<li><a href="#">Another Sub Menu Item 2</a></li>
										<li><a href="#">Another Sub Menu Item 3</a></li>
									</ul>
								</li>
								-->
								<li><a href="#">Jamaica Project 2</a></li>
								<li><a href="#">Jamaica Project 3</a></li>
								<?php foreach($all_projects as $project): ?>
											<li><a href="<?php echo "/project?pid=" . $project['id']; ?>"><?php echo $project['projectname']; ?></a></li>
								<?php endforeach;?>
								<li><a href="/project?pid=51e3b48b9cde7fef33e7aef7">LF Project Page</a></li>
							</ul>
						</li>
						<li><a href="/learn_language_forge">Learn</a>
							<ul>
								<li><a href="/learn_language_forge">About Language Forge</a></li>
								<li><a href="/learn_expand_your_team">Expand Your Team</a></li>
								<li><a href="/learn_contribute">Contribute</a></li>
							</ul>
						</li>
						<li><a href="/contribute">Contribute</a></li>
						<li><a href="/discuss">Discuss</a></li>
					</ul>
				</div>
				
				<?php if ($logged_in):?>
					<div class="right">
							<ul class="sf-menu">
								<li><a href="/app/projects">My Projects</a>
									<ul>
									<?php foreach($projects as $project): ?>
										<li><a href="<?php echo "/project?pid=" . $project['id']; ?>"><?php echo $project['projectname']; ?></a></li>
									<?php endforeach;?>
									</ul>
								</li>
							</ul>
							<ul class="sf-menu">
							<li>
							<a href="#"><img src="<?php echo $small_avatar_url; ?>" style="width:30px; height:30px; float:left; position:relative; top:-6px; border:1px solid white; margin-right:10px" />Hi, <?php echo $user_name; ?></a>
								<ul>
									<?php if ($is_admin):?>
									<li><a href="/app/lfadmin">Site Administration</a></li>
									<?php endif;?>
									<li><a href="/app/userprofile">My Profile</a></li>
									<li><a href="/app/changepassword">Change Password</a></li>
									<li><a href="/auth/logout">Logout</a></li>
								</ul>
							</li>
						</ul>
					</div>
				
				<?php else:?>
					<div id="account" class="right">
						<input type="button" value="Login" class="login-btn left" onclick="window.location='/auth/login'"/> &nbsp; or &nbsp; <a href="#">Create an Account</a>
					</div>
				<?php endif;?>
				
			</div>
		</div>