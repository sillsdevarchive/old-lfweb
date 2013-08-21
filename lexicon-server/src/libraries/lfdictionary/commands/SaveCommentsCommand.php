<?php
namespace libraries\lfdictionary\commands;
require_once(dirname(__FILE__) . '/../Config.php');
class SaveCommentsCommand {

	//if need to change follow definations, please change client side too.
	public static $TYPE_UNDEFINED = "undefined";
	public static $TYPE_QUESTION = "question";
	public static $TYPE_MERGECONFLICT = "mergeConflict";

	public static $STATUS_UNDEFINED = "undefined";
	public static $STATUS_CLOSED = "closed";
	public static $STATUS_REVIEWED = "reviewed";
	public static $STATUS_TODO = "todo";


	var $_fileName;
	var $_status;
	var $_isStatusReviewed;
	var $_isStatusTodo;
	var $_type;
	var $_parentGuid;
	var $_message;
	var $_datetime;
	var $_author;
	var $_isRoot;
	var $_dto;

	function __construct($fileName,$status,$isStatusReviewed,$isStatusTodo,$type,$parentGuid,$message,$datetime,$author, $isRoot) {
		if (!file_exists($fileName))
		{
			throw new \Exception('ChorusNotes file is missing on server: ' . $fileName);
		}
		$this->_fileName = $fileName;
		$this->_status = $status;
		$this->_isStatusReviewed = $isStatusReviewed;
		$this->_isStatusTodo = $isStatusTodo;
		$this->_type = $type;
		$this->_parentGuid = $parentGuid;
		$this->_message = $message;
		$this->_datetime = $datetime;
		$this->_author = $author;
		$this->_isRoot = $isRoot;
	}



	function execute(){
		$this->processFile();
		return $this->_dto;
	}


	function processFile() {

		$this->_dto = new \libraries\lfdictionary\dto\ConversationDTO();
		$doc = new \DOMDocument;
		$doc->preserveWhiteSpace = false;
		$doc->Load($this->_fileName);
		$newNodeGuid="";
		$xpath = new \DOMXPath($doc);

		if ($this->_isRoot=="1")
		{
			//TODO: 2012-06 XZ: maybe we also want create new question or conflict
		}else{
			$entries = $xpath->query('//notes/annotation/message[@guid="' . $this->_parentGuid . '"]');
			if ($entries->length!=1)
			{
				throw new \Exception("message with guid $this->_parentGuid not exist or broken");
			}else
			{
				$newMessageNode = $doc->createElement("message");
				$newMessageNode->nodeValue=$this->_message;
				$newMessageNode->setAttribute("author", $this->_author);

				if  ($this->_status==GetCommentsCommand::$STATUS_CLOSED)
				{
					$newMessageNode->setAttribute("status", GetCommentsCommand::$STATUS_CLOSED);
					$newMessageNode->setAttribute("status.resolved", "true");
				}else
				{
					$newMessageNode->setAttribute("status", "");
					$newMessageNode->setAttribute("status.resolved", "false");
				}

				
				if  ($this->_isStatusReviewed==GetCommentsCommand::$STATUS_REVIEWED)
				{
					$newMessageNode->setAttribute("status.reviewed", "true");
				}else
				{
					$newMessageNode->setAttribute("status.reviewed", "false");
				}
				
				if  ($this->_isStatusTodo==GetCommentsCommand::$STATUS_TODO)
				{
					$newMessageNode->setAttribute("status.todo", "true");
				}else
				{
					$newMessageNode->setAttribute("status.todo", "false");
				}
				
							
				$newMessageNode->setAttribute("date", $this->_datetime);
				$newNodeGuid=strtolower (\libraries\lfdictionary\common\UUIDGenerate::uuid_generate_php());
				$newMessageNode->setAttribute("guid",  $newNodeGuid);
					
				$entries->item(0)->parentNode->appendChild($newMessageNode);
			}
		}
		$doc->formatOutput=true;
		$doc->save($this->_fileName);

		$this->_dto->setAuthor($this->_author);
		$this->_dto->setClass("");
		$this->_dto->setComment($this->_message);
		$this->_dto->setDate(strtotime($this->_datetime));
		$this->_dto->setGuid($newNodeGuid);
		$this->_dto->setReference("");
		$this->_dto->setStatus($this->_status);
		$this->_dto->setStatusReviewed($this->_reviewed);
		$this->_dto->setStatusTodo($this->_todo);
	}


}
?>