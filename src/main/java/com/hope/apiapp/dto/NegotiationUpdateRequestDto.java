package com.hope.apiapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;

public class NegotiationUpdateRequestDto {

	@DecimalMin(value = "0.0", inclusive = false, message = "Counter hourly rate must be greater than 0")
	private BigDecimal counterHourlyRate;

	@DecimalMin(value = "0.0", inclusive = false, message = "Counter total paid must be greater than 0")
	private BigDecimal counterTotalPaid;

	private Long jobId;

	private String mode;

	private String status;

	private LocalDateTime updatedAt;

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

}