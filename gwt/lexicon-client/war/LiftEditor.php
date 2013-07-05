<!doctype html>
<!-- The DOCTYPE declaration above will set the    -->
<!-- browser's rendering engine into               -->
<!-- "Standards Mode". Replacing this declaration  -->
<!-- with a "Quirks Mode" doctype may lead to some -->
<!-- differences in layout.                        -->

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<META http-equiv="CACHE-CONTROL" content="NO-CACHE">
<!--                                                               -->
<!-- Consider inlining CSS to reduce the number of requested files -->
<!--                                                               -->
<!--<link type="text/css" rel="stylesheet" href="LiftEditor.css">  -->
<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
		<link media="all" rel="stylesheet" href="css/all.css" >
		<link media="all" rel="stylesheet" href="css/onlinetool.css" >
		<link media="all" rel="stylesheet" href="css/gwt-lf.css">
		<link href="http://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet" type="text/css" >
		<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="js/jquery.main.js"></script>
<!--[if lt IE 8]><link rel="stylesheet" type="text/css" href="css/ie.css" /><![endif]-->
<!--                                           -->
<!-- Any title is fine                         -->
<!--                                           -->
<title>Language Forge Dictionary</title>

<!--                                           -->
<!-- This script loads your compiled module.   -->
<!-- If you add any GWT meta tags, they must   -->
<!-- be added before this line.                -->
<!--                                           -->
<script type="text/javascript" language="javascript" src="lifteditor/lifteditor.nocache.js"></script>
</head>

<!--                                           -->
<!-- The body can have arbitrary html, or      -->
<!-- you can leave the body empty if you want  -->
<!-- to create a completely dynamic UI.        -->
<!--                                           -->
<body class="flexible">
<?php
		error_reporting(E_ALL | E_STRICT);
		require_once('/var/www/languageforge.local/api/lex/Config.php');
		require_once(LF_BASE_PATH . "lfbase/Loader.php");

		//Load Drupal		
		$errorHandler = new \lfbase\common\ErrorHandler();
		\lfbase\common\LFDrupal::loadDrupal();

		global $user; // TODO the user info should be encapsulated in LFDrupal CP 2012-09
		$projectNodeId = isset($_GET['projectid']) ? $_GET['projectid'] : $_GET['p'];

		$lexClientEnvironment = new \environment\LexClientEnvironment($projectNodeId, $user->uid);
		echo $lexClientEnvironment->getSettings();
		
		$_SESSION['projectid'] = $projectNodeId;
 	?>
	
	<!-- OPTIONAL: include this if you want history support -->
	<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
		style="position: absolute; width: 0; height: 0; border: 0"></iframe>

	<!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
	<noscript>
		<div
			style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
			Your web browser must have JavaScript enabled in order for this
			application to display correctly.</div>
	</noscript>

</body>
</html>