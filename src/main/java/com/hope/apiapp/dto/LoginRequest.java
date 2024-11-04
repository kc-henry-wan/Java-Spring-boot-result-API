package com.hope.apiapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginRequest {

	@Email(message = "email is required")
	@Size(max = 50, message = "email must not exceed 50 characters")
	private String username;

	@NotNull(message = "password is required")
	@Size(max = 30, message = "password must not exceed 30 characters")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
