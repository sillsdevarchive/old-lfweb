<?php

use libraries\communicate\IDelivery;
use libraries\communicate\Communicate;
use libraries\communicate\Email;
use libraries\sms\SmsModel;
use models\MessageModel;
use models\ProjectModel;
use models\UnreadMessageModel;
use models\UserModel;

require_once(dirname(__FILE__) . '/../TestConfig.php');
require_once(SimpleTestPath . 'autorun.php');

require_once(TEST_PATH . 'common/MongoTestEnvironment.php');

class MockCommunicateDelivery implements IDelivery
{
	public $from;
	public $to;
	public $subject;
	public $content;
	public $smsModel;

	public function sendEmail($from, $to, $subject, $content) {
		$this->from = $from;
		$this->to = $to;
		$this->subject = $subject;
		$this->content = $content;
	}
	
	public function sendSms($smsModel) {
		$this->smsModel = $smsModel;
	}
	
}

class TestCommunicate extends UnitTestCase {

	function testCommunicateToUser_SendEmail_PropertiesToFromMessageOk() {
		$e = new MongoTestEnvironment();
		$e->clean();
		$userId = $e->createUser("User", "Name", "name@example.com");
		$user = new UserModel($userId);
		$user->communicate_via = UserModel::COMMUNICATE_VIA_EMAIL;
		$project = $e->createProjectSettings(LF_TESTPROJECT);
		$subject = 'TestSubject';
		$project->emailSettings->fromAddress = 'projectName@scriptureforge.org';
		$project->emailSettings->fromName = 'ScriptureForge ProjectName';
		$smsTemplate = '';
		$emailTemplate = 'TestMessage';
		$delivery = new MockCommunicateDelivery();
		
		Communicate::communicateToUser($user, $project, $subject, $smsTemplate, $emailTemplate, $delivery);
		
		// What's in the delivery?
		$expectedFrom = array($project->emailSettings->fromAddress => $project->emailSettings->fromName);
		$expectedTo = array($user->email => $user->name);
		$this->assertEqual($expectedFrom, $delivery->from);
		$this->assertEqual($expectedTo, $delivery->to);
		$this->assertEqual($subject, $delivery->subject);
		$this->assertEqual($emailTemplate, $delivery->content);
		
	}
	
	function testCommunicateToUsers_SendEmail_BroadcastMessageStoredAndUnread() {
		$e = new MongoTestEnvironment();
		$e->clean();
		$userId = $e->createUser("User", "Name", "name@example.com");
		$user = new UserModel($userId);
		$user->communicate_via = UserModel::COMMUNICATE_VIA_EMAIL;
		$project = $e->createProjectSettings(LF_TESTPROJECT);
		$subject = 'TestSubject';
		$project->emailSettings->fromAddress = 'projectName@scriptureforge.org';
		$project->emailSettings->fromName = 'ScriptureForge ProjectName';
		$smsTemplate = '';
		$emailTemplate = 'TestMessage';
		$delivery = new MockCommunicateDelivery();
		
		Communicate::communicateToUsers(array($user), $project, $subject, $smsTemplate, $emailTemplate, $delivery);
		
		$unread = new UnreadMessageModel($userId, $project->id->asString());
		$messageIds = $unread->unreadItems();
		$this->assertEqual(count($messageIds), 1);
		
		$messageId = $messageIds[0];
		$message = new MessageModel($project, $messageId);
		$this->assertEqual($message->subject, $subject);
		$this->assertEqual($message->content, $emailTemplate);
	}
	
	function testSendSignup_NoProject_PropertiesToFromBodyOk() {
		$e = new MongoTestEnvironment();
		$e->clean();
		$userId = $e->createUser("User", "Name", "name@example.com");
		$user = new UserModel($userId);
		$project = null;
		$delivery = new MockCommunicateDelivery();
		
		Communicate::sendSignup($user, $project, $delivery);
		
		// What's in the delivery?
		$expectedFrom = array(LF_DEFAULT_EMAIL => LF_DEFAULT_EMAIL_NAME);
		$expectedTo = array($user->emailPending => $user->name);
		$this->assertEqual($expectedFrom, $delivery->from);
		$this->assertEqual($expectedTo, $delivery->to);
		$this->assertNoPattern('/' . LF_TESTPROJECT . '/', $delivery->subject);
		$this->assertPattern('/Name/', $delivery->content);
		$this->assertPattern('/' . $user->validationKey . '/', $delivery->content);
		$this->assertNoPattern('/' . LF_TESTPROJECT . '/', $delivery->content);
	}

