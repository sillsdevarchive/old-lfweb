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
	public static boolean getPermission(DomainPermissionType domain,
			OperationPermissionType operation) {
		ConsoleLog.log("Rights Check asked for: " +  domain.getValue() + "/" + operation.getValue());
		if (CurrentEnvironmentDto.getCurrentProjectAccess() == null) {
			ConsoleLog.log("Denied! No permission");
			return false;
		}
		if (CurrentEnvironmentDto.getCurrentProjectAccess().getPermissions() == null
				|| CurrentEnvironmentDto.getCurrentProjectAccess()
						.getPermissions().length() == 0) {
			ConsoleLog.log("Denied! No permission");
			return false;
		}
		JsArrayNumber permissionArray = CurrentEnvironmentDto
				.getCurrentProjectAccess().getPermissions();
		for (int i = 0; i < permissionArray.length(); i++) {
			int permission = (int) permissionArray.get(i);
			if (Integer.parseInt(domain.getValue())
					+ Integer.parseInt(operation.getValue()) == permission) {
				ConsoleLog.log("Accpet!");
				return true;
			}
		}
		ConsoleLog.log("No permission matchs");
		return false;
	}

	public static UserRoleType getCurrentUserRole()
	{
		if (CurrentEnvironmentDto.getCurrentProjectAccess() == null) {
			return UserRoleType.UNDEFINED;
		}
		return CurrentEnvironmentDto.getCurrentProjectAccess().getRole();
	}

	public static boolean isProjectAdmin() {
		if (getCurrentUserRole()==UserRoleType.PROJECT_ADMIN)
		{
			return true;
		}
		return false;
	}
	
}
