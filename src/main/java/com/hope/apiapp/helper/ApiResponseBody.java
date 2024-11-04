package com.hope.apiapp.helper;

public abstract class ApiResponseBody {
	private String apiVersion;
	private String apiStatus;
	private String errorCode;
	private String errorMessage;

	// Constructor
	public ApiResponseBody(String apiVersion, String apiStatus, String errorCode, String errorMessage) {
		this.apiVersion = apiVersion;
		this.apiStatus = apiStatus;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	// Getters and Setters
	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(String apiStatus) {
		this.apiStatus = apiStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
