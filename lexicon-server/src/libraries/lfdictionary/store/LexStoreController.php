<?php
namespace libraries\lfdictionary\store;

use libraries\lfdictionary\dto\ListDTO;
use libraries\lfdictionary\environment\LexProject;

class LexStoreType
{
	const STORE_TEST  = 0;
	const STORE_MONGO = 1;
}
class LexStoreController
{
	
	/**
	 * @var string
	 */
	private $_databaseName;
	
	/**
	 * @var LexProject
	 */
	private $_lexProject;
	
	/**
	 * @var ILexStore
	 */
	private $_lexStore;
	
	/**
	* @var int
	*/
	private $_lexStoreType;
	
	
	/**
	 * @param string $databaseName
	 * @param LexProject $lexProject
	 */
	public function __construct($lexStoreType, $databaseName, $lexProject) {
		$this->_lexStoreType = $lexStoreType;
		$this->_databaseName = $databaseName;
		$this->_lexProject = $lexProject;
		$this->_lexStore = LexStoreFactory::getLexStore($this->_lexStoreType, $databaseName);
		}
	
	/**
	 * Writes the Lexical Entry to the Store.
	 * @param EntryDTO $entry
	 * @param string $action
	 */
	public function writeEntry($entry, $action) {
		$this->_lexStore->writeEntry($entry);
	}
	
	/**
	 * Reads a Lexical Entry from the Store
	 * @param string $guid
	 * @return EntryDTO
	 */
	public function readEntry($guid) {
		return $this->_lexStore->readEntry($guid);
	}
	
	/**
	 * Returns $entryCount entries starting at $startAt
	 * @param int $startAt
	 * @param int $maxEntryCount
	 * @return ListDTO
	 */
	public function readEntriesAsListDTO($startAt, $maxEntryCount) {
		$this->updateIfNeeded();
		return $this->_lexStore->readEntriesAsListDTO($startAt, $maxEntryCount);
	}
	
	/**
	 * Returns the total number of Entries in the Store.
	 * @return int
	 */
	public function entryCount() {
		$this->updateIfNeeded();
		return $this->_lexStore->entryCount();
	}
	
	/**
	 * Deletes an entry from the Store
	 * @param string $guid
	 * @param string $mercurialSHA
	 */
	public function deleteEntry($guid, $mercurialSHA) {
		return $this->_lexStore->deleteEntry($guid);
	}
	
	/**
	 * Returns a list of suggestions that are similar to the $search term given.
	 * @param string $field
	 * @param string $search
	 * @param int $indexFrom
	 * @param int $limit
	 * @return \dto\AutoListDTO
	 */
	public function readSuggestions($field, $search, $indexFrom, $limit) {
		return $this->_lexStore->readSuggestions($field, $search, $indexFrom, $limit);
	}

	/**
	 * Returns a ListDTO of entries that do not have data for $language in the given $field.
	 * @param LexStoreMissingInfo $field
	 * @param string $language
	 * @return \dto\ListDTO
	 */
	public function readMissingInfo($field, $language = null) {
		$this->updateIfNeeded();
		return $this->_lexStore->readMissingInfo($field, $language);
	}
	
	private function updateIfNeeded() {
		$projectHash = $this->_lexProject->getCurrentHash();
		$mongoHash = $this->_lexStore->readHashOfLastUpdate();
		if ($projectHash != $mongoHash) {
			$projectHash = $this->_lexProject->getCurrentHash();
			$liftImporter = LiftImporterFactory::getImportFactory($this->_lexStoreType, $this->_lexProject->getLiftFilePath(), $this->_databaseName);
			$liftImporter->update(LiftImporterUpdatePolicy::OVERWRITE); // TODO not the best policy, so change this at some stage. CP 2012-11
			$this->_lexStore->writeHashOfLastUpdate($projectHash);
		}
	}
	
	public function searchEntriesAsWordList($lang, $titleLetter, $startFrom, $maxEntryCount)
	{
		return $this->_lexStore->searchEntriesAsWordList($lang, $titleLetter, $startFrom, $maxEntryCount);
	}
	
}

?>
