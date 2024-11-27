package com.hope.apiapp.dto;

import jakarta.validation.constraints.NotNull;

public class JwtAuthenticationResponse {

	@NotNull(message = "accessToken is required")
	private String accessToken;
	private String tokenType = "Bearer";
	private Long userId = null;

	public JwtAuthenticationResponse(String accessToken, Long userId) {
		this.accessToken = accessToken;
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
