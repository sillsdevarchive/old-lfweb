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
	 * @return string $Id
	 */
	public static function updateEntry($params, $action, $project, $userId) {
		CodeGuard::checkTypeAndThrow($params, 'array');
		CodeGuard::checkTypeAndThrow($action, 'string');
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
		$result = $entry->write();
		ActivityCommands::writeEntry($project, $userId, $entry, $action);
		return $result;
	}
	
	/**
	 * @param array $userIds
	 * @return int Total number of users removed.
	 */
	public static function deleteUsers($userIds) {
		CodeGuard::checkTypeAndThrow($userIds, 'array');
		$count = 0;
		foreach ($userIds as $userId) {
 			CodeGuard::checkTypeAndThrow($userId, 'string');
			$userModel = new UserModel($userId);
			$userModel->remove();
			$count++;
		}
		return $count;
	}

}

?>
