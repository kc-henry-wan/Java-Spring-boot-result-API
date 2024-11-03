package com.hope.apiapp.dto;

public interface JobProjection {

	Long getJobId();

	String getJobRef();

	java.sql.Date getJobDate();

//	BigDecimal getHourlyRate();
//
//	BigDecimal getTotalWorkHour();
//
//	BigDecimal getTotalPaid();
//
//	String getLunchArrangement();
//
//	String getParkingOption();
//
//	BigDecimal getRatePerMile();
//
//	String getStatus();
//
//	Long getGroupCode();
//
//	// Pharmacist fields
//	String getPharmacistFirstName();
//
//	String getPharmacistLastName();

	// PharmacyBranch fields
	String getBranchName();

//	String getBranchAddress1();
//
//	String getBranchAddress2();
//
//	String getBranchPostalCode();
//
//	BigDecimal getBranchLatitude();
//
//	BigDecimal getBranchLongitude();
//
//	BigDecimal getDistance();

}