	function testSendSignup_WithProject_PropertiesToFromBodyOk() {
		$e = new MongoTestEnvironment();
		$e->clean();
		$userId = $e->createUser("User", "Name", "name@example.com");
		$user = new UserModel($userId);
		$project = $e->createProject(LF_TESTPROJECT);
		$delivery = new MockCommunicateDelivery();
		
		Communicate::sendSignup($user, $project, $delivery);
		
		// What's in the delivery?
		$expectedFrom = array(LF_DEFAULT_EMAIL => LF_DEFAULT_EMAIL_NAME);
		$expectedTo = array($user->emailPending => $user->name);
		$this->assertPattern('/' . LF_TESTPROJECT . '/', $delivery->from[LF_DEFAULT_EMAIL]);
		$this->assertEqual($expectedTo, $delivery->to);
		$this->assertPattern('/' . LF_TESTPROJECT . '/', $delivery->subject);
		$this->assertPattern('/Name/', $delivery->content);
		$this->assertPattern('/' . $user->validationKey . '/', $delivery->content);
		$this->assertPattern('/' . LF_TESTPROJECT . ' Team/', $delivery->content);
	}

	function testCommunicateToUser_SendSms_PropertiesToFromMessageProviderInfoOk() {
		if (isset($project->smsSettings)) {
			$e = new MongoTestEnvironment();
			$e->clean();
			$userId = $e->createUser("User", "Name", "name@example.com");
			$user = new UserModel($userId);
			$user->communicate_via = UserModel::COMMUNICATE_VIA_SMS;
			$user->mobile_phone = '+66837610205';
			$project = $e->createProjectSettings(LF_TESTPROJECT);
			$project->smsSettings->fromNumber = '13852904211';
			$project->smsSettings->accountId = 'ACc03c2767c2c9c138bde0aa0b30ac9d6e';
			$project->smsSettings->authToken = 'be77f02cd3b6b13d3b42d8a64050fd35';
			$subject = '';
			$smsTemplate = 'Test message';
			$emailTemplate = '';
			$delivery = new MockCommunicateDelivery();
		
			Communicate::communicateToUser($user, $project, $subject, $smsTemplate, $emailTemplate, $delivery);
		
			// What's in the delivery?
			$expectedTo = $user->mobile_phone;
			$expectedFrom = $project->smsSettings->fromNumber;
			$expectedProviderInfo = $project->smsSettings->accountId . '|' . $project->smsSettings->authToken;
			$this->assertEqual($expectedTo, $delivery->smsModel->to);
			$this->assertEqual($expectedFrom, $delivery->smsModel->from);
			$this->assertEqual($smsTemplate, $delivery->smsModel->message);
			$this->assertEqual(SmsModel::SMS_TWILIO, $delivery->smsModel->provider);  // expected to be set by default
			$this->assertEqual($expectedProviderInfo, $delivery->smsModel->providerInfo);
		}	
	}
	
	function testSendNewUserInProject_PropertiesFromToBodyOk() {
		$e = new MongoTestEnvironment();
		$e->clean();
		$toUserId = $e->createUser("touser", "To Name", "toname@example.com");
		$toUser = new UserModel($toUserId);
		$newUserName = 'newusername';
		$newUserPassword = 'password';
		$project = $e->createProjectSettings(LF_TESTPROJECT);
		$delivery = new MockCommunicateDelivery();
		
		Communicate::sendNewUserInProject($toUser, $newUserName, $newUserPassword, $project, $delivery);
		
		// What's in the delivery?
		$expectedFrom = array(LF_DEFAULT_EMAIL => LF_DEFAULT_EMAIL_NAME);
		$expectedTo = array($toUser->email => $toUser->name);
		$this->assertEqual($expectedFrom, $delivery->from);
		$this->assertEqual($expectedTo, $delivery->to);
		$this->assertPattern('/To Name/', $delivery->content);
		$this->assertPattern('/' . $newUserName . '/', $delivery->content);
		$this->assertPattern('/' . $newUserPassword . '/', $delivery->content);
	}

}

?>
