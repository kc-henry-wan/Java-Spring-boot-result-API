package com.hope.apiapp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_negotiation")
public class Negotiation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long negotiationId;

	@Column(columnDefinition = "TEXT")
	private String reason;

	private Long jobId;

	private Long pharmacistId;

	private BigDecimal originalHourlyRate;
	private BigDecimal originalTotalPaid;
	private BigDecimal purposedHourlyRate;
	private BigDecimal purposedTotalPaid;
	private BigDecimal counterHourlyRate;
	private BigDecimal counterTotalPaid;
	private String status;
	private Boolean deleted = false;

	private Long updatedUserId;

	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();

	public Long getNegotiationId() {
		return negotiationId;
	}

	public void setNegotiationId(Long negotiationId) {
		this.negotiationId = negotiationId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getPharmacistId() {
		return pharmacistId;
	}

	public void setPharmacistId(Long pharmacistId) {
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Long getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(Long updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	// Getters and setters omitted for brevity

}
