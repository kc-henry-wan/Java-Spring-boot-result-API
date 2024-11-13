package com.hope.apiapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface NegotiationProjection {

	Long getNegotiationId();

	Long getJobId();

	String getJobRef();

	LocalDate getJobDate();

	LocalTime getJobStartTime();

	LocalTime getJobEndTime();

	BigDecimal getTotalWorkHour();

	String getLunchArrangement();

	String getParkingOption();

	BigDecimal getRatePerMile();

	BigDecimal getOriginalHourlyRate();

	BigDecimal getOriginalTotalPaid();

	BigDecimal getPurposedHourlyRate();

	BigDecimal getPurposedTotalPaid();

	BigDecimal getCounterHourlyRate();

	BigDecimal getCounterTotalPaid();

	String getStatus();

	String getReason();

	Long getUpdatedUserId();

	LocalDateTime getUpdatedAt();

	String getBranchName();

	String getBranchAddress1();

	String getBranchAddress2();

	String getBranchPostalCode();

	String getPharmacistFirstName();

	String getPharmacistLastName();
}
