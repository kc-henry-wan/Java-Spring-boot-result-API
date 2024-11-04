{\rtf1\ansi\ansicpg1252\cocoartf2709
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;\f1\froman\fcharset0 Times-Roman;}
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
public class JobUpdateRequestDto \{\
    
\f1 \expnd0\expndtw0\kerning0
\
\pard\pardeftab720\partightenfactor0
\cf0     @NotBlank(message = "
\f0 \kerning1\expnd0\expndtw0 New Status
\f1 \expnd0\expndtw0\kerning0
 is required")
\f0 \kerning1\expnd0\expndtw0 \
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0     @Size(max = 20, message = \'93New Status must not exceed 20 characters")\
    private String status; \
\
    // Getters and setters\
    // ...\
\}\
}