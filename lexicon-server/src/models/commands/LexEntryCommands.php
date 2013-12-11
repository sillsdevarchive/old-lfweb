<?php

namespace models\commands;

use libraries\palaso\CodeGuard;
use libraries\lfdictionary\common\UserActionDeniedException;
use models\commands\ActivityCommands;
use models\mapper\JsonEncoder;
use models\mapper\JsonDecoder;
use models\lex\LexEntryModel;
use models\rights\Domain;
use models\rights\Operation;
use models\UserModel;
use models\mapper\ArrayOf;

class LexEntryCommands {

	/**
	 * Included to allow automatic adding of 'use' statments when constructed IJH 2013-12
	 */
	public function __construct() {
	}
	
	/**
	 * Create/Update a single Lexical Entry
	 * @param LexEntryModel $params
	 * @param Action $action
	 * @throws UserActionDeniedException
	 * @return string $entryId
	 */
	public static function updateEntry($params, $action, $project, $userId) {
		CodeGuard::checkTypeAndThrow($params, 'array');
		CodeGuard::checkTypeAndThrow($userId, 'string');
		
		// Check that user has edit privileges on the project
		if (! $project->hasRight($userId, Domain::LEX_ENTRY + Operation::EDIT_OTHER)) {
			throw new UserActionDeniedException('Access Denied For Update');
		}
		
		// Update entry
		$entry = new LexEntryModel($project);
		if ($params['id']) {
			$entry->read($params['id']);
		}
		JsonDecoder::decode($entry, $params);
		$entryId = $entry->write();
		ActivityCommands::writeEntry($project, $userId, $entry, $action);
		return $entryId;
	}
	/**
	 * @param ProjectModel $project
	 * @param array $entryIds
	 * @return int Total number of entries removed.
	 */
	public static function deleteEntries($project, $userId, $entryIds) {
		CodeGuard::checkTypeAndThrow($userId, 'string');
		CodeGuard::checkTypeAndThrow($entryIds, 'array');

		// TODO Add. class to include passing in mecurial SHA during delete. IJH 2013-12	
/*		class LexEntryId
		{
		public $id;
		public $mercurialSha;
		}
		
		$x = new ArrayOf(function($data) {
			return new LexEntryId();
		});
		JsonDecoder::decode($x, $entryIds);
*/

		// Error Validtion for User having access to Delete the project
		if (! $project->hasRight($userId, Domain::LEX_ENTRY + Operation::DELETE_OTHER)) {
			throw new UserActionDeniedException('Access Denied For Delete');
		}
		
		$count = 0;
		foreach ($entryIds as $entryId) {
 			CodeGuard::checkTypeAndThrow($entryId, 'string');
 			ActivityCommands::deleteEntry($project, $userId, $entryId);
			LexEntryModel::remove($project, $entryId);
			$count++;
		}
		return $count;
	}

}

?>
