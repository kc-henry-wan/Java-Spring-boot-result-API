package com.hope.apiapp.util;

public class ApiResponseSuccess<T> extends ApiResponseBody {
	private T data;

	// Constructor
	public ApiResponseSuccess(String apiVersion, T data) {
		super(apiVersion, "Success", "", ""); // Success response has no error code or message
		this.data = data;
	}

	// Getter and Setter for data
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
