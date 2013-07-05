package org.palaso.languageforge.client.lex.common;

import org.palaso.languageforge.client.lex.model.CurrentEnvironmentDto;

import com.google.gwt.core.client.JsArrayNumber;

public final class PermissionManager {
	/**
	 * get permission by type
	 * 
	 * @param type
	 * @return
	 */
	public static boolean getPermission(ProjectPermissionType type) {
		return getPermission(type, false);
	}

	/**
	 * get permission by type
	 * 
	 * @param type
	 * @param if set to TRUE, admin right will not overwrite others
	 * @return
	 */
	public static boolean getPermission(ProjectPermissionType type, boolean IgnoreAdminTakeover) {

		// ADMIN has all right.
		if (!IgnoreAdminTakeover && hasAdminPermission()) {
			return true;
		} else {
			return (getPermissionInternal(type));
		}
	}

	private static boolean hasAdminPermission() {
		return getPermissionInternal(ProjectPermissionType.CAN_ADMIN);
	}

	private static boolean getPermissionInternal(ProjectPermissionType type) {
		if (CurrentEnvironmentDto.getCurrentProjectAccess() == null) {
			return false;
		}
		if (CurrentEnvironmentDto.getCurrentProjectAccess().getActiveRole() == null
				|| CurrentEnvironmentDto.getCurrentProjectAccess().getActiveRole() == "") {
			return false;
		}
		if (CurrentEnvironmentDto.getCurrentProjectAccess().getPermissions() == null
				|| CurrentEnvironmentDto.getCurrentProjectAccess().getPermissions().length() == 0) {
			return false;
		}
		JsArrayNumber permissionArray = CurrentEnvironmentDto.getCurrentProjectAccess().getPermissions();
		for (int i = 0; i < permissionArray.length(); i++) {
			int permission = (int) permissionArray.get(i);
			if (type.getValue() == String.valueOf(permission)) {
				return true;
			}
		}
		return false;
	}
}
