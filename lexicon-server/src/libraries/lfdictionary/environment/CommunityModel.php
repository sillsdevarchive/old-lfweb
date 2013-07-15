<?php
/** 
 * LanguageForge Dictionary API
 * @author Arivusudar
 */
namespace libraries\lfdictionary\environment;
require_once(dirname(__FILE__) . '/../Config.php');


class CommunityModel
{
	/**
	 * @var int
	 */
	var $result;
	 
	/**
	 * @var string
	 */
	var $sql;
	
	/**
	 * @var int
	 */
	var $_projectId;
	
	
	function __construct($projectId) {
	
		$this->_projectId = $projectId;		
		//Database connection
		$this->_connection = new \libraries\lfdictionary\common\DataConnection(DB_SERVER, DB_USER, DB_PASS, DB_NAME);
		$this->_connection->open();
		$this->_communityListdto = new \libraries\lfdictionary\dto\CommunityListDTO();
	}
	
	function __destruct() {
		
		//Database Connection close
		$this->_connection->close();
	}
	
	
	/**
	 * This method used to Add New community
	 * @return bool value
	 */
	function addCommunity($newCommunity) {
		
		$sql = "SELECT * FROM node WHERE LOWER(title) = LOWER('$newCommunity[title]')";
		$sql_query = $this->_connection->execute($sql);
		$num_row = $this->_connection->numrows($sql_query);
		if($num_row == 0) {
			$node = new \stdClass();
			$node->type = 'community';
			$node->language = 'en';
			$node->uid = $newCommunity['uid'];
			$node->title = $newCommunity['title'];
			$node->og_description = $newCommunity['code'];
			$node->og_directory = 1;
			node_save($node);
			//og_insert_group($node);
			og_save_subscription($node->nid, $node->uid, array('is_active' => 1, 'is_admin' => 1));
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return string
	 * This method used to List of Community by title
	 */
	function listCommunities($from, $to) {

		$result = db_query_range("SELECT n.nid, n.title FROM node n WHERE n.status = 1 AND n.type = 'community'", $from, $to);

		while ($community = db_fetch_object($result)) {
			$CommunityDTO = new \libraries\lfdictionary\dto\CommunityDTO();
			$CommunityDTO->addCommunityId($community->nid);
			$CommunityDTO->addCommunityName($community->title);
			$this->_communityListdto->addListCommunity($CommunityDTO);
		}
		
		return $this->_communityListdto;
	}
	
	/**
	 * @return string
	 * This method used to search the Community by title
	 */
	function searchCommunity($string, $maxResultCount) {
		
		$result = db_query_range("SELECT n.nid, n.title FROM node n WHERE n.status = 1 AND n.type = 'community' AND LOWER(n.title) LIKE LOWER('%s%%')", $string, 0, $maxResultCount);
		
		while ($community = db_fetch_object($result)) {
			$communityDTO = new \libraries\lfdictionary\dto\CommunityDTO();
			$communityDTO->addCommunityId($community->nid);
			$communityDTO->addCommunityName($community->title);
			$this->_communityListdto->addListCommunity($communityDTO);
		}
		
		return $this->_communityListdto;
	}	
}

?>