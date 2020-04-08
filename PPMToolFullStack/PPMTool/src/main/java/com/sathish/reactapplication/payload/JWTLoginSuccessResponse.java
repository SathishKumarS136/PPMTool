package com.sathish.reactapplication.payload;

public class JWTLoginSuccessResponse {

	private String token;
	private boolean success;

	public JWTLoginSuccessResponse(String token, boolean success) {
		super();
		this.token = token;
		this.success = success;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "JWTLoginSuccessResponse [token=" + token + "]";
	}

}
