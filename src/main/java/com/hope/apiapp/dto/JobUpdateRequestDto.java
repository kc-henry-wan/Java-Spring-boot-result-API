package com.hope.apiapp.dto;

import jakarta.validation.constraints.Size;

public class JobUpdateRequestDto {

	@Size(max = 20, message = "New Status must not exceed 20 characters")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
