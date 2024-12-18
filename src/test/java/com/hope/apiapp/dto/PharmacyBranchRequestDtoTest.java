package com.hope.apiapp.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hope.apiapp.util.CommonUtil;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PharmacyBranchRequestDtoTest {

	private static Validator validator;

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

//	@Test
//	public void testUtil() {
//
//		JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("bf6403129c2347dd836c2b806b9a9472");
//
//		JOpenCageForwardRequest request = new JOpenCageForwardRequest("10%20Downing%20Street,%20London");
//		request.setMinConfidence(1);
//		request.setRestrictToCountryCode("gb");
//		request.setNoAnnotations(false);
//		request.setNoDedupe(true);
//		JOpenCageResponse response = jOpenCageGeocoder.forward(request);
//		JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result
//		logger.info(firstResultLatLng.getLat().toString() + "," + firstResultLatLng.getLng().toString());
//
//	}

	@Test
	public void testValidPharmacyBranchRequestDto() {
		PharmacyBranchRequestDto dto = new PharmacyBranchRequestDto();
		dto.setBranchName("Main Branch");
		dto.setAddress1("123 Main Street");
		dto.setAddress2("Suite 456");
		dto.setPostalCode("12345");

		Set<ConstraintViolation<PharmacyBranchRequestDto>> violations = validator.validate(dto);
		assertTrue(violations.isEmpty(), "Expected no validation errors");
	}

	@Test
	public void testBranchNameIsBlank() {
		PharmacyBranchRequestDto dto = new PharmacyBranchRequestDto();
		dto.setBranchName("");
		dto.setAddress1("123 Main Street");
		dto.setPostalCode("12345");

		Set<ConstraintViolation<PharmacyBranchRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Branch name is required", violations.iterator().next().getMessage());
	}

	@Test
	public void testAddress1IsBlank() {
		PharmacyBranchRequestDto dto = new PharmacyBranchRequestDto();
		dto.setBranchName("Main Branch");
		dto.setAddress1("");
		dto.setPostalCode("12345");

		Set<ConstraintViolation<PharmacyBranchRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Address 1 is required", violations.iterator().next().getMessage());
	}

	@Test
	public void testPostalCodeIsBlank() {
		PharmacyBranchRequestDto dto = new PharmacyBranchRequestDto();
		dto.setBranchName("Main Branch");
		dto.setAddress1("123 Main Street");
		dto.setPostalCode("");

		Set<ConstraintViolation<PharmacyBranchRequestDto>> violations = validator.validate(dto);
		assertEquals(1, violations.size(), "Expected one validation error");
		assertEquals("Postal code is required", violations.iterator().next().getMessage());
	}

	@Test
	public void testExceedCharacterLimit() {
		PharmacyBranchRequestDto dto = new PharmacyBranchRequestDto();
		dto.setBranchName("A".repeat(256)); // Exceeding 255 characters
		dto.setAddress1("B".repeat(51)); // Exceeding 50 characters
		dto.setPostalCode("C".repeat(11)); // Exceeding 10 characters

		Set<ConstraintViolation<PharmacyBranchRequestDto>> violations = validator.validate(dto);
		assertEquals(3, violations.size(), "Expected three validation errors");
	}
}
