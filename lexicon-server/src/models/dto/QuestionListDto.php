<?php

namespace models\dto;

use models\ProjectModel;
use models\QuestionAnswersListModel;
use models\TextModel;
use models\UserModel;

class QuestionListDto
{
	/**
	 *
	 * @param string $projectId
	 * @param string $textId
	 * @param string $userId
	 * @returns array - the DTO array
	 */
	public static function encode($projectId, $entryGuid, $userId) {
		$userModel = new UserModel($userId);
		$projectModel = new ProjectModel($projectId);
		//$textModel = new TextModel($projectModel, $textId);
		$questionList = new QuestionAnswersListModel($projectModel, $entryGuid);
		$questionList->read();

		$data = array();
		$data['rights'] = RightsHelper::encode($userModel, $projectModel);
		$data['count'] = $questionList->count;
		$data['projectlanguagecode'] = $projectModel->languageCode;
		$data['entries'] = array();
		$data['project'] = array(
				'name' => $projectModel->projectname,
				'id' => $projectId);
		$entryDto = new EntryDto();
		$entry =  $entryDto->encode($projectId, $entryGuid);
		$data['entry'] = $entry;
		
		$data['text'] = array(
				'title' =>  $entry["entry"][$projectModel->languageCode],
				'id' => $entryGuid);
		
		foreach ($questionList->entries as $questionData) {
			// Just want answer count, not whole list
			$questionData['answerCount'] = count($questionData['answers']);
			unset($questionData['answers']);

			$data['entries'][] = $questionData;
		}

		return $data;
	}
}

?>
