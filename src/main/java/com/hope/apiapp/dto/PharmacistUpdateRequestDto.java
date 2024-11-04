package com.hope.apiapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PharmacistUpdateRequestDto {

	@NotBlank(message = "First name is required")
	@Size(max = 50, message = "First name must not exceed 50 characters")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(max = 50, message = "Last name must not exceed 50 characters")
	private String lastName;

	@NotBlank(message = "Mobile number is required")
	@Size(max = 20, message = "Mobile number must not exceed 20 characters")
	@Pattern(regexp = "^[+]?[0-9]*$", message = "Mobile number must contain only digits and optional leading '+'")
	private String mobile;

	@Size(max = 50, message = "Address 1 must not exceed 50 characters")
	private String address1;

	@Size(max = 50, message = "Address 2 must not exceed 50 characters")
	private String address2;

	@Size(max = 10, message = "Postal code must not exceed 10 characters")
	private String postalCode;

	@Size(max = 20, message = "Status must not exceed 20 characters")
	private String status;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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