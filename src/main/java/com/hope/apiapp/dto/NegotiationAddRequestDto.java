package com.hope.apiapp.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NegotiationAddRequestDto {

	@Size(max = 2000, message = "Reason must not exceed 2000 characters")
	private String reason;

	@NotNull(message = "Job ID is required")
	private Integer jobId;

	@NotNull(message = "Pharmacist ID is required")
	private Integer pharmacistId;

	@DecimalMin(value = "0.0", inclusive = false, message = "Original hourly rate must be greater than 0")
	private BigDecimal originalHourlyRate;

	@DecimalMin(value = "0.0", inclusive = false, message = "Original total paid must be greater than 0")
	private BigDecimal originalTotalPaid;

	@DecimalMin(value = "0.0", inclusive = false, message = "Purposed hourly rate must be greater than 0")
	private BigDecimal purposedHourlyRate;

	@DecimalMin(value = "0.0", inclusive = false, message = "Purposed total paid must be greater than 0")
	private BigDecimal purposedTotalPaid;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public Integer getPharmacistId() {
		return pharmacistId;
	}

	public void setPharmacistId(Integer pharmacistId) {
		this.pharmacistId = pharmacistId;
	}

	public BigDecimal getOriginalHourlyRate() {
		return originalHourlyRate;
	}

	public void setOriginalHourlyRate(BigDecimal originalHourlyRate) {
		this.originalHourlyRate = originalHourlyRate;
	}

	public BigDecimal getOriginalTotalPaid() {
		return originalTotalPaid;
	}

	public void setOriginalTotalPaid(BigDecimal originalTotalPaid) {
		this.originalTotalPaid = originalTotalPaid;
	}

	public BigDecimal getPurposedHourlyRate() {
		return purposedHourlyRate;
	}

	public void setPurposedHourlyRate(BigDecimal purposedHourlyRate) {
		this.purposedHourlyRate = purposedHourlyRate;
	}

	public BigDecimal getPurposedTotalPaid() {
		return purposedTotalPaid;
	}

	public void setPurposedTotalPaid(BigDecimal purposedTotalPaid) {
		this.purposedTotalPaid = purposedTotalPaid;
	}
}
