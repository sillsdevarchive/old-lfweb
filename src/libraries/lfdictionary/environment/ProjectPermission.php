<?php
namespace lfbase\environment;

class ProjectPermission {

	//Notes: Server / Client mapping is in ProjectAccessDTO.php.
	/**
	 * CAN_ADMIN :  if set, user will automaticlly have all permissions in case client allow admin take over.
	 * 				if client not allow admin take over, you will need to set permissions to admin one by one.
	 * 				Ref: PermissionManager.java
	 */
	const CAN_ADMIN            		= 'can_admin';
	
	/**
	* CAN_CREATE_ENTRY :  if set, user will allow to create a new entry.
	*/
	const CAN_CREATE_ENTRY     		= 'can_create_entry';
	
	/**
	* CAN_EDIT_ENTRY :  if set, user will allow to modify a existing entry.
	*/
	const CAN_EDIT_ENTRY         	= 'can_edit_entry';
	
	/**
	* CAN_DELETE_ENTRY :  if set, user will allow to delete a existing entry.
	*/
	const CAN_DELETE_ENTRY       	= 'can_delete_entry';
	
	/**
	* CAN_EDIT_REVIEW_ALL :  if set, user will allow to modify reivews include created by other user.
	*/
	const CAN_EDIT_REVIEW_ALL 		= 'can_edit_reivew_all';
	
	/**
	* CAN_EDIT_REVIEW_OWN :  if set, user will allow to modify own reivews.
	*/
	const CAN_EDIT_REVIEW_OWN 		= 'can_edit_reivew_own';
	
	/**
	* CAN_CREATE_REVIEW :  if set, user will allow to create a new review.
	*/
	const CAN_CREATE_REVIEW 		= 'can_create_reivew';

	/**
	* CAN_REPLY_REVIEW :  if set, user will allow to reply a review.
	*/
	const CAN_REPLY_REVIEW 		= 'can_reply_reivew';



	private $_permissions;

	public function __construct() {
		$this->_permissions = func_get_args();
	}

	public function has($permission) {
		return in_array($permission, $this->_permissions);
	}
	
	public function asArray() {
		return $this->_permissions;
	}

}

?>