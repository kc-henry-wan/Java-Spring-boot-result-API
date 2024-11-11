package com.hope.apiapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface JobProjection {

	Long getJobId();

	String getJobRef();

	LocalDate getJobDate();

	LocalTime getJobStartTime();

	LocalTime getJobEndTime();

	BigDecimal getHourlyRate();

	BigDecimal getTotalWorkHour();

	BigDecimal getTotalPaid();

	String getLunchArrangement();

	String getParkingOption();

	BigDecimal getRatePerMile();

	String getStatus();

	String getDescription();

	LocalDateTime getUpdatedAt();

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
