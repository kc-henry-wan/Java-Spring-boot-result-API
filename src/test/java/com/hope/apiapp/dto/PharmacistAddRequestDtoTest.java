package com.hope.apiapp.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PharmacistAddRequestDtoTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testValidPharmacistAddRequestDto() {
		PharmacistAddRequestDto dto = new PharmacistAddRequestDto();
		dto.setPassword("StrongPassword123!");
		dto.setEmail("example@example.com");
		dto.setFirstName("John");
		dto.setLastName("Doe");
		dto.setMobile("+1234567890");
		dto.setAddress1("123 Main Street");
		dto.setAddress2("Suite 456");
		dto.setPostalCode("12345");

		Set<ConstraintViolation<PharmacistAddRequestDto>> violations = validator.validate(dto);
		assertTrue(violations.isEmpty(), "Expected no validation errors");
	}

	@Test
	public void testPasswordIsBlank() {
		PharmacistAddRequestDto dto = new PharmacistAddRequestDto();
		dto.setPassword("");
		dto.setFirstName("John");
		dto.setLastName("Doe");
		dto.setMobile("+1234567890");

		Set<ConstraintViolation<PharmacistAddRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Password is required", violations.iterator().next().getMessage());
	}

	@Test
	public void testEmailInvalidFormat() {
		PharmacistAddRequestDto dto = new PharmacistAddRequestDto();
		dto.setPassword("StrongPassword123!");
		dto.setEmail("invalid-email");
		dto.setFirstName("John");
		dto.setLastName("Doe");
		dto.setMobile("+1234567890");

		Set<ConstraintViolation<PharmacistAddRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Invalid email format", violations.iterator().next().getMessage());
	}

	@Test
	public void testFirstNameIsBlank() {
		PharmacistAddRequestDto dto = new PharmacistAddRequestDto();
		dto.setPassword("StrongPassword123!");
		dto.setFirstName("");
		dto.setLastName("Doe");
		dto.setMobile("+1234567890");

		Set<ConstraintViolation<PharmacistAddRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("First name is required", violations.iterator().next().getMessage());
	}

	@Test
	public void testMobileNumberInvalidFormat() {
		PharmacistAddRequestDto dto = new PharmacistAddRequestDto();
		dto.setPassword("StrongPassword123!");
		dto.setFirstName("John");
		dto.setLastName("Doe");
		dto.setMobile("123-456-7890"); // Invalid format

		Set<ConstraintViolation<PharmacistAddRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Mobile number must contain only digits and optional leading '+'",
				violations.iterator().next().getMessage());
	}

}
