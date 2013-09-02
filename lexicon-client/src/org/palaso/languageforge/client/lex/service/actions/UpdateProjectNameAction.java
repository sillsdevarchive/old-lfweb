package org.palaso.languageforge.client.lex.service.actions;

import org.palaso.languageforge.client.lex.jsonrpc.JsonRpcAction;
import org.palaso.languageforge.client.lex.model.ProjectDto;

public class UpdateProjectNameAction extends JsonRpcAction<ProjectDto> {

	String projectId;
	String projecName;

	public UpdateProjectNameAction(String projectId, String projecName) {
		super("updateProjectName", 2);
		this.projectId = projectId;
		this.projecName = projecName;
	}


	@Override
	public String encodeParam(int i) {
		switch (i) {
		case 0:
			return projectId;
		case 1:
			return projecName;
		}
		return null;
	}
}
