{\rtf1\ansi\ansicpg1252\cocoartf2709
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww16920\viewh12020\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 package com.hope.apiapp.dto;\
\
import static org.junit.jupiter.api.Assertions.assertEquals;\
import static org.junit.jupiter.api.Assertions.assertTrue;\
\
import java.util.Set;\
import javax.validation.ConstraintViolation;\
import javax.validation.Validation;\
import javax.validation.Validator;\
import javax.validation.ValidatorFactory;\
\
import org.junit.jupiter.api.BeforeAll;\
import org.junit.jupiter.api.Test;\
\
public class PharmacyBranchRequestDtoTest \{\
\
    private static Validator validator;\
\
    @BeforeAll\
    public static void setUp() \{\
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();\
        validator = factory.getValidator();\
    \}\
\
    @Test\
    public void testValidPharmacyBranchRequestDto() \{\
        PharmacyBranchRequestDto dto = new PharmacyBranchRequestDto();\
        dto.setBranchName("Main Branch");\
        dto.setAddress1("123 Main Street");\
        dto.setAddress2("Suite 456");\
        dto.setPostalCode("12345");\
\
        Set<ConstraintViolation<PharmacyBranchRequestDto>> violations = validator.validate(dto);\
        assertTrue(violations.isEmpty(), "Expected no validation errors");\
    \}\
\
    @Test\
    public void testBranchNameIsBlank() \{\
        PharmacyBranchRequestDto dto = new PharmacyBranchRequestDto();\
        dto.setBranchName("");\
        dto.setAddress1("123 Main Street");\
        dto.setPostalCode("12345");\
\
        Set<ConstraintViolation<PharmacyBranchRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Branch name is required", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testAddress1IsBlank() \{\
        PharmacyBranchRequestDto dto = new PharmacyBranchRequestDto();\
        dto.setBranchName("Main Branch");\
        dto.setAddress1("");\
        dto.setPostalCode("12345");\
\
        Set<ConstraintViolation<PharmacyBranchRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Address 1 is required", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testPostalCodeIsBlank() \{\
        PharmacyBranchRequestDto dto = new PharmacyBranchRequestDto();\
        dto.setBranchName("Main Branch");\
        dto.setAddress1("123 Main Street");\
        dto.setPostalCode("");\
\
        Set<ConstraintViolation<PharmacyBranchRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Postal code is required", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testExceedCharacterLimit() \{\
        PharmacyBranchRequestDto dto = new PharmacyBranchRequestDto();\
        dto.setBranchName("A".repeat(256)); // Exceeding 255 characters\
        dto.setAddress1("B".repeat(51));    // Exceeding 50 characters\
        dto.setPostalCode("C".repeat(11));  // Exceeding 10 characters\
\
        Set<ConstraintViolation<PharmacyBranchRequestDto>> violations = validator.validate(dto);\
        assertEquals(3, violations.size(), "Expected three validation errors");\
    \}\
\}\
}