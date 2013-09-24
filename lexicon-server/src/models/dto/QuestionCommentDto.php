<?php

namespace models\dto;

use models\UserVoteModel;

use models\ProjectModel;
use models\QuestionModel;
use models\TextModel;
use models\UserModel;
use models\mapper\JsonEncoder;
use models\mapper\Id;
class QuestionCommentDto
{
	/**
	 * Encodes a QuestionModel and related data for $questionId
	 * @param string $projectId
	 * @param string $questionKey
	 * @param string $userId
	 * @return array - The DTO.
	 */
	public static function encode($projectId, $entryId, $questionKey, $userId) {
		// Question Key format: {partGuid}+{parttype}+{partlanguage}
		
		$keyParts = explode ('+', $questionKey);
		$partLanguage = "";
		$partGuid = $keyParts[0];
		$partType = $keyParts[1];
		if (count($keyParts)==3)
		{
			$partLanguage = $keyParts[2];
		}
		
		$userModel = new UserModel($userId);
		$projectModel = new ProjectModel($projectId);
		
		$entryDto = new EntryDto();
		$entry =  $entryDto->encode($projectId, $entryId);
		$entryHelper = new EntryHelper($entry);
		$questionModel = new QuestionModel($projectModel);
		$questionModel->findOneByQuery(array("entryId" =>$entryId , "entryRef" => $questionKey));
		
		if (Id::isEmpty($questionModel->id))
		{
			// new Question.
			$questionModel->entryRef = $questionKey;
			$questionModel->entryId = $entryId;
			$questionModel->entry = json_encode(EntryDto::encode($projectId, $entryId));
			$questionModel->title = strtolower($partType) . " / " .
					$partLanguage . ": " . $entryHelper->getPartData($partType, $partGuid, $partLanguage);
			$questionModel->write();
		}
		$question = QuestionCommentDtoEncoder::encode($questionModel);

		
 		$votes = new UserVoteModel($userId, $projectId, $questionModel->id->asString());
 		$votesDto = array();
 		foreach ($votes->votes->data as $vote) {
 			$votesDto[$vote->answerRef->id] = true;
 		}
		
		$dto = array();
		$dto['question'] = $question;
		//$dto['votes'] = $votesDto;
		$dto['entry'] = $entry;
		//$dto['text'] = JsonEncoder::encode($textModel);
		$dto['text']['title'] = $projectModel->languageCode . ": " . $entry["entry"][$projectModel->languageCode];
		$dto['project'] = JsonEncoder::encode($projectModel);
		$dto['rights'] = RightsHelper::encode($userModel, $projectModel);

		return $dto;
	}
	
	/**
	 * Encodes a $answerModel in the same method as returned by the 
	 * @param AnswerModel $answerModel
	 * @return array - The DTO.
	 */
	public static function encodeAnswer($answerModel) {
		$dto = QuestionCommentDtoEncoder::encode($answerModel);
		return $dto;
	}
	
	/**
	 * Encodes a $commentModel in the same method as returned by the 
	 * @param CommentModel $commentModel
	 * @return array - The DTO.
	 */
	public static function encodeComment($commentModel) {
		$dto = QuestionCommentDtoEncoder::encode($commentModel);
		return $dto;
	}
	
}

?>
