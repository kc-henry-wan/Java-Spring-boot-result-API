{\rtf1\ansi\ansicpg1252\cocoartf2709
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww31500\viewh14560\viewkind0
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
public class PharmacistAddRequestDtoTest \{\
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
    public void testValidPharmacistAddRequestDto() \{\
        PharmacistAddRequestDto dto = new PharmacistAddRequestDto();\
        dto.setPassword("StrongPassword123!");\
        dto.setEmail("example@example.com");\
        dto.setFirstName("John");\
        dto.setLastName("Doe");\
        dto.setMobile("+1234567890");\
        dto.setAddress1("123 Main Street");\
        dto.setAddress2("Suite 456");\
        dto.setPostalCode("12345");\
        dto.setStatus("Active");\
\
        Set<ConstraintViolation<PharmacistAddRequestDto>> violations = validator.validate(dto);\
        assertTrue(violations.isEmpty(), "Expected no validation errors");\
    \}\
\
    @Test\
    public void testPasswordIsBlank() \{\
        PharmacistAddRequestDto dto = new PharmacistAddRequestDto();\
        dto.setPassword("");\
        dto.setFirstName("John");\
        dto.setLastName("Doe");\
        dto.setMobile("+1234567890");\
\
        Set<ConstraintViolation<PharmacistAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Password is required", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testEmailInvalidFormat() \{\
        PharmacistAddRequestDto dto = new PharmacistAddRequestDto();\
        dto.setPassword("StrongPassword123!");\
        dto.setEmail("invalid-email");\
        dto.setFirstName("John");\
        dto.setLastName("Doe");\
        dto.setMobile("+1234567890");\
\
        Set<ConstraintViolation<PharmacistAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Invalid email format", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testFirstNameIsBlank() \{\
        PharmacistAddRequestDto dto = new PharmacistAddRequestDto();\
        dto.setPassword("StrongPassword123!");\
        dto.setFirstName("");\
        dto.setLastName("Doe");\
        dto.setMobile("+1234567890");\
\
        Set<ConstraintViolation<PharmacistAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("First name is required", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testMobileNumberInvalidFormat() \{\
        PharmacistAddRequestDto dto = new PharmacistAddRequestDto();\
        dto.setPassword("StrongPassword123!");\
        dto.setFirstName("John");\
        dto.setLastName("Doe");\
        dto.setMobile("123-456-7890");  // Invalid format\
\
        Set<ConstraintViolation<PharmacistAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Mobile number must contain only digits and optional leading '+'", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testStatusExceedsMaxLength() \{\
        PharmacistAddRequestDto dto = new PharmacistAddRequestDto();\
        dto.setPassword("StrongPassword123!");\
        dto.setFirstName("John");\
        dto.setLastName("Doe");\
        dto.setMobile("+1234567890");\
        dto.setStatus("ThisStatusIsTooLongForTheField");\
\
        Set<ConstraintViolation<PharmacistAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Status must not exceed 20 characters", violations.iterator().next().getMessage());\
    \}\
\}\
}