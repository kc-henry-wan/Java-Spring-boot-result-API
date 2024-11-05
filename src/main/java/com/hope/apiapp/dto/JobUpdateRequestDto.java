package com.hope.apiapp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;

public class JobUpdateRequestDto {

	@Size(max = 20, message = "New Status must not exceed 20 characters")
	private String status;
	private LocalDateTime updatedAt;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
