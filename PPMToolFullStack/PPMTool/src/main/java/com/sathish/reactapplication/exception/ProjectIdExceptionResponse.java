package com.sathish.reactapplication.exception;

public class ProjectIdExceptionResponse {

	private String projectNotFound;

	public ProjectIdExceptionResponse(String projectIdentifier) {
		super();
		this.projectNotFound = projectIdentifier;
	}

	public String getProjectIdentifier() {
		return projectNotFound;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectNotFound = projectIdentifier;
	}

}
