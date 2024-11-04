package com.hope.apiapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PharmacistAddRequestDto {

	@NotBlank(message = "Password is required")
	@Size(max = 30, message = "Password must not exceed 30 characters")
	private String password;

	@Email(message = "Invalid email format")
	@Size(max = 50, message = "Email must not exceed 50 characters")
	private String email;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

}