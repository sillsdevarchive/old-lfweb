<?php

namespace models\lex;

use models\mapper\Id;
use models\ProjectModel;
use models\mapper\ArrayOf;

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
			static::$_pool[$databaseName] = new LexEntryModelMongoMapper($databaseName, 'lex');
		}
		return static::$_pool[$databaseName];
	}
	
}

class LexEntryModel extends \models\mapper\MapperModel {

	/**
	 * @param ProjectModel $projectModel
	 * @param string $id
	 */
	public function __construct($projectModel, $id = '') {
		$this->id = new Id();
		$this->_projectModel = $projectModel;
		$this->lexeme = new MultiText();
		$this->senses = new ArrayOf(ArrayOf::OBJECT, function($data) {
			return new Sense();
		});
		$this->authorInfo = new AuthorInfo();
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
	 * @var ArrayOf ArrayOf<Sense>
	 */
	public $senses;

	/**
	 *
	 * @var AuthorInfo
	 */
	public $authorInfo; // TODO Renamed $_metadata to $authorInfo, remove this comment when stitched in IJH 2013-11

	/**
	 * @var ProjectModel;
	 */
	private $_projectModel;
	
	/**
	 * Remove this LexEntry from the collection
	 * @param ProjectModel $projectModel
	 * @param unknown $id
	 */
	public static function remove($projectModel, $id) {
		$databaseName = $projectModel->databaseName();
		LexEntryModelMongoMapper::connect($databaseName)->remove($id);
	}

}

class LexEntryListModel extends \models\mapper\MapperListModel {

	public function __construct($projectModel) {
		parent::__construct(
				LexEntryModelMongoMapper::connect($projectModel->databaseName()),
				array(),
				array('lexeme', 'senses')
		);
	}

}

?>
