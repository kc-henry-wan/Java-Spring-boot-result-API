package com.hope.apiapp.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class NegotiationAddRequestDtoTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testValidNegotiationAddRequestDto() {
		NegotiationAddRequestDto dto = new NegotiationAddRequestDto();
		dto.setJobId(1);
		dto.setPharmacistId(1);
		dto.setOriginalHourlyRate(new BigDecimal("50.00"));
		dto.setOriginalTotalPaid(new BigDecimal("400.00"));
		dto.setPurposedHourlyRate(new BigDecimal("55.00"));
		dto.setPurposedTotalPaid(new BigDecimal("440.00"));

		Set<ConstraintViolation<NegotiationAddRequestDto>> violations = validator.validate(dto);
		assertTrue(violations.isEmpty(), "Expected no validation errors");
	}

	@Test
	public void testJobIdIsNull() {
		NegotiationAddRequestDto dto = new NegotiationAddRequestDto();
		dto.setJobId(null);
		dto.setPharmacistId(1);
		dto.setOriginalHourlyRate(new BigDecimal("50.00"));

		Set<ConstraintViolation<NegotiationAddRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Job ID is required", violations.iterator().next().getMessage());
	}

	@Test
	public void testPharmacistIdIsNull() {
		NegotiationAddRequestDto dto = new NegotiationAddRequestDto();
		dto.setJobId(1);
		dto.setPharmacistId(null);
		dto.setOriginalHourlyRate(new BigDecimal("50.00"));

		Set<ConstraintViolation<NegotiationAddRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Pharmacist ID is required", violations.iterator().next().getMessage());
	}

	@Test
	public void testOriginalHourlyRateIsZero() {
		NegotiationAddRequestDto dto = new NegotiationAddRequestDto();
		dto.setJobId(1);
		dto.setPharmacistId(1);
		dto.setOriginalHourlyRate(new BigDecimal("0.00"));

		Set<ConstraintViolation<NegotiationAddRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Original hourly rate must be greater than 0", violations.iterator().next().getMessage());
	}

}
