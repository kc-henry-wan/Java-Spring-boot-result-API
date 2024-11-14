package com.hope.apiapp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;

public class JobUpdateRequestDto {

	@Size(max = 20, message = "New Status must not exceed 20 characters")
	private String action;
	private LocalDateTime updatedAt;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
