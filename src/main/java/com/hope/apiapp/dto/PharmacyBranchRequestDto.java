package com.hope.apiapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PharmacyBranchRequestDto {

	@NotBlank(message = "Branch name is required")
	@Size(max = 255, message = "Branch name must not exceed 255 characters")
	private String branchName;

	@NotBlank(message = "Address 1 is required")
	@Size(max = 50, message = "Address 1 must not exceed 50 characters")
	private String address1;

	@Size(max = 50, message = "Address 2 must not exceed 50 characters")
	private String address2;

	@NotBlank(message = "Postal code is required")
	@Size(max = 10, message = "Postal code must not exceed 10 characters")
	private String postalCode;

	@Size(max = 20, message = "Status must not exceed 20 characters")
	private String status;

	// Getters and setters

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
