<nav class="accordion main_top_menu_group">
	<ul>
		<div class="main_top_menu" style="display: none;" id="gwt-div-dashboard">
			<li><a class="noarrow mainNavBarboldTitle" href="#"
				onClick="openGWTPage('dashboard')"><span class="ico-dashboard">
						Dashboard </span></a></li>
		</div>
		<div class="main_top_menu_190PX" style="display: none;" id="gwt-div-root-gatherword">
			<li><a href="#"> <span class="ico1"> Gather Words </span>
			</a>
				<div class="slide" style="display: none;">
					<ul>
						<div style="display: none;" id="gwt-div-review">
							<li><a class="slide li a" href="#"
								onClick="openGWTPage('review');">Review</a></li>
						</div>
						<div style="display: none;" id="gwt-div-fromtexts">
							<li><a class="slide li a" href="#"
								onClick="openGWTPage('fromtexts');">From texts</a></li>
						</div>
						<div style="display: none;" id="gwt-div-fromdomain">
							<li><a class="slide li a" href="#"
								onClick="openGWTPage('fromdomain');">From Semantic Domains</a></li>
						</div>
						<div style="display: none;" id="gwt-div-fromwordlist">
							<li><a class="slide li a" href="#"
								onClick="openGWTPage('fromwordlist');">From a Word List</a></li>
						</div>
						<div style="display: none;" id="gwt-div-browseandedit">
							<li><a class="gwt-Anchor" href="#"
								onClick="openGWTPage('browseandedit');">Browse and edit</a></li>
						</div>
					</ul>
				</div></li>
		</div>
		<div class="main_top_menu_190PX" style="display: none;" id="gwt-div-root-addinfo">
			<li><a href="#"> <span class="ico3"> Add Information </span>
			</a>
				<div class="slide" style="display: none;">
					<ul>
						<div style="display: none;" id="gwt-div-addmeanings">
							<li><a class="gwt-Anchor" href="#"
								onClick="openGWTPage('addmeanings');">Add meanings</a></li>
						</div>
						<div style="display: none;" id="gwt-div-addgrammaticalusage">
							<li><a class="gwt-Anchor" href="#"
								onClick="openGWTPage('addgrammaticalusage');">Add grammatical
									usage</a></li>
						</div>
						<div style="display: none;" id="gwt-div-addexamples">
							<li><a class="gwt-Anchor" href="#"
								onClick="openGWTPage('addexamples');">Add examples</a></li>
						</div>
					</ul>
				</div></li>
		</div>
		<div class="main_top_menu" style="display: none;" id="gwt-div-setting">
			<li><a class="noarrow" href="#" onClick="openGWTPage('setting');"><span
					class="ico-conf"> Settings </span></a></li>
		</div>
	</ul>
</nav>

<script type="text/javascript">

	var GWTTaskSettingsChanged = function(taskname, visible){
		console.log(taskname + " / " + visible);
		if (visible)
		{
			document.getElementById('gwt-div-'+taskname).style.display = "";
		}else
		{
			document.getElementById('gwt-div-'+taskname).style.display = "none";
		}

		//Gather Words root menu
		if (document.getElementById('gwt-div-review').style.display == "none" &&
				document.getElementById('gwt-div-fromtexts').style.display == "none" &&
				document.getElementById('gwt-div-fromdomain').style.display == "none" &&
				document.getElementById('gwt-div-fromwordlist').style.display == "none" &&
				document.getElementById('gwt-div-browseandedit').style.display == "none")
		{
			document.getElementById('gwt-div-root-gatherword').style.display = "none";
		}else
		{
			document.getElementById('gwt-div-root-gatherword').style.display = "";
		}

		//Addinfo root menu
		if (document.getElementById('gwt-div-addmeanings').style.display == "none" &&
			document.getElementById('gwt-div-addgrammaticalusage').style.display == "none" &&
			document.getElementById('gwt-div-addexamples').style.display == "none")
		{
			document.getElementById('gwt-div-root-addinfo').style.display = "none";
		}else
		{
			document.getElementById('gwt-div-root-addinfo').style.display = "";
		}
	};

</script>