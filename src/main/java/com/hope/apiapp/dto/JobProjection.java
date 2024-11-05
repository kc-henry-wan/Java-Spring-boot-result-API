package com.hope.apiapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface JobProjection {

	Long getJobId();

	String getJobRef();

	LocalDate getJobDate();

	BigDecimal getHourlyRate();

	BigDecimal getTotalWorkHour();

	BigDecimal getTotalPaid();

	String getLunchArrangement();

	String getParkingOption();

	BigDecimal getRatePerMile();

	String getStatus();
//
//	Long getGroupCode();
//
	// PharmacyBranch fields

	Long getPharmacyBranchId();

	String getBranchName();

	String getBranchAddress1();

	String getBranchAddress2();

	String getBranchPostalCode();
//
//	BigDecimal getBranchLatitude();
//
//	BigDecimal getBranchLongitude();
//
//	BigDecimal getDistance();

	// Pharmacist fields
	Long getPharmacistId();

	String getPharmacistFirstName();

	String getPharmacistLastName();

}
