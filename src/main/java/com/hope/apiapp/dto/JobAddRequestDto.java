{\rtf1\ansi\ansicpg1252\cocoartf2709
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww16920\viewh12020\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 package com.hope.apiapp.dto;\
\
import javax.validation.constraints.*;\
import java.math.BigDecimal;\
import java.time.LocalDate;\
import java.time.LocalTime;\
\
public class JobAddRequestDto \{\
\
    private String description; // Optional field, no constraints\
\
    private Integer pharmacyGroupId; // Optional field, no constraints\
\
    private Integer pharmacyBranchId; // Optional field, no constraints\
\
    @NotNull(message = "Job date is required")\
    private LocalDate jobDate;\
\
    @NotNull(message = "Job start time is required")\
    private LocalTime jobStartTime;\
\
    @NotNull(message = "Job end time is required")\
    private LocalTime jobEndTime;\
\
    @DecimalMin(value = "0.0", inclusive = false, message = "Hourly rate must be greater than 0")\
    @Digits(integer = 8, fraction = 2, message = "Hourly rate must be a valid decimal number with up to 2 decimal places")\
    private BigDecimal hourlyRate; // Optional, but validated if provided\
\
    @DecimalMin(value = "0.0", inclusive = false, message = "Total work hour must be greater than 0")\
    @Digits(integer = 3, fraction = 2, message = "Total work hour must be a valid decimal number with up to 2 decimal places")\
    private BigDecimal totalWorkHour; // Optional, but validated if provided\
\
    @DecimalMin(value = "0.0", inclusive = false, message = "Total paid must be greater than 0")\
    @Digits(integer = 8, fraction = 2, message = "Total paid must be a valid decimal number with up to 2 decimal places")\
    private BigDecimal totalPaid; // Optional, but validated if provided\
\
    @Size(max = 255, message = "Lunch arrangement must not exceed 255 characters")\
    private String lunchArrangement; // Optional field\
\
    @Size(max = 255, message = "Parking option must not exceed 255 characters")\
    private String parkingOption; // Optional field\
\
    @DecimalMin(value = "0.0", inclusive = false, message = "Rate per mile must be greater than 0")\
    @Digits(integer = 3, fraction = 2, message = "Rate per mile must be a valid decimal number with up to 2 decimal places")\
    private BigDecimal ratePerMile; // Optional, but validated if provided\
\
    // Getters and setters\
    // ...\
\}\
}