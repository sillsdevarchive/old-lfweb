package org.palaso.languageforge.client.lex.service.actions;

import org.palaso.languageforge.client.lex.common.BaseConfiguration;
import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.ProjectDto;

public class UpdateProjectNameAction extends JsonRpcAction<ProjectDto> {

	int projectId;
	String projecName;

	public UpdateProjectNameAction(int projectId, String projecName) {
		super(BaseConfiguration.getInstance().getLFApiPath(), BaseConfiguration.getInstance().getApiFileName(), "updateProjectName", 2);
		this.projectId = projectId;
		this.projecName = projecName;
	}


	@Override
	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return String.valueOf(projectId);
		case 1:
			return projecName;
		}
		return null;
	}
}
