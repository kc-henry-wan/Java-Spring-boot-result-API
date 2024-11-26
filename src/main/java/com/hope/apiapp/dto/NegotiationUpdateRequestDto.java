package com.hope.apiapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public class NegotiationUpdateRequestDto {

	@DecimalMin(value = "0.0", inclusive = false, message = "Counter hourly rate must be greater than 0")
	private BigDecimal counterHourlyRate;

	@DecimalMin(value = "0.0", inclusive = false, message = "Counter total paid must be greater than 0")
	private BigDecimal counterTotalPaid;

	private Long jobId;

	@Size(max = 20, message = "Action mode must not exceed 20 characters")
	private String mode;

	@Size(max = 20, message = "New Status must not exceed 20 characters")
	private String status;

	private LocalDateTime updatedAt;
	private LocalDateTime jobUpdatedAt;

	public BigDecimal getCounterHourlyRate() {
		return counterHourlyRate;
	}

	public void setCounterHourlyRate(BigDecimal counterHourlyRate) {
		this.counterHourlyRate = counterHourlyRate;
	}

	public BigDecimal getCounterTotalPaid() {
		return counterTotalPaid;
	}

	public void setCounterTotalPaid(BigDecimal counterTotalPaid) {
		this.counterTotalPaid = counterTotalPaid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public LocalDateTime getJobUpdatedAt() {
		return jobUpdatedAt;
	}

	public void setJobUpdatedAt(LocalDateTime jobUpdatedAt) {
		this.jobUpdatedAt = jobUpdatedAt;
	}

}