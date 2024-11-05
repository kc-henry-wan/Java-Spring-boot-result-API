package com.hope.apiapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class JobRequestDto {

	private String description; // Optional field, no constraints\

	private Long pharmacyGroupId; // Optional field, no constraints\

	private Long pharmacyBranchId; // Optional field, no constraints\

	@NotNull(message = "Job date is required")
	private LocalDate jobDate;

	@NotNull(message = "Job start time is required")
	private LocalTime jobStartTime;

	@NotNull(message = "Job end time is required")
	private LocalTime jobEndTime;

	@DecimalMin(value = "0.0", inclusive = false, message = "Hourly rate must be greater than 0")
	@Digits(integer = 8, fraction = 2, message = "Hourly rate must be a valid decimal number with up to 2 decimal places")
	private BigDecimal hourlyRate; // Optional, but validated if provided\

	@DecimalMin(value = "0.0", inclusive = false, message = "Total work hour must be greater than 0")
	@Digits(integer = 3, fraction = 2, message = "Total work hour must be a valid decimal number with up to 2 decimal places")
	private BigDecimal totalWorkHour; // Optional, but validated if provided\

	@DecimalMin(value = "0.0", inclusive = false, message = "Total paid must be greater than 0")
	@Digits(integer = 8, fraction = 2, message = "Total paid must be a valid decimal number with up to 2 decimal places")
	private BigDecimal totalPaid; // Optional, but validated if provided\

	@Size(max = 255, message = "Lunch arrangement must not exceed 255 characters")
	private String lunchArrangement; // Optional field\

	@Size(max = 255, message = "Parking option must not exceed 255 characters")
	private String parkingOption; // Optional field\

	@DecimalMin(value = "0.0", inclusive = false, message = "Rate per mile must be greater than 0")
	@Digits(integer = 3, fraction = 2, message = "Rate per mile must be a valid decimal number with up to 2 decimal places")
	private BigDecimal ratePerMile; // Optional, but validated if provided\

	private LocalDateTime updatedAt;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getPharmacyGroupId() {
		return pharmacyGroupId;
	}

	public void setPharmacyGroupId(Long pharmacyGroupId) {
		this.pharmacyGroupId = pharmacyGroupId;
	}

	public Long getPharmacyBranchId() {
		return pharmacyBranchId;
	}

	public void setPharmacyBranchId(Long pharmacyBranchId) {
		this.pharmacyBranchId = pharmacyBranchId;
	}

	public LocalDate getJobDate() {
		return jobDate;
	}

	public void setJobDate(LocalDate jobDate) {
		this.jobDate = jobDate;
	}

	public LocalTime getJobStartTime() {
		return jobStartTime;
	}

	public void setJobStartTime(LocalTime jobStartTime) {
		this.jobStartTime = jobStartTime;
	}

	public LocalTime getJobEndTime() {
		return jobEndTime;
	}

	public void setJobEndTime(LocalTime jobEndTime) {
		this.jobEndTime = jobEndTime;
	}

	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public BigDecimal getTotalWorkHour() {
		return totalWorkHour;
	}

	public void setTotalWorkHour(BigDecimal totalWorkHour) {
		this.totalWorkHour = totalWorkHour;
	}

	public BigDecimal getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public String getLunchArrangement() {
		return lunchArrangement;
	}

	public void setLunchArrangement(String lunchArrangement) {
		this.lunchArrangement = lunchArrangement;
	}

	public String getParkingOption() {
		return parkingOption;
	}

	public void setParkingOption(String parkingOption) {
		this.parkingOption = parkingOption;
	}

	public BigDecimal getRatePerMile() {
		return ratePerMile;
	}

	public void setRatePerMile(BigDecimal ratePerMile) {
		this.ratePerMile = ratePerMile;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
