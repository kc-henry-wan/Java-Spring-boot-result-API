{\rtf1\ansi\ansicpg1252\cocoartf2709
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww16920\viewh12020\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 package com.example.dto;\
\
import static org.junit.jupiter.api.Assertions.assertEquals;\
import static org.junit.jupiter.api.Assertions.assertTrue;\
\
import java.math.BigDecimal;\
import java.time.LocalDate;\
import java.time.LocalTime;\
import java.util.Set;\
import javax.validation.ConstraintViolation;\
import javax.validation.Validation;\
import javax.validation.Validator;\
import javax.validation.ValidatorFactory;\
\
import org.junit.jupiter.api.BeforeAll;\
import org.junit.jupiter.api.Test;\
\
public class JobAddRequestDtoTest \{\
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
    public void testValidJobAddRequestDto() \{\
        JobAddRequestDto dto = new JobAddRequestDto();\
        dto.setJobDate(LocalDate.now());\
        dto.setJobStartTime(LocalTime.of(9, 0));\
        dto.setJobEndTime(LocalTime.of(17, 0));\
        dto.setHourlyRate(BigDecimal.valueOf(50.00));\
        dto.setTotalWorkHour(BigDecimal.valueOf(8.00));\
        dto.setTotalPaid(BigDecimal.valueOf(400.00));\
        dto.setLunchArrangement("1-hour lunch break");\
        dto.setParkingOption("Free parking");\
        dto.setRatePerMile(BigDecimal.valueOf(0.50));\
\
        Set<ConstraintViolation<JobAddRequestDto>> violations = validator.validate(dto);\
        assertTrue(violations.isEmpty(), "Expected no validation errors");\
    \}\
\
    @Test\
    public void testJobDateIsNull() \{\
        JobAddRequestDto dto = new JobAddRequestDto();\
        dto.setJobDate(null);\
        dto.setJobStartTime(LocalTime.of(9, 0));\
        dto.setJobEndTime(LocalTime.of(17, 0));\
\
        Set<ConstraintViolation<JobAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Job date is required", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testJobStartTimeIsNull() \{\
        JobAddRequestDto dto = new JobAddRequestDto();\
        dto.setJobDate(LocalDate.now());\
        dto.setJobStartTime(null);\
        dto.setJobEndTime(LocalTime.of(17, 0));\
\
        Set<ConstraintViolation<JobAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Job start time is required", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testJobEndTimeIsNull() \{\
        JobAddRequestDto dto = new JobAddRequestDto();\
        dto.setJobDate(LocalDate.now());\
        dto.setJobStartTime(LocalTime.of(9, 0));\
        dto.setJobEndTime(null);\
\
        Set<ConstraintViolation<JobAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Job end time is required", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testInvalidHourlyRate() \{\
        JobAddRequestDto dto = new JobAddRequestDto();\
        dto.setJobDate(LocalDate.now());\
        dto.setJobStartTime(LocalTime.of(9, 0));\
        dto.setJobEndTime(LocalTime.of(17, 0));\
        dto.setHourlyRate(BigDecimal.valueOf(-10.00)); // Invalid\
\
        Set<ConstraintViolation<JobAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
        assertEquals("Hourly rate must be greater than 0", violations.iterator().next().getMessage());\
    \}\
\
    @Test\
    public void testInvalidTotalWorkHour() \{\
        JobAddRequestDto dto = new JobAddRequestDto();\
        dto.setJobDate(LocalDate.now());\
        dto.setJobStartTime(LocalTime.of(9, 0));\
        dto.setJobEndTime(LocalTime.of(17, 0));\
        dto.setTotalWorkHour(BigDecimal.valueOf(100.00)); // Invalid, exceeds limit\
\
        Set<ConstraintViolation<JobAddRequestDto>> violations = validator.validate(dto);\
        assertEquals(1, violations.size(), "Expected one validation error");\
    \}\
\}\
}