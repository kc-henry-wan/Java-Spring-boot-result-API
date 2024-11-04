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

public class PharmacistUpdateRequestDtoTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testValidPharmacistUpdateRequestDto() {
		PharmacistUpdateRequestDto dto = new PharmacistUpdateRequestDto();
		dto.setFirstName("John");
		dto.setLastName("Doe");
		dto.setMobile("+1234567890");
		dto.setAddress1("123 Main Street");
		dto.setAddress2("Suite 456");
		dto.setPostalCode("12345");
		dto.setStatus("Active");

		Set<ConstraintViolation<PharmacistUpdateRequestDto>> violations = validator.validate(dto);
		assertTrue(violations.isEmpty(), "Expected no validation errors");
	}

	@Test
	public void testFirstNameIsBlank() {
		PharmacistUpdateRequestDto dto = new PharmacistUpdateRequestDto();
		dto.setFirstName("");
		dto.setLastName("Doe");
		dto.setMobile("+1234567890");

		Set<ConstraintViolation<PharmacistUpdateRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("First name is required", violations.iterator().next().getMessage());
	}

	@Test
	public void testMobileNumberInvalidFormat() {
		PharmacistUpdateRequestDto dto = new PharmacistUpdateRequestDto();
		dto.setFirstName("John");
		dto.setLastName("Doe");
		dto.setMobile("123-456-7890"); // Invalid format

		Set<ConstraintViolation<PharmacistUpdateRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Mobile number must contain only digits and optional leading '+'",
				violations.iterator().next().getMessage());
	}

	@Test
	public void testStatusExceedsMaxLength() {
		PharmacistUpdateRequestDto dto = new PharmacistUpdateRequestDto();
		dto.setFirstName("John");
		dto.setLastName("Doe");
		dto.setMobile("+1234567890");
		dto.setStatus("ThisStatusIsTooLongForTheField");

		Set<ConstraintViolation<PharmacistUpdateRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Status must not exceed 20 characters", violations.iterator().next().getMessage());
	}

}