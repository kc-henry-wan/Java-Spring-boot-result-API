package com.hope.apiapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PharmacistRequest {

	@NotBlank(message = "Password is mandatory")
	@Size(max = 255, message = "Password cannot be longer than 255 characters")
	private String password;

	@Email(message = "Invalid email format")
	@Size(max = 100, message = "Email cannot be longer than 100 characters")
	private String email;

	@NotBlank(message = "First name is mandatory")
	@Size(max = 50, message = "First name cannot be longer than 50 characters")
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	@Size(max = 50, message = "Last name cannot be longer than 50 characters")
	private String lastName;

	@NotBlank(message = "Mobile number is mandatory")
	@Size(max = 20, message = "Mobile number cannot be longer than 20 characters")
	private String mobile;

	@NotBlank(message = "Address1 is mandatory")
	@Size(max = 50, message = "Address1 cannot be longer than 50 characters")
	private String address1;

	@Size(max = 50, message = "Address2 cannot be longer than 50 characters")
	private String address2;

	@NotBlank(message = "Postal code is mandatory")
	@Size(max = 10, message = "Postal code cannot be longer than 10 characters")
	private String postalCode;

	// Getters and Setters
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
