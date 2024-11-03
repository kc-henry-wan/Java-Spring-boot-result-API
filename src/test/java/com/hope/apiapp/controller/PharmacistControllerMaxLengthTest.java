package com.hope.apiapp.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hope.apiapp.dto.PharmacistRequest;
import com.hope.apiapp.service.PharmacistService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class PharmacistControllerMaxLengthTest {

	@InjectMocks
	private PharmacistController pharmacistController;

	@Mock
	private PharmacistService pharmacistService;

	private Validator validator;

	// Initialize the validator in the constructor or a setup method
	public PharmacistControllerMaxLengthTest() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testUpdatePharmacist_MaxLengthExceeded() {
		// Arrange
		Long id = 1L;
		PharmacistRequest request = new PharmacistRequest();
		request.setPassword("a".repeat(256)); // Exceeds max length of 255
		request.setEmail("a".repeat(101) + "@example.com"); // Exceeds max length of 100
		request.setFirstName("a".repeat(51)); // Exceeds max length of 50
		request.setLastName("a".repeat(51)); // Exceeds max length of 50
		request.setMobile("a".repeat(21)); // Exceeds max length of 20
		request.setAddress1("a".repeat(51)); // Exceeds max length of 50
		request.setPostalCode("a".repeat(11)); // Exceeds max length of 10

		// Act & Assert
		assertThatThrownBy(() -> validator.validate(request)).isInstanceOf(ConstraintViolationException.class)
				.hasMessageContaining("Password cannot be longer than 255 characters")
				.hasMessageContaining("Email cannot be longer than 100 characters")
				.hasMessageContaining("First name cannot be longer than 50 characters")
				.hasMessageContaining("Last name cannot be longer than 50 characters")
				.hasMessageContaining("Mobile number cannot be longer than 20 characters")
				.hasMessageContaining("Address1 cannot be longer than 50 characters")
				.hasMessageContaining("Postal code cannot be longer than 10 characters");
	}
}
