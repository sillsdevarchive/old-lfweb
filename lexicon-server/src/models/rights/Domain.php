<?php
namespace models\rights;

class Domain {

	const ANY			= 100;
	const USERS			= 110;
	const PROJECTS		= 120;
	const QUESTIONS		= 130;
	const ANSWERS		= 140;
	const COMMENTS		= 150;
	const LEX_ENTRY		= 160;
	
	public static $domains = array(
			self::ANY,
			self::USERS,
			self::PROJECTS,
			self::QUESTIONS,
			self::ANSWERS,
			self::COMMENTS,
			self::LEX_ENTRY
	);
	
}

?>