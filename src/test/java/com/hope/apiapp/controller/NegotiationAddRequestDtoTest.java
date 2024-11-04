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
import java.math.BigDecimal;\
import java.util.Set;\
import javax.validation.ConstraintViolation;\
import javax.validation.Validation;\
import javax.validation.Validator;\
import javax.validation.ValidatorFactory;\
\
import org.junit.jupiter.api.BeforeAll;\
import org.junit.jupiter.api.Test;\
\
public class NegotiationAddRequestDtoTest \{\
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
    public void testValidNegotiationAddRequestDto() \{\
        NegotiationAddRequestDto dto = new NegotiationAddRequestDto();\
        dto.setJobId(1);\
        dto.setPharmacistId(1);\
        dto.setOriginalHourlyRate(new BigDecimal("50.00"));\
        dto.setOriginalTotalPaid(new BigDecimal("400.00"));\
        dto.setPurposedHourlyRate(new BigDecimal("55.00"));\
        dto.setPurposedTotalPaid(new BigDecimal("440.00"));\
        dto.setCounterHourlyRate(new BigDecimal("52.50"));\
        dto.setCounterTotalPaid(new BigDecimal("420.00"));\
        dto.setStatusCode("A");\
\
        Set<ConstraintViolation<NegotiationAddRequestDto>> violations = validator.validate(dto);\
        assertTrue(violations.isEmpty(), "Expected no validation errors");\
    \}\
\
    @Test\
    public void testJobIdIsNull() \{\
        NegotiationAddRequestDto dto = new NegotiationAddRequestDto();\
        dto.setJobId(null);\
        dto.setPharmacistId(1);\
        dto.setOriginalHourlyRate(new BigDecimal("50.00"));\
\
        Set<ConstraintViolation<NegotiationAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Job ID is required", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testPharmacistIdIsNull() \{\
        NegotiationAddRequestDto dto = new NegotiationAddRequestDto();\
        dto.setJobId(1);\
        dto.setPharmacistId(null);\
        dto.setOriginalHourlyRate(new BigDecimal("50.00"));\
\
        Set<ConstraintViolation<NegotiationAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Pharmacist ID is required", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testOriginalHourlyRateIsZero() \{\
        NegotiationAddRequestDto dto = new NegotiationAddRequestDto();\
        dto.setJobId(1);\
        dto.setPharmacistId(1);\
        dto.setOriginalHourlyRate(new BigDecimal("0.00"));\
\
        Set<ConstraintViolation<NegotiationAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Original hourly rate must be greater than 0", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testStatusCodeExceedsMaxLength() \{\
        NegotiationAddRequestDto dto = new NegotiationAddRequestDto();\
        dto.setJobId(1);\
        dto.setPharmacistId(1);\
        dto.setOriginalHourlyRate(new BigDecimal("50.00"));\
        dto.setStatusCode("AB");\
\
        Set<ConstraintViolation<NegotiationAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Status code must be a single character", violations.iterator().next().getMessage());\
    \}\
\}\
}