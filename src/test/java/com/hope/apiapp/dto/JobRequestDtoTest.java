package com.hope.apiapp.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class JobRequestDtoTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testValidJobAddRequestDto() {
		JobRequestDto dto = new JobRequestDto();
		dto.setJobDate(LocalDate.now());
		dto.setJobStartTime(LocalTime.of(9, 0));
		dto.setJobEndTime(LocalTime.of(17, 0));
		dto.setHourlyRate(BigDecimal.valueOf(50.00));
		dto.setTotalWorkHour(BigDecimal.valueOf(8.00));
		dto.setTotalPaid(BigDecimal.valueOf(400.00));
		dto.setLunchArrangement("1-hour lunch break");
		dto.setParkingOption("Free parking");
		dto.setRatePerMile(BigDecimal.valueOf(0.50));

		Set<ConstraintViolation<JobRequestDto>> violations = validator.validate(dto);
		assertTrue(violations.isEmpty(), "Expected no validation errors");
	}

	@Test
	public void testJobDateIsNull() {
		JobRequestDto dto = new JobRequestDto();
		dto.setJobDate(null);
		dto.setJobStartTime(LocalTime.of(9, 0));
		dto.setJobEndTime(LocalTime.of(17, 0));

		Set<ConstraintViolation<JobRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Job date is required", violations.iterator().next().getMessage());
	}

	@Test
	public void testJobStartTimeIsNull() {
		JobRequestDto dto = new JobRequestDto();
		dto.setJobDate(LocalDate.now());
		dto.setJobStartTime(null);
		dto.setJobEndTime(LocalTime.of(17, 0));

		Set<ConstraintViolation<JobRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Job start time is required", violations.iterator().next().getMessage());
	}

	@Test
	public void testJobEndTimeIsNull() {
		JobRequestDto dto = new JobRequestDto();
		dto.setJobDate(LocalDate.now());
		dto.setJobStartTime(LocalTime.of(9, 0));
		dto.setJobEndTime(null);

		Set<ConstraintViolation<JobRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Job end time is required", violations.iterator().next().getMessage());
	}

	@Test
	public void testInvalidHourlyRate() {
		JobRequestDto dto = new JobRequestDto();
		dto.setJobDate(LocalDate.now());
		dto.setJobStartTime(LocalTime.of(9, 0));
		dto.setJobEndTime(LocalTime.of(17, 0));
		dto.setHourlyRate(BigDecimal.valueOf(-10.00)); // Invalid

		Set<ConstraintViolation<JobRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Hourly rate must be greater than 0", violations.iterator().next().getMessage());
	}

	@Test
	public void testInvalidTotalWorkHour() {
		JobRequestDto dto = new JobRequestDto();
		dto.setJobDate(LocalDate.now());
		dto.setJobStartTime(LocalTime.of(9, 0));
		dto.setJobEndTime(LocalTime.of(17, 0));
		dto.setTotalWorkHour(BigDecimal.valueOf(200.003)); // Invalid, exceeds limit

		Set<ConstraintViolation<JobRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
	}
}
