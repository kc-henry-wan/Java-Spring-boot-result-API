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

public class NegotiationUpdateRequestDtoTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testValidNegotiationUpdateRequestDto() {
		NegotiationUpdateRequestDto dto = new NegotiationUpdateRequestDto();
		dto.setCounterHourlyRate(new BigDecimal("52.50"));
		dto.setCounterTotalPaid(new BigDecimal("420.00"));
		dto.setStatus("Counter");

		Set<ConstraintViolation<NegotiationUpdateRequestDto>> violations = validator.validate(dto);
		assertTrue(violations.isEmpty(), "Expected no validation errors");
	}

	@Test
	public void testStatusCodeExceedsMaxLength() {
		NegotiationUpdateRequestDto dto = new NegotiationUpdateRequestDto();
		dto.setStatus("A12345678901234567890");

		Set<ConstraintViolation<NegotiationUpdateRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("New Status must not exceed 20 characters", violations.iterator().next().getMessage());
	}

}