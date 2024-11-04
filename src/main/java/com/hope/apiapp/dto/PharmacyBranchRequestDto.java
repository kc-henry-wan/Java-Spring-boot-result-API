{\rtf1\ansi\ansicpg1252\cocoartf2709
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\froman\fcharset0 Times-Roman;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww31500\viewh14560\viewkind0
\deftab720
\pard\pardeftab720\partightenfactor0

\f0\fs24 \cf0 \expnd0\expndtw0\kerning0
package com.hope.apiapp.dto;\
\
import javax.validation.constraints.NotBlank;\
import javax.validation.constraints.Size;\
\
public class PharmacyBranchRequestDto \{\
\
    @NotBlank(message = "Branch name is required")\
    @Size(max = 255, message = "Branch name must not exceed 255 characters")\
    private String branchName;\
\
    @NotBlank(message = "Address 1 is required")\
    @Size(max = 50, message = "Address 1 must not exceed 50 characters")\
    private String address1;\
\
    @Size(max = 50, message = "Address 2 must not exceed 50 characters")\
    private String address2;\
\
    @NotBlank(message = "Postal code is required")\
    @Size(max = 10, message = "Postal code must not exceed 10 characters")\
    private String postalCode;\
\
    // Getters and setters\
\
    public String getBranchName() \{\
        return branchName;\
    \}\
\
    public void setBranchName(String branchName) \{\
        this.branchName = branchName;\
    \}\
\
    public String getAddress1() \{\
        return address1;\
    \}\
\
    public void setAddress1(String address1) \{\
        this.address1 = address1;\
    \}\
\
    public String getAddress2() \{\
        return address2;\
    \}\
\
    public void setAddress2(String address2) \{\
        this.address2 = address2;\
    \}\
\
    public String getPostalCode() \{\
        return postalCode;\
    \}\
\
    public void setPostalCode(String postalCode) \{\
        this.postalCode = postalCode;\
    \}\
\}\
}