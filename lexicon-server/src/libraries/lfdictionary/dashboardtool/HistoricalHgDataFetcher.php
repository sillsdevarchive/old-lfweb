<?php
namespace dashboardtool;
class HistoricalHgDataFetcher extends \lfbase\common\HgWrapper
{
	function __construct($repositoryPath) {		
		parent::__construct($repositoryPath);
	}
}