<?php

namespace models\lex;

use models\mapper\Id;
use models\ProjectModel;

class LexEntryModelMongoMapper extends \models\mapper\MongoMapper {

	/**
	 * @var LexEntryModelMongoMapper[]
	 */
	private static $_pool = array();
	
	/**
	 * @param string $databaseName
	 * @return LexEntryModelMongoMapper
	 */
	public static function connect($databaseName) {
		if (!isset(static::$_pool[$databaseName])) {
			static::$_pool[$databaseName] = new LexEntryModelMongoMapper($databaseName, 'texts');
		}
		return static::$_pool[$databaseName];
	}
	
}

class LexEntryModel extends \models\mapper\MapperModel {

	/**
	 * @param ProjectModel $projectModel
	 * @param string $id
	 */
	public function __construct($projectModel, $id = '')
	{
		$this->id = new Id();
		$this->_projectModel = $projectModel;
		$databaseName = $projectModel->databaseName();
		parent::__construct(LexEntryModelMongoMapper::connect($databaseName), $id);
	}
	
	/**
	 * @var IdReference
	 */
	public $id;
	
	/**
	 *
	 * @var string
	 */
	public $mercurialSHA;

	/**
	 * This is a single LF domain
	 * @var MultiText
	 */
	public $lexeme; // TODO Renamed $_entry to $lexeme, remove this comment when stitched in IJH 2013-11

	/**
	 * @var array<Sense>
	 */
	public $senses;

	/**
	 *
	 * @var AuthorInfoModel
	 */
	public $authorInfo; // TODO Renamed $_metadata to $authorInfo, remove this comment when stitched in IJH 2013-11

	/**
	 * @var ProjectModel;
	 */
	private $_projectModel;
	
	/**
	 * Remove this LexEntry from the collection
	 * @param unknown $databaseName
	 * @param unknown $id
	 */
	public static function remove($id) {
		$databaseName = $_projectModel->databaseName();
		LexEntryModelMongoMapper::connect($databaseName)->remove($id);
	}

// TODO all the following functions are deprecated, remove when stiched in IJH 2013-11	
	
	/**
	 * @param string $guid
	 */
	function setGuid($guid) {
		$this->_guid = $guid;
	}

	/**
	 * @return string
	 */
	function getGuid() {
		return $this->_guid;
	}

	/**
	 * @param string $mercurialSHA
	 */
	function setMercurialSHA($mercurialSHA) {
		$this->mercurialSHA = $mercurialSHA;
	}

	/**
	 * @return string
	 */
	function getMercurialSHA() {
		return $this->mercurialSHA;
	}

	/**
	 * @param MultiText $multitext
	 */
	function setEntry($multitext) {
		$this->_entry = $multitext;
	}

	/**
	 * @return MultiText
	 */
	function getEntry() {
		return $this->_entry;
	}

	/**
	 * @param Sense $sense
	 */
	function addSense($sense) {
		$this->_senses[] = $sense;
	}

	/**
	 * @return int
	 */
	function senseCount() {
		return count($this->_senses);
	}

	/**
	 * @param int $index
	 * @return Sense
	 */
	function getSense($index) {
		return $this->_senses[$index];
	}

	/**
	 * @return LexEntryModel
	 */
	static function create($guid) {
		return new LexEntryModel($guid);
	}

	/**
	 * @return LexEntryModel
	 */
	static function createFromArray($value) {
		$result = new LexEntryModel();
		$result->decode($value);
		return $result;
	}

}

class LexEntryListModel extends \models\mapper\MapperListModel {

	public function __construct($projectModel) {
		parent::__construct(
				LexEntryModelMongoMapper::connect($projectModel->databaseName()),
				array('lexeme' => array('$regex' => '')),
				array('lexeme')
		);
	}

}

?>
