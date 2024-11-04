{\rtf1\ansi\ansicpg1252\cocoartf2709
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww31500\viewh14560\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 package com.hope.apiapp.dto;\
\
import javax.validation.constraints.Email;\
import javax.validation.constraints.NotBlank;\
import javax.validation.constraints.Pattern;\
import javax.validation.constraints.Size;\
\
public class PharmacistUpdateRequestDto \{\
\
    @NotBlank(message = "First name is required")\
    @Size(max = 50, message = "First name must not exceed 50 characters")\
    private String firstName;\
\
    @NotBlank(message = "Last name is required")\
    @Size(max = 50, message = "Last name must not exceed 50 characters")\
    private String lastName;\
\
    @NotBlank(message = "Mobile number is required")\
    @Size(max = 20, message = "Mobile number must not exceed 20 characters")\
    @Pattern(regexp = "^[+]?[0-9]*$", message = "Mobile number must contain only digits and optional leading '+'")\
    private String mobile;\
\
    @Size(max = 50, message = "Address 1 must not exceed 50 characters")\
    private String address1;\
\
    @Size(max = 50, message = "Address 2 must not exceed 50 characters")\
    private String address2;\
\
    @Size(max = 10, message = "Postal code must not exceed 10 characters")\
    private String postalCode;\
\
    @Size(max = 20, message = "Status must not exceed 20 characters")\
    private String status;\
\
    // Getters and Setters\
    // ...\
\}\
}