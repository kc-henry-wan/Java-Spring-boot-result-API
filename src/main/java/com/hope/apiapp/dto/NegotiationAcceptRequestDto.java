package com.hope.apiapp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;

public class NegotiationAcceptRequestDto {

	private Long jobId;

	@Size(max = 20, message = "aActino mode must not exceed 20 characters")
	private String mode;

	@Size(max = 20, message = "Status must not exceed 20 characters")
	private String status;

	private LocalDateTime updatedAt;
	private LocalDateTime jobUpdatedAt;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

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

	public LocalDateTime getJobUpdatedAt() {
		return jobUpdatedAt;
	}

	public void setJobUpdatedAt(LocalDateTime jobUpdatedAt) {
		this.jobUpdatedAt = jobUpdatedAt;
	}

}
