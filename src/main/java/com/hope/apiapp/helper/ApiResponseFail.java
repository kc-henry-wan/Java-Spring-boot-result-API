package com.hope.apiapp.helper;

public class ApiResponseFail extends ApiResponseBody {
	// Constructor
	public ApiResponseFail(String errorCode, String errorMessage) {
		super("1.0", "Fail", errorCode, errorMessage);
	}
}
