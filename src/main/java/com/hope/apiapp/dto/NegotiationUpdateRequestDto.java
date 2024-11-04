{\rtf1\ansi\ansicpg1252\cocoartf2709
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww16920\viewh12020\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 package com.hope.apiapp.dto;\
\
import javax.validation.constraints.DecimalMin;\
import javax.validation.constraints.NotNull;\
import javax.validation.constraints.Size;\
import java.math.BigDecimal;\
\
public class NegotiationUpdateRequestDto \{\
\
    @DecimalMin(value = "0.0", inclusive = false, message = "Counter hourly rate must be greater than 0")\
    private BigDecimal counterHourlyRate;\
\
    @DecimalMin(value = "0.0", inclusive = false, message = "Counter total paid must be greater than 0")\
    private BigDecimal counterTotalPaid;\
\
    @Size(max = 1, message = "Status code must be a single character")\
    private String statusCode;\
\
    // Getters and Setters\
    // ...\
\}\
}