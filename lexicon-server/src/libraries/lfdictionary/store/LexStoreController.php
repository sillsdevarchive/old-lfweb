<?php
namespace libraries\lfdictionary\store;

use libraries\lfdictionary\dto\ListDTO;
use libraries\lfdictionary\environment\LexProject;
use libraries\lfdictionary\dto\EntryDTO;
use libraries\lfdictionary\dto\Example;

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
	public function writeEntry($entry, $action, $userId, $userName) {
		$this->_lexStore->writeEntry($this->entryMetadataUpdater($entry, $userId, $userName));
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

	public function  getAllEntries() {
		return $this->_lexStore->getAllEntries();
	}


	private function entryMetadataUpdater(EntryDTO $entry, $userId, $userName)
	{
		$date = new \DateTime();
		$unixTimeStamp = $date->getTimestamp();
		$original = $this->readEntry($entry->getGuid());
		if ($original == null)
		{
			//new Entry
			$entry->_metadata->_createdby=$userName;
			$entry->_metadata->_createdbyId=$userId;
			$entry->_metadata->_createdDate=$unixTimeStamp;
			$entry->_metadata->_modifiedBy=$userName;
			$entry->_metadata->_modifiedById=$userId;
			$entry->_metadata->_modifiedDate=$unixTimeStamp;
			//sense
			foreach ($entry->_senses as $sense)
			{
				$sense->_metadata->_createdby=$userName;
				$sense->_metadata->_createdbyId=$userId;
				$sense->_metadata->_createdDate=$unixTimeStamp;
				$sense->_metadata->_modifiedBy=$userName;
				$sense->_metadata->_modifiedById=$userId;
				$sense->_metadata->_modifiedDate=$unixTimeStamp;
				//example
				foreach ($sense->_examples as $example)
				{
					$example->_metadata->_createdby=$userName;
					$example->_metadata->_createdbyId=$userId;
					$example->_metadata->_createdDate=$unixTimeStamp;
					$example->_metadata->_modifiedBy=$userName;
					$example->_metadata->_modifiedById=$userId;
					$example->_metadata->_modifiedDate=$unixTimeStamp;
				}
			}
		}else {
			//check and update exists Entry
			$newEntryCopy = EntryDTO::createFromArray(unserialize(serialize($entry->encode())));
			$originalEntryCopy = EntryDTO::createFromArray(unserialize(serialize($original->encode())));

			$copyOfSenses = $newEntryCopy->_senses;
			$copyOfOriginalSenses = $originalEntryCopy->_senses;

			//remove non-need part
			$newEntryCopy->_senses = Array();
			$originalEntryCopy->_senses = Array();
			$newEntryCopy->_metadata = Array();
			$originalEntryCopy->_metadata = Array();

			//compare
			if (json_encode($newEntryCopy)!==json_encode($originalEntryCopy))
			{
				//changed
				$entry->_metadata->_modifiedBy=$userName;
				$entry->_metadata->_modifiedById=$userId;
				$entry->_metadata->_modifiedDate=$unixTimeStamp;

				//sense
				foreach ($copyOfSenses as $sense)
				{
					$isSenseMatch = false;
					foreach ($copyOfOriginalSenses as $originalSense)
					{
						if ($sense->getId()==$originalSense->getId())
						{
							$isSenseMatch = true;
							break;
						}
							
					}
					if ($isSenseMatch==true)
					{
						$newSenseCopy = Sense::createFromArray(unserialize(serialize($sense->encode())));
						$originalSenseCopy = Sense::createFromArray(unserialize(serialize($originalSense->encode())));
							
						$copyOfExamples = $newSenseCopy->_examples;
						$copyOfOriginalExamples = $originalSenseCopy->_examples;
			
						//remove non-need part
						$newSenseCopy->_examples = Array();
						$originalSenseCopy->_examples = Array();
						$newSenseCopy->_metadata = Array();
						$originalSenseCopy->_metadata = Array();

						//compare
						if (json_encode($newEntryCopy)!==json_encode($originalEntryCopy))
						{
							//changed
							$sense->_metadata->_modifiedBy=$userName;
							$sense->_metadata->_modifiedById=$userId;
							$sense->_metadata->_modifiedDate=$unixTimeStamp;
						}
					}else {
						// new sense
						$sense->_metadata->_createdby=$userName;
						$sense->_metadata->_createdbyId=$userId;
						$sense->_metadata->_createdDate=$unixTimeStamp;
						$sense->_metadata->_modifiedBy=$userName;
						$sense->_metadata->_modifiedById=$userId;
						$sense->_metadata->_modifiedDate=$unixTimeStamp;
					}
					
					//Example
					foreach ($copyOfExamples as $example)
					{
						$isExampleMatch = false;
						foreach ($copyOfOriginalExamples as $originalExample)
						{
							if ($example->getId()==$originalExample->getId())
							{
								$isExampleMatch = true;
								break;
							}

						}
						
						if ($isExampleMatch==true)
						{
							$newExampleCopy = Example::createFromArray(unserialize(serialize($example->encode())));
							$originalExampleCopy = Example::createFromArray(unserialize(serialize($originalExample->encode())));


							//remove non-need part
							$newExampleCopy->_metadata = Array();
							$originalExampleCopy->_metadata = Array();

							//compare
							if (json_encode($newExampleCopy)!==json_encode($originalExampleCopy))
							{
								//changed
								$example->_metadata->_modifiedBy=$userName;
								$example->_metadata->_modifiedById=$userId;
								$example->_metadata->_modifiedDate=$unixTimeStamp;
							}
						}else {
							// new example
							$example->_metadata->_createdby=$userName;
							$example->_metadata->_createdbyId=$userId;
							$example->_metadata->_createdDate=$unixTimeStamp;
							$example->_metadata->_modifiedBy=$userName;
							$example->_metadata->_modifiedById=$userId;
							$example->_metadata->_modifiedDate=$unixTimeStamp;
						}
					}
				}
			}
		}

		return $entry;
	}
}

?>
