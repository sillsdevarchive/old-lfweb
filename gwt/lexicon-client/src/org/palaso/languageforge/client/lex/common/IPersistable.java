package org.palaso.languageforge.client.lex.common;

import org.palaso.languageforge.client.lex.model.UserDto;

/**
 * 
 * To be implemented by presenters that have content that can be persisted.
 * 
 * 
 */
public interface IPersistable {
	/**
	 * call to this to notify the presenter to persist the content to the server
	 * 
	 * @param user
	 *            not required, but in almost all cases null means to all user.
	 * @return true or false, true means pre-condition check passed, otherwise
	 *         false.
	 */
	public boolean persistData(UserDto user);

	/**
	 * 
	 * @return true means the control supports save based on each user; false means
	 *         only save as global setting
	 */
	public boolean isMultiUserSupported();

	/**
	 * 
	 * @return true means the control supports to let the parent persist it's data; false means
	 *         the control persists the data by itself.
	 *         
	 *         Note: If a control has no parent then it will always return false;
	 *         
	 */
	public boolean isPersistDataByParentSupported();

}