package com.hope.apiapp.dto;

import jakarta.validation.constraints.NotNull;

public class JwtAuthenticationResponse {

	@NotNull(message = "accessToken is required")
	private String accessToken;
	private String tokenType = "Bearer";
	private Long userId = null;
	private Double userLat = null;
	private Double userLng = null;

	public JwtAuthenticationResponse(String accessToken, Long userId, Double userLat, Double userLng) {
		this.accessToken = accessToken;
		this.userId = userId;
		this.userLat = userLat;
		this.userLng = userLng;
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

	public Double getUserLat() {
		return userLat;
	}

	public void setUserLat(Double userLat) {
		this.userLat = userLat;
	}

	public Double getUserLng() {
		return userLng;
	}

	public void setUserLng(Double userLng) {
		this.userLng = userLng;
	}

}
