package com.hope.apiapp.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

public class NegotiationUpdateRequestDto {

	@DecimalMin(value = "0.0", inclusive = false, message = "Counter hourly rate must be greater than 0")
	private BigDecimal counterHourlyRate;

	@DecimalMin(value = "0.0", inclusive = false, message = "Counter total paid must be greater than 0")
	private BigDecimal counterTotalPaid;

	@Size(max = 1, message = "Status code must be a single character")
	private String statusCode;

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

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}