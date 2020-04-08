package com.sathish.reactapplication.exception;

public class ProjectNotFoundExceptionResponse {

	private String projectIdentifier;

	public ProjectNotFoundExceptionResponse(String projectIdentifier) {
		super();
		this.projectIdentifier = projectIdentifier;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

}
