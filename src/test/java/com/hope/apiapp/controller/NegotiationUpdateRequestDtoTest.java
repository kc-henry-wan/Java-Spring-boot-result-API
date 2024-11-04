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
public class NegotiationUpdateRequestDtoTest \{\
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
    public void testValidNegotiationUpdateRequestDto() \{\
        NegotiationUpdateRequestDto dto = new NegotiationUpdateRequestDto();\
        dto.setCounterHourlyRate(new BigDecimal("52.50"));\
        dto.setCounterTotalPaid(new BigDecimal("420.00"));\
        dto.setStatusCode("A");\
\
        Set<ConstraintViolation<NegotiationUpdateRequestDto>> violations = validator.validate(dto);\
        assertTrue(violations.isEmpty(), "Expected no validation errors");\
\
    @Test\
    public void testStatusCodeExceedsMaxLength() \{\
        NegotiationUpdateRequestDto dto = new NegotiationUpdateRequestDto();\
        dto.setJobId(1);\
        dto.setPharmacistId(1);\
        dto.setOriginalHourlyRate(new BigDecimal("50.00"));\
        dto.setStatusCode("AB");\
\
        Set<ConstraintViolation<NegotiationUpdateRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Status code must be a single character", violations.iterator().next().getMessage());\
    \}\
\}\
}