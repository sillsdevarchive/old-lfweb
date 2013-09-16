<nav class="accordion main_top_menu_group" style="display: none;" id="gwt-div-menu-root">
	<ul>
		<div class="main_top_menu_view" style="" id="gwt-div-root-view">
			<li><a class="noarrow mainNavBarboldTitle" href="#"
				onClick="showViewControl();"><span class="ico-view-switch">
						View </span></a></li>
		</div>
		
		<div class="main_top_menu" style="display: none;" id="gwt-div-dashboard">
			<li><a class="noarrow mainNavBarboldTitle" href="#"
				onClick="openGWTPageInner('dashboard');"><span class="ico-dashboard">
						Dashboard </span></a></li>
		</div>
		<div class="main_top_menu_190PX" style="display: none;" id="gwt-div-root-gatherword">
			<li><a href="#"> <span class="ico1"> Gather Words </span>
			</a>
				<div class="slide" style="display: none;">
					<ul>
						<div style="display: none;" id="gwt-div-review">
							<li><a class="slide li a" href="#"
								onClick="openGWTPageInner('review');">Review</a></li>
						</div>
						<div style="display: none;" id="gwt-div-fromtexts">
							<li><a class="slide li a" href="#"
								onClick="openGWTPageInner('fromtexts');">From texts</a></li>
						</div>
						<div style="display: none;" id="gwt-div-fromdomain">
							<li><a class="slide li a" href="#"
								onClick="openGWTPageInner('fromdomain');">From Semantic Domains</a></li>
						</div>
						<div style="display: none;" id="gwt-div-fromwordlist">
							<li><a class="slide li a" href="#"
								onClick="openGWTPageInner('fromwordlist');">From a Word List</a></li>
						</div>
						<div style="display: none;" id="gwt-div-browseandedit">
							<li><a class="gwt-Anchor" href="#"
								onClick="openGWTPageInner('browseandedit');">Browse and edit</a></li>
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
								onClick="openGWTPageInner('addmeanings');">Add meanings</a></li>
						</div>
						<div style="display: none;" id="gwt-div-addgrammaticalusage">
							<li><a class="gwt-Anchor" href="#"
								onClick="openGWTPageInner('addgrammaticalusage');">Add grammatical
									usage</a></li>
						</div>
						<div style="display: none;" id="gwt-div-addexamples">
							<li><a class="gwt-Anchor" href="#"
								onClick="openGWTPageInner('addexamples');">Add examples</a></li>
						</div>
					</ul>
				</div></li>
		</div>
		<div class="main_top_menu" style="display: none;" id="gwt-div-setting">
			<li><a class="noarrow" href="#" onClick="openGWTPageInner('setting');"><span
					class="ico-conf"> Settings </span></a></li>
		</div>
	</ul>
</nav>

<div id="ViewerContent" class="readonly-view-view-with-edit" style="display: none;">
	<?php include ("listview.html.php"); ?>
</div>
<div id="GWTContent" style="height: 730px; width: 100%;"
	targetPage="<?php echo $gwt_page; ?>">
	<?php if ($canShowEditor): ?>
	<?php echo $lexSettings; ?>
			<script type="text/javascript" src="/../js/gwt/lifteditor/lifteditor.nocache.js"></script>
	<?php endif; ?>
</div>


<script type="text/javascript">

	var showGWTControl = function(){
		document.getElementById('ViewerContent').style.display = "none";
		document.getElementById('GWTContent').style.display = "";
		return false;
	};
	
	var showViewControl = function(){
		document.getElementById('ViewerContent').style.display = "";
		document.getElementById('GWTContent').style.display = "none";
		return false;
	};
	
	var openGWTPageInner =  function(taskName) {
		showGWTControl();
		openGWTPage(taskName);
		return false;
	};
	var GWTTaskSettingsChanged = function(taskname, visible){

		document.getElementById('gwt-div-menu-root').style.display = "";
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